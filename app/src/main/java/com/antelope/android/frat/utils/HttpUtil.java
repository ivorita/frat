package com.antelope.android.frat.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 网络请求工具类
 */
public class HttpUtil {

    /*public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");*/

    /**
     * 以下这些参数根据自己的需求增删
     *
     * @param address  这是基本地址，例如：http://api.heclouds.com/devices/30964714/datapoints?，这里？表示get方式
     * @param dataType 这里指的是 datastream_id
     * @param year
     * @param month
     * @param day
     * @param callback 这是回调接口
     */
    public static void sendOkHttpRequest(String address, String dataType, String year, String month, String day, okhttp3.Callback callback) {
        //1、创建OkHttpClient实例
        OkHttpClient okHttpClient = new OkHttpClient();
        //Log.d("historyf", "onViewClicked: " + dataType);
        String content = "datastream_id=" + dataType + "&start=" + year + "-" + month + "-" + day + "T00:00:00&limit=10";
        //Log.d("HttpUtil", "sendOkHttpRequest: " + content);

        Request request = new Request.Builder()
                .url(address + content)//这里是api的地址，即基本地址+参数，这里参数就是datastream_id这些
                .addHeader("api-key", "Uv=e=yMBymo8In9FVA4Ub16Oleo=")//头部，api-key要自己去注册账号获取，然后去个人中心找下
                .get()//这是get方式，用于获取数据
                .build();

        //采用异步访问网络
        okHttpClient.newCall(request).enqueue(callback);
    }

}
