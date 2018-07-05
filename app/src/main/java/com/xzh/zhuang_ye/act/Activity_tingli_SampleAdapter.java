package com.xzh.zhuang_ye.act;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.xzh.zhuang_ye.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 谢植焕 on 2018/6/10.
 */

public class Activity_tingli_SampleAdapter extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tingli_sampleadapter);

        //给simpleaddpter准备数据
        List< Map<String, String>> data=new ArrayList<Map<String, String>>();
        Map<String, String> map1=new HashMap<String, String>();
        map1.put("name","张三");
        map1.put("phone","1888832428");

        Map<String, String> map2=new HashMap<String, String>();
        map2.put("name","刘备");
        map2.put("phone","111000");

        Map<String, String> map3=new HashMap<String, String>();
        map3.put("name","曹操");
        map3.put("phone","102120");

        //将map加到集合中
        data.add(map1);
        data.add(map2);
        data.add(map3);

        ListView lv = (ListView) findViewById(R.id.sampleAdapter);
        //this上下文  R.layout.item显示文本的Textview布局  new String[]{"name","phone"}键值对key   new int[]{R.id.tv1,R.id.tv2}两个Textview的id
        SimpleAdapter sa=new SimpleAdapter(this,data,R.layout.activity_tingli_sampleadapter_item,new String[]{"name","phone"},new int[]{R.id.tv1,R.id.tv2});

        //设置数据适配器
        lv.setAdapter(sa);

    }
}
