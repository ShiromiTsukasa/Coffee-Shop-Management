package com.UserAsClient.Controller;

public class UserProfile {
    public String userName;
    UserProfile(String userName){
        this.userName = userName;
    }
    public String getUserName(){
        return userName;
    }
}
