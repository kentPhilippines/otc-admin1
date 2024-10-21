package com.ruoyi.common.utils.el;

/**
 * @author : mudi
 * @date : Created in 2021/12/3 21:03
 * @description :
 * @modified By :
 */
public class ExpressionRootObject {
    private final Object object;
    private final Object[] args;

    public ExpressionRootObject(Object object, Object[] args) {
        this.object = object;
        this.args = args;
    }

    public Object getObject() {
        return object;
    }

    public Object[] getArgs() {
        return args;
    }
}
