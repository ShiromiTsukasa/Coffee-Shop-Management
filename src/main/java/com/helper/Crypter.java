package com.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypter {
    public static RandomString generator = new RandomString(32);

    public static String hashSHA256(String message) {
        String hash = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(message.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            hash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static String convUPWithSalt(String username, String password) {
        String salt = generator.nextString();

        String hash = Crypter.hashSHA256(username + password + salt);

        return String.format("%s$%s:%s", username, salt, hash);
    }

    public static void main(String[] args) {
        System.out.println(Crypter.convUPWithSalt("HongMeiling", "Password"));
    }
}
