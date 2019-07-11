# 审核管理模块 HTTP RESTful 接口设计

该模块负责系统所有的 申请/审核 管理

## 统一返回结果格式

- <a id='ResponseBody'></a>**ResponseBody**

   |Field|Description|
   |--|--|
   |`data` _any_|对应接口约定好的返回类型, 为 `null` 时请检查 `error` 和 `message`|
   |`error` _string_|错误码|
   |`message` _string_|人类友好的错误信息|

## 申请

- <a id='Application'></a>**Application**

   |Field|Description|
   |--|--|
   |`id` _int_|申请ID|
   |`applicantId` _int_|申请者ID|
   |`type` _string_|申请类型|
   |`object` _any_|自定义json对象|
   |`materialIds` _int[]_|申请材料ID列表|
   |`createdTime` _string_|申请时间|
   |`handledTime` _string_|处理时间|
   |`state` _string_|状态,可以是 _pnding_, _accepted_, _refused_, _canceled_|

### 创建申请

- HTTP Request

   **POST** `/applications`

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`applicantId` _int_|申请者ID|
   |`type` _string_|申请类型|
   |`object` _any_|自定义json对象|
   |`materialIds` _int[]_|申请材料ID列表|

- Response

   |Code|Description|
   |--|--|
   |200 [_Application_](#Application)|成功|
   |400 "invalidType"|无效的申请类型|

- Examples

   ``` json
   >>> POST /applications
   {
     "applicantId": 111,
     "type": "signupActivity",
     "object": {
        "activityId": 55
     },
     "materialIds": []
   }

   <<< 200
   {
     "data": {
       "id": 271,
       "applicantId": 111,
       "type": "signupActivity",
       "object": {
         "activityId": 55
       },
       "materialIds": [],
       "createdTime": "2019-07-10T03:19:06.618Z",
       "handledTime": null,
       "state": "pending"
     },
     "error": "",
     "message": ""
   }
   ```

   ```json
   >>> POST /applications
   {
     "applicantId": 111,
     "type": "dsafsdfasd",
     "object": {
       "activityId": 55
     },
     "materialIds": []
   }

   <<< 400
   {
     "data": null,
     "error": "invalidType",
     "message": "无效的申请类型"
   }
   ```

### 变更状态

- HTTP Request

   **PATCH** `/applications/{applicationId}`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`applicationId`|申请ID|

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`userId` _int_|操作者ID|
   |`type` _string_|操作类型, 可以是 _accept_, _refuse_, _cancel_|

- Response

   |Code|Description|
   |--|--|
   |200 [_Application_](#Application)|操作成功|
   |400 "invalidType"|无效的类型, 只能是 _accept_, _refuse_, _cancel_ 之一|
   |403 "noPermission"|用户没有执行该操作的权限|
   |409 "conflict"|状态冲突, 当前状态为xxx|

- Examples

   ```json
   >>> PATCH /applications/271
   {
      "userId": 1,
      "type": "accept"
   }

   <<< 200
   {
     "data": {
       "id": 271,
       "applicantId": 111,
       "type": "signupActivity",
       "object": {
         "activityId": 55
       },
       "materialIds": [],
       "createdTime": "2019-07-10T03:19:06.618Z",
       "handledTime": "2019-07-10T03:24:49.797Z",
       "state": "accepted"
     },
     "error": "",
     "message": ""
   }
   ```

   ```json
   >>> PATCH /appllications/270
   {
      "userId": 1,
      "type": "cancel"
   }

   <<< 403
   {
      "data": null,
      "error": "noPermission",
      "message": "用户没有执行该操作的权限"
   }
   ```

   ```json
   >>> PATCH /applications/270
   {
      "userId": 1,
      "type": "asdfasdf"
   }

   <<< 400
   {
      "data": null,
      "status": "invalidType",
      "message": "无效的类型, 只能是 \"accept\", \"refuse\", \"cancel\" 之一"
   }
   ```

   ```json
   >>> PATCH /applications/271
   {
      "userId": 1,
      "type": "refuse"
   }

   <<< 409
   {
      "data": null,
      "status": "conflict",
      "message": "状态冲突, 当前状态为 \"accepted\""
   }
   ```

### 获取单个申请

- HTTP Request

   **GET** `/applications/{applicationId}`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`applicationId` _int_|申请ID|

- Response

   |Code|Description|
   |--|--|
   |200 [_Application_](#Application)|查询成功|
   |404 "notFound"|不存在的申请|

- Examples

   ```json
   >>> GET /applications/271

   <<< 200
   {
     "data": {
       "id": 271,
       "applicantId": 111,
       "type": "signupActivity",
       "object": {
         "activityId": 55
       },
       "materialIds": [],
       "createdTime": "2019-07-10T03:19:06.618Z",
       "handledTime": "2019-07-10T03:24:49.797Z",
       "state": "accepted"
     },
     "error": "",
     "message": ""
   }
   ```

   ```json
   >>> GET /applications/7810212

   <<< 404
   {
     "data": null,
     "error": "notFound",
     "message": "不存在该申请"
   }
   ```

### 获取申请列表

- HTTP Request

   **GET** `/applications`

- Query Parameters

   |Parameter|Description|
   |--|--|
   |`pageNum`|页码, 默认为0|
   |`pageSize`|每页数量, 默认为20|
   |`sortBy`|排序方式, 可以为 _createdTime_, _handledTime_, _object.userDefinedField_, 默认为 _createdTime_|

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`applicantId` _int_|申请者ID, 默认为null|
   |`type` _int_|申请类型, 默认为null(全部)|
   |`createdAfter` _string_|申请创建于此时间之后,默认为最早|
   |`createdBefore` _string_|申请创建于此时间之前,默认为当前时间|
   |`state` _string_|申请状态|
   |`object.userDefinedField` _any_|用户自定义字段|

- Response

   |Code|Description|
   |--|--|
   |200 [_Application[]_](#Application)|查询成功, 返回列表可能为空|

- Examples

   ```json
   >>> GET /applications?pageSize=2&pageNum=0&sortBy=createdTime
   {
     "applicantID": 111
   }

   <<< 200
   {
     "data": [
       {
         "applicantId": 111,
         "type": "createAssociation",
         "object": {
           "name": "hahahha",
           "description": "hello world",
           ...
         },
         "materialIds": [],
         "createdTime": "2019-07-09T03:19:06.618Z",
         "handledTime": "2019-07-09T03:24:49.797Z",
         "state": "refused"
       },
       {
         "applicantId": 111,
         "type": "signupActivity",
         "object": {
           "activityId": 55
         },
         "materialIds": [],
         "createdTime": "2019-07-10T03:19:06.618Z",
         "handledTime": "2019-07-10T03:24:49.797Z",
         "state": "accepted"
       }
     ],
     "error": "",
     "message": ""
   }
   ```

   ```json
   >>> GET /applications
   {
     "type": "signupActivity",
     "object": {
       "activityId": 55,
     }
   }

   <<< 200
   {
     "data": [
       {
         "applicantId": 111,
         "type": "signupActivity",
         "object": {
           "activityId": 55
         },
         "materialIds": [],
         "createdTime": "2019-07-10T03:19:06.618Z",
         "handledTime": "2019-07-10T03:24:49.797Z",
         "state": "accepted"
       },
       ...
     ],
     "error": "",
     "message": ""
   }
   ```

   ```json
   >>> GET /applications
   {
     "type": "fsafasdf"
   }

   <<< 200
   {
     "data": [],
     "error": "",
     "message": ""
   }
   ```
## 回调函数

其他模块通过本模块的回调函数功能接入 申请/审核 的功能。

1. **其他模块** 在 **本模块** 绑定: (类型, 事件) => [_回调函数_](#CallbackInterface)
2. 申请者在 **本模块** 创建申请, 会发生事件
3. 申请者在 **本模块** 取消申请, 会发生事件
4. 审核者在 **本模块** 审核申请, 根据审核结果发生不同事件
5. **本模块** 在事件发生时, 查找其绑定的 _回调函数_, 将 操作者、事件、申请详情 作为参数调用函数, 发送给 **其他模块** 进行处理
6. **其他模块** 收到事件之后根据 审核者、事件、申请详情 进行内部逻辑的处理, 成功时返回200, 失败时返回对应错误
7. **本模块** 收到返回结果后修改申请的状态并返回结果给操作者。

- <a id='Callback'></a>**Callback**

   |Field|Description|
   |--|--|
   |`id` _int_|回调函数ID|
   |`event` _string_|事件, 可以是 _created_, _accepted_, _refused_, _canceled_|
   |`target` _string_|回调函数服务地址|
   |`url` _string_|回调函数url|
   |`description` _string_|回调函数描述|

### 绑定回调函数

- HTTP Request

   **POST** `/callbacks`

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`type` _string_|申请类型|
   |`event` _string_|事件, 可以是 _created_, _accepted_, _refused_, _canceled_|
   |`target` _string_|回调函数服务地址|
   |`url` _string_|回调函数url|
   |`description` _string_|回调函数描述, 默认为""|

- Response

   |Code|Description|
   |--|--|
   |200 [_Callback_](#Callback)|绑定成功|
   |400 "invalidEvent"|无效的事件, 只能是 _created_, _accepted_, _refused_, _canceld_ 之一|
   |409 "conflic"|给定申请类型和给定事件已经绑定了回调函数, 先解除绑定再进行注册|

- Examples

   ```json
   >>> POST /callbacks
   {
     "type": "signupActivity",
     "event": "accepted",
     "target": "xxx.xxx.xxx.xxx",
     "url": "/callback/acceptsignup",
     "description": "处理接受活动报名申请"
   }

   <<< 200
   {
     "data": {
       "id": 1,
       "type": "signupActivity",
       "event": "accepted",
       "target": "xxx.xxx.xxx.xxx",
       "url": "/callback/acceptsignup",
       "description": "处理接受活动报名申请"
     }
   }
   ```


### 解除绑定

- HTTP Request

   **DELETE** `/callbacks/{callbackId}`

- Response

   |Code|Description|
   |--|--|
   |204 _{}_|删除成功|
   |404 "notFound"|不存在该回调函数|

- Examples

   ```json
   >>> DELETE /callbacks/1

   <<< 204
   {
     "data": {},
     "error": "",
     "message": ""
   }
   ```

   ```json
   >>> DELETE /callbacks/0

   <<< 404
   {
     "data": null,
     "error": "notFound",
     "message": "不存在该回调函数"
   }
   ```


### 获取回调函数列表

- HTTP Request

   **GET** `/callbacks`

- Response

   |Code|Description|
   |--|--|
   |200 [_Callback[]_](#Callback)|查询成功, 返回列表可能为空|

- Examples

   ```json
   >>> GET /callbacks

   <<< 200
   {
     "data": [
       {
         "id": 1,
         "type": "signupActivity",
         "event": "accepted",
         "target": "xxx.xxx.xxx.xxx",
         "url": "/callback/acceptsignup",
         "description": "处理接受活动报名申请"
       },
       {
         "id": 2,
         "type": "createAssociation",
         "event": "accepted",
         "target": "xxx.xxx.xxx.xxx",
         "url": "/callback/acceptcreation",
         "description": "处理接受社团创建申请"
       },
       ...
     ]
   }
   ```

### <a id="CallbackInterface"></a>回调函数接口

接入的回调函数需要有如下接口

- HTTP Request

   **POST** `/some/url/path`

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`userId` _int_|操作者ID|
   |`event` _string_|事件, 可以是 _created_, _accepted_, _refused_, _canceled_|
   |`application` [_Application_](#Application)|申请|

- Response

   |Code|Description|
   |--|--|
   |200 _{}_|操作成功|
   |statusCode "error"|"message"|

- Examples

   ```json
   >>> POST /callback/signactivity
   {
     "userId": 1,
     "event": "created",
     "application": {
       "id": 271,
       "applicantId": 111,
       "type": "signupActivity",
       "object": {
         "activityId": 55
       },
       "materialIds": [],
       "createdTime": "2019-07-10T03:19:06.618Z",
       "handledTime": null,
       "state": "pending"
     }
   }

   <<< 200
   {
     "data": {},
     "error": "",
     "message": ""
   }
   ```