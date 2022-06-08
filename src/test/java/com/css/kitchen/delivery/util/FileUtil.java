package com.css.kitchen.delivery.util;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {

    @SneakyThrows
    public static String ReadFile(String Path) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            InputStream in = FileUtil.class.getClassLoader().getResourceAsStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }


}
