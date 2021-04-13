package com.hg39.helleng.Models;

public class RegUser {

    private String FirstName;
    private String LastName;
    private String FullName;
    private String Email;
    private String Password;
    private String profileImage;
    private String registerDate;

    public RegUser() {}

    public RegUser(String firstName, String lastName, String fullName,
                   String email, String password,
                   String profileImage, String registerDate) {
        this.registerDate = registerDate;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.FullName = fullName;
        this.Email = email;
        this.Password = password;
        this.profileImage = profileImage;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        this.FullName = fullName;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String emailName) {
        this.Email = emailName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String passwordName) {
        this.Password = passwordName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
