package com.speedata.wharfsupplies.utils;


import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * Created by xu on 2017/8/14.
 */

public class ExcelReader {


    public static String readXLSX(String path) {

        String jisuan = "";

        String str = "";
        String v = null;
        boolean flat = false;
        List<String> ls = new ArrayList<String>();
        try {
            ZipFile xlsxFile = new ZipFile(new File(path));
            ZipEntry sharedStringXML = xlsxFile
                    .getEntry("xl/sharedStrings.xml");
            InputStream inputStream = xlsxFile.getInputStream(sharedStringXML);
            XmlPullParser xmlParser = Xml.newPullParser();
            xmlParser.setInput(inputStream, "utf-8");
            int evtType = xmlParser.getEventType();
            while (evtType != XmlPullParser.END_DOCUMENT) {
                switch (evtType) {
                    case XmlPullParser.START_TAG:
                        String tag = xmlParser.getName();
                        if (tag.equalsIgnoreCase("t")) {
                            ls.add(xmlParser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                evtType = xmlParser.next();
            }
            ZipEntry sheetXML = xlsxFile.getEntry("xl/worksheets/sheet1.xml");
            InputStream inputStreamsheet = xlsxFile.getInputStream(sheetXML);
            XmlPullParser xmlParsersheet = Xml.newPullParser();
            xmlParsersheet.setInput(inputStreamsheet, "utf-8");
            int evtTypesheet = xmlParsersheet.getEventType();
            while (evtTypesheet != XmlPullParser.END_DOCUMENT) {
                switch (evtTypesheet) {
                    case XmlPullParser.START_TAG:
                        String tag = xmlParsersheet.getName();
                        if (tag.equalsIgnoreCase("row")) {
                        } else if (tag.equalsIgnoreCase("c")) {
                            String t = xmlParsersheet.getAttributeValue(null, "t");
                            if (t != null) {
                                flat = true;
                                System.out.println(flat + "有");
                            } else {
                                System.out.println(flat + "没有");
                                flat = false;
                            }
                        } else if (tag.equalsIgnoreCase("v")) {
                            v = xmlParsersheet.nextText();
                            if (v != null) {
                                if (flat) {
                                    jisuan = ls.get(Integer.parseInt(v)) + "";
                                    if (!jisuan.contains("000000")) { //不包含
                                        str += ls.get(Integer.parseInt(v)) + "   ";
                                    } else { //包含
                                        String jieguo = "";
                                        jieguo = jisuan.substring(0, str.indexOf("000000"));
                                        Log.d("excel", jieguo);
                                        str += jieguo + "   ";
                                    }


                                } else {
                                    jisuan = v + "";
                                    if (jisuan.contains("000000")) { //包含
                                        jisuan = jisuan.substring(0, jisuan.indexOf("000000"));
                                    }
                                    str += jisuan + "   ";
                                }
                            } else if (v == null) {
                                str += "空白   ";
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (xmlParsersheet.getName().equalsIgnoreCase("row")
                                && v != null) {
                            str += "\n\n";
                        }
                        break;
                }
                evtTypesheet = xmlParsersheet.next();
            }
            System.out.println(str);
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        if (str == null) {
            str = "解析文件出现问题";
        }

        return str;
    }


}
