package com.xzh.zhuang_ye.util;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 谢植焕 on 2018/6/14.
 */

public class XmlParseUtils {
    public static List<News> parserXml(InputStream inputStream) throws Exception {
        List<News> newsLists = null;
        News news = null;
        //获取xml的解析器
        XmlPullParser xmlPullParser = Xml.newPullParser();
        //设置解析器 要解析的内容
        xmlPullParser.setInput(inputStream, "utf-8");
        //获取解析的事件类型
        int type = xmlPullParser.getEventType();
        //不停地向下解析
        Log.d("Activity_day04_4", "进入xml解析方法");
        while (type != XmlPullParser.END_DOCUMENT) {
            //判断一下解析的是开始节点 还是 结束节点
            switch (type) {
                case XmlPullParser.START_TAG: //解析开始节点
                    if ("channel".equals(xmlPullParser.getName())) {
                        //创建一个list集合
                        newsLists = new ArrayList<News>();
                    } else if ("item".equals(xmlPullParser.getName())) {
                        news = new News();
                    } else if ("title".equals(xmlPullParser.getName())) {
                        news.setTitle(xmlPullParser.nextText());
                    } else if ("description".equals(xmlPullParser.getName())) {
                        news.setDescription(xmlPullParser.nextText());
                    } else if ("image".equals(xmlPullParser.getName())) {
                        news.setImage(xmlPullParser.nextText());
                    } else if ("type".equals(xmlPullParser.getName())) {
                        news.setType(xmlPullParser.nextText());
                    } else if ("comment".equals(xmlPullParser.getName())) {
                        news.setComment(xmlPullParser.nextText());
                    }
                    break;
                case XmlPullParser.END_TAG: //解析结束节点
                    if ("item".equals(xmlPullParser.getName())) {
                        //将javabean添加到集合
                        newsLists.add(news);
                    }

                    break;
            }
            type=xmlPullParser.next();
        }
        return newsLists;
    }
}
