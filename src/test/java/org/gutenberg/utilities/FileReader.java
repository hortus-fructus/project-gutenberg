package org.gutenberg.utilities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public static void executeScript(String command) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));


        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        line = "";
        while ((line = errorReader.readLine()) != null) {
            System.out.println(line);
        }
    }

}
