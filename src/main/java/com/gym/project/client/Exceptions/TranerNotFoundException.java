package com.gym.project.client.Exceptions;

public class TranerNotFoundException extends Exception {
    public TranerNotFoundException(String trainerNotFound) {
        super(trainerNotFound);
    }
}
