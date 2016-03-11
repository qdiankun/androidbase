package com.me.diankun.commonview.bean;

import java.util.Date;

/**
 * Class: ChatMessage
 * Description:
 *
 * @author diankun
 * @date 2015/11/17  17:36
 */
public class ChatMessage {

    private String name;
    private String msg;
    private Type type;
    private Date date;

    public ChatMessage() {
    }

    public ChatMessage(String name, String msg, Type type, Date date) {
        this.name = name;
        this.msg = msg;
        this.type = type;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public enum Type {
        GET_MSG, SEND_MSG;
    }

    @Override
    public String toString() {
        return "ChatMessage [name=" + name + ", msg=" + msg + ", type=" + type
                + ", date=" + date + "]";
    }

}
