package com.tsupryk.repository.service;

import com.tsupryk.api.JsonReadException;
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

    @Value("${json.repository.filepath}")
    private String filePath;

    @Value("${json.repository.isCacheEnabled}")
    private boolean cacheEnabled;

    @Value("${json.repository.maxFileSizeBytes}")
    private int maxFileSizeBytes;

    private String dataCache;

    @Override
    public String getData() {
        if (cacheEnabled && dataCache != null) {
            return dataCache;
        }
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(filePath);
        try {
            int bytesNumber = stream.available();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(maxFileSizeBytes);
            while (bytesNumber != 0) {
                byte[] bytes = new byte[bytesNumber];
                stream.read(bytes);
                outputStream.write(bytes);
                bytesNumber = stream.available();
            }
            dataCache = new String(outputStream.toByteArray());
        } catch (IOException e) {
            throw new JsonReadException(e);
        }
        return dataCache;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }

    public void setMaxFileSizeBytes(int maxFileSizeBytes) {
        this.maxFileSizeBytes = maxFileSizeBytes;
    }
}
