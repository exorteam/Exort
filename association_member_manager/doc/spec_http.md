

# 社团成员管理模块 HTTP RESTful 接口设计

## 统一返回结果格式

- <a id='ResponseBody'></a> ResponseBody

  | Field            | Description                                               |
  | ---------------- | --------------------------------------------------------- |
  | `data` any       | 对应接口约定好的返回类型, 为null时请检查 error 和 message |
  | `error` string   | 状态/错误码                                               |
  | `message` string | 人类友好的状态/错误信息                                   |

## 申请信息

- <a id='Application'></a> Application

  | Field                                              | Description                                              |
  | -------------------------------------------------- | -------------------------------------------------------- |
  | `id` *int*                                         | 申请ID                                                   |
  | `applicantId` *int*                                | 申请者ID                                                 |
  | `type` *string*                                    | 申请类型                                                 |
  | `departmentInfo` [DepartmentInfo](#DepartmentInfo) | 自定义json对象                                           |
  | `materialIds` *int[]*                              | 申请材料ID列表                                           |
  | `createdTime` *string*                             | 申请时间                                                 |
  | `handledTime` *string*                             | 处理时间                                                 |
  | `state` *string*                                   | 状态,可以是 *pending*, *accepted*, *refused*, *canceled* |

  

## 申请社团信息

- <a id='DepartmentInfo'></a> DepartmentInfo

  | 属性                | 说明       |
  | ------------------- | ---------- |
  | `associationId` int | 申请社团ID |
  | `departmentId` int  | 申请部门ID |

## 部门

- <a id='Department'></a> Department

  | 属性               | 说明         |
  | ------------------ | ------------ |
  | int associationId  | 社团ID       |
  | int departmentId   | 部门ID       |
  | String name        | 部门名称     |
  | String description | 部门描述     |
  | int parentId       | 父部门节点ID |

### 通过申请

> 回调注册

- HTTP Request

  **POST** `/application/accept`

- Body Parameters

  | Parameter                                   | Description                                          |
  | ------------------------------------------- | ---------------------------------------------------- |
  | `userId` int                                | 用户ID                                               |
  | `event` string                              | 事件，可以是 *create*, *accepte*, *refuse*, *cancel* |
  | `application` [_Application_](#Application) | 申请                                                 |

- Response

  | Code              | Description |
  | ----------------- | ----------- |
  | 200 true          | 请求成功    |
  | 400 "InvalidUser" | 用户已存在  |

- Example

  ```json
  >>> POST /association/application
  {
      "userId":2,
      "event":"create",
      "application":{
          "id":123,
          "applicant":2,
          "type":"acceptApplication",
          "AssociationInfo":{
              "associationId":2,
              "departmentId":3
          },
        "materialIds":[
              12,
              31
          ],
          "createdTime":"2019-7-17",
          "handledTime":"2019-5-15",
          "state":"pending"
      }
  }
  
  <<< 200
  {
      "data":true,
      "error":"",
    "message":""
  }
  ```
  
  ```json
  >>> POST /association/application
  {
      "userId":2,
      "event":"create",
      "application":{
          "id":123,
          "applicant":2,
          "type":"acceptApplication",
          "AssociationInfo":{
              "associationId":2,
              "departmentId":3
          },
          "materialIds":[
              12,
              31
          ],
          "createdTime":"2019-7-17",
          "handledTime":"2019-5-15",
          "state":"pending"
      }
  }
  
  <<< 400
  {
      "data":null,
      "error":"InvalidUser",
      "message":"用户已存在"
  }
  ```
  
  

### 得到部门树

- HTTP Request

  **GET** `/associations/{associationId}/departments`

- Path Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |

- Reponse

  | Code                              | Description                                        |
  | --------------------------------- | -------------------------------------------------- |
  | 200 [*Department[]*](#Department) | 查询成功, 返回列表(至少有两个，有管理层和普通成员) |
  | 404 “AssociationNotFound”         | 该社团不存在                                       |

- Examples

  ```json
  >>> GET /associations/271/departments
  
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
  >>> GET /associations/3516/departments
  
  <<< 404
  {
    "data":null,
    "error":"AssociationNotFound",
    "message":"该社团不存在",
  }
  ```
  
  


### 得到某个部门的信息

- HTTP Request

  **GET** `/associations/{associationId}/departments/{departmentId}`

- Path Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |
  | `departmentId` int  | 部门ID      |

- Response

  | Code                            | Description  |
  | ------------------------------- | ------------ |
  | 200 [_Department_](#Department) | 查询成功     |
  | 404 "DepartmentNotFound"        | 不存在该部门 |

- Examples

  ```json
  >>> GET /associations/271/departments/1
  
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
  >>> GET /associations/271/departments/1
  
  <<< 404
  {
      "data":null,
      "error":"DepartmentNotFound",
    	"message":"不存在该社团"
  }
  ```

### 创建部门

- HTTP Request

  **POST** `/associations/{associationId}/departments`

- Path Parameters

  | Parameter           | Description                                 |
  | ------------------- | ------------------------------------------- |
  | `associationId` int | 社团ID (应为已存在社团ID，否则为BadRequest) |
  
- Body Parameters

  | Parameter            | Description                                     |
  | -------------------- | ----------------------------------------------- |
  | `name` string        | 部门名称                                        |
  | `description` string | 部门描述                                        |
  | `parentId` int       | 部门父节点 (应为已存在部门ID，否则为BadRequest) |
  
- Response

  | Code                            | Description        |
  | ------------------------------- | ------------------ |
  | 201 [_Department_](#Department) | 成功               |
  | 400 "InvalidDepartment"         | 部门创建信息不合法 |
  | 404 “AssociationNotFound”       | 该社团不存在       |

- Examples

  ```json
  >>> POST /associations/26/departments
  {
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
  >>> POST /associations/26/departments
  {
    "name": "asfaasd",
    "description": "asfqwd",
    "parentId":100
  }
  
  <<< 400
  {
    "data": null,
    "error": "InvalidDepartment",
    "message": "部门创建信息不合法"
  }
```


### 删除部门

- HTTP Request

  **DELETE** `/associations/{associationId}/departments/{departmentId}`

- Path Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |
  | `departmentId` int  | 部门ID      |

- Response

  | Code                            | Description                  |
  | ------------------------------- | ---------------------------- |
  | 200 [_Department_](#Department) | 删除成功，返回删除的部门信息 |
  | 404 "DepartmentNotFound"        | 不存在该部门                 |

- Examples

  ```
  >>> Delete /associations/1/departments/2
  
  
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
  >>> POST /associations/1/departments/23156

  
  <<< 404
  {
    "data": null,
    "error": "DepartmentNotFound",
    "message": "该部门不存在"
  }
  ```

### 编辑部门

- HTTP Request

  **PUT** `/associations/{associationId}/departments/{departmentId}`

- Path Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |
  | `departmentId` int  | 部门ID      |

- Body Parameters

  | Parameter            | Description                      |
  | -------------------- | -------------------------------- |
  | `name` string        | 部门名称                         |
  | `description` string | 部门描述                         |
  | `parentId` int       | 父节点部门ID（应为已存在部门ID） |
  
- Response

  | Code                            | Description                    |
  | ------------------------------- | ------------------------------ |
  | 200 [_Department_](#Department) | 编辑成功，返回编辑后的部门信息 |
  | 400 "InvalidDepartment"         | 修改的部门信息不合法           |
  | 400 "InvalidParentId"           | 无效父节点                     |
  | 404 "DepartmentNotFound"        | 不存在该部门                   |

- Example

  ```json
  >>> PUT /associations/1/departments/2
  {
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
  PUT /associations/1/departments/23515
  {
        "name":"asfawd",
        "description":"asfqwdw",
        "parentId":0
  }
  
  <<< 404
  {
      "data":null,
      "error":"DepartmentNotFound",
      "message":"不存在该部门",
  }
  ```



  ```
  
  ```json
  >>> PUT /associations/1/departments/2
  {
    "name":"asfawd",
      "description":"asfqwdw",
      "parentId":321515
  }
  
  <<< 400
  {
    "data":null,
    "error":"InvalidParentId",
    "message":"无效父节点",
  }
  ```

  ```json
  >>> PUT /associations/1/departments/2213124
  {
      "name":"asfawd",
      "description":"asfqwdw",
      "parentId":2
  }
  
  <<< 400
  {
    "data":null,
    "error":"InvalidDepartment",
    "message":"修改的部门信息不合法",
  }
  ```

  

### 得到某个部门的成员列表

- HTTP Request

  **GET** `/asssociations/{associationId}/departments/{departmentId}/members`

- Path Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |
  | `departmentId` int  | 部门ID      |
  
- Response

  | Code                     | Description                                  |
  | ------------------------ | -------------------------------------------- |
  | 200 `userId[]` int[]     | 请求成功，返回用户ID列表(用户列表不可能为空) |
  | 404 "DepartmentNotFound" | 不存在该部门                                 |

- Example

  ```json
  >>> GET /asssociations/1/departments/2/members
  
  <<< 200
  {
  "data":[5,65,78],
    "error":"",
    "message":"",
  }
  ```
  
  ```json
  >>> GET /asssociations/1/departments/31412/members
  
  <<< 404
  {
    "data":null,
    "error":"DepartmentNotFound",
    "message":"不存在该部门",
  }
  ```

### 将成员从某个部门中移除

- HTTP Request

  **DELETE** `/associations/{associationId}/departments/{departmentId}/members/{userId}`

- Path Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |
  | `departmentId` int  | 部门ID      |
  | `userId` int        | 用户ID      |

- Response

  | Code                     | Description                |
  | ------------------------ | -------------------------- |
  | 200 true                 | 请求成功，并且成功删除用户 |
  | 400 “InvalidUserId”      | 不存在该用户               |
  | 404 "DepartmentNotFound" | 不存在该部门               |
  | 404 "UserNotFound"       | 社团中不存在该用户         |
  
- Example

  ```json
  >>> DELETE /associations/1/departments/2/members/20
  
  
  <<< 200
  {
      "data":true,
      "error":"",
      "message":""
  }
  ```
  
  ```json
  >>> DELETE /associations/1/departments/2/members/205186
  
  
  <<< 400
    {
        "data":null,
        "error":"InvalidUserId",
        "message":"不存在该用户"
    }
  ```

  ```json
  
  ```
 >>> DELETE /associations/1/departments/2516651/members/20

 <<< 404
 {
      "data":null,
      "error":"DepartmentNotFound",
      "message":"不存在该部门"
 }
  ```

  ```json
  >>> DELETE /associations/1/departments/2/members/22
  
  <<< 404
  {
      "data":null,
      "error":"UserNotFound",
      "message":"社团中不存在该用户"
  }
  ```

  

### 为某个部门添加成员

- HTTP Request

  **POST** `/associations/{associationId}/departments/{departmentId}/members`

- Path Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |
  | `departmentId` int  | 部门ID      |
  
- Body Parameters

  | Parameter    | Description |
  | ------------ | ----------- |
  | `userId` int | 用户ID      |
  
- Response

  | Code                     | Description                        |
  | ------------------------ | ---------------------------------- |
  | 200 true                 | 请求成功，并且成功在部门中添加用户 |
  | 400 “InvalidUserId”      | 不存在该用户                       |
  | 404 "DepartmentNotFound" | 不存在该部门                       |
  
- Example

  ```json
  >>> POST /associations/1/departments/2/members
  {
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
  >>> POST /associations/1/departments/21412/members
  {
      "userId":20
  }
    
    
  <<< 404
  {
       "data":null,
       "error":"DepartmentNotFound",
       "message":"不存在该部门"
  } 	
  ```
  
  
  
  ```json
  >>> POST /associations/1/departments/21412/members
  {
      "userId":202312
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

  **GET** `/associations/{associationId}/members/{userId}/permissions/{permission}`

- Path Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |
  | `userId` int        | 用户ID      |
  | `permission` string | 用户权限    |

- Response

  | Code                      | Description           |
  | ------------------------- | --------------------- |
  | 200 true                  | 请求成功,且拥有该权限 |
  | 400 “InvalidUserId”       | 不存在该用户          |
  | 404 "AssociationNotFound" | 不存在该社团          |
  | 404 "UserNotFound"        | 用户不在该社团中      |
  | 404 "PermissionNotFound"  | 没有该权限            |

- Example

  ```json
  >>> GET /associations/1/members/2/permissions/read
  
  <<< 200
  {
      "data":true,
      "error":"",
      "message":""
  }
  ```

  ```json
  >>> GET associations/1322/members/2/permissions/read
  
  <<< 404
  {
      "data":null,
      "error":"AssociationNotFound",
      "message":"不存在该社团"
  }
  ```

  ```json
  >>> GET associations/1/members/22/permissions/read
  
  <<< 404
  {
      "data":null,
      "error":"UserNotFound",
      "message":"用户不在该社团中"
  }
  ```
  
  ```json
  >>> GET associations/1/members/2/permissions/read
  
  <<< 404
  {
      "data":null,
      "error":"PermissionNotFound",
      "message":"没有该权限"
  }
  ```
  
  
  
  ```json
  >>> GET associations/1/members/2214/permissions/read
  
  <<< 400
  {
      "data":null,
      "error":"InvalidUserId",
      "message":"不存在该用户"
  }
  ```

### 查询用户所属社团

- HTTP Request

  **GET** `/users/{userId}/associations`

- Path Parameters

  | Parameter    | Description |
  | ------------ | ----------- |
  | `userId` int | 用户ID      |

- Response

  | Code                        | Description  |
  | --------------------------- | ------------ |
  | 200 `associationId[]` int[] | 请求成功     |
  | 400 “InvalidUserId”         | 不存在该用户 |

- Example

  ```json
  >>> GET /users/20/associations
  
  <<< 200
  {
      "data":[1,2,3],
      "error":"",
      "message":""
  }
  ```

  ```json
  >>> GET /users/200000/associations
  
  <<< 400
  {
      "data":null,
      "error":"InvalidUserId",
      "message":"不存在该用户"
  }
  ```

### 查询用户在指定社团所属部门

- HTTP Request

  **GET** `/users/{userId}/associations/{associationId}/departments`

- Path Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `userId` int        | 用户ID      |
  | `associationId` int | 社团ID      |

- Response

  | Code                              | Description        |
  | --------------------------------- | ------------------ |
  | 200 [*Department[]*](#Department) | 请求成功           |
  | 400 “InvalidUserId”               | 不存在该用户       |
  | 404 "AssociationNotFound"         | 不存在该社团       |
  | 404 "UserNotFound"                | 用户没有参与该社团 |

- Example

  ```json
  >>> GET /users/1/associations/21/departments
  
  <<< 200
  {
      "data":[
        {
            "associationId":21,
            "departmentId":1,
            "name":"asda",
            "description":"safda",
            "parentId":0
        },
        {
            "associationId":21,
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
  >>> GET /users/112312/associations/21/departments
  
  <<< 400
  {
      "data":null,
      "error":"InvalidUserId",
      "message":"不存在该用户"
  }
  ```

  ```json
  >>> GET /users/1/associations/2123123/departments
  
  <<< 400 
  {
      "data":null,
      "error":"AssociationNotFound",
      "message":"不存在该社团"
  }
  ```

  ```json
  >>> GET /users/1/associations/2/departments
  
  <<< 404
  {
      "data":null,
      "error":"UserNotFound",
      "message":"用户没有参与该社团"
  }
  ```
  
  

### 将成员从社团中删除

- HTTP Request

  **DELETE** `/associations/{associationId}/members/{userId}`

- Path Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |
  | `userId` int        | 用户ID      |

- Response

  | Code                      | Description                |
  | ------------------------- | -------------------------- |
  | 200 true                    | 请求成功，并且成功删除用户 |
  | 400 “InvalidUserId”       | 不存在该用户               |
  | 404 "UserNotFound"        | 社团中不存在该用户         |
  | 404 "AssociationNotFound" | 不存在该社团               |

- Example

  ```json
  >>> DELETE /associations/1/members/20
  
  
  <<< 200
  {
      "data":true,
      "error":"",
      "message":""
  }
  ```
  
  ```json
  >>> DELETE /associations/1/members/200231

  <<< 400
  {
      "data":null,
      "error":"InvalidUserId",
      "message":"不存在该用户"
  }
  ```
  
  ```json
  >>> DELETE /associations/1231/members/20
  
  <<< 404
  {
      "data":null,
    "error":"AssociationNotFound",
      "message":"不存在该社团"
  }
  ```
  
  
  
  ```json
  >>> DELETE /associations/1/members/20
  
  <<< 404
  {
      "data":null,
      "error":"UserNotFound",
      "message":"社团中不存在该用户"
  }
  ```

### 将用户添加到社团中

- HTTP Request

  **POST** `/associations/{associationId}/members`

- Path Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |

- Body Parameters

  | Parameter    | Description |
  | ------------ | ----------- |
  | `userId` int | 用户ID      |
  
- Response

  | Code                      | Description                |
  | ------------------------- | -------------------------- |
  | 200 true                  | 请求成功，并且成功删除用户 |
  | 400 “InvalidUserId”       | 不存在该用户               |
  | 404 "AssociationNotFound" | 不存在该社团               |

- Example

  ```json
  >>> POST /associations/1/members
  {
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
  >>> POST /associations/1/members
  {
      "userId":20051
  }
  
  <<< 400
  {
      "data":null,
      "error":"InvalidUserId",
      "message":"不存在该用户"
  }
```

  ```json
>>> POST /associations/111/members
  {
      "userId":20
  }
  
  <<< 404
  {
      "data":null,
      "error":"AssociationNotFound",
      "message":"不存在该社团"
  }
  ```

### 得到社团成员列表

- HTTP Request

  **GET** `/associations/{associationId}/members`

- Path Parameters

  | Parameter           | Description |
  | ------------------- | ----------- |
  | `associationId` int | 社团ID      |

- Response

  | Code                      | Description            |
  | ------------------------- | ---------------------- |
  | 200 `userId[]` int[]      | 请求成功，返回用户列表 |
  | 404 "AssociationNotFound" | 不存在该社团           |

- Example

  ```json
  >>> GET /associations/1/members
  
  <<< 200
  {
      "data":[1,2,3],
      "error":"",
      "message":""
  }
  ```

  ```json
  >>> GET /associations/2000/members
  
  <<< 404
  {
      "data":null,
      "error":"AssociationNotFound",
      "message":"不存在该社团"
  }
  ```

  