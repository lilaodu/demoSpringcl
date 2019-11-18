package com.entity;

import java.io.Serializable;

/**
 * 返回实体
 */
public class DataReturn implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String msg;
    private Object data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getResponse() {
        return data;
    }

    public void setResponse(Object response) {
        this.data = response;
    }
}

