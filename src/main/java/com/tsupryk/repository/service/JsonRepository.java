package com.tsupryk.repository.service;

import com.tsupryk.repository.api.IJsonRepository;
import org.springframework.stereotype.Repository;

/**
 * The Class JsonRepository.
 * Date: 06.09.13
 */
@Repository
public class JsonRepository implements IJsonRepository {

    private String filePath;

    private String dataCache;

    @Override
    public String getData() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
