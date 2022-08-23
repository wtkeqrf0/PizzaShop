package com.PizzaApi.Enums;

public enum Permission {
    USER_BUY("user:buy"),
    USER_EDIT_READ("user:edit&read");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}