package com.xzh.zhuang_ye.act.Android11day.day04.day05;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xzh.zhuang_ye.R;
import com.xzh.zhuang_ye.util.FileUtils;
import com.xzh.zhuang_ye.util.ShowToast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 谢植焕 on 2018/7/1.
 */

public class ActivityDay05Two extends Activity {
    private EditText et_path;
    private EditText et_threadCount;
    private Button bt_download;

    private LinearLayout ll_progressBar;
    private static List<ProgressBar> pbLists;
    private ProgressBar progressBar;

    private static int runningThread;

    private EditText et_fileContent;
    private EditText et_fileName;
    private String path;

    private LinearLayout ll_editText;
    private static List<EditText> etList;
    private EditText editText;

    private MyProgressBar myProgressBar;

//    private boolean isFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activityday05two);
            find();
            //添加一个集合用来存进度条的引用
//        pbLists = new ArrayList<ProgressBar>();
//        etList = new ArrayList<EditText>();

            //在主线程建立进度条对象
            myProgressBar = new MyProgressBar();

//            if (isFinish) {    //当下载完成，关掉进度条
//                myProgressBar.close();
//            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "err: " + e.toString());
            ShowToast.errToast(e, getApplicationContext());
        }
    }

    private void find() {
        et_path = (EditText) findViewById(R.id.et_path);
        et_threadCount = (EditText) findViewById(R.id.et_threadCount);
        et_fileContent = (EditText) findViewById(R.id.et_fileContent);
        et_fileName = (EditText) findViewById(R.id.et_fileName);
        bt_download = (Button) findViewById(R.id.bt_download);
        ll_progressBar = (LinearLayout) findViewById(R.id.ll_progressBar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar111);

        ll_editText = (LinearLayout) findViewById(R.id.ll_editText);
        editText = (EditText) findViewById(R.id.edittext);
    }

    public void CreateFile(View view) {
        String fileName = et_fileName.getText().toString().trim();//文件名
        String filecontent = et_fileContent.getText().toString().trim();//文件内容
        String path = Environment.getExternalStorageDirectory().getPath();//文件地址
        File file = FileUtils.creatFile(fileName, path);//建立文件
        Toast.makeText(getApplication(), "文件建立成功", Toast.LENGTH_SHORT);
        FileUtils.rewrite(file, filecontent);//给文件写内容
        Toast.makeText(getApplication(), "文件写入成功", Toast.LENGTH_SHORT);

    }

    public void Test(View view) {
        try {
            etList.clear();//清空集合
            boolean flag = true;
            EditText etView0 = null;
            if (flag) {
                for (int i = 0; i < 3; i++) {
                    switch (i) {
                        case 0:
                            etView0 = (EditText) View.inflate(getApplicationContext(), R.layout.edittext, null);
                            ll_editText.addView(etView0);
                            etList.add(etView0);
                            etList.get(i).setHint("请输入测试线程号");
                            break;
                        case 1:
                            EditText etView1 = (EditText) View.inflate(getApplicationContext(), R.layout.edittext, null);
                            ll_editText.addView(etView1);
                            etList.add(etView1);
                            etList.get(i).setHint("请输入文件名");
                            break;
                        case 2:
                            EditText etView2 = (EditText) View.inflate(getApplicationContext(), R.layout.edittext, null);
                            ll_editText.addView(etView2);
                            etList.add(etView2);
                            etList.get(i).setHint("请输入文件内容");
                            break;
                        default:
                            Log.d("test", "添加EditText控件异常 ");
                    }
                }
                flag = false;
            }
            String path = "http://192.168.199.158:8080/shipin.avi";

            String threadIdd = etView0.getText().toString().trim();
            int threadId = Integer.parseInt(threadIdd);
            Log.d("threadId", "threadId: " + threadId);
            File file = new File(Environment.getExternalStorageDirectory().getPath(), getFilename2(path) + threadId + ".txt");
            if (file.exists() && file.length() > 0) {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader bufr = new BufferedReader(new InputStreamReader(fis));
                String lastPositionn = bufr.readLine();//读取出来的内容就是上一次下载的位置
                int lastPosition = Integer.parseInt(lastPositionn);//int格式的lastPosition
                Toast.makeText(getApplication(), "重新下载", Toast.LENGTH_SHORT);
                fis.close();//关闭流
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "eeee: " + e.toString());
            ShowToast.errToast(e, getApplicationContext());
        }
    }

    //    //在多线程的基础上，加入断点续传
    public void DownloadTwo(View view) {
        try {
            String threadCountt = et_threadCount.getText().toString().trim();
            final int threadCount = Integer.parseInt(threadCountt);
            //按线程个数建立进度条
            myProgressBar.setProgressBar(threadCount);
//        ll_progressBar.removeAllViews();  //添加进度条之前，先清除所有进度条
//        pbLists.clear();                  //添加集合内容之前先清空集合内容
//        for (int i = 0; i < threadCount; i++) {
//            //将定义的progrebar布局转换为一个view对象(转为一个progressBar对象)
//            ProgressBar pbView = (ProgressBar) View.inflate(getApplicationContext(), R.layout.progressbar, null);
//            //动态地添加进度条
//            ll_progressBar.addView(pbView);
//            pbLists.add(pbView);
//        }
            //开启线程
            new Thread() {
                @Override
                public void run() {
                    try {
                        path = et_path.getText().toString().trim();
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
                        if (code == 200) {
                            //获取文件的大小
                            int length = httpURLConnection.getContentLength();
                            Log.d("test1", "length: " + length);
                            //确定正在运行的线程数
                            runningThread = threadCount;
//                        //创建一个大小和服务器一模一样的文件  目的是提前把空间申请出来
                            RandomAccessFile randomAccessFile = new RandomAccessFile(getFilename(path), "rw");
                            randomAccessFile.setLength(length);
//                        //算出每个线程下载的大小
                            int blockSize = length / threadCount;
                            Log.d("test1", "length: 1");

                            for (int threadId = 0; threadId < threadCount; threadId++) {//计算每个线程的开始位置和结束位置
                                //当前线程
                                int startIndex = threadId * blockSize;//开始位置
                                int endIndex = (threadId + 1) * blockSize - 1;
                                //最后一个线程的结束是特殊情况
                                if (threadId == threadCount - 1) {
                                    //如果条件成立，则说明是最后一个线程
                                    endIndex = length - 1;
                                }
                                Log.d("test1", "length: 2");

                                //设置进度条的最大值
                                myProgressBar.setPbMaxSize(threadId, startIndex, endIndex);

                                //如果下载有中断，则从文件中读取上次的位置，从这个位置开始下载
                                boolean isReconnect = false;
                                int restartIndex = 0;
                                int pblastPosition = 0;
                                File file = new File(Environment.getExternalStorageDirectory().getPath(), getFilename2(path) + threadId + ".txt");
                                if (file.exists() && file.length() > 0) {
                                    FileInputStream fis = new FileInputStream(file);
                                    BufferedReader bufr = new BufferedReader(new InputStreamReader(fis));
                                    String lastPositionn = bufr.readLine();//读取出来的内容就是上一次下载的位置
                                    int lastPosition = Integer.parseInt(lastPositionn);//int格式的lastPosition
                                    restartIndex = lastPosition + 1;//将String转换为int；将上次断开的位置定义为这次开始的位置
                                    isReconnect = true;//表示执行了重连网络的操作


                                    myProgressBar.isReconnect(isReconnect);//判断是否重连
//                                    myProgressBar.getLastPosition(threadId, lastPosition - startIndex);//获取重连前进度条的值
                                    myProgressBar.getLastPosition(lastPosition - startIndex);//获取重连前进度条的值

                                    //上次的下载量；进度条的长度；给我们的进度条赋值
//                                pblastPosition = lastPosition - startIndex;
//                            Looper.prepare();
//                            ShowToast toast = new ShowToast();
//                            toast.showToast("重新下载", getApplicationContext());
//                            Log.d("looper", "looper: "+ myLooper());
//                            Looper A=Looper.myLooper();
//                            Looper.loop();
//                            myLooper().getThread();
//                            A.quit();
                                    fis.close();//关闭流
                                } else {
                                    Log.d("test3", "pblastPosition没有被赋值，线程id： " + Thread.currentThread().toString());
                                }
//                            //开启线程去服务器获取文件
                                new DownloadThread(threadId, startIndex, endIndex, isReconnect, restartIndex, pblastPosition).start();
                            }
                        } else {
                            Log.d("test30", "没有获取200，获取服务器状态码失败！！！！！！！！！！！！！！！！！！！！！！！！！！！");
                        }
                    } catch (final Exception e) {
//                    e.printStackTrace();
//                    Looper.prepare();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("test1", "catch错误: " + e.toString());
                                ShowToast.errToast(e, ActivityDay05Two.this);
                            }
                        });

//                    Looper.loop();
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "err: " + e.toString());
            ShowToast.errToast(e, getApplicationContext());
        }
    }

    //在多线程的基础上，加入断点续传    去服务器下载资源的方法
    private class DownloadThread extends Thread {
        //通过构造方法将每个线程的开始位置和结束位置传进来
        private int threadId;
        private int startIndex;
        private int endIndex;
        private boolean isReconnect;
        private int restartIndex;
        private int pblastPosition;

        public DownloadThread(int threadId, int startIndex, int endIndex, boolean isReconnect, int restartIndex, int pblastPosition) {
            this.threadId = threadId;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.isReconnect = isReconnect;
            this.restartIndex = restartIndex;
            this.pblastPosition = pblastPosition;
        }

        private int pbMaxSize;//代表当前线程下载的最大值
        //如果中断过，获取上次的下载位置

        @Override
        public void run() {//实现去服务器下载文件的逻辑
            if (isReconnect != false) {  //判断是都有重新连接下载
                startIndex = restartIndex;
            }
            try {
                //计算当前进度条的最大值
//                        pbMaxSize = endIndex - startIndex;
                //设置一下当前进度条的最大值
//                        pbLists.get(threadId).setMax(pbMaxSize);
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
                Log.d("test1", "length: 2+" + code);

                if (code == 206) {
                    //创建一个随机读写文件的对象
                    InputStream inputStream = httpURLConnection.getInputStream();
                    RandomAccessFile random = new RandomAccessFile(getFilename(path), "rw");
                    random.seek(startIndex);//设置每个线程都从startIndex开始
                    int total = 0;//代表当前线程已下载的字节数
                    //把数据写到文件中
                    int len = -1;              //new byte[1024*1024] 每次写入1M
                    byte[] buffer = new byte[1024 * 1024];//byte[] buffer = new byte[1024]; 每次读取下入1k的数据，因为打开流关闭流需要耗费很多的时间，所以减少开流关流的频率可以提高效率
                    while ((len = inputStream.read(buffer)) != -1) {
                        random.write(buffer, 0, len);
                        total += len;//将每次len的值加到total上
                        int currentThreadPositon = startIndex + total;//每个线程下载到的位置   将该位置存起来，下次再下载的时候按这个位置 继续下载
                        String currentThreadPositon2 = String.valueOf(currentThreadPositon);
                        File file2 = FileUtils.creatFile(getFilename2(path) + threadId + ".txt", Environment.getExternalStorageDirectory().getPath());
                        FileUtils.rewrite(file2, currentThreadPositon2);
//                                RandomAccessFile raf = new RandomAccessFile(getFilename(path) + threadId + ".txt", "rwd"); //把这个数值存到一个文件中
//                                raf.write(currentThreadPositon);
//                                raf.close();
                        //这种方式有可能出现数据存不进去的情况
//                                File file=new File(threadId+".txt");
//                                FileOutputStream fos=new FileOutputStream(file);
//                                fos.write(currentThreadPositon);

                        //设置一下当前进度条的当前值
                        myProgressBar.setProgress(threadId,total);
                        //设置一下当前进度条的当前值
//                                pbLists.get(threadId).setProgress(pblastPosition + total);
                    }
                    random.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "下载完成", Toast.LENGTH_SHORT);
                        }
                    });
                    //当所有线程下载完成后将储存断点位置的文件删除掉
                    synchronized (this) {//锁，避免某两个线程同时执行runningThread--;
                        runningThread--;
                        if (runningThread == 0) {//说明所有线程都执行完成。可以把断点文件删除掉
//                            isFinish = true;
                            for (int ii = 0; ii < 3; ii++) {
                                File deleteFile = new File(getFilename(path) + ii + ".txt");
                                deleteFile.delete();
                            }
                        }
                    }
                    switch (threadId) {
                        case 0:
                            Log.d("test1", "线程1下载完成 ");
                            break;
                        case 1:
                            Log.d("test1", "线程2下载完成 ");
                            break;
                        case 2:
                            Log.d("test1", "线程3下载完成 ");
                            break;
                        default:
                            Log.d("test1", "线程下载完成机制异常 ");
                            break;
                    }
                } else {
                    Log.d("test1", "返回状态码非206，状态码为 " + code);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Looper.prepare();
                ShowToast.errToast(e, ActivityDay05Two.this);
                Looper.loop();
            }
        }
    }

    //获取文件的名字
    private static String getFilename(String path) {
        int start = path.lastIndexOf("/") + 1;
        String substring = path.substring(start);
        String fileName = Environment.getExternalStorageDirectory().getPath() + "/" + substring;

        //创建一个文件
//        File file=new File("123.txt");
//        if (!file.exists()){
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return fileName;
    }

    private static String getFilename2(String path) {
        int start = path.lastIndexOf("/") + 1;
        String fileName = path.substring(start);


        //创建一个文件
//        File file=new File("123.txt");
//        if (!file.exists()){
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return fileName;
    }

    private class MyProgressBar {
        private int pblastPosition=0;
        private boolean isReconnect = false;
        private LinearLayout ll_progressBar;
        private List<ProgressBar> pbLists;

//        Handler handler1;
//        final int thraed0 = 0;
//        final int thraed1 = 1;
//        final int thraed2 = 2;
//        Handler handler = new Handler() {
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case thraed0:
//                        Message msg0 = new Message();
//                        msg0.what = thraed0;//给msg做标号 0
//                        msg0.obj = msg.obj;
//                        handler1.sendMessage(msg0);
//                        break;
//                    case thraed1:
//                        Message msg1 = new Message();
//                        msg1.what = thraed1;//给msg做标号 0
//                        msg1.obj = msg.obj;
//                        handler1.sendMessage(msg1);
//                        break;
//                    case thraed2:
//                        Message msg2 = new Message();
//                        msg2.what = thraed2;//给msg做标号 0
//                        msg2.obj = msg.obj;
//                        handler1.sendMessage(msg2);
//                        break;
//                }
//            }
//        };

        //将进度条对象部署在主线程上
        public MyProgressBar() {
            //添加一个集合用来存进度条的引用
            pbLists = new ArrayList<ProgressBar>();
            etList = new ArrayList<EditText>();
            ll_progressBar = (LinearLayout) findViewById(R.id.ll_progressBar);
        }

        //获取需要建立的进度条个数
        private void setProgressBar(int threadCount) {
            ll_progressBar.removeAllViews();  //添加进度条之前，先清除所有进度条
            pbLists.clear();                //添加集合内容之前先清空集合内容
            for (int i = 0; i < threadCount; i++) {
                //将定义的progrebar布局转换为一个view对象(转为一个progressBar对象)
                ProgressBar pbView = (ProgressBar) View.inflate(getApplicationContext(), R.layout.progressbar, null);
                //动态地添加进度条
                ll_progressBar.addView(pbView);
                pbLists.add(pbView);
            }
        }

        //获取各段线程的下载区间的始末位置，计算出最大值，并给进度条设置最大值
        private void setPbMaxSize(int threadId, int startIndex, int endIndex) {
            int pbMaxSize = endIndex - startIndex;
            pbLists.get(threadId).setMax(pbMaxSize);
        }

        //判断当前下载进度是否为重新连接
        private boolean isReconnect(boolean flag) {
            isReconnect = flag;
            return isReconnect;
        }

        //获取上次断开的位置
        private void getLastPosition( int pblastPosition) {
           this.pblastPosition=pblastPosition;
        }
//        private void getLastPosition(int threadId, int pblastPosition) {
//            Message msg = new Message();
//            msg.what = threadId;//给msg做标号 0
//            msg.obj = pblastPosition;
//            handler.sendMessage(msg);
//        }

        //给进度条赋当前值
        private void setProgress(int threadId,int total) throws Exception {
            if (pblastPosition!=0){
            pbLists.get(threadId).setProgress(pblastPosition+ total);
            } else {
                pbLists.get(threadId).setProgress(total);
            }
//            final int[] pblastPosition = {3};
//            if (isReconnect) {
//                final int thread0 = 0;
//                final int thraed1 = 1;
//                final int thraed2 = 2;
//                handler1 = new Handler() {
//                    public void handleMessage(Message msg) {
//                        switch (msg.what) {
//                            case thread0:
//                                pblastPosition[thread0] = (int) msg.obj;
//                                break;
//                            case thraed1:
//                                pblastPosition[thraed1] = (int) msg.obj;
//                                break;
//                            case thraed2:
//                                pblastPosition[thraed2] = (int) msg.obj;
//                                break;
//                        }
//                    }
//                };
//                pbLists.get(threadId).setProgress(pblastPosition[threadId] + total);
//            } else {
//                pbLists.get(threadId).setProgress(total);
//            }
        }

        //关闭进度条
        private void close() {
            ll_progressBar.removeAllViews();  //清除所有进度条
        }
    }
}
