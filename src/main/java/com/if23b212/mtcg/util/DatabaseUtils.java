package com.if23b212.mtcg.util;

public class DatabaseUtils {

    private static final PropertiesUtil propertiesUtil = new PropertiesUtil();
    public static final String DB_URL = propertiesUtil.getProperty("DB_URL");
    public static String USERNAME = propertiesUtil.getProperty("USERNAME");
    public static String PASSWORD = propertiesUtil.getProperty("PASSWORD");


}
