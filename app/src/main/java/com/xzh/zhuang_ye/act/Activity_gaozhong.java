package com.xzh.zhuang_ye.act;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;

import com.xzh.zhuang_ye.R;

/**
 * Created by 谢植焕 on 2018/6/10.
 */

public class Activity_gaozhong extends Activity {

    //定义一个全局变量
    EditText gaozhong_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaozhong_cidian);
        gaozhong_editText = (EditText) findViewById(R.id.gaozhong_edittext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = gaozhong_editText.getText().toString();
        //   Input_output.saveDataToFile(Gaozhong.class,inputText,data);
    }

    //将EditText里是的数据存在本地“data”文件中
//    public void save(String inputText){
//        FileOutputStream out = null;
//        BufferedWriter writer = null;
//        try{
//            out = openFileOutput("data",Context.MODE_PRIVATE);
//            writer = new BufferedWriter(new OutputStreamWriter(out));
//            writer.write(inputText);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            try{
//                if (writer != null){
//                    writer.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


}
