package com.example.database;

import java.security.MessageDigest;
import java.util.Base64;

public class HashTool {
    public static void main(String[] args) throws Exception {
        String password = "general123";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        String hashed = Base64.getEncoder().encodeToString(hash);
        System.out.println("Hashed password: " + hashed);
    }
}
