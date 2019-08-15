package com.lx.core.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

/**
 *
 * @author lx.ds
 */
public class LxJwtAuthProvider implements ITokenProvider {

    public static final String AUTHENTICATION_SCHEME = "Bearer";
    public static final int SESSION_DURATION_IN_MINUTES = 240;
    
    private RsaJsonWebKey rsaJsonWebKey;
    private String prefix;

    public LxJwtAuthProvider() throws Exception {
        try {
            // Generate an RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
            rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
            // Give the JWK a Key ID (kid), which is just the polite thing to do
            rsaJsonWebKey.setKeyId("k1");

        } catch (JoseException ex) {
            throw new Exception(ex.getMessage(), ex);
        }
    }
    
    @Override
    public String getPrefix() {
        if (prefix == null) {
            prefix = AUTHENTICATION_SCHEME + " ";
        }
        return prefix;
    }

    @Override
    public IUserIdentity extractToken(String token) throws Exception {
        
        AuthenticatedUser theUserIdentity = null;
        
        token = token.replaceFirst(getPrefix(), "");
    
        // Use JwtConsumerBuilder to construct an appropriate JwtConsumer, which will
        // be used to validate and process the JWT.
        // The specific validation requirements for a JWT are context dependent, however,
        // it typically advisable to require a (reasonable) expiration time, a trusted issuer, and
        // and audience that identifies your system as the intended recipient.
        // If the JWT is encrypted too, you need only provide a decryption key or
        // decryption key resolver to the builder.
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime() // the JWT must have an expiration time
                .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
                .setRequireSubject() // the JWT must have a subject claim
                .setExpectedIssuer("LX Token Provider") // whom the JWT needs to have been issued by
                .setExpectedAudience("Web Client") // to whom the JWT is intended for
                .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
                .setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
                        new AlgorithmConstraints(ConstraintType.WHITELIST, // which is only RS256 here
                                AlgorithmIdentifiers.RSA_USING_SHA256))
                .build(); // create the JwtConsumer instance

        try {            
            //  Validate the JWT and process it to the Claims
            JwtClaims claims = jwtConsumer.processToClaims(token);
            // get user identity
            theUserIdentity = new AuthenticatedUser(claims.getSubject(), claims.getStringClaimValue("role"), (Map<String, String>)claims.getClaimValue("permissions"));
            theUserIdentity.setDisplayName(claims.getStringClaimValue("displayName"));
            theUserIdentity.setCompanyId(claims.getStringClaimValue("company"));
            theUserIdentity.setDepartmentId(claims.getStringClaimValue("department"));
            theUserIdentity.setLocale(claims.getStringClaimValue("locale"));
            
        } catch (InvalidJwtException e) {
            // InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
            //theUserIdentity = new AuthenticatedUser();
            Logger.getLogger(LxJwtAuthProvider.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return theUserIdentity;
    }

    @Override
    public String generateToken(IUserIdentity user, String locale) throws Exception {

        // Create the Claims, which will be the content of the JWT
        JwtClaims claims = new JwtClaims();
        claims.setIssuer("LX Token Provider");  // who creates the token and signs it
        claims.setAudience("Web Client"); // to whom the token is intended to be sent
        claims.setExpirationTimeMinutesInTheFuture(SESSION_DURATION_IN_MINUTES); // time when the token will expire (10 minutes from now)
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
        claims.setNotBeforeMinutesInThePast(0); // time before which the token is not yet valid (2 minutes ago)
        claims.setSubject(user.getUsername()); // the subject/principal is whom the token is about
        claims.setClaim("displayName", user.getDisplayName()); // additional claims/attributes about the subject can be added
        claims.setClaim("role", user.getGroupId()); // multi-valued claims work too and will end up as a JSON array
        claims.setClaim("company", user.getCompanyId());
        claims.setClaim("department", user.getDepartmentId());
        claims.setClaim("locale", locale);
        claims.setClaim("permissions", user.getPermissions());

        // A JWT is a JWS and/or a JWE with JSON claims as the payload.
        // In this example it is a JWS so we create a JsonWebSignature object.
        JsonWebSignature jws = new JsonWebSignature();

        // The payload of the JWS is JSON content of the JWT Claims
        jws.setPayload(claims.toJson());

        // The JWT is signed using the private key
        jws.setKey(rsaJsonWebKey.getPrivateKey());

        // Set the Key ID (kid) header because it's just the polite thing to do.
        // We only have one key in this example but a using a Key ID helps
        // facilitate a smooth key rollover process
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());

        // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        // Sign the JWS and produce the compact serialization or the complete JWT/JWS
        // representation, which is a string consisting of three dot ('.') separated
        // base64url-encoded parts in the form Header.Payload.Signature
        // If you wanted to encrypt it, you can simply set this jwt as the payload
        // of a JsonWebEncryption object and set the cty (Content Type) header to "jwt".
        return jws.getCompactSerialization();
    }

}
