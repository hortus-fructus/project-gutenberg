package org.gutenberg.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileReader {
    private static Properties propFile;
    public static String getProperty(String key,String filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            propFile = new Properties();
            propFile.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            System.out.println("Failed to load properties file!");
            e.printStackTrace();
        }
        return propFile.getProperty(key);
    }

}
