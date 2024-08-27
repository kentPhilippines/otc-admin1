package com.ruoyi.common.exception.user;

/**
* 用户名称不正确或不符合规范异常类
 *
* @author qianyun.zhao
* @since 2024/8/27
*/
public class UserNameNotMatchException extends UserException{
    private static final long serialVersionUID = 1L;
    public UserNameNotMatchException()
    {
        super("user.not.match", null);
    }
}
