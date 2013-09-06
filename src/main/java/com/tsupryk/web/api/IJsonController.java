package com.tsupryk.web.api;

import com.tsupryk.api.User;
import org.springframework.web.servlet.ModelAndView;

/**
 * The Interface IJsonController.
 * Date: 06.09.13
 *
 */
public interface IJsonController {

    public User getGlobalData(String param);

    public ModelAndView getCitiesData(String param);
}
