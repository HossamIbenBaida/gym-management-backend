package com.gym.project.client.Exceptions;

public class MemberNotFoundException extends  Exception {
    public MemberNotFoundException(String gymNotFound) {

        super(gymNotFound);

    }
}
