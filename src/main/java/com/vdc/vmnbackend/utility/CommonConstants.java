package com.vdc.vmnbackend.utility;

import com.vdc.vmnbackend.enumerators.Roles;

import javax.management.relation.Role;

public class CommonConstants {
    public static final String LOGIN_SUCCESSFUL = "Login Successful!";
    private static final String MAIL_SUCCESS = "Email has been sent successfully!";
    private static final String MAIL_ERROR = "Some Error Occurred while sending mail!";
    public static final String SECURITY_SCOPE_PREFIX = "SCOPE_";

    public static final String SUPER_ADMIN_ROLE = SECURITY_SCOPE_PREFIX + Roles.SUPER_ADMIN;
    public static final String ADMIN_ROLE = SECURITY_SCOPE_PREFIX + Roles.ADMIN;
    public static final String USER_ROLE = SECURITY_SCOPE_PREFIX+ Roles.USER;

    public static final String INVITE_URLS = "/invite/**";

}
