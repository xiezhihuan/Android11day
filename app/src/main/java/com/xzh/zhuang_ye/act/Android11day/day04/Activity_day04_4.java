package com.xzh.zhuang_ye.act.Android11day.day04;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xzh.zhuang_ye.R;
import com.xzh.zhuang_ye.util.News;
import com.xzh.zhuang_ye.util.XmlParseUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by 谢植焕 on 2018/6/15.
 */

public class Activity_day04_4 extends Activity {
    private ListView myListView;
    List<News> newsList;
//    private Handler handler = new Handler() {
//
//        public void handleMessage(Message msg) {
//            Log.d("q1q1", "handleMessage+msg: "+msg);
//            Log.d("q1q1", "handleMessage+msg.obj: "+msg.obj);
//            Log.d("q1q1", "handleMessage+msg.tostring: "+msg.toString());
//            newsList = (List<News>) msg.obj;
//            Log.d("q1q1", "handleMessage:newslist: "+newsList.toString());
//            Log.d("q1q1", "handleMessage:newslist222: "+newsList.toString());
//            myListView = (ListView) findViewById(R.id.lv);
//            MyListAdapter myListAdapter = new MyListAdapter();
//            myListView.setAdapter(myListAdapter);
//        }
//    };
private static final String TAG = "Activity_day04_4";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day04_4);
        myListView = (ListView) findViewById(R.id.lv);
        initListDate();

//        find();
//        handler();//更新UI
    }

    private void initListDate() {
        new Thread() {
            public void run() {
                try {
                    String path = " http://192.168.199.158:8080/1234.xml";
                    //拿到链接地址
                    URL url = new URL(path);
                    //拿到httpURLConnection对象  用于发送或接受数据
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //设置发送get请求
                    httpURLConnection.setRequestMethod("GET");
                    //设置请求超时时间
                    httpURLConnection.setConnectTimeout(5000);
                    //获取服务器返回的状态码
                    //查看是否有缓存，有则调用缓存
//                    File file = new File(getFilesDir(), Base64.encodeToString(path.getBytes(),Base64.DEFAULT)); //Base64是一种加密方法  使用Base64.DEFAULT Base64的一种默认加密方法
                    //获取返回的数据    以流的方式返回的
                    Log.d(TAG, "已经发起了网络请求 ");
                    InputStream inputStream = httpURLConnection.getInputStream();
                    Log.d(TAG, "获取数据流，inputStream： "+inputStream.toString());
                    //将获得的数据流（xml）解析  自定义一个xmlparseutils方法
                    Log.d(TAG, "执行调用解析方法 ");

                    newsList = XmlParseUtils.parserXml(inputStream);
                    Log.d(TAG, "newslist: " + newsList.get(0).getTitle());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myListView.setAdapter(new MyListAdapter());
                        }
                    });
//                    Message msg = new Message();
//                    msg.what = 0;//给msg做标号 0
//                    msg.obj = newsList;
//                    handler.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                    if (e.toString().equals("java.net.SocketTimeoutException: connect timed out")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "连接超时", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Log.d(TAG, "e:" + e.toString());
                    }
                }
            }
        }.start();

    }

    //定义适配器
    private class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v;
            if (view == null) {
                v = View.inflate(getApplicationContext(), R.layout.activity_day04_4_item, null);
            } else {
                v = view;
            }
            //找到控件 显示集合里的数据
            ImageView icon = (ImageView) v.findViewById(R.id.day04_4_item_icon);
            TextView title = (TextView) v.findViewById(R.id.day04_4_item_title);
            TextView descr = (TextView) v.findViewById(R.id.day04_4_item_descr);
            TextView label = (TextView) v.findViewById(R.id.day04_4_item_label);
            //显示数据
            title.setText(newsList.get(i).getTitle());
            descr.setText(newsList.get(i).getTitle());
            String typee = newsList.get(i).getType();
            int type = Integer.parseInt(typee);
            String commit = newsList.get(i).getComment();
            int count = Integer.parseInt(commit);
            switch (type) {
                case 1:
                    label.setText("国内");
                    break;
                case 2:
                    label.setText("国外");

                    break;
                case 3:
                    label.setText(count + "人跟帖");

                    break;
            }

            return view;
        }
    }
}
