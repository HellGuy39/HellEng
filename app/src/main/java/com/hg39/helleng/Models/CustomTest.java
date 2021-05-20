package com.hg39.helleng.Models;

public class CustomTest {

    private String testName;
    private String dateOfCreation;
    private String testID;

    public CustomTest() {}

    public CustomTest(String testName, String date, String testID) {
        this.testName = testName;
        this.dateOfCreation = date;
        this.testID = testID;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getDate() {
        return dateOfCreation;
    }

    public void setDate(String date) {
        this.dateOfCreation = date;
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }
}
