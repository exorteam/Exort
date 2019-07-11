# 社团管理模块 HTTP RESTful 接口设计

该模块负责系统的社团创建与管理
## 统一返回结果


- <a id='ResponseBody'></a>**ResponseBody**

   |Field|Description|
   |--|--|
   |`data` _any_|对应接口约定好的返回类型, 为 `null` 时请检查 `error` 和 `message`|
   |`error` _string_|错误码|
   |`message` _string_|人类友好的错误信息|

## 社团列表

- <a id='AssociationList'></a>**AssociationList**
   |Field|Description|
   |--|--|
   |`pageNum` _int_|所返回的列表的实际的页码|
   |`pageSize` _int_|所返回的列表的实际的社团数量|
   |`totalSize` _int_|总的社团数量|
   |`content` [_Association[]_](#Association)|社团列表|


## 社团

- <a id='Association'></a> **Association**

    |Field | Description |
    |---|---|
    |`id` _int_|社团ID|
    |`name` _string_ | 社团名 |
    |`description` _string_  | 社团描述 |
    |`logo` _string_  | 社团图标 |
    |`tags` _string_[] | 社团标签 |
    |`blockState` int | 社团是否封禁 0表示已封紧 |
    |`reason` _string_ | 封禁理由 |

## 接口

### 查找单个社团

	根据社团id查找指定社团

- HTTP Request
	
	   **GET** `/associations/{associationId}`
	
- Path Parameters
	
	|Parameter|Description|
	|--|--|
	|`associationId` _int_|社团id|
	
- Response
	
	|Code|Description|
	|--|--|
	|200 [_Association_](#Association)|查询成功|
	|404  "notFound" | 社团不存在 |

- Examples

   ```json
   >>> GET /association/111
   

   <<< 200
   {
	 "data": {
		 "id":111,
		 "name":"qqwrv",
		 "description":"我的绝招之一",
		 "logo":"幽灵漫步",
		 "tags":[
			 "q",
			 "q",
			 "w"
		 ],
		 "blockState":1,
		 "reason":""
	 },
	 "error": "",
	 "message": ""
   }
   ```
   
   ```json
   >>> GET /association/99999

   <<< 404
   {
	 "data": null,
	 "error": "notFound",
	 "message": "不存在该申请"
   }
   ```


### 查找指定社团

	将筛选条件作为参数传进函数，将符合条件的所有社团作为结果输出  

- HTTP Request  

   **GET** `/associations`

- Query Parameters

   |Parameter|Description|
   |--|--|
   |`pageNum` _int_|页码，默认为0|
   |`pageSize` _int_|每页数量，默认为20|

- Body Parameters

   |Parameter | Description |
   |---|---|
   |`keyword` _string_| 搜索关键词 |
   |`tags` _string[]_ | 社团标签 |
   |`blockState` _int_| 封禁状态 |
    
- Response  

   |Code|Description|
   |--|--|
   |200 [_AssociationList_](#AssociationList)||
   |400 "invalidBlockState"| 无效的封禁状态  |
   |400 "invalidTags"|无效的标签 |
   

- Examples

   ``` json
   >>> GET /associations?pageNum=3&pageSize=4

   {
	 "keyword":"asso",
     "tags":[
		  "T1",
		  "T2"
	  ],
     "block_state":1,
   }

   <<< 200
	{
		"data":[
		{
			"id":1,
			"name":"Association1",
			"description":"nothing to say",
			"logo":"image1(base64)",
			"tags":[
				"T1",
				"T2",
			],
			"block_state":1,
			"reason":"18+"
		},
		{
			"id":3,
			"name":"Association1",
			"description":"nothing to say",
			"logo":"image1(base64)",
			"tags":[
				"T1",
				"T2",
			],
			"block_state":1,
			"reason":"18+"
		},
		{
			"id":4,
			"name":"Association1",
			"description":"nothing to say",
			"logo":"image1(base64)",
			"tags":[
				"T1",
				"T2",
			],
			"block_state":1,
			"reason":"18+"
		},
		{
			"id":2,
			"name":"Association2",
			"description":"nothing to say,too",
			"logo":"image2(base64)",
			"tags":[
				"T3",
				"T4",
			],
			"block_state":0,
			"reason":""
		},
      ],
      "error":null,
      "message":null
	}
   ```

   ```json
   >>> GET /associations?pageNum=3&pageSize=4
	{
		"id":2,
		"name":"Association2",
		"description":"nothing to say,too",
		"logo":"image2(base64)",
		"tags":[
			"T3",
			"T4",
		],
		"block_state":0,
		"reason":""
	},

   <<< 400
   {
	 "data":null ,
	 "error": "invalidFormat",
	 "message": "无效的格式"
   }
   ```
### 直接创建社团

   系统管理员输入社团信息，创建并返回社团对象

- HTTP Request    

   **POST** `/associations`

- Body Parameters

   |Parameter | Description |
   |---|---|
   |`name` string | 社团名称 |
   |`description` string | 社团描述 | 
   |`tags` string[]| 社团标签 |
   |`logo` string| 社团Logo |

- Response

   |code|description|
   |---|---|
   |200 Association | 创建成功 |
   |400 invalidFormat| 创建失败,无效的格式 |

- example

   ``` json
   >>> POST /associations
   {
      "name":"Association1",
      "description":"lalala",
      "tags":[
         "T1",
         "T2",
         "T3"
      ],
      "logo":"image(base64)"
   }
	<<< 200
   {
      {
      "id":1,
      "name":"Association1",
      "description":"nothing to say",
      "logo":"image1(base64)",
      "tags":[
         "T1",
         "T2",
         ],
      "block_state":1,
      "reason":"18+"
      },
      "error":null,
      "message":null
   }
   ```

### 删除社团

	根据社团id删除指定社团

- HTTP Request  
	**DELETE** `/assocoations/{associationId}`

- Path Parameters

   |Parameter | Description |
   |---|---|
   |`associationId` string | 社团id |

- Response

   |code|description|
   |---|---|
   |200 {} | 删除成功 |
   |400  invalidAssociationId | 无效的社团id |
   |404  notFound | 社团id不存在 |

- example
	``` json
	>>> DELETE /associations/123

   <<< 200
   {
	   "data":{
		   "ture"
         },
      "error":null,
      "message":null
   }
   
	>>> DELETE /association/qweqwx

	<<< 400
	{
		"data":null,
		"error": "invalidFormat",
   		"message": "无效的社团id"
   }

   >>> DELETE /association/123999

	<<< 404
	{
		"data":null,
		"error": "notFound",
   		"message": "社团id不存在"
	}
	```


### 编辑社团信息

	更新指定社团的信息

- HTTP Request

   **PUT** `/association/{associationId}`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`associationId` string | 社团id |

- Body Parameters

   |Parameter | Description |
   |---|---|
   |`name` string | 社团名称 |
   |`description` string | 社团描述 |
   |`tags` string[] | 社团标签 |
   |`logo` string | 社团logo |

- Response
   |code|description|
   |---|---|
   |200 Association | 更新成功 |
   |400  invalidFormat | 无效的格式 |
	|404  notFound| 社团id不存在 |

- example
   ``` json
	>>> PUT /association/123
	{
		"name":"qwe",
		"description":"asd",
		"tags":[
			"q",
			"w",
			"e"
		],
		"logo":"image"

	}
	<<< 200
	{
      "data":{
         "id":123,
         "name":"Association1",
         "description":"nothing to say",
         "logo":"image1(base64)",
         "tags":[
            "T1",
            "T2",
         ],
         "block_state":1,
         "reason":"18+"
      },
      "error":null,
      "message":null
   }
	```
   ```json
   >>> PUT /association/111
   {
	 "keyword":"asso",
	 "pageSize":4,
	 "pageNum":3,
     "tags":[
		  "T1",
		  "T2"
	  ],
     "block_state":1,
   }

   <<< 400	
   {
	 "data":null ,
	 "error": "invalidFormat",
	 "message": "无效的格式"
   }

   ```json
   >>> POST /association/12312312
   {
	   "keyword":"asso",
	   "pageSize":4,
	   "pageNum":3,
      "tags":[
		  "T1",
		  "T2"
	   ],
      "block_state":1,
   }
   <<< 404
   {
	   "data": null,
	   "error": "notFound",
	   "message": "请求更新的社团不存在"
   }
   ```

### 封禁或解禁社团

- HTTP Request

   **PATCH** `/associations/{applicantId}`

- Body Parameters

   |Parameter | Description |
   |---|---|
   |`type` _string_|操作类型,"block"表示封禁，"unblock"表示解禁|
   |`description` string | 操作描述 |

- Response

   |code|description|
   |---|---|
   |200 {} | 操作成功 |
   |400 invalidFormat| 无效的格式 |
   |404 notFound| 社团id不存在 |

- example
   ``` json
	>>> PUT /associations/123
	{
      "type":"block",
		"description":"asd",
	}
	<<< 200
	{
      "data":{
         "id":1,
         "name":"Association1",
         "description":"nothing to say",
         "logo":"image1(base64)",
         "tags":[
            "T1",
            "T2",
         ],
         "block_state":1,
         "reason":"18+"
      },
      "error":null,
      "message":null
   }
   ```json
   >>> PUT /associations/123
	{
		"id":1,
		"name":"Association1",
		"description":"nothing to say",
		"logo":"image1(base64)",
		"tags":[
			"T1",
			"T2",
		],
		"block_state":1,
		"reason":"18+"
   },
   <<< 400	
   {
	 "data":null ,
	 "error": "invalidFormat",
	 "message": "无效的格式"
   }
	```
	```json
	>>> PUT /associations/123
	{
		"associationId":"123999",
		"reason":"asd",
	}
	<<< 404
	{
		"data":null,
      "error": "notFound",
      "message": "社团id不存在"
   }
	```

### 社团申请处理

- HTTP Request

	**POST** `/association_applications`

- Body Parameters

   |Parameter | Description |
   |---|---|
   |`userId` string | 操作者id |
   |`operation` string| 申请操作（创建申请，解禁申请）|
   |`application`  [_Association_](#Association) | 申请对象 | 

#### 申请

- <a id='Application'></a>**Application**

   |Field|Description|
   |--|--|
   |`id` _int_|申请ID|
   |`applicantId` _int_|申请者ID|
   |`type` _string_|申请类型|
   |`association` [_Association_](#Association)|自定义json对象|
   |`materialIds` _int[]_|申请材料ID列表|
   |`createdTime` _string_|申请时间|
   |`handledTime` _string_|处理时间|
   |`state` _string_|状态,可以是 _pnding_, _accepted_, _refused_, _canceled_|

- Response

   |code|description|
   |---|---|
   |200 {} | 申请成功 |
   |400  invalidFormat| 无效的格式 |
   |401 noAuthorized|用户未提供身份验证凭据，或者没有通过身份验证|
   |403 noAuthorized|用户通过了身份验证，但是不具有访问资源所需的权限|
   |404  notFoundFound| 社团不存在 |

- example
   ``` json
	>>> POST /association_applications

	{
		"userId":123,
		"operation":"_createAsso_",
		"application":{
			"id":"123",
			"applicantId":"111",
			"type":"refuse",
			"association":{
				"id":1,
				"name":"Association1",
				"description":"nothing to say",
				"logo":"image1(base64)",
				"tags":[
					"T1",
					"T2",
				],
				"block_state":1,
				"reason":"18+"
			},
			"materialIds":[
				123,
				1233
			],
			"createdTime":"1111-2-23",
			"handledTime":"1223-3-22",
			"state":"_pending_"
		}
	}
	<<< 200
	{
   	"association":{
		"id":1,
			"name":"Association1",
			"description":"nothing to say",
			"logo":"image1(base64)",
			"tags":[
				"T1",
				"T2",
			],
			"block_state":1,
			"reason":"18+"
		},
	}
   ```
   ```json
   >>> POST /association_applications
   {
	 "data":null,
     "error": "invalidFormat",
     "message": "无效的申请格式"
   }

   <<< 400
   {
	 "data":null,
     "error": "invalidFormat",
     "message": "无效的申请格式"
   }
   ```

   ```json
   >>> POST /association_applications
	{
		"userId":"12399999",
		"operation":"_createAsso_",
		"application":{
			"id":"123",
			"applicantId":"111",
			"type":"refuse",
			"association":{
				"id":1,
				"name":"Association1",
				"description":"nothing to say",
				"logo":"image1(base64)",
				"tags":[
					"T1",
					"T2",
				],
				"block_state":1,
				"reason":"18+"
			},
			"materialIds":[
				123,
				1233
			],
			"createdTime":"1111-2-23",
			"handledTime":"1223-3-22",
			"state":"_pending_"
		}
	}

	<<< 401
	{
	   "data":null,
       "error": "Unauthorized",
       "message": "用户未提供身份验证凭据，或者没有通过身份验证"
	}
   ```
   ```json
   >>> POST /association_applications
	{
		"userId":"12399999",
		"operation":"_createAsso_",
		"application":{
			"id":"123",
			"applicantId":"111",
			"type":"refuse",
			"association":{
				"id":1,
				"name":"Association1",
				"description":"nothing to say",
				"logo":"image1(base64)",
				"tags":[
					"T1",
					"T2",
				],
				"block_state":1,
				"reason":"18+"
			},
			"materialIds":[
				123,
				1233
			],
			"createdTime":"1111-2-23",
			"handledTime":"1223-3-22",
			"state":"_pending_"
		}
	}

	<<< 403
	{
	   "data":null,
     "error": "Unauthorized",
     "message": "用户未提供身份验证凭据，或者没有通过身份验证"
	}
   ```

   ```json
   >>> POST /association_applications
	{
		"userId":"122",
		"operation":"_createAsso_",
		"application":{
			"id":"123",
			"applicantId":"111",
			"type":"refuse",
			"association":{
				"id":1,
				"name":"Association1",
				"description":"nothing to say",
				"logo":"image1(base64)",
				"tags":[
					"T1",
					"T2",
				],
				"block_state":1,
				"reason":"18+"
			},
			"materialIds":[
				123,
				1233
			],
			"createdTime":"1111-2-23",
			"handledTime":"1223-3-22",
			"state":"_pending_"
		}
	}

	<<< 404
	{
	   "data":null,
       "error": "invalidFormat",
       "message": "用户通过了身份验证，但是不具有访问资源所需的权限"
    }

   ```