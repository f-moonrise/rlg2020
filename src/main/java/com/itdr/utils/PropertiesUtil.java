package com.itdr.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/20 23:36
 */
public class PropertiesUtil {

    private static Properties p;

    static {
        InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("initparam.properties");
        p = new Properties();
        try{
            p.load(in);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        String s = p.getProperty(key);
        return s;
    }

    public static void main(String[] args) {
        System.out.println(getValue("ImageHost"));
    }
}
