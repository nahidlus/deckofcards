package com.decks.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Config {
    static Properties properties = new Properties();
    static String env;
    static {
         env = System.getProperty("env")==null ? "local" : System.getProperty("env");
         loadProperties(env);
    }

    private static void loadProperties(String fileName){
        String filePath = "environment/"+env+".properties";
        InputStream is = Config.class.getClassLoader().getResourceAsStream(filePath);
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }

    public static String getBaseUrl(){
        return properties.getProperty("baseUrl");
    }

}
