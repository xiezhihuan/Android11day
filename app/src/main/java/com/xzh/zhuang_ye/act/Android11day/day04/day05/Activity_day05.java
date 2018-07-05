package com.xzh.zhuang_ye.act.Android11day.day04.day05;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.xzh.zhuang_ye.R;

/**
 * Created by 谢植焕 on 2018/6/22.
 */

public class Activity_day05 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day05);
    }


    //“两种get/post请求”按钮的点击事件
    public void turn(View view) {
        Intent intent = new Intent(Activity_day05.this, Activity_day05_1.class);
        startActivity(intent);
        //intent只能用一次
//
//        switch (view.getId()){
//            case R.id.httpRequest:
//                Intent intent = new Intent(Activity_day05.this, Activity_day05_1.class);
//                startActivity(intent);
//                break;
//            case R.id.moreThreadDownload:
//                Intent intent = new Intent(Activity_day05.this, Activity_day04.class);
//                startActivity(intent);
//                break;
//        }//
    }
    //“多线程下载”按钮的点击事件
    public void turn2(View view) {
        Intent intent = new Intent(Activity_day05.this, ActivityDay05Two.class);
        startActivity(intent);

    }
}
