package com.xzh.zhuang_ye.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 谢植焕 on 2018/6/25.
 */

public class HttpUrlConnectionUtil {

    public static String get(final String url) {
        final String[] content = new String[1];


        try {

            //拿到链接地址
            URL url1 = new URL(url);
            //拿到httpURLConnection对象  用于发送或接受数据
            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
            //设置发送get请求
            httpURLConnection.setRequestMethod("GET");
            //设置请求超时时间
            httpURLConnection.setConnectTimeout(5000);

            InputStream inputStream = httpURLConnection.getInputStream();

            //使用工具类将流`转换为String
            content[0] = StreamTool.readStream(inputStream);
            return content[0];
        } catch (Exception e) {
            e.printStackTrace();

        }

        return "错误";
    }


}
