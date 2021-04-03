package com.hg39.helleng.Models;

public class User {

    private String email,password,registerDate;
    private String firstName,lastName,status;
    private String webStatus;
    private String username;
    private String profileImage,profileImageUri;
    private String userStatus;
    private int testsStarted,testsFullCompleted;
    private int test1Interest,test2Interest,test3Interest,test4Interest,
            test5Interest,test6Interest,test7Interest,test8Interest,
            test9Interest,test10Interest,test11Interest,test12Interest,
            test13Interest,test14Interest,test15Interest,test16Interest;

    public User(String email, String password, String registerDate, String firstName, String username,
                String lastName, String status, String webStatus, String profileImage, int testsStarted,
                String userStatus, String profileImageUri,
                int testsFullCompleted, int test1Interest, int test2Interest, int test3Interest,
                int test4Interest, int test5Interest, int test6Interest, int test7Interest,
                int test8Interest, int test9Interest, int test10Interest, int test11Interest,
                int test12Interest, int test13Interest, int test14Interest, int test15Interest,
                int test16Interest) {
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
        this.test1Interest = test1Interest;
        this.test2Interest = test2Interest;
        this.test3Interest = test3Interest;
        this.test4Interest = test4Interest;
        this.test5Interest = test5Interest;
        this.test6Interest = test6Interest;
        this.test7Interest = test7Interest;
        this.test8Interest = test8Interest;
        this.test9Interest = test9Interest;
        this.test10Interest = test10Interest;
        this.test11Interest = test11Interest;
        this.test12Interest = test12Interest;
        this.test13Interest = test13Interest;
        this.test14Interest = test14Interest;
        this.test15Interest = test15Interest;
        this.test16Interest = test16Interest;
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

    public int getTest1Interest() {
        return test1Interest;
    }

    public void setTest1Interest(int test1Interest) {
        this.test1Interest = test1Interest;
    }

    public int getTest2Interest() {
        return test2Interest;
    }

    public void setTest2Interest(int test2Interest) {
        this.test2Interest = test2Interest;
    }

    public int getTest3Interest() {
        return test3Interest;
    }

    public void setTest3Interest(int test3Interest) {
        this.test3Interest = test3Interest;
    }

    public int getTest4Interest() {
        return test4Interest;
    }

    public void setTest4Interest(int test4Interest) {
        this.test4Interest = test4Interest;
    }

    public int getTest5Interest() {
        return test5Interest;
    }

    public void setTest5Interest(int test5Interest) {
        this.test5Interest = test5Interest;
    }

    public int getTest6Interest() {
        return test6Interest;
    }

    public void setTest6Interest(int test6Interest) {
        this.test6Interest = test6Interest;
    }

    public int getTest7Interest() {
        return test7Interest;
    }

    public void setTest7Interest(int test7Interest) {
        this.test7Interest = test7Interest;
    }

    public int getTest8Interest() {
        return test8Interest;
    }

    public void setTest8Interest(int test8Interest) {
        this.test8Interest = test8Interest;
    }

    public int getTest9Interest() {
        return test9Interest;
    }

    public void setTest9Interest(int test9Interest) {
        this.test9Interest = test9Interest;
    }

    public int getTest10Interest() {
        return test10Interest;
    }

    public void setTest10Interest(int test10Interest) {
        this.test10Interest = test10Interest;
    }

    public int getTest11Interest() {
        return test11Interest;
    }

    public void setTest11Interest(int test11Interest) {
        this.test11Interest = test11Interest;
    }

    public int getTest12Interest() {
        return test12Interest;
    }

    public void setTest12Interest(int test12Interest) {
        this.test12Interest = test12Interest;
    }

    public int getTest13Interest() {
        return test13Interest;
    }

    public void setTest13Interest(int test13Interest) {
        this.test13Interest = test13Interest;
    }

    public int getTest14Interest() {
        return test14Interest;
    }

    public void setTest14Interest(int test14Interest) {
        this.test14Interest = test14Interest;
    }

    public int getTest15Interest() {
        return test15Interest;
    }

    public void setTest15Interest(int test15Interest) {
        this.test15Interest = test15Interest;
    }

    public int getTest16Interest() {
        return test16Interest;
    }

    public void setTest16Interest(int test16Interest) {
        this.test16Interest = test16Interest;
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
