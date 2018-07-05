package com.xzh.zhuang_ye.core.http;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by 谢植焕 on 2018/6/10.
 */

public class Http {
    //全局的静态变量
    public static String src="ww";
    public static String dst="QQ";



    /**
     * 向指定URL发送GET方法的请求
     *
     */
    public static String get(String url) {
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    //JSON解析
    /**
     * JSON-->纯对象(Object)的解析
     *
     * 注：我们在eclipse里面操作JSON解析的时候需要第三方jar包的支持
     * @author sKy°
     * @date 2016-5-8
     * @version 1.0
     */
    private static final String TAG = "Http";
    public static void parse(String s) {
//      编辑一个我们要解析的数据对象
//     根据JSON的官方定义，键，加"",值，如果是字符串，就加""，其他不加。
        String json=s;


        // JSONObject jsonObject;

        try {
//          创建JSON解析对象 传入“需要解析的数据”
            JSONObject obj = new JSONObject(json);
//          获取“数据”中的“to”
            String name = obj.getString("to");
//          获取“数据”中的数组“trans_result”
            JSONArray jsonArray_trans_result = obj.getJSONArray("trans_result");
//          从数组“trans_result”中获取JSONObject对象{"src":"good","dst":"\u597d\u7684"}
            JSONObject trans_result_array= jsonArray_trans_result.getJSONObject(0);
            //最终要的结果
            //原文
            src = trans_result_array.getString("src");
            //译文
            dst = trans_result_array.getString("dst");
            Log.d("xzh：输出文字", src);
            Log.d("dst：输出文字", dst);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}