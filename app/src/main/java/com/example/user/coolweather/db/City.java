package com.example.user.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * 表2：市
 * @author
 */
public class City extends DataSupport {
    private int id;//实体对象id
    private String cityName;//市名
    private int cityCode;//编号
    private int provinceId;//所属省份id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
