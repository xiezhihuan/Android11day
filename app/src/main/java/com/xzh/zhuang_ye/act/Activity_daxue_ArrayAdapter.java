package com.xzh.zhuang_ye.act;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xzh.zhuang_ye.R;

/**
 * Created by 谢植焕 on 2018/5/30.
 */

public class Activity_daxue_ArrayAdapter extends Activity {
    String objects[]={"老张","老刘","老谢","老板","老公","老铁"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daxue_arrayadapter_listview);
        //用listview通过arrayadapter的方式排列字符数组
        ListView lv= (ListView) findViewById(R.id.lv2);
        //方法一
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,R.layout.activity_daxue_arrayadapter_item,objects);
        //方法二
//        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,R.layout.activity_1,R.id.TextView2222,objects);
        lv.setAdapter(adapter1);


//        TextView bt_chaxun= (TextView) findViewById(R.id.TextView2222);
//        bt_chaxun.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Listview_ArrayAdapter_demo.this,Gaozhong.class);
//                startActivity(intent);
//            }
//        });


    }
}