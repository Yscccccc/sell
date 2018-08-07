package com.ysc.utils;

import java.net.HttpURLConnection;
import java.net.URL;

public class WebUtils {

    public static String getUrl(String str)throws Exception{
        URL url=new URL(str);
        String res = "";
        try {
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();
            conn.setRequestProperty("contentType", "UTF-8");
            conn.setConnectTimeout(1000*3);
            conn.connect();
            conn.setReadTimeout(1000*3);


            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res += line;
            }
            in.close();
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    /**
     * post提交数据
     * */

    public static String sendPostRequestByForm(String path, String params) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");// 提交模式
        // conn.setConnectTimeout(10000);//连接超时 单位毫秒
        // conn.setReadTimeout(2000);//读取超时 单位毫秒
        conn.setDoOutput(true);// 是否输入参数
        byte[] bypes = params.toString().getBytes();
        conn.getOutputStream().write(bypes);// 输入参数
        String res="";
        try {
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res += line;
            }
            in.close();
        } catch (Exception e) {
            throw e;
        }
        return res;
    }


}
