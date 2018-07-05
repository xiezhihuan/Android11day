package com.xzh.zhuang_ye.act.Android11day.day04.day05;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.xzh.zhuang_ye.R;
import com.xzh.zhuang_ye.core.http.Http;
import com.xzh.zhuang_ye.util.HttpUrlConnectionUtil;
import com.xzh.zhuang_ye.util.StreamTool;

import net.callumtaylor.asynchttp.AsyncHttpClient;
import net.callumtaylor.asynchttp.response.JsonResponseHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by 谢植焕 on 2018/6/22.
 */

public class Activity_day05_1 extends Activity {
    private EditText et_name;
    private EditText et_password;
    private Button bt_get;
    private Button bt_post;
    private static final String TAG = "Activity_day05_1";
    private static int runningThread;
    //静态变量知识：静态变量属于整个类所有，而不是某个对象所有，即被类的所有对象所共享。
//                  静态变量是在在创建的时候创建，动态变量是在对象创建的时候创立


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day05_1);
        Log.d("day24", "进入oncreat方法");
        find();
    }

    private void find() {
        et_name = (EditText) findViewById(R.id.day05_1_name);
        et_password = (EditText) findViewById(R.id.day05_1_password);
        bt_get = (Button) findViewById(R.id.day05_1_get);
        bt_post = (Button) findViewById(R.id.day05_1_post);
    }

    public void get(View view) {
        final String name = et_name.getText().toString().trim();
        final String password = et_password.getText().toString().trim();

        if (!"".equals(name) && !"".equals(password) && name != null && password != null) {
            //如果条件成立，则判断“是否存在该账号”“密码是否正确”
            new Thread() {
                public void run() {
                    try {
                        String path = "http://47.94.227.99/Amazon/getBookList";
                        //拿到链接地址
                        URL url = new URL(path);
                        //拿到httpURLConnection对象  用于发送或接受数据
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        //设置发送get请求
                        httpURLConnection.setRequestMethod("GET");
                        //设置请求超时时间
                        httpURLConnection.setConnectTimeout(5000);
                        //获取服务器返回的状态码
                        InputStream inputStream = httpURLConnection.getInputStream();

                        //使用工具类将流`转换为String
                        String content = StreamTool.readStream(inputStream);
                        Log.d("test30", "content:" + content);

                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }.start();

        } else if ("".equals(name)) {
            showToast("请输入账号");
        } else if ("".equals(password)) {
            showToast("请输入密码");
        } else {
            showToast("检测到异常");
        }

    }

    //使用封装方法
    public void get2(View view) {
        final String name = et_name.getText().toString().trim();
        final String password = et_password.getText().toString().trim();

        if (!"".equals(name) && !"".equals(password) && name != null && password != null) {
            //如果条件成立，则判断“是否存在该账号”“密码是否正确”
            new Thread() {
                public void run() {
                    try {
                        String path = "http://47.94.227.99/Amazon/getBookList";
                        String content = HttpUrlConnectionUtil.get(path);
                        Log.d("test30", "content：" + content);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        } else if ("".equals(name)) {
            showToast("请输入账号");
        } else if ("".equals(password)) {
            showToast("请输入密码");
        } else {
            showToast("检测到异常");
        }
    }

    public void post(View view) {
        String name = et_name.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        final String data = "uPhone=" + name + "&uPassword=" + password;//请求体内容
        Log.d("test27", "data：" + data);
        new Thread() {
            @Override
            public void run() {
                String content = Http.sendPost("http://47.94.227.99/Amazon/login", data);
                Log.d("test27", "content：" + content);
            }
        }.start();


    }

    //httpClient已被弃用
    //方法被弃用
//    public void httpClient_get(View view) {
//        new Thread() {
//            @Override
//            public void run() {
//                String name = et_name.toString().trim();
//                String password = et_password.toString().trim();
//                String path = "http://47.94.227.99/Amazon/getBookList";
//
//                //获取httpclient实例
//                DefaultHttpClient client = new DefaultHttpClient();
//                //准备一个get的请求  定义一个httpget实现
//                HttpGet get = new HttpGet(path);
//                //执行一个get请求
//                HttpResponse response = null;
//                try {
//                    response = client.execute(get);
//
//
//                    //获取服务器返回的状态码
//                    int code = response.getStatusLine().getStatusCode();
//                    if (code == 200) {
//                        //服务器都是以流的形式返回
//                        InputStream inputStream = response.getEntity().getContent();
//                        //将流转换为字符串
//                        String content=StreamTool.readStream(inputStream);
//                        Log.d("test30", "content:"+content);
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        };
//
//    }

    public void AsyncHttpClient_get(View view) {
        Log.d("test30", "执行点击事件");
        String name = et_name.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String path = "http://47.94.227.99/Amazon/getBookList";
        Log.d("test30", "name+password+path" + name + password + path);

        AsyncHttpClient client = new AsyncHttpClient("http://47.94.227.99");
        Log.d("test30", "建立AsyncHttpClient对象");

        client.get("Amazon/getBookList", new JsonResponseHandler() {
            @Override
            public void onSuccess() {
                Log.d("test30", "开始执行get");
                JsonElement result = getContent();
                Log.d("test30", "result: " + result);
            }

            @Override
            public void onFailure() {
                Log.d("test30", "请求失败");
            }
        });
        Log.d("test30", "执行过get");


    }

    public void AsyncHttpClient_post(View view) {
        Log.d("test30", "执行点击事件");
        String name = et_name.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String path = "http://47.94.227.99/Amazon/getBookList";
        Log.d("test30", "name+password+path" + name + password + path);

        AsyncHttpClient client = new AsyncHttpClient("http://47.94.227.99");
        String data = "uPhone=" + name + "&uPassword=" + password;
        Log.d("test30", "data: " + data);

        RequestBody postBody = RequestBody.create(MediaType.parse("application/json"), data);
        Log.d("test30", "执行RequestBody");

        client.post("Amazon/login", postBody, new JsonResponseHandler() {

            @Override
            public void onSuccess() {
                Log.d("test30", "执行onSuccess");
                JsonElement result = getContent();
                Log.d("test30", "result: " + result);
            }
        });


//
//        AsyncHttpClient client = new AsyncHttpClient("http://47.94.227.99");
//        client.post("Amazon/getBookList", new JsonResponseHandler()
//        {
//            @Override public void onSuccess()
//            {
//                JsonElement result = getContent();
//                Log.d("test30", "result: " + result);
//
//            }
//        });
//        Log.d("test30", "执行过post");

    }

    private void showToast(final String string) {
        final String content = string;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //多线程下载的点击事件
    public void Download(View view) {
//        Http.get("http://192.168.199.158:8080/dingding.ppt");

        final String path = "http://192.168.199.158:8080/dingding.ppt";
        final int threadCount = 3;
        //拿到链接地址
        final URL[] url = {null};
        //开启线程
        new Thread() {
            @Override
            public void run() {
                try {
                    url[0] = new URL(path);
                    Log.d("test30", "url[0]: " + url[0]);
                    //拿到httpURLConnection对象  用于发送或接受数据
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url[0].openConnection();
                    //设置发送get请求
                    httpURLConnection.setRequestMethod("GET");
                    //设置请求超时时间
                    httpURLConnection.setConnectTimeout(5000);

                    //获取服务器返回的状态码
                    int code = httpURLConnection.getResponseCode();
                    Log.d("test30", "code: " + code);

                    if (code == 200) {

                        //获取文件的大小
                        int length = httpURLConnection.getContentLength();
                        Log.d("test30", "length: " + length);

//                        //创建一个大小和服务器一模一样的文件  目的是提前把空间申请出来
//                        File dir = Environment.getExternalStorageDirectory();
//                        File file = new File(dir, "dng.ppt");
                        RandomAccessFile randomAccessFile = new RandomAccessFile("dng.ppt", "rw");
                        randomAccessFile.setLength(length);


                        //算出每个线程下载的大小
                        int blockSize = length / threadCount;
                        Log.d("test30", "blockSize: " + blockSize);

                        for (int i = 0; i < threadCount; i++) {//计算每个线程的开始位置和结束位置
                            //当前线程
                            Log.d("test30", "当前线程id: " + i);
                            Log.d(TAG, "当前线程名字：" + Thread.currentThread().getName());

                            int startIndex = i * blockSize;//开始位置
                            Log.d("test30", "startIndex: " + startIndex + "+++++++" + "当前线程id: " + i);

                            int endIndex = (i + 1) * blockSize - 1;
                            Log.d("test30", "endIndex: " + endIndex + "+++++++" + "当前线程id: " + i);

                            //最后一个线程的结束是特殊情况
                            if (i == threadCount - 1) {
                                //如果条件成立，则说明是最后一个线程
                                endIndex = length - 1;
                                Log.d("test30", "endIndex: " + endIndex + "+++++++" + "当前线程id: " + i);
                            }

                            //开启线程去服务器获取文件
                            new DownloadThread(startIndex, endIndex, i).start();
                        }

                    } else {
                        Log.d("test30", "获取服务器状态码失败！！！！！！！！！！！！！！！！！！！！！！！！！！！");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
    //去服务器下载资源的方法
    private static class DownloadThread extends Thread {
        //通过构造方法将每个线程的开始位置和结束位置传进来
        private int startIndex;
        private int endIndex;
        private int threadId;

        public DownloadThread(int startIndex, int endIndex, int threadId) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.threadId = threadId;
        }

        @Override
        public void run() {//实现去服务器下载文件的逻辑
            new Thread() {
                public void run() {
                    try {
                        String path = "http://192.168.199.158:8080/dingding.ppt";
                        Log.d("test30", "path: " + path + "+++++++" + "当前线程id: " + threadId);
                        //拿到链接地址
                        URL url = new URL(path);
                        //拿到httpURLConnection对象  用于发送或接受数据
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        //设置发送get请求
                        httpURLConnection.setRequestMethod("GET");
                        //设置请求超时时间
                        httpURLConnection.setConnectTimeout(5000);
                        //设置一个请求头range，（作用是告诉服务器每个线程下载的开始位置和结束位置）
                        httpURLConnection.setRequestProperty("range", "bytes=" + startIndex + "-" + endIndex);
                        //获取服务器返回的状态码
                        int code = httpURLConnection.getResponseCode();//200 代表获取服务器资源全部完成； 206  请求部分资源完成
                        Log.d("test30", "DownloadThread_code: " + code + "+++++++" + "当前线程id: " + threadId);


                        if (code == 206) {
                            //创建一个随机读写文件的对象
                            InputStream inputStream = httpURLConnection.getInputStream();
//                            File dir = Environment.getExternalStorageDirectory();
//                            File file = new File(dir, "dng.ppt");
//                            RandomAccessFile random = new RandomAccessFile("dng.ppt", "rw");
//                            random.seek(startIndex);//设置每个线程都从startIndex开始写

//                            //把数据写到文件中
//                            int len = -1;
//                            byte[] buffer = new byte[1024];
//                            while ((len = inputStream.read(buffer)) != -1) {
//                                random.write(buffer, 0, len);
//                            }
//                            Log.d("test30", "成功将数据流写入 文件 ");
//
//                            random.close();//关闭流  释放资源

                            Log.d("test30", "DownloadThread_线程id: " + threadId + "——下载完成" + "+++++++" + "当前线程id: " + threadId);

                        }
//                        InputStream inputStream = httpURLConnection.getInputStream();
//                        //使用工具类将流`转换为String
//                        String content = StreamTool.readStream(inputStream);
//                        Log.d("test30", "content:" + content);

                    } catch (Exception e) {
                        Log.d("test30", "content:" + e.toString());
                        e.printStackTrace();
                    }
                }
            }.start();

        }
    }



//
//    //在多线程的基础上，加入断点续传
    public void Download2(View view) {
//        Http.get("http://192.168.199.158:8080/dingding.ppt");

        final String path = "http://192.168.199.158:8080/dingding.ppt";
        final int threadCount = 3;
        final int[] runningThread = new int[1];
        //拿到链接地址
        final URL[] url = {null};
        //开启线程
        new Thread() {
            @Override
            public void run() {
                try {
                    url[0] = new URL(path);
                    Log.d("test30", "url[0]: " + url[0]);
                    //拿到httpURLConnection对象  用于发送或接受数据
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url[0].openConnection();
                    //设置发送get请求
                    httpURLConnection.setRequestMethod("GET");
                    //设置请求超时时间
                    httpURLConnection.setConnectTimeout(5000);

                    //获取服务器返回的状态码
                    int code = httpURLConnection.getResponseCode();
                    Log.d("test30", "code: " + code);





                    if (code == 200) {

                        //获取文件的大小
                        int length = httpURLConnection.getContentLength();
                        Log.d("test30", "length: " + length);

                        //确定正在运行的线程数
                        runningThread[0] =threadCount;

//                        //创建一个大小和服务器一模一样的文件  目的是提前把空间申请出来
//                        File dir = Environment.getExternalStorageDirectory();
//                        File file = new File(dir, "dng.ppt");
                        RandomAccessFile randomAccessFile = new RandomAccessFile(getFilename(path), "rw");
                        randomAccessFile.setLength(length);

                        //算出每个线程下载的大小
                        int blockSize = length / threadCount;
                        Log.d("test30", "blockSize: " + blockSize);

                        for (int i = 0; i < threadCount; i++) {//计算每个线程的开始位置和结束位置
                            //当前线程
                            Log.d("test30", "当前线程id: " + i);
                            Log.d(TAG, "当前线程名字：" + Thread.currentThread().getName());

                            int startIndex = i * blockSize;//开始位置
                            Log.d("test30", "startIndex: " + startIndex + "+++++++" + "当前线程id: " + i);

                            int endIndex = (i + 1) * blockSize - 1;
                            Log.d("test30", "endIndex: " + endIndex + "+++++++" + "当前线程id: " + i);

                            //最后一个线程的结束是特殊情况
                            if (i == threadCount - 1) {
                                //如果条件成立，则说明是最后一个线程
                                endIndex = length - 1;
                                Log.d("test30", "endIndex: " + endIndex + "+++++++" + "当前线程id: " + i);
                            }

                            //开启线程去服务器获取文件
                            new DownloadThread2(startIndex, endIndex, i).start();

                        }
                    } else {
                        Log.d("test30", "没有获取200，获取服务器状态码失败！！！！！！！！！！！！！！！！！！！！！！！！！！！");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    //在多线程的基础上，加入断点续传    去服务器下载资源的方法
    private static class DownloadThread2 extends Thread {
        //通过构造方法将每个线程的开始位置和结束位置传进来
        private int startIndex;
        private int endIndex;
        private int threadId;

        public DownloadThread2(int startIndex, int endIndex, int threadId) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.threadId = threadId;
        }

        @Override
        public void run() {//实现去服务器下载文件的逻辑
            new Thread() {
                public void run() {
                    try {
                        String path = "http://192.168.199.158:8080/dingding.ppt";
                        Log.d("test30", "path: " + path + "+++++++" + "当前线程id: " + threadId);
                        //拿到链接地址
                        URL url = new URL(path);
                        //拿到httpURLConnection对象  用于发送或接受数据
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        //设置发送get请求
                        httpURLConnection.setRequestMethod("GET");
                        //设置请求超时时间
                        httpURLConnection.setConnectTimeout(5000);

                        //如果下载有中断，则从文件中读取上次的位置，从这个位置开始下载
                        File file=new File(getFilename(path)+threadId+".txt");
                        if (file.exists()&&file.length()>0){
                            FileInputStream fis=new FileInputStream(file);
                            BufferedReader bufr=new BufferedReader(new InputStreamReader(fis));
                            String lastPosition=bufr.readLine();//读取出来的内容就是上一次下载的位置
                            startIndex=Integer.parseInt(lastPosition)+1;//将String转换为int；将上次断开的位置定义为这次开始的位置

                            //给我们的进度条赋值
                            
                            fis.close();//关闭流
                        }

                        //设置一个请求头range，（作用是告诉服务器每个线程下载的开始位置和结束位置）
                        httpURLConnection.setRequestProperty("range", "bytes=" + startIndex + "-" + endIndex);
                        //获取服务器返回的状态码
                        int code = httpURLConnection.getResponseCode();//200 代表获取服务器资源全部完成； 206  请求部分资源完成
                        Log.d("test30", "DownloadThread_code: " + code + "+++++++" + "当前线程id: " + threadId);

                        if (code == 206) {
                            //创建一个随机读写文件的对象
                            InputStream inputStream = httpURLConnection.getInputStream();
                            RandomAccessFile random = new RandomAccessFile(getFilename(path), "rw");
                            random.seek(startIndex);//设置每个线程都从startIndex开始写



                            int total=0;//代表当前线程已下载的字节数
//                            //把数据写到文件中
                            int len = -1;              //new byte[1024*1024] 每次写入1M
                            byte[] buffer = new byte[1024*1024];//byte[] buffer = new byte[1024]; 每次读取下入1k的数据，因为打开流关闭流需要耗费很多的时间，所以减少开流关流的频率可以提高效率
                            while ((len = inputStream.read(buffer)) != -1) {
                                random.write(buffer, 0, len);
                                total+=len;//将每次len的值加到total上

                                //每个线程下载到的位置   将该位置存起来，下次再下载的时候按这个位置 继续下载
                                int currentThreadPositon=startIndex+total;

                                //把这个数值存到一个文件中
                                RandomAccessFile raf = new RandomAccessFile(getFilename(path)+threadId+".txt", "rwd");
                                raf.write(currentThreadPositon);
                                raf.close();

                                //这种方式有可能出现数据存不进去的情况
//                                File file=new File(threadId+".txt");
//                                FileOutputStream fos=new FileOutputStream(file);
//                                fos.write(currentThreadPositon);

                            }
                            random.close();
                            Log.d("test30", "DownloadThread_线程id: " + threadId + "——下载完成" + "+++++++" + "当前线程id: " + threadId);
                            //当所有线程下载完成后将储存断点位置的文件删除掉
                            synchronized (this){
                                runningThread--;
                                if (runningThread==0){//说明所有线程都执行完成。可以把断点文件删除掉l
                                    for (int i=0;i<3;i++){
                                        File deleteFile=new File(getFilename(path)+i+".txt");
                                        deleteFile.delete();
                                    }
                                }

                            }
                        }
//                        InputStream inputStream = httpURLConnection.getInputStream();
//                        //使用工具类将流`转换为String
//                        String content = StreamTool.readStream(inputStream);
//                        Log.d("test30", "content:" + content);

                    } catch (Exception e) {
                        Log.d("test30", "content:" + e.toString());
                        e.printStackTrace();

                    }
                }
            }.start();

        }
    }

    //获取文件的名字
    public static String getFilename(String path){
        int start=path.lastIndexOf("/")+1;
        return path.substring(start);
    }
}
