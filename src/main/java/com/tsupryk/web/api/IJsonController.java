package com.tsupryk.web.api;

/**
 * The Interface IJsonController.
 * Date: 06.09.13
 *
 */
public interface IJsonController {

    public Object getGlobalData(String param);

    public Object getCitiesData(String param);

    public String getMainPage();
}
