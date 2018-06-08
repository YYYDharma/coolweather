package com.example.user.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * 表1：省份
 * @author
 */
public class Province extends DataSupport {
    private int id;//实体对象id
    private String provinceName;//名称
    private int provinceCode;//编号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
