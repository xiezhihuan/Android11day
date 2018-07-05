package com.xzh.zhuang_ye.act.Android11day.day04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.xzh.zhuang_ye.R;

/**
 * Created by 谢植焕 on 2018/6/11.
 */

public class Activity_day04 extends Activity {
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day04);
        findView();
        //初始化
//        init();
    }
    private void findView() {
        button1 = (Button) findViewById(R.id.day04_bt_wangye);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_day04_1.class);
                startActivity(intent);
            }
        });

        button2 = (Button) findViewById(R.id.day04_bt_img);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_day04_2.class);
                startActivity(intent);
            }
        });

        button3 = (Button) findViewById(R.id.day04_bt_3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_day04_3.class);
                startActivity(intent);
            }
        });

        button4 = (Button) findViewById(R.id.day04_bt_4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_day04_4.class);
                startActivity(intent);
            }
        });


    }
}