package com.ruoyi.system.api.entity.U02cx;

import java.util.HashMap;
import java.util.Map;

public class ApiConstants {

    public static  Map<Integer,String> returnCodeMap = new HashMap();

     static {
         returnCodeMap.put(10001,"无appId");
         returnCodeMap.put(10002,"请求过于频繁");
         returnCodeMap.put(10003,"达到日创建任务上限");
         returnCodeMap.put(10004,"达到日修改任务上限");

         returnCodeMap.put(20001,"appId 与 secret 不匹配");
         returnCodeMap.put(20002,"token失效");
         returnCodeMap.put(20003,"未携带token");
         returnCodeMap.put(20004,"非法任务id");
         returnCodeMap.put(20005,"缺少物料文件信息");
         returnCodeMap.put(20006,"无效的物料文件地址");
         returnCodeMap.put(20007,"物料文件md5不匹配");
         returnCodeMap.put(20008,"创建任务时 携带无效参数taskId");
         returnCodeMap.put(20009,"没有文件名称");
         returnCodeMap.put(20010,"参数未携带下载文件令牌");
         returnCodeMap.put(20011,"文件令牌失效");
     }
}
