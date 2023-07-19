package com.itheima;

import java.util.Map;
import java.util.Properties;

public class AppSystemProperties {
    public static void main(String[] args) {
        Properties properties = System.getProperties();
        Map<String, String> getenv = System.getenv();
        System.out.println("properties"+properties);
        System.out.println("getenv"+getenv);
    }
}
