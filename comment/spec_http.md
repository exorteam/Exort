# 评论管理模块 HTTP RESTful 接口设计

根据之前的设计，用户只能对 `comment` ,  `article` 和 `activity` 进行评论,因此我们根据字段 `type` 来判断被评论对象的类型。由于当初article的ID用的事 `string` ,而activity的ID用的是 `int`，所以为了都能够使用，这里我们的 `commentedId` 统一为 `string`，用以指明被评论对象的ID。

如果是评论回复，则 `type` 为 `comment` ，用以指明父节点，被评论对象ID为被回复的评论ID，并且发表回复时 `type` 不需要指定。

考虑到可能不是展示所有的评论（如果评论非常多的情况，只展示最近的几条），采用 `replies` 这个字段用以表示对于评论的回复，而不是根据父节点把整个评论树给建起来，初始是数组为空，每次对于评论回复都会生成一个新的 `Comment` 实例，然后被回复对象的 `replies` 字段中就会添加这个新生成的comment的ID。

## 统一返回结果格式

- <a id='ResponseBody'></a>**ResponseBody**

  | Field              | Description                                                  |
  | ------------------ | ------------------------------------------------------------ |
  | `data` _any_       | 对应接口约定好的返回类型, 为 `null` 时请检查 `error` 和 `message` |
  | `error` _string_   | 错误码                                                       |
  | `message` _string_ | 人类友好的错误信息                                           |

## 评论

1. <a id='Comment'></a>**Comment**
   
   |属性|说明|
   |---|---|
   |`id` *string* | 评论ID |
   |`userId` *int* | 评论人 |
   |`type` *string* | 被评论对象类型（`article`,`activity`,`comment`） |
   |`commentedId` *string* | 被评论对象ID |
   |`content` *string* | 评论内容 |
   |`time` *string* | 评论时间 |
   |`replies` *string[]* | 回复生成的comment对应Id数组（初始为空） |

------

### 发表评论

- HTTP Request

  **POST** `/comments`

- Body Parameters

  | 属性                   | 说明                                   |
  | ---------------------- | -------------------------------------- |
  | `userId` *int*         | 评论人                                 |
  | `type` *string*        | 被评论对象类型（`article`,`activity`） |
  | `commentedId` *string* | 被评论对象ID                           |
  | `content` *string*     | 评论内容                               |

- Response

  | Code                      | Description               |
  | ------------------------- | ------------------------- |
  | 200 [_Comment_](#Comment) | 成功，返回新生成的Comment |
  | 400 “invalidType”         | 被评论对象类型非法        |
  | 404 "notFound"            | 没有找到被评论对象        |

- Example

  ```json
  >>> POST /comments
  {
    "userId": 111,
    "type": "article",
    "commentedId" : "21651",
    "content" : "try",
  }
  
  <<< 200
  {
    "data": {
      "id" : 11,
      "userId": 111,
    	"type": "article",
    	"commentedId" : "21651",
    	"content" : "try",
      "time" : "2019-07-10T03:19:06.618Z",
      "replies" : []
    },
    "error": "",
    "message": ""
  }
  ```
  
```json
  >>> POST /comments
  {
    "userId": 111,
    "type": "aa",
    "commentedId" : "comment",
    "content" : "try",
  }
  
  <<< 400
  {
    "data": null,
    "error": "invalidType",
    "message": "被评论对象类型非法"
  }
```

  ```json
  >>> POST /comments
  {
    "userId": 111,
    "type": "activity",
    "commentedId" : "21651",
    "content" : "try",
  }
  
<<< 404
  {
    "data": null,
    "error": "notFound",
    "message": "没有找到被评论对象"
  }
  ```


### 发表回复

- HTTP Request

  **POST** `/comments/{commentedId}/replies`

- Path Params

  | Parameters           | Description      |
  | -------------------- | ---------------- |
  | `commentedId` string | 被回复的评论的ID |

- Body Parameters

  | 属性               | 说明     |
  | ------------------ | -------- |
  | `userId` *int*     | 评论人   |
  | `content` *string* | 评论内容 |

- Response

  | Code                      | Description               |
  | ------------------------- | ------------------------- |
  | 200 [_Comment_](#Comment) | 成功，返回新生成的Comment |
  | 404 "notFound"            | 没有找到被评论对象        |

- Example

  ```json
  >>> POST /comments/123/replies
  {
    "userId": 111,
    "content" : "try",
  }
  
  <<< 200
  {
    "data": {
      "id" : 11,
      "userId": 111,
    	"type": "comment",
    	"commentedId" : "123",
    	"content" : "try",
      "time" : "2019-07-10T03:19:06.618Z",
      "replies" : []
    },
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> POST /comments/123/replies
  {
    "userId": 111,
    "content" : "try",
  }
  
  <<< 404
  {
    "data": null,
    "error": "notFound",
    "message": "没有找到被评论对象"
  }
  ```



### 删除评论

> 只有**发表此评论的人**和**社团管理员**有权限删除评论，如果被删除的评论中replies不为空，则同时删除对应的replies；如果删除某条回复，则父节点的 `replies` 中也要对应删除ID

- HTTP Request

  **DELETE** `/comments`

- Body Parameters

  | Parameter      | Description  |
  | -------------- | ------------ |
  | `id` *string*  | 评论ID       |
  | `userId` *int* | 通知发布者ID |
  
- Response

  | Code                      | Description              |
  | ------------------------- | ------------------------ |
  | 200 [_Comment_](#Comment) | 成功，返回被删除的评论   |
  | 400 "noPermission"        | 用户没有执行该操作的权限 |
  | 404 "notFound"            | 没有此评论               |

- Example

  ```json
  >>> DELETE /comments
  {
    "id" : "335151",
    "userId": 111
  }
  
  <<< 200
  {
    "data": {
      "id" : "335151",
      "userId": 111,
    	"type": "article",
    	"commentedId" : "21651",
    	"content" : "try",
      "time" : "2019-07-10T03:19:06.618Z",
      "replies" : []
    },
    "error": "",
    "message": ""
  }
  ```
  
  ```json
  >>> DELETE /comments
{
    "id" : "335151",
    "userId": 111
  }
  
  <<< 400
  {
    "data": null,
    "error": "noPermission",
    "message": "用户没有执行该操作的权限"
  }
  ```
  
  ```json
  >>> DELETE /comments
  {
    "id" : "335151",
    "userId": 111
  }
  
  <<< 404
  {
    "data": null,
    "error": "notFound",
    "message": "没有此评论"
  }
  ```
### 获取某个文章的所有评论

- HTTP Request

  **GET** `/articles/{articleId}/comments`

- Path Params

  | Parameters         | Description |
  | ------------------ | ----------- |
  | `articleId` string | 文章ID      |

- Response

  | Code                        | Description                            |
  | --------------------------- | -------------------------------------- |
  | 200 [_Comment[]_](#Comment) | 成功，得到该文章的所有评论（可以为空） |
  | 404 "notFound"              | 不存在该文章                           |

- Example

```json
  >>> GET /articles/ad516/comments
  
  <<< 200
  {
    "data": [
        {
      	"id" : "335151",
      	"userId": 111,
    		"type": "article",
    		"commentedId" : "ad516",
    		"content" : "trasfasy",
      	"time" : "2019-07-10T03:19:06.618Z",
      	"replies" : []
    	},{
      	"id" : "5135",
      	"userId": 111,
    		"type": "article",
    		"commentedId" : "ad516",
    		"content" : "try",
      	"time" : "2019-07-10T03:19:06.618Z",
      	"replies" : []
    	}
    ],
    "error": "",
    "message": ""
  }
```

  ```json
  >>> GET /articles/ad516/comments
  
  <<< 200
  {
    "data": [],
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> GET /articles/315/comments
  
  <<< 404
  {
    "data": null,
    "error": "notFound",
    "message": "不存在此文章"
  }
  ```



### 获取某个活动的所有评论

- HTTP Request

  **GET** `/activities/{activityId}/comments`

- Path Params

  | Parameters          | Description |
  | ------------------- | ----------- |
  | `activityId` string | 活动ID      |

- Response

  | Code                        | Description                            |
  | --------------------------- | -------------------------------------- |
  | 200 [_Comment[]_](#Comment) | 成功，得到该文章的所有评论（可以为空） |
  | 404 "notFound"              | 不存在该活动                           |

- Example

  ```json
  >>> GET /activities/ad516/comments
  
  <<< 200
  {
    "data": [
        {
      	"id" : "335151",
      	"userId": 111,
    		"type": "activity",
    		"commentedId" : "ad516",
    		"content" : "trasfasy",
      	"time" : "2019-07-10T03:19:06.618Z",
      	"replies" : []
    	},{
      	"id" : "5135",
      	"userId": 111,
    		"type": "activity",
    		"commentedId" : "ad516",
    		"content" : "try",
      	"time" : "2019-07-10T03:19:06.618Z",
      	"replies" : []
    	}
    ],
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> GET /activities/ad516/comments
  
  <<< 200
  {
    "data": [],
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> GET /activities/315/comments
  
  <<< 404
  {
    "data": null,
    "error": "notFound",
    "message": "不存在该活动"
  }
  ```

### 获取用户发表的所有评论

- HTTP Request

  **GET** `/users/{userId}/comments`

- Response

  | Code                        | Description                                |
  | --------------------------- | ------------------------------------------ |
  | 200 [_Comment[]_](#Comment) | 成功，得到该用户发表的所有评论（可以为空） |
  | 404 "notFound"              | 不存在此用户                               |

- Example

  ```json
  >>> GET /users/1/comments
  
  <<< 200
  {
    "data": [
        {
      	"id" : "335151",
      	"userId": 111,
    		"type": "activity",
    		"commentedId" : "ad516",
    		"content" : "trasfasy",
      	"time" : "2019-07-10T03:19:06.618Z",
      	"replies" : []
    	},{
      	"id" : "5135",
      	"userId": 111,
    		"type": "activity",
    		"commentedId" : "ad516",
    		"content" : "try",
      	"time" : "2019-07-10T03:19:06.618Z",
      	"replies" : []
    	}
    ],
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> GET /users/12/comments
  
  <<< 200
  {
    "data": [],
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> GET /users/12/comments
  
  <<< 404
  {
    "data": null,
    "error": "notFound",
    "message": "不存在此用户"
  }
  ```

### 获取某条评论

- HTTP Request

  **GET** `/comments/{commentId}`

- Response

  | Code                      | Description       |
  | ------------------------- | ----------------- |
  | 200 [_Comment_](#Comment) | 成功,返回对应评论 |
  | 404 "notFound"            | 不存在此评论      |

- Example

  ```json
  >>> GET /comments/1
  
  <<< 200
  {
    "data": {
      "id" : "335151",
      "userId": 111,
    	"type": "article",
    	"commentedId" : "21651",
    	"content" : "try",
      "time" : "2019-07-10T03:19:06.618Z",
      "replies" : []
    },
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> GET /comments/12
  
  <<< 404
  {
    "data": null,
    "error": "notFound",
    "message": "不存在此评论"
  }
  ```
