package com.example.wastematepro.Models;

public class UserModel {

    private String name;
    private String email;
    private String password;
    private String profile;
    private String referCode;
    private String redeemStatus;
    private String role;
    private String address;
    private String phoneNumber;
    private int coins,spin,scratch;
    private String uId;

    public UserModel(String name, String email, String password, String profile, String referCode, String redeemStatus, String role, String address, String phoneNumber, int coins, int spin, int scratch) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.referCode = referCode;
        this.redeemStatus = redeemStatus;
        this.role = role;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.coins = coins;
        this.spin = spin;
        this.scratch = scratch;
    }

    public UserModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode;
    }

    public String getRedeemStatus() {
        return redeemStatus;
    }

    public void setRedeemStatus(String redeemStatus) {
        this.redeemStatus = redeemStatus;
    }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getSpin() {
        return spin;
    }

    public void setSpin(int spin) {
        this.spin = spin;
    }

    public int getScratch() {
        return scratch;
    }

    public void setScratch(int scratch) {
        this.scratch = scratch;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
