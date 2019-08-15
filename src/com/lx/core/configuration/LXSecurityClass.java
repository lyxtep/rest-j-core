package com.lx.core.configuration;

/**
 *
 * @author lx.ds
 */
public class LXSecurityClass {

    public static final String PERMISSION_EMPTY = "00000000000000000000"; // no permission
    public static final String PERMISSION_FULL = "77777777777777777777"; // full permission

    public static final String OFFICIAL = "official"; // all companies
    public static final String RESTRICTED = "restricted"; // the company
    public static final String CONFIDENTIAL = "confidential"; // the department
    public static final String SECRET = "secret"; // owner
    public static final String TOPSECRET = "topsecret"; // system administrator

    public static final int CREATE = 1;
    public static final int READ = 2;
    public static final int UPDATE = 4;
    public static final int DELETE = 8;

    public static final int VALUE_NO_PERMISSION = 0; // no permission (0 0 0)
    public static final int VALUE_DOMAIN_A = 1; // >= 1 the smallest domain (0 0 1)
    public static final int VALUE_DOMAIN_AA = 2; // >= 3 bigger domain (0 1 0, 0 1 1)
    public static final int VALUE_DOMAIN_AAA = 4; // >= 7 the biggest domain (1 0 0, 1 0 1, 1 1 0, 1 1 1)

    public static final int OFFICIAL_CREATE = 19;
    public static final int OFFICIAL_READ = 18;
    public static final int OFFICIAL_UPDATE = 17;
    public static final int OFFICIAL_DELETE = 16;

    public static final int RESTRICTED_CREATE = 15;
    public static final int RESTRICTED_READ = 14;
    public static final int RESTRICTED_UPDATE = 13;
    public static final int RESTRICTED_DELETE = 12;

    public static final int CONFIDENTIAL_CREATE = 11;
    public static final int CONFIDENTIAL_READ = 10;
    public static final int CONFIDENTIAL_UPDATE = 9;
    public static final int CONFIDENTIAL_DELETE = 8;

    public static final int SECRET_CREATE = 7;
    public static final int SECRET_READ = 6;
    public static final int SECRET_UPDATE = 5;
    public static final int SECRET_DELETE = 4;

    public static final int TOPSECRET_CREATE = 3;
    public static final int TOPSECRET_READ = 2;
    public static final int TOPSECRET_UPDATE = 1;
    public static final int TOPSECRET_DELETE = 0;
}
