package com.example.user.coolweather.gson;

import com.google.gson.annotations.SerializedName;

import javax.security.auth.callback.Callback;

/**
 * 天气数据：出行建议
 * 利用注解建立映射关系
 * @author
 */
public class Suggestion {

    @SerializedName("comf")
    public Comfort comfort;//舒适程度

    @SerializedName("cw")
    public CarWash carWash;//洗车建议

    public Sport sport;//运动建议

    public class Comfort{
        @SerializedName("txt")
        public String info;
    }

    public class CarWash{
        @SerializedName("txt")
        public String info;
    }

    public class Sport{
        @SerializedName("txt")
        public String info;
    }
}
