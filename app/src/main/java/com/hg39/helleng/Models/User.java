package com.hg39.helleng.Models;

public class User {

    private String email,password,registerDate;
    private String firstName,lastName,status;
    private String webStatus;
    private String username;
    private String profileImage,profileImageUri;
    private String userStatus;
    private int testsStarted,testsFullCompleted;

    public User(String email, String password, String registerDate, String firstName, String username,
                String lastName, String status, String webStatus, String profileImage,
                String userStatus, String profileImageUri,
                int testsFullCompleted, int testsStarted ) {
        this.email = email;
        this.password = password;
        this.registerDate = registerDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.userStatus = userStatus;
        this.status = status;
        this.webStatus = webStatus;
        this.profileImage = profileImage;
        this.profileImageUri = profileImageUri;
        this.testsStarted = testsStarted;
        this.testsFullCompleted = testsFullCompleted;
    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getProfileImageUri() {
        return profileImageUri;
    }

    public void setProfileImageUri(String profileImageUri) {
        this.profileImageUri = profileImageUri;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public String getWebStatus() {
        return webStatus;
    }

    public void setWebStatus(String webStatus) {
        this.webStatus = webStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public int getTestsStarted() {
        return testsStarted;
    }

    public void setTestsStarted(int testsStarted) {
        this.testsStarted = testsStarted;
    }

    public int getTestsFullCompleted() {
        return testsFullCompleted;
    }

    public void setTestsFullCompleted(int testsFullCompleted) {
        this.testsFullCompleted = testsFullCompleted;
    }
}
