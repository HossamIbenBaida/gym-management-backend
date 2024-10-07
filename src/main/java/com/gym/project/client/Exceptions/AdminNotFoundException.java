package com.gym.project.client.Exceptions;

public class AdminNotFoundException extends Exception {
    public AdminNotFoundException(String adminNotFound) {
        super("adminNotFound");
    }
}
