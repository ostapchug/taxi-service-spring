package com.example.taxiservicespring.service.model;

public enum Language {
    EN, UK;
    
    public static boolean contains(String value) {

        for (Language lang : Language.values()) {
            if (lang.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
