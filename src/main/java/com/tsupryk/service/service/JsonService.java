package com.tsupryk.service.service;

import com.tsupryk.repository.api.IJsonRepository;
import com.tsupryk.service.api.IJsonService;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class JsonService.
 * Date: 06.09.13
 */
@Service
public class JsonService implements IJsonService {

    @Autowired
    private IJsonRepository jsonRepository;

    @Autowired
    private ObjectMapper jacksonMarshaller;

    @Override
    public String getGlobalData(String param) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map getCitiesData(String param) {
        Map<String, Object> map = null;
        try {
            String data = jsonRepository.getData();
            jacksonMarshaller.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

            map = jacksonMarshaller.readValue(data, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return map;
    }

    private String performToCitiesData(String data) {

        return null;
    }
}
