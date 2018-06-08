package com.example.user.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 天气数据：未来天气预报
 * 利用注解建立映射关系
 * @author
 */
public class Forecast {

    public String date;//日期

    @SerializedName("tmp")
    public Temperature temperature;//温度

    @SerializedName("cond")
    public More more;//天气

    public class More{

        @SerializedName("txt_d")
        public String info;
    }

    public class Temperature{

        public String max;
        public String min;
    }
}
