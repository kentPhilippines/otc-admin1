package com.ruoyi.system.api.entity.okx;

import java.io.Serializable;
import java.util.List;

public class OkxResponse implements Serializable {

    private static final long serialVersionUID = 1L;


    private String code;
    private String msg;
    private List<Data> data;
    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }
}
