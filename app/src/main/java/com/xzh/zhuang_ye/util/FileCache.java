package com.xzh.zhuang_ye.util;

import android.app.Activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 谢植焕 on 2018/6/11.
 */

public class FileCache extends Activity{
    public File file;
    public  void fileCache(InputStream inputStream, String name){
         file=new File(getCacheDir(),name);
        try {
            FileOutputStream fileOutputStream= new FileOutputStream(file);
            int len = -1;
            byte[] buffer = new byte[1024];//1kb
            try {
                while ((len=inputStream.read(buffer))!=-1){
                    fileOutputStream.write(buffer,0,len);
                }
                inputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
