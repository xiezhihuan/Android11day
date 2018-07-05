package com.xzh.zhuang_ye.util;

/**
 * Created by 谢植焕 on 2018/7/2.
 */

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FileUtils {

    /**
     * 创建文件
     *
     * @throws IOException
     */
    public static File creatFile(String fileName, String filePath) {
        File file = null;
        try {
            file = new File(filePath,fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("creatTxtFile", "e:" + e.toString());
        }
        return file;
    }


    /**
     * 写文件(不保留原内容)
     *
     * @param content 新内容
     * @throws IOException
     */
    public static boolean rewrite(File file, String content) {
        boolean flag = false;
        try {
            // 将文件读入输入流
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();
            buf.append(content);

            FileOutputStream fos = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
            pw.close();
            fos.close();
            br.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("rewrite", "e:" + e.toString());

        }
        return flag;
    }

    /**
     * 写文件(保留原内容)
     *
     * @param content 新内容
     * @throws IOException
     */
    public static boolean writeFile(File file, String content) {
        boolean flag = false;
        String temp = "";
        // 文件路径
        try {
            // 将文件读入输入流
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();
            // 保存该文件原有的内容
            for (int j = 1; (temp = br.readLine()) != null; j++) {
                buf = buf.append(temp);
                // System.getProperty("line.separator")
                // 行与行之间的分隔符 相当于“\n”
                buf = buf.append(System.getProperty("line.separator"));
            }
            buf.append(content);

            FileOutputStream fos = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
            pw.close();
            fos.close();
            br.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("writeFile", "e" + e.toString());

        }
//        finally {
//            if (pw != null) {
//                pw.close();
//            }
//            if (fos != null) {
//                fos.close();
//            }
//            if (br != null) {
//                br.close();
//            }
//            if (isr != null) {
//                isr.close();
//            }
//            if (fis != null) {
//                fis.close();
//            }
//        }
        return flag;
    }

}
