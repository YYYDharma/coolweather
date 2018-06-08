package com.example.user.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * 表3：区县
 * @author
 */
public class County extends DataSupport {
    private int id;//实体对象id
    private String countyName;//区县名
    private String weatherId;//天气id
    private int cityId;//所属市的id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
