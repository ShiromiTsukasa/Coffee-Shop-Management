package com.UserAsClient.Controller;


import java.security.MessageDigest;

import org.json.*;

public class UserProfile {
    public String userName;
    public JSONObject currentOrder;
    private String userHash;

    UserProfile(String userName){
        this.userName = userName;
        this.currentOrder = new JSONObject();

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(userName.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }

            this.userHash = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUserName(){
        return userName;
    }

    public String getUserHash(){
        return userHash;
    }

    public JSONObject getCurrentOrder(){
        return currentOrder;
    }

    public void setCurrentOrder(JSONObject currentOrder){
        this.currentOrder = currentOrder;
    }
}
