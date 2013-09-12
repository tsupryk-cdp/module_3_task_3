package com.tsupryk.service.service;

import com.tsupryk.api.JsonReadException;
import com.tsupryk.repository.api.IJsonRepository;
import com.tsupryk.service.api.IJsonService;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

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
    public Object getGlobalData() {

        Map<String, Object> rawData = getStringObjectMap();

        Map<String, Object> datasets = getGlobalData(rawData);
        return datasets;
    }

    @Override
    public Object getCitiesData() {
        Map<String, Object> rawData = getStringObjectMap();
        Collection<Map<String, Object>> datasets = getCitiesData(rawData);
        return datasets;
    }

    private Map<String, Object> getGlobalData(Map<String, Object> rawData) {
        Map<String, Object> datasets = new HashMap<>();
        datasets.put("data", new ArrayList<List<Double>>());
        datasets.put("label", "Epam");
        datasets.put("color", 0);

        Map<String, Object> all = (Map<String, Object>) rawData.get("EPAM");

        for (Object yearName : all.keySet()) {
            String yearNameString = (String) yearName;
            String year = yearNameString.replace("Year", "");

            Map<String, Object> towns = (Map<String, Object>) all.get(yearNameString);

            double[] yearValues = new double[]{0.0, 0.0, 0.0, 0.0};

            for (String townName : towns.keySet()) {
                List<Integer> townValuesList = (List<Integer>) towns.get(townName);
                for (int i = 0; i<townValuesList.size(); i++) {
                    yearValues[i] += Double.valueOf(townValuesList.get(i));
                }
            }
            List<List<Double>> data = (List<List<Double>>) datasets.get("data");
            for (int i = 0; i < yearValues.length; i++) {
                List<Double> ld = new ArrayList<>();
                ld.add(Integer.valueOf(year) + (0.25 * i));
                ld.add(yearValues[i]);
                data.add(ld);
            }

        }
        return datasets;
    }



    private Map<String, Object> getStringObjectMap() {
        Map<String, Object> rawData = null;
        try {
            String data = jsonRepository.getData();
            jacksonMarshaller.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            rawData = jacksonMarshaller.readValue(data, HashMap.class);
        } catch (IOException e) {
            throw new JsonReadException(e);
        }
        return rawData;
    }

    private Collection<Map<String, Object>> getCitiesData(Map<String, Object> data) {

        Map<String, Map<String, Object>> datasets = new HashMap<>();


        Map<String, Object> all = (Map<String, Object>) data.get("EPAM");

        int citiesCount = 0;

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
                    townMap.put("color", citiesCount++);
                    datasets.put(townName, townMap);
                }

                List<Integer> townValuesList = (List<Integer>) towns.get(townName);

                for (int i = 0; i < townValuesList.size(); i++) {
                    List<Double> point = new ArrayList<>();
                    Integer y = new Integer(year);
                    point.add(y + (0.25 * i));
                    point.add(Double.valueOf(townValuesList.get(i)));
                    List<List<Double>> townData1 = (List<List<Double>>) datasets.get(townName).get("data");
                    townData1.add(point);
                }
            }
        }
        return datasets.values();
    }
}
