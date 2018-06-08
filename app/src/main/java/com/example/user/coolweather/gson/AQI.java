package com.example.user.coolweather.gson;

/**
 * 天气数据：空气质量
 * @author
 */
public class AQI {

    public AQICity city;

    public class AQICity{
        public String aqi;//空气指数
        public String pm25;//pm2.5指数
    }
}
