package com.tsupryk.web.service;

import com.tsupryk.service.api.IJsonService;
import com.tsupryk.web.api.IJsonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The Class JsonController.
 * Date: 06.09.13
 */
@Controller
@RequestMapping("/json")
public class JsonController implements IJsonController {

    @Autowired
    private IJsonService jsonService;

    private static final String ERROR_RESPONSE = "{\"error\":\"Error\"}";

    @Override
    @RequestMapping(value = "/global", produces = "application/json")
    @ResponseBody
    public Object getGlobalData() {
        try {
            return jsonService.getGlobalData();
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_RESPONSE;
        }
    }

    @Override
    @RequestMapping(value = "/cities", produces = "application/json")
    @ResponseBody
    public Object getCitiesData() {
        try {
            return jsonService.getCitiesData();
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_RESPONSE;
        }
    }

    @Override
    @RequestMapping("/main")
    public String getMainPage() {
        return "index";
    }

}
