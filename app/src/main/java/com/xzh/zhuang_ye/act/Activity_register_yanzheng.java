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

public class Activity_register_yanzheng extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_yanzheng);

        //注册点击“确定”
        Button bt_queding = (Button) findViewById(R.id.yanzhengye_queding);
        bt_queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转回“登陆页”
                Intent intent = new Intent(Activity_register_yanzheng.this, Activity_login.class);
                startActivity(intent);
                //结束当前页
                finish();
            }
        });
    }
}
