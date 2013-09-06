package com.tsupryk.web.service;

import com.tsupryk.api.User;
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

    @Override
    @RequestMapping(value = "/global", produces = "application/json")
    @ResponseBody
    public User getGlobalData(String param) {
        User user = new User();
        user.setName("sss");
        return user;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @RequestMapping("/cities")
    public Object getCitiesData(String param) {
        return jsonService.getCitiesData("");  //To change body of implemented methods use File | Settings | File Templates.
    }
}
