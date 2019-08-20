package com.NumNums.models;


public class LogInUserObject {
    private String email;
    private String password;

    public LogInUserObject() {
    }



    public LogInUserObject(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "LogInUserObject{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
