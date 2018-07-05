package com.xzh.zhuang_ye.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 谢植焕 on 2018/7/1.
 */

public class ShowToast{
    public static void errToast(Exception e,Context context){
        try {
            switch (e.toString()) {
                case "java.net.SocketException: Software caused connection abort":
                    Log.d("test1", "网络链接中断 " );
                    Log.d("test1", " e:"+e.toString() );
                    Toast.makeText(context, "网络链接中断", Toast.LENGTH_SHORT).show();
                    break;
                case "java.net.ConnectException: Network is unreachable":
                    Log.d("test1", "网络未连接 " );
                    Log.d("test1", " e:"+e.toString() );
                    Toast.makeText(context, "网络未连接", Toast.LENGTH_SHORT).show();
                    break;
                case "java.net.NoRouteToHostException: No route to host":
                    Log.d("test1", "没有路由主机（服务器未开启） " );
                    Log.d("test1", " e:"+e.toString() );
                    Toast.makeText(context, "没有路由主机（服务器未开启）", Toast.LENGTH_SHORT).show();
                    break;
                case "java.net.SocketTimeoutException: connect timed out":
                    Log.d("test1", "连接超时" );
                    Log.d("test1", " e:"+e.toString() );
                    Toast.makeText(context, "连接超时", Toast.LENGTH_SHORT).show();
                    break;
                case "java.lang.NullPointerException: Attempt to invoke a virtual method on a null object reference":
                    Log.d("test1", "在null对象引用上调用虚拟方法" );
                    Log.d("test1", " e:"+e.toString() );
                    Toast.makeText(context, "程序错误", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Log.d("test1", "错误，无法判断错误类型 " );
                    Log.d("test1", " e:"+e.toString() );
                    Toast.makeText(context, "错误，无法判断错误类型", Toast.LENGTH_SHORT).show();
                    break;

            }
        } catch (Exception ee) {
            ee.printStackTrace();
            Log.d("test1", "errToast： " + ee.toString());
            Log.d("test1", "errToast：ShowToast未知错误 " );
        }
    }

}
