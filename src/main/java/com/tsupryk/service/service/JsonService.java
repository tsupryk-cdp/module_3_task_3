package com.tsupryk.service.service;

import com.tsupryk.repository.api.IJsonRepository;
import com.tsupryk.service.api.IJsonService;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
    public String getGlobalData() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getCitiesData() {
        Map<String, Object> rawData = null;
        try {
            String data = jsonRepository.getData();
            jacksonMarshaller.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

            rawData = jacksonMarshaller.readValue(data, HashMap.class);

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return performToCitiesData(rawData);
    }

    private Collection<Map<String, Object>> performToCitiesData(Map<String, Object> data) {

        Map<String, Map<String, Object>> datasets = new HashMap<>();


        Map<String, Object> all = (Map<String, Object>) data.get("EPAM");

        for (Object yearName : all.keySet()) {
            String yearNameString = (String) yearName;
            String year = yearNameString.replace("Year", "");

            Map<String, Object> towns = (Map<String, Object>) all.get(yearNameString);



            for (String townName : towns.keySet()) {
                // init fields
                if (datasets.get(townName) == null) {
                    Map<String, Object> townMap = new HashMap<>();
                    townMap.put("data", new ArrayList<List<Double>>());
                    townMap.put("label", townName);
                    datasets.put(townName, townMap);
                }

                List<Integer> townValuesList = (List<Integer>) towns.get(townName);

                for (int i = 0; i<townValuesList.size(); i++) {
                    List<Double> point = new ArrayList<>();
                    Integer y = new Integer(year);
                    point.add(y + (0.25 * i));
                    point.add(Double.valueOf(townValuesList.get(i)));
                    List<List<Double>> townData1 = (List<List<Double>>) datasets.get(townName).get("data");
                    townData1.add(point);
                }

            }

            towns.getClass();


        }


        return datasets.values();
    }
}
