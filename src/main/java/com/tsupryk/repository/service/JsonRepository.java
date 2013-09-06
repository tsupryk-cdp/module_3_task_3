package com.tsupryk.repository.service;

import com.tsupryk.repository.api.IJsonRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * The Class JsonRepository.
 * Date: 06.09.13
 */
@Repository
public class JsonRepository implements IJsonRepository {

    @Value("${json.filepath}")
    private String filePath;

    private String dataCache;

    @Override
    public String getData() {

        String content = null;
        File file = new File(filePath);
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
