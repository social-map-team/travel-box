package com.socialmap.yy.travelbox.model;

import java.util.Date;


public class User {



    public User(int resId, String name, String detail) {
        this.resId  = resId;
        this.name   = name;
        this.detail = detail;
    }
    public enum Gender{
        MALE,
        FEMALE,
        SECRET
    }

    private String username;
    private long _id;
    private String identityCardNumber;
    private String nickname;
    private String avatar;
    private Gender gender;
    private Date birthday;
    private int resId;
    private String name;
    private String detail;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }



    public void setImageId(int resId) {
        this.resId  = resId;
    }

    public int getImageId() {
        return resId;
    }

    public void setName(String name) {
        this.name   = name;
    }

    public String getName() {
        return name;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public String toString() {
        return "User[" + resId + ", " + name + ", " + detail + "]";
    }



}
