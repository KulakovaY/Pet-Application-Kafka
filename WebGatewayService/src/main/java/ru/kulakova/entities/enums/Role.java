package ru.kulakova.entities.enums;

public enum Role {
    ADMIN, USER;

    public String getAuthority() {
        return "ROLE_" + name();
    }
}
