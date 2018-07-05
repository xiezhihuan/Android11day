package com.xzh.zhuang_ye.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.xzh.zhuang_ye.R;
import com.xzh.zhuang_ye.act.Android11day.day04.Activity_day04;
import com.xzh.zhuang_ye.act.Android11day.day04.day05.Activity_day05;

import static com.xzh.zhuang_ye.R.id.day04;
import static com.xzh.zhuang_ye.R.id.day05;

/**
 * Created by 谢植焕 on 2018/6/10.
 */

public class Activity_adroid11day extends Activity {
    Button bt_day04;
    Button bt_day05;
    Button bt_day06;
    Button bt_day07;
    Button bt_day08;
    Button bt_day09;
    Button bt_day10;
    Button bt_day11;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adroid11day);
        findView();
    }
    private void findView() {
        bt_day04 = (Button) findViewById(day04);
        bt_day04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_adroid11day.this, Activity_day04.class);
                startActivity(intent);
            }
        });

        bt_day05 = (Button) findViewById(day05);
        bt_day05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_adroid11day.this, Activity_day05.class);
                startActivity(intent);
            }
        });

    }


//        bt_day05= (Button) findViewById(R.id.day05);
//        findViewById(R.id.android11day).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Activity_adroid11day.this,Activity_adroid11day.class);
//                startActivity(intent);
//            }
//        });
//        bt_day06= (Button) findViewById(R.id.day06);
//        findViewById(R.id.android11day).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Activity_adroid11day.this,Activity_adroid11day.class);
//                startActivity(intent);
//            }
//        });
//        bt_day07= (Button) findViewById(R.id.day07);
//        findViewById(R.id.android11day).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Activity_adroid11day.this,Activity_adroid11day.class);
//                startActivity(intent);
//            }
//        });
//        bt_day08= (Button) findViewById(R.id.day08);
//        findViewById(R.id.android11day).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Activity_adroid11day.this,Activity_adroid11day.class);
//                startActivity(intent);
//            }
//        });
//        bt_day09= (Button) findViewById(R.id.day09);
//        findViewById(R.id.android11day).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Activity_adroid11day.this,Activity_adroid11day.class);
//                startActivity(intent);
//            }
//        });
//        bt_day10= (Button) findViewById(R.id.day10);
//        findViewById(R.id.android11day).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Activity_adroid11day.this,Activity_adroid11day.class);
//                startActivity(intent);
//            }
//        });
//        bt_day11= (Button) findViewById(R.id.day11);
//        findViewById(R.id.android11day).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Activity_adroid11day.this,Activity_adroid11day.class);
//                startActivity(intent);
//            }
//        });

}

