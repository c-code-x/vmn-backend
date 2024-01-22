package com.vdc.vmnbackend.utility;

import com.vdc.vmnbackend.enumerators.Roles;

import javax.management.relation.Role;

public class CommonConstants {
    public static final String LOGIN_SUCCESSFUL = "Login Successful!";
    public static final String MAIL_SUCCESS = "Email has been sent successfully!";
    public static final String MAIL_ERROR = "Some Error Occurred while sending mail!";
    public static final String SECURITY_SCOPE_PREFIX = "SCOPE_";
    public static final String USER_CREATED_SUCCESSFULLY = "User Created!";
    public static final String INVITATION_EXPIRED = "Invitation Expired!";
    public static final String INVITATION_ACCEPTED = "Invitation Already Accepted!";
    public static final String INVITATION_INVALID = "Invalid Invitation!";
    public static final String INVITATION_PENDING = "Invitation yet pending!";
    public static final String INVITATION_INVALID_ROLE = "Invitation cant be sent to the Role!";
    public static final String INVITATION_TO_SAMEROLE = "Invitation cant be sent to the same Role!";
    public static final String USER_EXITS = "User Already Exists!";
    public static final String TOKEN_RENEWED = "Token Renewed";

    public static final String SUPER_ADMIN_ROLE = SECURITY_SCOPE_PREFIX + Roles.SUPER_ADMIN;
    public static final String ADMIN_ROLE = SECURITY_SCOPE_PREFIX + Roles.ADMIN;
    public static final String USER_ROLE = SECURITY_SCOPE_PREFIX+ Roles.USER;

    public static final String INVITE_URLS = "/invite/**";

    public static final String INVITATION_ALREADY_SENT = "Invitation Already Sent!";
    public static final String UNAUTHORISED_OPERATION = "Unauthorised Operation!";
}
