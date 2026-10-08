package com.parabank.config;

public final class Config {

    private Config() {
    }

    public static String baseUrl() {
        return System.getProperty(
                "baseUrl",
                "https://parabank.parasoft.com/parabank/index.htm"
        );
    }

    public static boolean headless() {
        return Boolean.parseBoolean(
                System.getProperty("headless", "true")
        );
    }

    public static String apiBase() {
        return baseUrl() + "/services/bank";
    }

    public static String adminUrl() {
        return baseUrl() + "/admin.htm";
    }
}