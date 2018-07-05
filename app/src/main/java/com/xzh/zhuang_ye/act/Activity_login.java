package com.xzh.zhuang_ye.act;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xzh.zhuang_ye.R;
import com.xzh.zhuang_ye.core.http.Http;

/**
 * Created by 谢植焕 on 2018/6/10.
 */

public class Activity_login extends Activity {
    private static final String TAG = "Login_Activity";


    //把定义“phone”“password”定义为全局变量(因为局部变量在此处不可用)
    String phone;
    String password;

    //post请求的返回数据
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //点击“新用户注册”
        TextView tv_zhuce = (TextView) findViewById(R.id.zhuce);
        tv_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_login.this, Activity_register.class);
                startActivity(intent);
            }
        });


        //点击“登陆”，获取“phone”和“password”输入的内容，提交网络请求后，跳转到第二页面
        ImageButton bt_denglu = (ImageButton) findViewById(R.id.bt_denglu);
        bt_denglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取“phone”内容
                EditText editText_phone = (EditText) findViewById(R.id.login_phone);
                phone = editText_phone.getText().toString();

                //获取“password”内容
                EditText editText_password = (EditText) findViewById(R.id.login_password);
                password = editText_password.getText().toString();

                //如果“phone”“password”不为空，才会跳转到“主页”
                if (!"".equals(phone) && !"".equals(password) && phone != null && password != null) {
                    //如果条件成立，则判断“是否存在该账号”“密码是否正确”
                    //   int flag=1;``
                    // String a=s[0];
                    //if (a)
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //发送网络请求
                            s = Http.sendPost("http://47.94.227.99/Amazon/login", "uPhone=" + phone + "&uPassword=" + password);
                            Log.d(TAG, "result=" + s);
                        }
                    }).start();

                    //跳转到“主页”
                    Intent intent = new Intent(Activity_login.this, Activity_home.class);
                    startActivity(intent);
                } else if ("".equals(phone)) {
                    Toast.makeText(Activity_login.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                } else if ("".equals(password)) {
                    Toast.makeText(Activity_login.this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Activity_login.this, "xzh:检测到异常", Toast.LENGTH_SHORT).show();
                }

            }


        });

        //点击“服务条款”，进入到百度首页
        TextView fuwutiaokuan = (TextView) findViewById(R.id.fuwutiaokuan);
        fuwutiaokuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });

        //记住密码
        CheckBox checkBox = (CheckBox) findViewById(R.id.rememberPassword);
        if (checkBox.isChecked()) {
            //执行保存密码

        } else {
            //执行“不保存密码”

        }


    }
}


