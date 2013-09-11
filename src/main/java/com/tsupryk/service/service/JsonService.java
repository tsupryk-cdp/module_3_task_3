package com.tsupryk.service.service;

import com.tsupryk.repository.api.IJsonRepository;
import com.tsupryk.service.api.IJsonService;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        return performToCitiesData(map);
    }

    private Map<String, List<List<String>>> performToCitiesData(Map<String, Object> data) {

        Map<String, List<List<String>>> datasets = new HashMap<>();


        Map<String, Object> all = (Map<String, Object>) data.get("EPAM");

        for (Object yearName : all.keySet()) {
            String yearNameString = (String) yearName;
            String year = yearNameString.replace("Year", "");

            Map<String, Object> towns = (Map<String, Object>) all.get(yearNameString);



            for (String townName : towns.keySet()) {
                if (datasets.get(townName) == null){
                    datasets.put(townName, new ArrayList<List<String>>());
                }

                List<Integer> townValuesList = (List<Integer>) towns.get(townName);

                for (int i = 0; i<townValuesList.size(); i++) {
                    List<String> point = new ArrayList<>();
                    Integer y = new Integer(year);
                    point.add(String.valueOf(y + (0.25 * i)));
                    point.add(townValuesList.get(i).toString());
                    datasets.get(townName).add(point);
                }

            }

            towns.getClass();


        }


        return datasets;
    }
}
