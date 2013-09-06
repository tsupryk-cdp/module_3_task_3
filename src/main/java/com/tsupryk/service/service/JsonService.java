package com.tsupryk.service.service;

import com.tsupryk.repository.api.IJsonRepository;
import com.tsupryk.service.api.IJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class JsonService.
 * Date: 06.09.13
 */
@Service
public class JsonService implements IJsonService {

    @Autowired
    private IJsonRepository jsonRepository;

    @Override
    public String getGlobalData(String param) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getCitiesData(String param) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
