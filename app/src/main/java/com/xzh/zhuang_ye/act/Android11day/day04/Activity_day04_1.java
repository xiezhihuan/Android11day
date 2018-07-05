package com.xzh.zhuang_ye.act.Android11day.day04;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xzh.zhuang_ye.R;
import com.xzh.zhuang_ye.util.StreamTool;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 谢植焕 on 2018/6/10.
 */

public class Activity_day04_1 extends Activity {
    EditText editText;
    Button button;
    TextView textView;
    final int REQUESTSUCCEED = 0;
    final int REQUESTNOTFOUND = 1;
    final int REQUESTEXCEPTION = 2;

    //在主线程定义一个handler，重写handler方法
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUESTSUCCEED:
                    String content = (String) msg.obj;
                    textView.setText(content);
                    break;
                case REQUESTNOTFOUND:
                    Toast.makeText(getApplicationContext(), "请求资源不存在", Toast.LENGTH_SHORT).show();
                    break;
                case REQUESTEXCEPTION:
                    Toast.makeText(getApplicationContext(), "服务器忙，请稍后。。。", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

        }
    };


    private static final String TAG = "Activity_day04";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day04_1);
        find();
//        init();
        //查看当前线程的名字
        Log.d(TAG, "当前线程名字：" + Thread.currentThread().getName());
    }

    private void find() {
        editText = (EditText) findViewById(R.id.day04_et);
        button = (Button) findViewById(R.id.day04_bt);
        textView = (TextView) findViewById(R.id.day04_tv);
    }//实例化各种键

    public void click(View view) {

        //Android4.0后谷歌公司强制“使用网络”必须开辟子线程
        new Thread() {
            public void run() {
                try {
                    Log.d(TAG, "当前线程名字：" + Thread.currentThread().getName());
                    String path = editText.getText().toString().trim();
                    //拿到链接地址
                    URL url = new URL(path);
                    //拿到httpURLConnection对象  用于发送或接受数据
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //设置发送get请求
                    httpURLConnection.setRequestMethod("GET");
                    //设置请求超时时间
                    httpURLConnection.setConnectTimeout(5000);
                    //获取服务器返回的状态码
                    int code = httpURLConnection.getResponseCode();
                    //
                    if (code == 200) {
                        //获取返回的数据    以流的方式返回的
                        InputStream inputStream = httpURLConnection.getInputStream();
                        //将流转换为一段字符串   自定义方法StreamTool
                        //使用工具类将流转换为String
                        String content = StreamTool.readStream(inputStream);
                        //将数据展示在TextView上(更新ui)   //谷歌工程师规定只能在主线程更新UI   使用Handler
//                    textView.setText(content);
                        //使用Handler
                        Message msg = new Message();
                        msg.what = REQUESTSUCCEED;//给msg做标号 0
                        msg.obj = content;
                        handler.sendMessage(msg);
                    } else {
                        //用一个Toast提示用户   //Toast是一个view，不能在子线程更新
                        Message msg = new Message();
                        msg.what = REQUESTNOTFOUND;//给msg做标号 1
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    e.printStackTrace();
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = REQUESTEXCEPTION;//给msg做标号 2
                    handler.sendMessage(msg);
                }
            }
        }.start();


    }//通过链接加载网页源码，存入缓存


}

