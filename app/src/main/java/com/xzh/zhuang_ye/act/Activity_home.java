package com.xzh.zhuang_ye.act;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.xzh.zhuang_ye.R;


/**
 * Created by 谢植焕 on 2018/6/10.
 */

public class Activity_home extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mImageButton;

  //  private RollPagerView mRollViewPager;//轮播图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findView();

        init();
        //隐藏导航栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }


        //轮播图
        RollPagerView mRollViewPager = (RollPagerView) findViewById(R.id.rollPager);
        mRollViewPager.setAdapter(new TestNomalAdapter());

        mRollViewPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(Activity_home.this, "Item " + position + " clicked", Toast.LENGTH_SHORT).show();
            }
        });


        //注册菜单图标的的点击事件,点击菜单弹出侧滑菜单
        final DrawerLayout mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        final ImageButton bt_menu= (ImageButton) findViewById(R.id.bt_menu);
        bt_menu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);  //调用该方法打开“侧滑菜单”
            }
        });

        //注册撤销活动键X
        ImageButton bt_x= (ImageButton) findViewById(R.id.bt_x);
        bt_x.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //通过点击图片按钮“小学”进入第二个页面
        ImageButton bt_xiaoxue=(ImageButton) findViewById(R.id.bt_xiaoxue);
        bt_xiaoxue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity_home.this,Activity_xiaoxue.class);
                startActivity(intent);
            }
        });

        //点击“初中”，进行listview demo  //LinearLayout的整体点击
        LinearLayout chuzhong=(LinearLayout) findViewById(R.id.L_chuzhong);
        chuzhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity_home.this,Activity_chuzhong_BaseAdapter.class);
                startActivity(intent);
            }
        });

        //注册侧滑菜单里item的点击事件

        NavigationView navigationView=(NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.item1:
                        //Toast.makeText(this,"你点击了笔记", Toast.LENGTH_SHORT).show();  为什么不能处理这条方法？
                    case R.id.item2:
                    case R.id.item3:
                        Intent intent3=new Intent(Intent.ACTION_DIAL);
                        intent3.setData(Uri.parse("tel:18810447748"));
                        startActivity(intent3);
                    case R.id.item4:
                    case R.id.item5:
                        mDrawerLayout.closeDrawers();
                }
                return true;
            }
        });

        //注册“高中”的点击事件
        LinearLayout bt_gaozhong = (LinearLayout) findViewById(R.id.bt_gaozhong);
        bt_gaozhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显式的页面跳转
                Intent intent = new Intent(Activity_home.this,Activity_gaozhong.class);
                startActivity(intent);
            }
        });

        //注册“大学”的点击事件
        LinearLayout bt_daxue = (LinearLayout) findViewById(R.id.bt_daxue);
        bt_daxue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显式的页面跳转
                Intent intent = new Intent(Activity_home.this,Activity_daxue_ArrayAdapter.class);
                startActivity(intent);
            }
        });

        //注册“词典”的点击
        LinearLayout bt_cidian= (LinearLayout) findViewById(R.id.bt_cidian);
        bt_cidian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_home.this,Activity_cidian.class);
                startActivity(intent);
            }
        });

        //注册“听力”的点击
        LinearLayout bt_tingli= (LinearLayout) findViewById(R.id.bt_tingli);
        bt_tingli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_home.this,Activity_tingli_SampleAdapter.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void findView() {
        mImageButton = (ImageButton) findViewById(R.id.bt_xiaoxue);

        findViewById(R.id.bt_x).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_x:

                break;

        }
    }


    //轮播图
    private class TestNomalAdapter extends StaticPagerAdapter {
        private int[] imgs = {
                R.drawable.roll_img1,
                R.drawable.roll_img2,
                R.drawable.roll_img3,
                R.drawable.roll_img4,
        };

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }
    }

}