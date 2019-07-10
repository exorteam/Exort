

# 社团成员管理模块 HTTP RESTful 接口设计

## 统一返回结果格式

- <a id='ResponseBody'></a> ResponseBody

  | Field            | Description                                               |
  | ---------------- | --------------------------------------------------------- |
  | `data` any       | 对应接口约定好的返回类型, 为null时请检查 error 和 message |
  | `error` string   | 状态/错误码                                               |
  | `message` string | 人类友好的状态/错误信息                                   |

## 部门

- <a id='Department'></a> Department

  | 属性               | 说明         |
  | ------------------ | ------------ |
  | int associationId  | 社团ID       |
  | int departmentId   | 部门ID       |
  | String name        | 部门名称     |
  | String description | 部门描述     |
  | int parentId       | 父部门节点ID |

### 得到部门树

- HTTP Request

  **GET** `/association-member-manager/departments/{applicationId}`

- Path Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |

- Reponse

  | Code                              | Description                |
  | --------------------------------- | -------------------------- |
  | 200 [*Department[]*](#Department) | 查询成功, 返回列表可能为空 |

- Examples

  ```json
  >>> GET /association-member-manager/departments/271
  
  <<< 200
  {
    "data":[
        {
            "associationId":271,
            "departmentId":1,
            "name":"asda",
            "description":"safda",
            "parentId":0
        },
        {
            "associationId":271,
            "departmentId":2,
            "name":"sfas",
            "description":"asfe",
            "parentId":1
        }
    ],
    "error":"",
    "message":"",
  }
  ```

  ```json
  >>> GET /association-member-manager/departments/7810212
  
  <<< 200
  {
    "data": [],
    "error": "",
    "message": ""
  }
  ```

### 得到某个部门的信息

- HTTP Request

  **GET** `/association-member-manager/departments/{applicationId}/{departmentId}`

- Path Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |
  | `departmentId` int  | 部门ID      |

- Response

  | Code                            | Description  |
  | ------------------------------- | ------------ |
  | 200 [_Department_](#Department) | 查询成功     |
  | 404 "notFound"                  | 不存在该部门 |

- Examples

  ```json
  >>> GET /association-member-manager/departments/271/1
  
  <<< 200
  {
    "data":{
        "associationId":271,
        "departmentId":1,
        "name":"asda",
        "description":"safda",
        "parentId":0
    },
    "error":"",
    "message":"",
  }
  ```

  ```json
  >>> GET /association-member-manager/departments/271/100
  
  <<< 404
  {
      "data":null,
      "error":"notFound",
    	"message":"不存在该申请"
  }
  ```

### 创建部门

- HTTP Request

  **POST** `/association-member-manager/departments`

- Body Parameters

  | Parameter            | Description                                     |
  | -------------------- | ----------------------------------------------- |
  | `associationId` int  | 社团ID (应为已存在社团ID，否则为BadRequest)     |
  | `name` string        | 部门名称                                        |
  | `description` string | 部门描述                                        |
  | `parentId` int       | 部门父节点 (应为已存在部门ID，否则为BadRequest) |

- Response

  | Code                            | Description        |
  | ------------------------------- | ------------------ |
  | 201 [_Department_](#Department) | 成功               |
  | 400 "BadRequest"                | 部门创建信息不合法 |

- Examples

  ```json
  >>> POST /association-member-manager/departments
  {
    	"applicantId": 26,
    	"name": "asfaasd",
    	"description": "asfqwd",
      "parentId":2
  }
  
  <<< 200
  {
    "data": 
    {
      "applicantId": 26,
      "departmentId":5,
    	"name": "asfaasd",
    	"description": "asfqwd",
     	"parentId": 2
     },
    "error": "",
    "message": ""
  }
  ```

  ```json
  >>> POST /association-member-manager/departments
  {
    "applicantId": 26,
    "name": "asfaasd",
    "description": "asfqwd",
    "parentId":100
  }
  
  <<< 400
  {
    "data": null,
    "error": "BadRequest",
    "message": "部门创建信息不合法"
  }
  ```
  

### 删除部门

- HTTP Request

  **DELETE** `/association-member-manager/departments`

- Body Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |
  | `departmentId` int  | 部门ID      |

- Response

  | Code                            | Description                  |
  | ------------------------------- | ---------------------------- |
  | 200 [_Department_](#Department) | 删除成功，返回删除的部门信息 |
  | 404 "notFound"                  | 不存在该部门                 |

- Examples

  ```
  >>> Delete /association-member-manager/departments
  {
  	"associationId":1,
  	"departmentId":2
  }
  
  <<< 200
  {
    "data":{
        "associationId":1,
        "departmentId":2,
        "name":"asda",
        "description":"safda",
        "parentId":0
    },
    "error":"",
    "message":"",
  }
  ```

  ```json
  >>> POST /association-member-manager/departments
  {
  	"associationId":1,
  	"departmentId":23165
  }
  
  <<< 404
  {
    "data": null,
    "error": "notFound",
    "message": "该部门不存在"
  }
  ```

### 编辑部门

- HTTP Request

  **PUT** `/association-member-manager/departments`

- Body Parameters

  | Parameter            | Description                      |
  | -------------------- | -------------------------------- |
  | `associationId` int  | 社团ID                           |
  | `departmentId` int   | 部门ID                           |
  | `name` string        | 部门名称                         |
  | `description` string | 部门描述                         |
  | `parentId` int       | 父节点部门ID（应为已存在部门ID） |

- Response

  | Code                            | Description                    |
  | ------------------------------- | ------------------------------ |
  | 200 [_Department_](#Department) | 编辑成功，返回编辑后的部门信息 |
  | 400 "BadRequest"                | 修改的部门信息不合法           |
  | 404 "notFound"                  | 不存在该部门                   |

- Example

  ```json
  >>> PUT /association-member-manager/departments
  {
  	"associationId":1,
  	"departmentId":2,
      "name":"asfawd",
      "description":"asfqwdw",
      "parentId":0
  }
  
  <<< 200
  {
    "data":{
        "associationId":1,
        "departmentId":2,
        "name":"asda",
        "description":"safda",
        "parentId":0
    },
    "error":"",
    "message":"",
  }
  ```

  ```json
  >>> PUT /association-member-manager/departments
  {
  	"associationId":1,
  	"departmentId":23515,
      "name":"asfawd",
      "description":"asfqwdw",
      "parentId":0
  }
  
  <<< 404
  {
    "data":null,
    "error":"notFound",
    "message":"不存在该部门",
  }
  ```

  ```json
  >>> PUT /association-member-manager/departments
  {
  	"associationId":1,
  	"departmentId":2,
      "name":"asfawd",
      "description":"asfqwdw",
      "parentId":321515
  }
  
  <<< 400
  {
    "data":null,
    "error":"BadRequest",
    "message":"修改的部门信息不合法",
  }
  ```

### 得到某个部门的成员列表

- HTTP Request

  **GET** `/association-member-manager/members`

- Body Parameters

  | Parameter           | Description                                                  |
  | ------------------- | ------------------------------------------------------------ |
  | `associationId` int | 社团ID                                                       |
  | `departmentId` int  | 部门ID                                                       |
  | `pageNum` int       | 页码, 默认为0                                                |
  | `pageSize` int      | 每页数量, 默认为20                                           |
  | `sortBy`            | 排序方式, 可以为 _createdTime_, _handledTime_, _object.userDefinedField_, 默认为 _createdTime_ |

- Response

  | Code                 | Description                                |
  | -------------------- | ------------------------------------------ |
  | 200 `userId[]` int[] | 请求成功，返回用户ID列表(用户列表可能为空) |
  | 404 "notFound"       | 不存在该部门                               |

- Example

  ```json
  >>> GET /association-member-manager/members?associationId=1&departmentId=2&pageSize=2&pageNum=0&sortBy=createdTime
  
  <<< 200
  {
    "data":[5,65,78],
    "error":"",
    "message":"",
  }
  ```

  ```json
  >>> GET /association-member-manager/members?associationId=1&departmentId=3
  
  <<< 200
  {
    "data":[],
    "error":"",
    "message":"",
  }
  ```

  ```json
  >>> GET /association-member-manager/members?associationId=1&departmentId=312
  
  <<< 404
  {
    "data":null,
    "error":"notFound",
    "message":"不存在该部门或者社团",
  }
  ```

### 将成员从某个部门中移除

- HTTP Request

  **DELETE** `/association-member-manager/members`

- Body Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |
  | `departmentId` int  | 部门ID      |
  | `userId` int        | 用户ID      |

- Response

  | Code                | Description                |
  | ------------------- | -------------------------- |
  | 200 true            | 请求成功，并且成功删除用户 |
  | 400 “InvalidUserId” | 部门中不存在该用户         |
  | 404 "notFound"      | 不存在该部门或者社团       |

- Example

  ```json
  >>> DELETE /association-member-manager/members
  {
      "associationId":1,
      "departmentId":2,
      "userId":20
  }
  
  <<< 200
  {
      "data":true,
      "error":"",
      "message":""
  }
  ```

  ```json
  >>> DELETE /association-member-manager/members
  {
      "associationId":1,
      "departmentId":2,
      "userId":20151
  }
  
  <<< 400
  {
      "data":null,
      "error":"InvalidUserId",
      "message":"部门中不存在该用户"
  }
  ```

  ```json
  >>> DELETE /association-member-manager/members
  {
      "associationId":1,
      "departmentId":23561,
      "userId":20
  }
  
  <<< 404
  {
      "data":null,
      "error":"notFound",
      "message":"不存在该部门或者社团"
  }
  ```

### 为某个部门添加成员

- HTTP Request

  **POST** `/association-member-manager/members`

- Body Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |
  | `departmentId` int  | 部门ID      |
  | `userId` int        | 用户ID      |

- Response

  | Code                | Description                        |
  | ------------------- | ---------------------------------- |
  | 200 true            | 请求成功，并且成功在部门中添加用户 |
  | 400 “InvalidUserId” | 部门中不存在该用户                 |
  | 404 "notFound"      | 不存在该部门或者社团               |

- Example

  ```json
  >>> POST /association-member-manager/members
  {
      "associationId":1,
      "departmentId":2,
      "userId":20
  }
  
  <<< 200
  {
      "data":true,
      "error":"",
      "message":""
  }
  ```

  ```json
  >>> POST /association-member-manager/members
  {
      "associationId":1,
      "departmentId":3515,
      "userId":20
  }
  
  <<< 404
  {
      "data":null,
      "error":"notFound",
      "message":"不存在该部门或者社团"
  }
  ```

  ```json
  >>> POST /association-member-manager/members
  {
      "associationId":1,
      "departmentId":2,
      "userId":20151
  }
  
  <<< 400
  {
      "data":null,
      "error":"InvalidUserId",
      "message":"不存在该用户"
  }
  ```

### 判断该用户在某个社团是否有某个权限

- HTTP Request

  **GET** `/association-member-manager/permissions`

- Path Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |
  | `userId` int        | 用户ID      |
  | `permissionId` int  | 用户权限    |

- Response

  | Code                | Description                    |
  | ------------------- | ------------------------------ |
  | 200 true/false      | 请求成功，返回是否拥有这个权限 |
  | 400 “InvalidUserId” | 不存在该用户                   |
  | 404 "notFound"      | 不存在该社团                   |

- Example

  ```json
  >>> GET /association-member-manager/permissions?associationId=1&userId=20&permissionId=2
  
  <<< 200
  {
      "data":true,
      "error":"",
      "message":""
  }
  ```

  ```json
  >>> GET /association-member-manager/permissions?associationId=656151&userId=20&permissionId=2
  
  <<< 404
  {
      "data":null,
      "error":"notFound",
      "message":"不存在该社团"
  }
  ```

  ```json
  >>> GET /association-member-manager/permissions?associationId=1&userId=10521526&permissionId=2
  
  <<< 400
  {
      "data":null,
      "error":"InvalidUserId",
      "message":"不存在该用户"
  }
  ```

### 查询用户所属社团

- HTTP Request

  **GET** `/association-member-manager/get-user-associations`

- Body Parameters

  | Parameter    | Description |
  | ------------ | ----------- |
  | `userId` int | 用户ID      |

- Response

  | Code                        | Description                        |
  | --------------------------- | ---------------------------------- |
  | 200 `associationId[]` int[] | 请求成功，返回社团ID列表(可能为空) |
  | 400 “InvalidUserId”         | 不存在该用户                       |

- Example

  ```json
  >>> GET /association-member-manager/permissions?userId=20
  
  <<< 200
  {
      "data":[1,2,3],
      "error":"",
      "message":""
  }
  ```

  ```json
  >>> GET /association-member-manager/permissions?userId=12
  
  <<< 200
  {
      "data":[],
      "error":"",
      "message":""
  }
  ```

  ```json
  >>> GET /association-member-manager/permissions?userId=20000
  
  <<< 400
  {
      "data":null,
      "error":"InvalidUserId",
      "message":"不存在该用户"
  }
  ```

### 查询用户在指定社团所属部门

- HTTP Request

  **GET** `/association-member-manager/get-user-departments`

- Body Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `userId` int        | 用户ID      |
  | `associationId` int | 社团ID      |

- Response

  | Code                              | Description                        |
  | --------------------------------- | ---------------------------------- |
  | 200 [*Department[]*](#Department) | 请求成功，返回社团ID列表(可能为空) |
  | 400 “InvalidUserId”               | 不存在该用户                       |
  | 404 "notFound"                    | 不存在该社团                       |

- Example

  ```json
  >>> GET /association-member-manager/get-user-departments?userId=20&associationId=271
  
  <<< 200
  {
      "data":[
        {
            "associationId":271,
            "departmentId":1,
            "name":"asda",
            "description":"safda",
            "parentId":0
        },
        {
            "associationId":271,
            "departmentId":2,
            "name":"sfas",
            "description":"asfe",
            "parentId":1
        } 
      ],
      "error":"",
      "message":""
  }
  ```

  ```json
  >>> GET /association-member-manager/get-user-departments?userId=20&associationId=272
  
  <<< 200
  {
      "data":[],
      "error":"",
      "message":""
  }
  ```

  ```json
  >>> GET /association-member-manager/get-user-departments?userId=212220&associationId=2
  
  <<< 400
  {
      "data":null,
      "error":"InvalidUserId",
      "message":"不存在该用户"
  }
  ```

  ```json
  >>> GET /association-member-manager/get-user-departments?userId=20&associationId=21231
  
  <<< 404
  {
      "data":null,
      "error":"notFound",
      "message":"不存在该社团"
  }
  ```

### 将成员从社团中删除

- HTTP Request

  **DELETE** `/association-member-manager/delete-association-user`

- Body Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |
  | `userId` int        | 用户ID      |

- Response

  | Code                | Description                |
  | ------------------- | -------------------------- |
  | 200 true            | 请求成功，并且成功删除用户 |
  | 400 “InvalidUserId” | 社团中不存在该用户         |
  | 404 "notFound"      | 不存在该社团               |

- Example

  ```json
  >>> DELETE /association-member-manager/delete-association-user
  {
      "associationId":1,
      "userId":20
  }
  
  <<< 200
  {
      "data":true,
      "error":"",
      "message":""
  }
  ```

  ```json
  >>> DELETE /association-member-manager/delete-association-user
  {
      "associationId":1,
      "userId":20151
  }
  
  <<< 400
  {
      "data":null,
      "error":"InvalidUserId",
      "message":"社团中不存在该用户"
  }
  ```

  ```json
  >>> DELETE /association-member-manager/delete-association-user
  {
      "associationId":1,
      "userId":20
  }
  
  <<< 404
  {
      "data":null,
      "error":"notFound",
      "message":"不存在该社团"
  }
  ```

### 将成员从社团中删除

- HTTP Request

  **POST** `/association-member-manager/create-association-user`

- Body Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |
  | `userId` int        | 用户ID      |

- Response

  | Code                | Description                |
  | ------------------- | -------------------------- |
  | 200 true            | 请求成功，并且成功删除用户 |
  | 400 “InvalidUserId” | 不存在该用户               |
  | 404 "notFound"      | 不存在该社团               |

- Example

  ```json
  >>> POST /association-member-manager/create-association-user
  {
      "associationId":1,
      "userId":20
  }
  
  <<< 200
  {
      "data":true,
      "error":"",
      "message":""
  }
  ```

  ```json
  >>> POST /association-member-manager/create-association-user
  {
      "associationId":1,
      "userId":20151
  }
  
  <<< 400
  {
      "data":null,
      "error":"InvalidUserId",
      "message":"不存在该用户"
  }
  ```

  ```json
  >>> POST /association-member-manager/create-association-user
  {
      "associationId":1,
      "userId":20
  }
  
  <<< 404
  {
      "data":null,
      "error":"notFound",
      "message":"不存在该社团"
  }
  ```

### 

