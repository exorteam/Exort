# 通知模块 HTTP RESTful 接口设计

**系统管理员** 能发 **系统通知** 和 **个人通知**

**社团管理员** 能发当前社团的 **社团通知** 和 **个人通知**

`systemNotice` **系统通知** 的 **接收者** 是 **系统内的所有用户** ( `receiver` 的内容为null )

`associationNotice` **社团通知** 的 **接收者** 是 **该社团内的所有用户** ( `receiver` 的内容为社团内的所有用户ID)

`personalNotice` **个人通知** 的 **接收者** 是 **指定的接收人** （ `receiver` 的内容为指定用户的用户ID ）

## 统一返回结果格式

- <a id='ResponseBody'></a>**ResponseBody**

  | Field              | Description                                                  |
  | ------------------ | ------------------------------------------------------------ |
  | `data` _any_       | 对应接口约定好的返回类型, 为 `null` 时请检查 `error` 和 `message` |
  | `error` _string_   | 错误码                                                       |
  | `message` _string_ | 人类友好的错误信息                                           |

## 通知

1. <a id='Notice'></a>**Notice**
   
   |属性|说明|
   |---|---|
   |`id` *int* | 通知ID |
   |`userId` *int* | 通知发布者 |
   |`type` *string* | 通知类型（`systemNotice`，`associationNotice`，`personalNotice`） |
   |`receiver` *int[]* | 接收者ID （可以为null） |
   |`title` *string* | 通知标题 |
   |`content` *string* | 通知内容 |
   |`time` *string* | 通知时间 |

------

### 创建通知

- HTTP Request

  **POST** `/notices`

- Body Parameters

  | Parameter          | Description             |
  | ------------------ | ----------------------- |
  | `userId` *int*     | 通知发布者ID            |
  | `type` _string_    | 通知类型                |
  | `receiver` *int[]* | 接收者ID （可以为null） |
  | `title` _string_   | 通知标题                |
  | `content` _string_ | 通知内容                |

- Response

  | Code                    | Description              |
  | ----------------------- | ------------------------ |
  | 200 [_Notice_](#Notice) | 成功                     |
  | 400 "noPermission"      | 用户没有执行该操作的权限 |

- Example

  ```json
  >>> POST /notices
  {
    "userId": 111,
    "type": "systemNotice",
    "receiver": null,
    "title" : "try",
    "content" : "try",
  }
  
  <<< 200
  {
    "data": {
      "id" : 11,
      "userId": 111,
    	"type": "systemNotice",
    	"receiver": null,
    	"title" : "try",
    	"content" : "try",
      "time" : "2019-07-10T03:19:06.618Z",
    },
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> POST /notices
  {
    "userId": 111,
    "type": "associationNotice",
    "receiver": [1,2,3,4],
    "title" : "try",
    "content" : "try",
  }
  
  <<< 200
  {
    "data": {
      "id" : 11,
      "userId": 111,
    	"type": "associationNotice",
    	"receiver": [1,2,3,4],
    	"title" : "try",
    	"content" : "try",
      "time" : "2019-07-10T03:19:06.618Z",
    },
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> POST /notices
  {
    "userId": 111,
    "type": "personalNotice",
    "receiver": [1],
    "title" : "try",
    "content" : "try",
  }
  
  <<< 200
  {
    "data": {
      "id" : 11,
      "userId": 111,
    	"type": "personalNotice",
    	"receiver": [1],
    	"title" : "try",
    	"content" : "try",
      "time" : "2019-07-10T03:19:06.618Z",
    },
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> POST /notices
  {
    "userId": 111,
    "type": "systemNotice",
    "receiver": null,
    "title" : "try",
    "content" : "try",
  }
  
  <<< 200
  {
    "data": null,
    "error": "noPermission",
    "message": "用户没有执行该操作的权限"
  }
  ```

### 修改通知

- HTTP Request

  **PUT** `/notices`

- Body Parameters

  | Parameter          | Description             |
  | ------------------ | ----------------------- |
  | `userId` *int*     | 通知发布者ID            |
  | `type` _string_    | 通知类型                |
  | `receiver` *int[]* | 接收者ID （可以为null） |
  | `title` _string_   | 通知标题                |
  | `content` _string_ | 通知内容                |

- Response

  | Code                    | Description                  |
  | ----------------------- | ---------------------------- |
  | 200 [_Notice_](#Notice) | 成功（返回修改后的通知内容） |
  | 400 "noPermission"      | 用户没有执行该操作的权限     |

- Example

  ```json
  >>> PUT /notices
  {
    "userId": 111,
    "type": "systemNotice",
    "receiver": null,
    "title" : "try",
    "content" : "try",
  }
  
  <<< 200
  {
    "data": {
      "id" : 11,
      "userId": 111,
    	"type": "systemNotice",
    	"receiver": null,
    	"title" : "try",
    	"content" : "try",
      "time" : "2019-07-10T03:19:06.618Z",
    },
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> PUT /notices
  {
    "userId": 111,
    "type": "associationNotice",
    "receiver": [1,2,3,4],
    "title" : "try",
    "content" : "try",
  }
  
  <<< 200
  {
    "data": {
      "id" : 11,
      "userId": 111,
    	"type": "associationNotice",
    	"receiver": [1,2,3,4],
    	"title" : "try",
    	"content" : "try",
      "time" : "2019-07-10T03:19:06.618Z",
    },
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> PUT /notices
  {
    "userId": 111,
    "type": "personalNotice",
    "receiver": [1],
    "title" : "try",
    "content" : "try",
  }
  
  <<< 200
  {
    "data": {
      "id" : 11,
      "userId": 111,
    	"type": "personalNotice",
    	"receiver": [1],
    	"title" : "try",
    	"content" : "try",
      "time" : "2019-07-10T03:19:06.618Z",
    },
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> PUT /notices
  {
    "userId": 111,
    "type": "systemNotice",
    "receiver": null,
    "title" : "try",
    "content" : "try",
  }
  
  <<< 400
  {
    "data": null,
    "error": "noPermission",
    "message": "用户没有执行该操作的权限"
  }
  ```



### 获取用户接收到的所有通知

- HTTP Request

  **GET** `/receivers/{receiverId}/notices`

- Response

  | Code                      | Description                                |
  | ------------------------- | ------------------------------------------ |
  | 200 [_Notice[]_](#Notice) | 成功，得到该用户接收的所有通知（可以为空） |
  | 404 "notFound"            | 不存在此用户                               |

- Example

  ```json
  >>> GET /receivers/1/notices
  
  <<< 200
  {
    "data": [
        {
      	"id" : 11,
      	"userId": 111,
    		"type": "systemNotice",
    		"receiver": null,
    		"title" : "try",
    		"content" : "try",
      	"time" : "2019-07-10T03:19:06.618Z",
    	},{
      	"id" : 12,
      	"userId": 111,
    		"type": "systemNotice",
    		"receiver": null,
    		"title" : "try",
    		"content" : "try",
      	"time" : "2019-07-10T03:19:06.618Z",
    	}
    ],
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> GET /receivers/12/notices
  
  <<< 200
  {
    "data": [],
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> GET /receivers/12/notices
  
  <<< 404
  {
    "data": null,
    "error": "notFound",
    "message": "不存在此用户"
  }
  ```



### 获取用户创建的所有通知

- HTTP Request

  **GET** `/users/{userId}/notices`

- Response

  | Code                      | Description                                |
  | ------------------------- | ------------------------------------------ |
  | 200 [_Notice[]_](#Notice) | 成功，得到该用户创建的所有通知（可以为空） |
  | 404 "notFound"            | 不存在此用户                               |

- Example

  ```json
  >>> GET /users/1/notices
  
  <<< 200
  {
    "data": [
        {
      	"id" : 11,
      	"userId": 1,
    		"type": "systemNotice",
    		"receiver": null,
    		"title" : "try",
    		"content" : "try",
      	"time" : "2019-07-10T03:19:06.618Z",
    	},{
      	"id" : 12,
      	"userId": 111,
    		"type": "systemNotice",
    		"receiver": null,
    		"title" : "try",
    		"content" : "try",
      	"time" : "2019-07-10T03:19:06.618Z",
    	}
    ],
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> GET /users/12/notices
  
  <<< 200
  {
    "data": [],
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> GET /users/12/notices
  
  <<< 404
  {
    "data": null,
    "error": "notFound",
    "message": "不存在此用户"
  }
  ```

### 

### 获取某个通知

- HTTP Request

  **GET** `/notices/{noticeId}`

- Response

  | Code                    | Description       |
  | ----------------------- | ----------------- |
  | 200 [_Notice_](#Notice) | 成功,返回对应通知 |
  | 404 "notFound"          | 不存在此通知      |

- Example

  ```json
  >>> GET /notices/1
  
  <<< 200
  {
    "data": {
      "id" : 11,
      "userId": 111,
    	"type": "systemNotice",
    	"receiver": null,
    	"title" : "try",
    	"content" : "try",
      "time" : "2019-07-10T03:19:06.618Z",
    }
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> GET /notices/12
  
  <<< 404
  {
    "data": null,
    "error": "notFound",
    "message": "不存在此通知"
  }
  ```

### 