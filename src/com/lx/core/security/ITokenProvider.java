package com.lx.core.security;

/**
 *
 * @author lx.ds
 */
public interface ITokenProvider {
    
    String getPrefix();
    
    /**
     * Extract the token from the string
     * @param token
     * @return UserIdentity extracted from the token
     * @throws java.lang.Exception
     */
    IUserIdentity extractToken(String token) throws Exception;
    
    /**
     * Generate a token of the user identity
     * @param userIdentity
     * @return Token generated of the UserIdentity
     * @throws java.lang.Exception
     */
    String generateToken(IUserIdentity userIdentity, String locale) throws Exception;
}
