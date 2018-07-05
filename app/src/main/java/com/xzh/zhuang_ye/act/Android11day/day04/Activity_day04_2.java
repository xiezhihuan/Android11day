package com.xzh.zhuang_ye.act.Android11day.day04;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.xzh.zhuang_ye.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 谢植焕 on 2018/6/11.
 */

public class Activity_day04_2 extends Activity {
    EditText editText;
    Button button;
    ImageView imageView;
    private static final String TAG = "Activity_day04_2";

    final int REQUESTSUCCEED = 0;
    final int REQUESTNOTFOUND = 1;
    final int REQUESTEXCEPTION = 2;

    //定义一个全局变量  ，， 用在search中
    Handler handler;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {  
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day04_2);
        find();
        handler();//更新UI
    }

    private void find() {
        editText = (EditText) findViewById(R.id.day04_2_et);
        button = (Button) findViewById(R.id.day04_2_bt);
        imageView = (ImageView) findViewById(R.id.day04_2_iv);
    } //实例化各种键
    private void handler(){
        handler = new Handler() {

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case REQUESTSUCCEED:
                        Bitmap bitmap = (Bitmap) msg.obj;
                        imageView.setImageBitmap(bitmap);
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
    }
    public void search(View view) {

        new Thread() {
            public void run() {
                try {
                    Log.d(TAG, "当前线程名字：" + Thread.currentThread().getName());
                    String path = editText.getText().toString().trim();
                    //拿到链接地址
                    Log.d(TAG, "错误1xxxxxxxxx");

                    URL url = new URL(path);
                    //拿到httpURLConnection对象  用于发送或接受数据
                    Log.d(TAG, "错误2xxxxxxxxx");

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //设置发送get请求
                    Log.d(TAG, "错误3xxxxxxxxx");

                    httpURLConnection.setRequestMethod("GET");
                    //设置请求超时时间
                    Log.d(TAG, "错误4xxxxxxxxx");

                    httpURLConnection.setConnectTimeout(5000);
                    //获取服务器返回的状态码
                    Log.d(TAG, "错误5xxxxxxxxx");

                    int code = httpURLConnection.getResponseCode();
                    //
                    Log.d(TAG, "错误6xxxxxxxxx");

                    //查看是否有缓存，有则调用缓存
                    File file = new File(getCacheDir(), Base64.encodeToString(path.getBytes(),Base64.DEFAULT)); //Base64是一种加密方法  使用Base64.DEFAULT Base64的一种默认加密方法
//                    File file = new File(getCacheDir(), "image.png");//文件的名字为“image.png” 对文件名 未加密
                    if (file.exists() && file.length() > 0) {
                        //调用缓存
                        Log.d(TAG, "有缓存，调用缓存");
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        Message msg = Message.obtain();//使用msg的静态方法 可以减少对象的建立
                        msg.what = REQUESTSUCCEED;//给msg做标号 0
                        msg.obj = bitmap;
                        handler.sendMessage(msg);
                    } else {
                        //不存在缓存，发起网络请求
                        Log.d(TAG, "缓存不存在，发起网络请求");
                        if (code == 200) {
                            //获取返回的数据    以流的方式返回的
                            InputStream inputStream = httpURLConnection.getInputStream();
                            //将流转换为一段字符串   自定义方法StreamTool
                            //使用工具类将流转换为Bitmap
                            //缓存图片
//                       File file=new File(getCacheDir(),"image.png");
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            int len = -1;
                            byte[] buffer = new byte[1024];
                            while ((len = inputStream.read(buffer)) != -1) {
                                fileOutputStream.write(buffer, 0, len);
                            }


                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            //将数据展示ImageView上(更新ui)   //谷歌工程师规定只能在主线程更新UI   使用Handler
//                        imageView.setImageBitmap(bitmap);
                            //使用Handler                     //obtain()   好像是先判断是否存在Message对象，有则用这个msg，无则新建一个。相比直接建一个新的要省资源
                            Message msg = Message.obtain();//使用msg的静态方法 可以减少对象的建立   //从全局池返回一个新的Message实例。允许我们在许多情况下避免分配新对象。
                            msg.what = REQUESTSUCCEED;//给msg做标号 0
                            msg.obj = bitmap;
                            handler.sendMessage(msg);
                        } else {
                            //用一个Toast提示用户   //Toast是一个view，不能在子线程更新
                            Message msg = new Message();
                            msg.what = REQUESTNOTFOUND;//给msg做标号 1
                            handler.sendMessage(msg);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "错误catchxxxxxxxxx");
                    Message msg = new Message();
                    msg.what = REQUESTEXCEPTION;//给msg做标号 2
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }//通过链接加载图片，存入缓存
    public void search2(View view) {
        new Thread() {
            public void run() {
                try {
                    Log.d(TAG, "当前线程名字：" + Thread.currentThread().getName());
                    String path = editText.getText().toString().trim();
                    //拿到链接地址
                    Log.d(TAG, "错误1xxxxxxxxx");

                    URL url = new URL(path);
                    //拿到httpURLConnection对象  用于发送或接受数据
                    Log.d(TAG, "错误2xxxxxxxxx");

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //设置发送get请求
                    Log.d(TAG, "错误3xxxxxxxxx");

                    httpURLConnection.setRequestMethod("GET");
                    //设置请求超时时间
                    Log.d(TAG, "错误4xxxxxxxxx");

                    httpURLConnection.setConnectTimeout(5000);
                    //获取服务器返回的状态码
                    Log.d(TAG, "错误5xxxxxxxxx");

                    int code = httpURLConnection.getResponseCode();
                    //
                    Log.d(TAG, "错误6xxxxxxxxx");

                    //查看是否有缓存，有则调用缓存
                    File file = new File(getFilesDir(), Base64.encodeToString(path.getBytes(),Base64.DEFAULT)); //Base64是一种加密方法  使用Base64.DEFAULT Base64的一种默认加密方法
                    Log.d(TAG, "数据成功存入内存");
//                    File file = new File(getCacheDir(), "image.png");//文件的名字为“image.png” 对文件名 未加密
                    if (file.exists() && file.length() > 0) {
                        //调用缓存
                        Log.d(TAG, "有内存，调用内存");
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        Message msg = Message.obtain();//使用msg的静态方法 可以减少对象的建立
                        msg.what = REQUESTSUCCEED;//给msg做标号 0
                        msg.obj = bitmap;
                        handler.sendMessage(msg);
                    } else {
                        //不存在缓存，发起网络请求
                        Log.d(TAG, "内存不存在，发起网络请求");
                        if (code == 200) {
                            //获取返回的数据    以流的方式返回的
                            InputStream inputStream = httpURLConnection.getInputStream();


                            //缓存图片
//                       File file=new File(getCacheDir(),"image.png");
                            FileOutputStream fileOutputStream = new FileOutputStream(file);//作用是。。。。？？？？
                            int len = -1;
                            byte[] buffer = new byte[1024];
                            while ((len = inputStream.read(buffer)) != -1) {
                                fileOutputStream.write(buffer, 0, len);
                            }

                            //使用工具类将流转换为Bitmap
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            //将数据展示ImageView上(更新ui)   //谷歌工程师规定只能在主线程更新UI   使用Handler
//                        imageView.setImageBitmap(bitmap);
                            //使用Handler
                            Message msg = Message.obtain();//使用msg的静态方法 可以减少对象的建立
                            msg.what = REQUESTSUCCEED;//给msg做标号 0
                            msg.obj = bitmap;
                            handler.sendMessage(msg);
                        } else {
                            //用一个Toast提示用户   //Toast是一个view，不能在子线程更新
                            Message msg = new Message();
                            msg.what = REQUESTNOTFOUND;//给msg做标号 1
                            handler.sendMessage(msg);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "错误catchxxxxxxxxx");
                    Message msg = new Message();
                    msg.what = REQUESTEXCEPTION;//给msg做标号 2
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }//通过链接加载图片，存入内存
    public void search3(View view) {
        new Thread() {
            public void run() {
                try {
                    Log.d(TAG, "当前线程名字：" + Thread.currentThread().getName());
                    String path = editText.getText().toString().trim();
                    //拿到链接地址
                    Log.d(TAG, "错误1xxxxxxxxx");

                    URL url = new URL(path);
                    //拿到httpURLConnection对象  用于发送或接受数据
                    Log.d(TAG, "错误2xxxxxxxxx");

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //设置发送get请求
                    Log.d(TAG, "错误3xxxxxxxxx");

                    httpURLConnection.setRequestMethod("GET");
                    //设置请求超时时间
                    Log.d(TAG, "错误4xxxxxxxxx");

                    httpURLConnection.setConnectTimeout(5000);
                    //获取服务器返回的状态码
                    Log.d(TAG, "错误5xxxxxxxxx");

                    int code = httpURLConnection.getResponseCode();
                    //
                    Log.d(TAG, "错误6xxxxxxxxx");

                    //查看是否有缓存，有则调用缓存
                    File file = new File(getFilesDir(), Base64.encodeToString(path.getBytes(),Base64.DEFAULT)); //Base64是一种加密方法  使用Base64.DEFAULT Base64的一种默认加密方法
                    Log.d(TAG, "数据成功存入内存");
//                    File file = new File(getCacheDir(), "image.png");//文件的名字为“image.png” 对文件名 未加密
                    if (file.exists() && file.length() > 0) {
                        //调用缓存
                        Log.d(TAG, "有内存，调用内存");
                        final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {  //替代handler更新UI     //原理：先是判断是否在主线程，在则直接执行；不在则用handler发送到主线程执行
                                imageView.setImageBitmap(bitmap);
                            }
                        });

                    } else {
                        //不存在缓存，发起网络请求
                        Log.d(TAG, "内存不存在，发起网络请求");
                        if (code == 200) {
                            //获取返回的数据    以流的方式返回的
                            InputStream inputStream = httpURLConnection.getInputStream();
                            //将流转换为一段字符串   自定义方法StreamTool
                            //使用工具类将流转换为Bitmap
                            //缓存图片
//                       File file=new File(getCacheDir(),"image.png");
                            FileOutputStream fileOutputStream = new FileOutputStream(file);//作用是。。。。？？？？
                            int len = -1;
                            byte[] buffer = new byte[1024];
                            while ((len = inputStream.read(buffer)) != -1) {
                                fileOutputStream.write(buffer, 0, len);
                            }


                            final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            //将数据展示ImageView上(更新ui)   //谷歌工程师规定只能在主线程更新UI   使用Handler
//                        imageView.setImageBitmap(bitmap);
                            //替代handler更新UI
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageView.setImageBitmap(bitmap);
                                }
                            });
                        } else {
                            //用一个Toast提示用户   //Toast是一个view，不能在子线程更新
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "请求资源不存在", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "错误catchxxxxxxxxx");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "服务器忙，请稍后。。。", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }.start();
    }//通过链接加载图片，存入内存,使用runOnUiThread,省去了handler的复杂步骤

}