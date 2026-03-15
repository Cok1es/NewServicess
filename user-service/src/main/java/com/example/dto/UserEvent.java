package com.example.dto;

public record UserEvent(String email, String operation) {} // operation: "CREATE" или "DELETE"
