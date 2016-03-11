package com.me.diankun.commonview.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.me.diankun.commonview.bean.ChatMessage;
import com.me.diankun.commonview.bean.Result;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URLEncoder;
import java.util.Date;


/**
 * @author ngup
 * @ClassName: HttpUtils
 * @Description: TODO(发送消息给机器人，机器人再返回数据)
 * @date 2015-4-29 下午11:10:03
 */
public class HttpUtils {
    private static final String URL = "http://www.tuling123.com/openapi/api";
    private static final String API_KEY = "eed271e44660e51d54953ab0a87f3355";

    /**
     * 请求机器人返回的数据 发送给机器人的信息
     *
     * @param msg
     * @return ChatMessage
     */
    public ChatMessage sendMessage(String msg) {
        ChatMessage chatMessage = new ChatMessage();

        String str = doGet(msg);
        //使用Gson解析数据
        Gson gson = new Gson();
        Result result = gson.fromJson(str, Result.class);

        if (!TextUtils.isEmpty(result.getText())) {
            chatMessage.setMsg(result.getText());
        } else {
            chatMessage.setMsg("服务器繁忙，请稍后再试!");
        }
        chatMessage.setDate(new Date());
        chatMessage.setName("小敏");
        chatMessage.setType(ChatMessage.Type.GET_MSG);
        return chatMessage;
    }

    public static String doGet(String msg) {
        String result = "";
        String url = setParams(msg);
//		Log.i("TAG","url = "+url);
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            java.net.URL urlNet = new java.net.URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlNet
                    .openConnection();
            //设置请求时间
            conn.setReadTimeout(5 * 1000);
            conn.setConnectTimeout(5 * 1000);
            //设置请求方法
            conn.setRequestMethod("GET");

            is = conn.getInputStream();
            int len = -1;
            byte[] buff = new byte[512];
            baos = new ByteArrayOutputStream();
            while ((len = is.read(buff)) != -1) {
                baos.write(buff, 0, len);
            }
            // 刷出缓冲区
            baos.flush();
            result = new String(baos.toByteArray());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @param msg
     * @return
     * @throws UnsupportedEncodingException
     * @Title: setParams
     */
    private static String setParams(String msg) {
        String url = "";
        try {
            url = URL + "?key=" + API_KEY + "&info="
                    + URLEncoder.encode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * IntpuStream转为String类型
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static String streamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public static String streamToStringWayTwo(InputStream is) throws IOException {
        String result = "";
        ByteArrayOutputStream baos = null;
        try {
            int len = -1;
            byte[] buff = new byte[512];
            baos = new ByteArrayOutputStream();
            while ((len = is.read(buff)) != -1) {
                baos.write(buff, 0, len);
            }
            // 刷出缓冲区
            baos.flush();
            result = new String(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                baos.close();
            }
        }
        return result;
    }
}
