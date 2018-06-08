package com.example.user.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 天气数据：basic
 * @author
 */
public class Basic {

    @SerializedName("city")
    public String cityName;//城市名

    @SerializedName("id")
    public String weatherId;//天气id

    public Update update;//更新时间

    public class Update{

        @SerializedName("loc")
        public String updateTime;
    }
}
