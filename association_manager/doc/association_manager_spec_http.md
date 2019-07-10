# 社团管理模块 HTTP RESTful 接口设计

## 数据结构

- <a id='Association'></a> Association

      |Field | Description |
      |---|---|
      |`id` int|社团ID|
      |`name` string | 社团名 |
      |`description` string  | 社团描述 |
      |`logo` string  | 社团图标 |
      |`tags` List<string> | 社团标签 |
		
      |`block_state` int | 社团是否封禁 |
      |`reason` string | 封禁理由 | 


- <a id='ErrorResponse'></a> ErrorResponse

   	|Field|Description|
   	|--|--|
   	|`error` string|错误码|
   	|`message` string|人类友好的错误信息|

## 接口

1. 查找指定社团

	将筛选条件作为参数传进函数，将符合条件的所有社团作为结果输出  

- HTTP Request  

   **GET** `/associations`

- Body Parameters

   |Parameter | Description |
   |---|---|
   |`keyword` string| 搜索关键词 |
	|`tags` string[] | 社团标签 |
   |`block_state` int| 封禁状态 |
    
- Response  

	|Code|Description|
   |--|--|
   |200 [_Association_](#Association)|成功|
   |400 [_ErrorResponse_](#ErrorResponse)|_invalid_format_: 无效的格式|

- Examples

   ``` json
   >>> GET /associations
   {
     "keyword":"asd",
     "tags":[
		  "T1",
		  "T2"
	  ],
     "block_state":1,
   }

   <<< 200
	{
		"associations":[
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
		]
	}
   <<< 400
   {
     "error": "invalidFormat",
     "message": "无效的申请格式"
   }
   ```
2. 直接创建社团

   系统管理员输入社团信息，创建并返回社团对象

- HTTP Request    
   **POST** `/association`
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
   |200 [_Association_](#Association) | 创建成功 |
   |400  [_ErrorResponse_](#ErrorResponse)| 创建失败,无效的格式 |

- example
   ``` json
   >>> POST /association
   {
      "name":"Association1",
      "description":"lalala",
      "tags":[
         "T1",
         "T2",
         "T3"
      ]
      "logo":"image(base64)"
   }
	<<< 200
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
		"error": "invalidFormat",
   	"message": "无效的创建格式"
	}

   ```

3. 删除社团

	根据社团id删除指定社团

- HTTP Request  
	**DELETE** `/assocoation`

- Body Parameters
   |Parameter | Description |
   |---|---|
   |`associationId` string | 社团id |

- Response
   |code|description|
   |---|---|
   |200 `true` | 删除成功 |
   |400  [_ErrorResponse_](#ErrorResponse)| 无效的社团id |
	|404  [_ErrorResponse_](#ErrorResponse)| 社团id不存在 |

- example
	``` json
	>>> DELETE /association
	{
		"associationId":"123"
	}
	<<< 200
	{
		"ture"
   }
	>>> DELETE /association
	{
		"associationId":"123"
	}
	<<< 400
	{
		"error": "invalidFormat",
   	"message": "无效的社团id"
	}
	<<< 404
	{
		"error": "notFound",
   	"message": "社团id不存在"
	}

	```
4. 编辑社团信息

	更新指定社团的信息

- HTTP Request

   **PUT** `/association`

- Body Parameters
   |Parameter | Description |
   |---|---|
   |`associationId` string | 社团id |
	|`name` string | 社团名称 |
	|`description` string | 社团描述 |
	|`tags` string[] | 社团标签 |
	|`logo` string | 社团logo |

- Response
   |code|description|
   |---|---|
   |200 [_Association_](#Association) | 更新成功 |
   |400  [_ErrorResponse_](#ErrorResponse)| 无效的格式 |
	|404  [_ErrorResponse_](#ErrorResponse)| 社团id不存在 |

- example
   ``` json
	>>> PUT /association
	{
		"associationId":"123",
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
     "error": "invalidFormat",
     "message": "无效的申请格式"
   }
	<<< 404
	   {
     "error": "notFound",
     "message": "社团id不存在"
   }
	```
5. 封禁社团
	封禁指定社团
- HTTP Request
   **PUT** `/illegal_association`
- Body Parameters
   |Parameter | Description |
   |---|---|
   |`associationId` string | 社团id |
	|`reason` string | 封禁理由 |
- Response
   |code|description|
   |---|---|
   |200 [_Association_](#Association) | 封禁成功 |
   |400  [_ErrorResponse_](#ErrorResponse)| 无效的格式 |
	|404  [_ErrorResponse_](#ErrorResponse)| 社团id不存在 |

- example
   ``` json
	>>> PUT /illegalAssociation
	{
		"associationId":"123",
		"reason":"asd",
	}
	<<< 200
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
     "error": "invalidFormat",
     "message": "无效的申请格式"
   }
	<<< 404
	   {
     "error": "notFound",
     "message": "社团id不存在"
   }
	```
6. 解封社团
- HTTP Request
   **PUT** `/legal_association`
- Body Parameters
   |Parameter | Description |
   |---|---|
   |`associationId` string | 社团id |

- Response
   |code|description|
   |---|---|
   |200 [_Association_](#Association) | 封禁成功 |
   |400  [_ErrorResponse_](#ErrorResponse)| 无效的格式 |
	|404  [_ErrorResponse_](#ErrorResponse)| 社团id不存在 |

- example
   ``` json
	>>> PUT /legalAssociation
	{
		"associationId":"123",
	}
	<<< 200
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
     "error": "invalidFormat",
     "message": "无效的申请格式"
   }
	<<< 404
	   {
     "error": "notFound",
     "message": "社团id不存在"
   }
	```
7. 社团申请处理
- HTTP Request
	**POST** `/association_application`

- Body Parameters
   |Parameter | Description |
   |---|---|
   |`userId` string | 社团id |
	|`operation` string| 申请操作（创建申请，解禁申请）|
	|`application` Application | 
	-Application
	|Parameter | Description |
   |---|---|
   |`id` int|申请ID|
   |`applicantId` int|申请者ID|
   |`type` string|申请类型|
   |`association` Association|自定义json对象|
   |`materialIds` int[]|申请材料ID列表|
   |`createdTime` string|申请时间|
   |`handledTime` string|处理时间|
   |`state` string|状态,可以是 _pending_, _accepted_, _refused_, _canceled_|

- Response
   |code|description|
   |---|---|
   |200 [_Association_](#Association) | 申请成功 |
   |400  [_ErrorResponse_](#ErrorResponse)| 无效的格式 |
	|401 [_ErrorResponse_](#ErrorResponse)|用户未提供身份验证凭据，或者没有通过身份验证|
	|403 [_ErrorResponse_](#ErrorResponse)|用户通过了身份验证，但是不具有访问资源所需的权限|
	|404  [_ErrorResponse_](#ErrorResponse)| 社团不存在 |

- example
   ``` json
	>>> POST /association_application

	{
		"userId":"123",
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
	<<< 400
   {
     "error": "invalidFormat",
     "message": "无效的申请格式"
   }
	<<< 401
	   {
     "error": "Unauthorized",
     "message": "用户未提供身份验证凭据，或者没有通过身份验证"
   }
		<<< 404
	   {
     "error": "invalidFormat",
     "message": "用户通过了身份验证，但是不具有访问资源所需的权限"
   }
	<<< 404
	   {
     "error": "notFound",
     "message": "社团不存在"
   }

	```