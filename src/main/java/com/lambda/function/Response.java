package com.lambda.function;

public class Response {

    private String firstName;
    private String lastName;

    public Response(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
