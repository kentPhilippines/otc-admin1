package com.ruoyi.system.dto;



import java.io.Serializable;
import java.util.List;


public class TronGridResponse implements Serializable {
    private static final long serialVersionUID = 7446744944022223302L;

    private List<Data> data;
    private boolean success;
    private Meta meta;


    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
