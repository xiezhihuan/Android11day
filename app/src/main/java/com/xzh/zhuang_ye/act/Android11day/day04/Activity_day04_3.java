package com.xzh.zhuang_ye.act.Android11day.day04;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xzh.zhuang_ye.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 谢植焕 on 2018/6/14.
 */


public class Activity_day04_3 extends Activity {
    Button button1;
    Button button2;
    TextView textView;
    Button button3;
    Button button4;
    TimerTask timerTask;
    Timer timer;
    private static final String TAG = "Activity_day04_3";

    protected void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day04_3);
        find();
        Log.d("start3", "执行onCreat");


//        handler();//更新UI
    }

    @Override//当activity销毁时执行
    protected void onDestroy() {
        super.onDestroy();
        timerTask.cancel();//取消执行    如果没有取消，退出这个activity后还会继续执行
        timer.cancel();//取消执行
    }

    private void find() {
        button1 = (Button) findViewById(R.id.day04_3_bt1);
        button2 = (Button) findViewById(R.id.day04_3_bt2);
        button3 = (Button) findViewById(R.id.day04_3_bt3);
        textView = (TextView) findViewById(R.id.day04_3_tv4);
    } //实例化各种键


    public void handlertimer(View view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("q1q1", "点击后5秒打印，handler定时器执行");
                Toast.makeText(getApplicationContext(), "5秒后打印，handler定时器，哈哈哈哈哈", Toast.LENGTH_SHORT).show();
            }
        }, 5000);
    }

    public void timertimer(View view) {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(Activity_day04_3.this, "点击后5秒打印，timer定时器,哈哈哈哈哈", Toast.LENGTH_SHORT).show();
                Looper.loop();
                Log.d("q1q1", "点击后3秒打印，timer定时器执行");
            }
        };
//        Log.d(TAG, "错误1");
        timer = new Timer();
        timer.schedule(timerTask, 3000);
    }

    public void timertimer2(View view) {
        timerTask = new TimerTask() {
            @Override
            public void run() {
//                Looper.prepare();   //没有looper的话，不能用toast
//                Toast.makeText(Activity_day04_3.this,"5秒后打印，timer定时器,n秒后重复，哈哈哈哈哈",Toast.LENGTH_SHORT).show();
                Log.d("q1q1", "点击3秒后打印，timer定时器，3秒后每隔1秒执行一次");
//                Looper.loop();
            }
        };
//        Log.d(TAG, "错误1");
        timer = new Timer();
        timer.schedule(timerTask, 3000, 1000);//3秒后，每隔一秒执行一次
    }

    public void handlerupdateui(View view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("q1q1", "点击后2秒更新UI，handler定时器执行");
                textView.setText("handler延迟更新了更新UI");
            }
        }, 2000);
    }

}
