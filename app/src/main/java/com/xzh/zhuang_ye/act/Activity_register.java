package com.xzh.zhuang_ye.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.xzh.zhuang_ye.R;
import com.xzh.zhuang_ye.core.http.Http;

/**
 * Created by 谢植焕 on 2018/6/10.
 */

public class Activity_register extends Activity {

    private static final String TAG = "ZhuCeYe";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //获取“name”
        EditText editText1 = (EditText) findViewById(R.id.signin_name);
        String name = editText1.getText().toString();

        //获取“phone”
        EditText editText2 = (EditText) findViewById(R.id.signin_phone);
        String phone = editText2.getText().toString();

        //获取“password”
        EditText editText3 = (EditText) findViewById(R.id.signin_password);
        String password = editText3.getText().toString();

        //注册“确定”
        Button bt_queding = (Button) findViewById(R.id.signin_queding);
        bt_queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交“注册信息”的网络请求
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String s = Http.sendPost("http://47.94.227.99/Amazon/insertUser", "uName=name&uPhone=phone&uPassword=password");
                        Log.d(TAG, "result="+s);
                    }
                }).start();
                //跳转到“短信验证页面”
                Intent intent = new Intent(Activity_register.this,Activity_register_yanzheng.class);
                startActivity(intent);
            }
        });


    }


}
