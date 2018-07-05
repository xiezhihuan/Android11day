package com.xzh.zhuang_ye.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 谢植焕 on 2018/4/1.
 */

public class MD5 {

    public static String md5(String password){
        StringBuffer sb = new StringBuffer();
        // 得到一个信息摘要器
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            // 把每一个byte做一个与运算 0xff
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    sb.append("0");
                }
                sb.append(str);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
