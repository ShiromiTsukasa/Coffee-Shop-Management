package com.helper;


import java.io.FileNotFoundException;

import org.json.JSONObject;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        JSONObject obj = new JSONObject().put("name", "John").put("age", 34);

        System.out.println(obj.getString("age"));
    }    
}
