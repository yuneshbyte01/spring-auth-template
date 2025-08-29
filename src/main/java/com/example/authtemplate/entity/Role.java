package com.example.authtemplate.entity;

/**
 * Enumeration of user roles within the system.
 *
 * <p>Each user is assigned exactly one role, which determines
 * their level of access and permissions in the application.</p>
 */
public enum Role {

    /** Standard user with limited access */
    ROLE_USER,

    /** Administrator with full system privileges */
    ROLE_ADMIN
}
