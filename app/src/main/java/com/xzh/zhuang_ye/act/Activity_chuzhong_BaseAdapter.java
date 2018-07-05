package com.xzh.zhuang_ye.act;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.xzh.zhuang_ye.R;

import java.util.List;

/**
 * Created by 谢植焕 on 2018/6/10.
 */


public class Activity_chuzhong_BaseAdapter extends Activity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    String date[]={"数据1","数据2","数据3","数据4","数据5","数据6","数据7"};

    private ListView myListView;//列表
    private List<String> itemList;//数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuzhong_baseadapter_listview);

        myListView = (ListView) findViewById(R.id.lv);
        MyListAdapter myListAdapter = new MyListAdapter();
        myListView.setAdapter(myListAdapter);
    }



    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Listview Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    //自定义的适配器
    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 600000;//设置返回多少行
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        TextView tv;
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            tv = new TextView(Activity_chuzhong_BaseAdapter.this);
            tv.setText("haha"+position);
            return tv;
        }

    }

}
