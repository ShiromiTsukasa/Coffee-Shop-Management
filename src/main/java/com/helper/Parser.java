package com.helper;

import java.util.Map;
import java.util.HashMap;

public class Parser {
    public static Map<String, String> parseRecord(String record) {
        Map<String, String> map = new HashMap<String, String>();
        String[] arr = record.split("[$:]");

        map.put("username", arr[0]);
        map.put("salt", arr[1]);
        map.put("hash", arr[2]);

        return map;
    }

    public static void main(String[] args) {
        System.out.println(parseRecord("HongMeiling$76HJh9vIeTbN7A1OdIbQmNp1hfQ9T2CS:46c1c8ddb0ed2f9e29d1363d6f4ef2e62e615e3764f1bd18b9a8b93bd42c5b4e"));
    }
}
