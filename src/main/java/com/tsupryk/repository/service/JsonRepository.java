package com.tsupryk.repository.service;

import com.tsupryk.repository.api.IJsonRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Arrays;

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

        if (dataCache != null) {
            return dataCache;
        }

        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(filePath);

        try {
            byte[] allBytes = new byte[3000];
            int bytesNumber = stream.available();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(3000);

            while (bytesNumber != 0) {
                byte[] bytes = new byte[bytesNumber];
                stream.read(bytes);
                outputStream.write(bytes);
                bytesNumber = stream.available();
            }

            dataCache = new String(outputStream.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return dataCache;

//        String content = null;
//        File file = new File(filePath);
//        try {
//            FileReader reader = new FileReader(file);
//            char[] chars = new char[(int) file.length()];
//            reader.read(chars);
//            content = new String(chars);
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return content;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
