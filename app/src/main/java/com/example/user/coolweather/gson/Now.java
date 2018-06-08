package com.example.user.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 天气数据：当前天气信息
 * 利用注解建立映射关系
 * @author
 */
public class Now {

    @SerializedName("tmp")
    public String temperature;//温度

    @SerializedName("cond")
    public More more;

    public class More{

        @SerializedName("txt")
        public String info;//天气
    }
}
