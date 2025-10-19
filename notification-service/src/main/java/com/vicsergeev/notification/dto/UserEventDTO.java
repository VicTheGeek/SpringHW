package com.vicsergeev.notification.dto;

public record UserEventDTO(String operation, String email, String name) {
    public static final String CREATE = "CREATE";
    public static final String DELETE = "DELETE";
    public static final String UPDATE = "UPDATE";
}


