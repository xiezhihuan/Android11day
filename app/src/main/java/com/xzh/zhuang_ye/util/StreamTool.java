package com.xzh.zhuang_ye.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by 谢植焕 on 2018/6/11.
 */

public class StreamTool {
    public static String readStream(InputStream inputStream) throws Exception {
        //定义一个内存输出流
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        int len=-1;
        byte[] buffer = new byte[1024];//1kb
        while ((len=inputStream.read(buffer))!=-1){
            byteArrayOutputStream.write(buffer,0,len);
        }
        //用完后，关闭流
        inputStream.close();
        String content=new String(byteArrayOutputStream.toByteArray());

        return content;

    }
}
