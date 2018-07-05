package com.xzh.zhuang_ye.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.xzh.zhuang_ye.R;

/**
 * Created by 谢植焕 on 2018/6/10.
 */

public class Activity_xiaoxue extends Activity {
    Button bt_android_11day;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaoxue);
        findView();
        //初始化
//        init();
    }
    private void findView() {
        bt_android_11day= (Button) findViewById(R.id.android11day);
        findViewById(R.id.android11day).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_xiaoxue.this,Activity_adroid11day.class);
                startActivity(intent);
            }
        });


    }
}
