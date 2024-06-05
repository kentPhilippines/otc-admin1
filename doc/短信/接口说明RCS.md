# `WhatsApi` 项目说明
### 项目介绍
~~~ tex
	该项目为spring boot项目，为合作方通过网络协议对WhatsApp后台进行简单的增加任务、修改任务、获取任务数据列表、获取任务生成明细文件等操作。
~~~
### 协议相关

* v6.0.0

  * 提供任务创建、修改以及报告等相关接口

* **相关策略**

  * `token`有效时间 1小时
  * 请求业务接口（除请求token外）频次限制 5秒
  * 单用户每日成功创建任务个数 500个
  * 单用户每日修改单任务次数 2000次

* **错误代码表**

  * 服务端策略异常

    | code  |        解释        |
    | :---: | :----------------: |
    | 10001 |      无appId       |
    | 10002 |    请求过于频繁    |
    | 10003 | 达到日创建任务上限 |
    | 10004 | 达到日修改任务上限 |

  * 客户端异常

    | code  |             解释              |
    | :---: | :---------------------------: |
    | 20001 |    appId 与 secret 不匹配     |
    | 20002 |           token失效           |
    | 20003 |          未携带token          |
    | 20004 |          非法任务id           |
    | 20005 |       缺少物料文件信息        |
    | 20006 |      无效的物料文件地址       |
    | 20007 |       物料文件md5不匹配       |
    | 20008 | 创建任务时 携带无效参数taskId |
    | 20009 |         没有文件名称          |
    | 20010 |    参数未携带下载文件令牌     |
    | 20011 |         文件令牌失效          |

## **获取 `Token`** 

  * 用于后续请求接口的身份校验
  * 调用方式：
    *  `http` 调用，`JSON` 请求

  ### **http调用**

* **请求地址** 

  > POST http://cot5b.u02cx.com:20086/whatsApi/getToken

* **请求参数**

  |   属性   |  类型  | 默认值 | 必填 |         说明         |
  | :------: | :----: | :----: | :--: | :------------------: |
  | `appId`  | string |        |  是  | 每个用户的各自独立Id |
  | `secret` | string |        |  是  |   配套appId的口令    |

### **返回值** 

> `Object`

* **返回参数**

  |   属性   |  类型  |             说明              |
  | :------: | :----: | :---------------------------: |
  | `status` |  int   |      成功 : 0,失败：其他      |
  |  `msg`   | string | 成功 : success,失败：具体原因 |
  | `token`  | string |      token值 时效 1小时       |

### **创建任务接口** (http://cot5b.u02cx.com:20086/whatsApi/rcs/task/addTask)

```tex
> 本接口应在服务器端调用。
```

  * 用于进行创建任务
  * 调用方式：
    *  `http` 调用，`JSON` 请求
    *  <font color='red'> 最终请求参数是Json对象使用公钥加密后的字串内容 </font>

### **`Http`调用**

* **请求地址** 

  > POST  http://cot5b.u02cx.com:20086/whatsApi/rcs/task/addTask

* **请求头参数**

  |     属性名     |       内容       |
  | :------------: | :--------------: |
  | `Content-Type` | application/json |
  |    `appId`     |       xxx        |
  |    `token`     |       xxx        |

* **请求参数**

  |      属性       |  类型  | 默认值 | 必填 |                    说明                    |
  | :-------------: | :----: | :----: | :--: | :----------------------------------------: |
  |   `taskName`    | string |        |  是  |                  任务名称                  |
  |     `price`     | double |   0    |  是  |                    单价                    |
  | `taskBeginTime` | string |        |  是  |   任务开始时间 格式 yyyy-MM-dd HH:mm:ss    |
  |   `fileName`    | string |        |  是  |                  物料名称                  |
  |   `filePath`    | string |        |  是  | 物料文件下载地址，文件为utf-8编码的txt文件 |
  |    `fileMd5`    | string |        |  是  |               物料文件MD5码                |
  |    `context`    | string |        |  是  |        任务文本内容 base64加密字串         |
  |     `type`      |  int   |   1    |  否  | 1-文本任务 2-图文任务 （当前默认文本任务） |

### **返回值** 

> `Object`

* **请求参数**

  |   属性   |  类型  |             说明              |
  | :------: | :----: | :---------------------------: |
  | `status` |  int   |      成功 : 0,失败：其他      |
  |  `msg`   | string | 成功 : success,失败：具体原因 |
  | `taskId` |  long  |       创建成功的任务ID        |

### **修改任务接口** (http://cot5b.u02cx.com:20086/whatsApi/rcs/task/updateTask)

```tex
> 本接口应在服务器端调用。
```

  * 用于修改任务参数
  * 调用方式：
    *  `http` 调用，`JSON `请求
    *  <font color='red'> 最终请求参数是Json对象使用公钥加密后的字串内容 </font>

### **`Http`调用**

* **请求地址** 

  > POST  http://cot5b.u02cx.com:20086/whatsApi/api/task/updateTask

* **请求头参数**

  |     属性名     |       内容       |
  | :------------: | :--------------: |
  | `Content-Type` | application/json |
  |    `appId`     |       xxx        |
  |    `token`     |       xxx        |

* **请求参数**

  |      属性       |  类型  | 默认值 | 必填 |                 说明                  |
  | :-------------: | :----: | :----: | :--: | :-----------------------------------: |
  |    `taskId`     |  long  |        |  是  |                任务ID                 |
  |   `taskName`    | string |        |  否  |               任务名称                |
  |     `price`     | double |   0    |  否  |                 单价                  |
  | `taskBeginTime` | string |        |  否  | 任务开始时间 格式 yyyy-MM-dd HH:mm:ss |
  |    `context`    | string |        |  否  |      任务文本内容 base64加密字串      |

### **返回值** 

> Object

* **请求参数**

  |   属性   |  类型  |             说明              |
  | :------: | :----: | :---------------------------: |
  | `status` |  int   |      成功 : 0,失败：其他      |
  |  `msg`   | string | 成功 : success,失败：具体原因 |
  | `taskId` |  long  |       修改成功的任务ID        |

### **修改任务状态接口** (http://cot5b.u02cx.com:20086/whatsApi/rcs/task/updateTaskStatus)

```tex
> 本接口应在服务器端调用。
```

  * 用于修改任务状态
  * 调用方式：
    *  `http` 调用，`JSON` 请求
    *  <font color='red'> 最终请求参数是Json对象使用公钥加密后的字串内容 </font>

### **`Http`调用**

* **请求地址** 

  > POST  http://cot5b.u02cx.com:20086/whatsApi/rcs/task/updateTaskStatus<br/>

* **请求头参数**

  |     属性名     |       内容       |
  | :------------: | :--------------: |
  | `Content-Type` | application/json |
  |    `appId`     |       xxx        |
  |    `token`     |       xxx        |

* **请求参数**

  |     属性     | 类型 | 默认值 | 必填 |          说明          |
  | :----------: | :--: | :----: | :--: | :--------------------: |
  |   `taskId`   | long |        |  是  |         任务ID         |
  | `taskStatus` | int  |        |  是  | 任务状态 0-关闭 1-开启 |

### **返回值** 

> Object

* **请求参数**

  |   属性   |  类型  |             说明              |
  | :------: | :----: | :---------------------------: |
  | `status` |  int   |      成功 : 0,失败：其他      |
  |  `msg`   | string | 成功 : success,失败：具体原因 |
  | `taskId` |  long  |       修改成功的任务ID        |

### **获取任务列表** (http://cot5b.u02cx.com:20086/whatsApi/rcs/report/getTaskList)

> 本接口应在服务器端调用。
>
> * 用于获取任务列表。
> * 调用方式：`http `调用，`JSON` 请求  

### **`Http`调用**

* **请求地址** 

  > POST  http://cot5b.u02cx.com:20086/whatsApi/rcs/report/getTaskList<br/>

  * **请求头参数**

  |     属性名     |       内容       |
  | :------------: | :--------------: |
  | `Content-Type` | application/json |
  |    `appId`     |       xxx        |
  |    `token`     |       xxx        |

* **请求参数**

  |   属性    |    类型     | 默认值 | 必填 |                   说明                   |
  | :-------: | :---------: | :----: | :--: | :--------------------------------------: |
  | `taskIds` | 数组 [long] |        |  否  | 任务ID集合，不传则默认返回该用户所有任务 |

### **返回值** 

> Object
>
> > 返回的` JSON` 数据包

* **参数**

  |   属性   |  类型  |             说明              |
  | :------: | :----: | :---------------------------: |
  | `status` |  int   |      成功 : 0,失败：其他      |
  |  `msg`   | string | 成功 : success,失败：具体原因 |
  |  `data`  |  数组  |       反馈任务详情数组        |

> Object

* **参数**

  |     属性      |  类型  |           说明            |
  | :-----------: | :----: | :-----------------------: |
  |  `sendDate`   | string | 下发时间 yyyy-MM-dd HH:mm |
  | `successRate` | string |          成功数           |
  | `issueCount`  | string |         物料总数          |
  |    `price`    | double |           单价            |
  |  `taskName`   | string |         任务名称          |
  |     `id`      |  long  |          任务id           |
  | `createDate`  | string |         创建时间          |
  |   `status`    |  int   |  任务状态 0-关闭 1-开启   |



### 强制完成任务(**http://cot5b.u02cx.com:20086/whatsApi/rcs/report/completeByBatch**)

> 本接口应在服务器端调用。
>
> * 用于强制完成任务。
> * 调用方式：`http` 调用，`JSON` 请求  

### **`Http`调用**

* **请求地址** 

  > POST  http://cot5b.u02cx.com:20086/whatsApi/rcs/report/completeByBatch

* **请求头参数**

  |     属性名     |       内容       |
  | :------------: | :--------------: |
  | `Content-Type` | application/json |
  |    `appId`     |       xxx        |
  |    `token`     |       xxx        |

* **请求参数**

  |   属性    |    类型     | 默认值 | 必填 |    说明    |
  | :-------: | :---------: | :----: | :--: | :--------: |
  | `taskIds` | 数组 [long] |        |  是  | 任务ID集合 |

### **返回值** 

​	执行完成成功的任务id



### **下载任务明细** (http://cot5b.u02cx.com:20086/whatsApi/rcs/report/getReport)

> 本接口应在服务器端调用。
>
> * 用于下载任务明细文件。
> * 调用方式：`http` 调用，`JSON` 请求  

### **`Http`调用**

* **请求地址** 

  > POST  http://cot5b.u02cx.com:20086/whatsApi/rcs/report/getReport

* **请求头参数**

  |     属性名     |       内容       |
  | :------------: | :--------------: |
  | `Content-Type` | application/json |
  |    `appId`     |       xxx        |
  |    `token`     |       xxx        |

* **请求参数**

  |   属性   |  类型  | 默认值 | 必填 |  说明  |
  | :------: | :----: | :----: | :--: | :----: |
  | `taskId` | string |        |  是  | 任务id |

### **返回值** 

* byte[] 字节数组，须根据`HttpResponse`的status码判断是文件流还是json信息流

* `HttpResponse `

  * 状态码 status 403 请求下载文件异常

    * **参数**

      |  属性  |  类型  |      说明       |
      | :----: | :----: | :-------------: |
      | status |  int   |     失败码      |
      |  msg   | string | 失败 : 具体原因 |

  * 状态码 status 200 请求下载文件流成功

    

