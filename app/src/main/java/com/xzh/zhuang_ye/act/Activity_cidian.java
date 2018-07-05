package com.xzh.zhuang_ye.act;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.xzh.zhuang_ye.R;
import com.xzh.zhuang_ye.core.http.Http;

import static com.xzh.zhuang_ye.util.MD5.md5;

/**
 * Created by 谢植焕 on 2018/6/10.
 */

public class Activity_cidian extends Activity {

    private static final String TAG = "Cidian";

    EditText input_word;
    EditText et_output;
    //需查询的“单词”
    String word;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            et_output.setText("原文："+ Http.src +"\n译文："+ Http.dst);
        }
    };
    public void handler(){
        handler.sendEmptyMessage(1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cidain);

        //获取“单词”
        input_word = (EditText) findViewById(R.id.cidian_input);


        et_output = (EditText) findViewById(R.id.cidian_output);

        //注册“查询”按键
        Button bt_chaxun= (Button) findViewById(R.id.cidian_chaxun);
        bt_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word = input_word.getText().toString();
                Log.d("word=", word);
                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        //设置salt“随机数”
                        String salt="602666178";
                        //密钥
                        String passKey="N7h_UHHaiu8NtR2gyW7y";
                        //获得签名“sign”     “a”+“b”="ab"
                        String pro_sign="20180401000142028"+word+salt+passKey;
                        String sign=md5(pro_sign);
                        //发送网络请求
                        String s = Http.get(
                                "http://api.fanyi.baidu.com/api/trans/vip/translate?q="+ word +"&from=en&to=zh&appid=20180401000142028&salt="+ salt +"&sign="+sign);
                        Log.d("s",s);
                        Http.parse(s);

                        handler();



                    }
                }).start();

                //将查询结果显示在“EditText”上


            }
        });

    }
}
