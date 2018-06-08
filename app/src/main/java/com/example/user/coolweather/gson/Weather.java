package com.example.user.coolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 天气数据总类
 * @author
 */
public class Weather {

    public String status;//请求状态
    public Basic basic;//城市基本信息
    public AQI aqi;//空气状况
    public Now now;//当前天气
    public Suggestion suggestion;//建议

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;//未来天气预报
}
