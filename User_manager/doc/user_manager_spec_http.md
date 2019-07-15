## 用户管理
- **User**

   |属性|说明|
   |---|---|
   |`id`_int_| |
   |`nickname`_string_| 昵称 |
   |`description`_string_| 个人描述 |
   |`gender`_int_| (可选)个人信息-性别 |
   |`birthday`_string_| (可选)个人信息-生日 |
   |`name`_string_| (可选)个人信息-真实姓名 |
   |`studentNumber`_string_| (可选)个人信息-学号 |
   |`phone`_string_| (可选)联系方式-手机 |
   |`email`_string_| (可选)联系方式-邮箱 |
   |`qq`_string_| (可选)联系方式-qq号 |
   |`wechat`_string_| (可选)联系方式-微信号 |
   |`status`_int_| 账号状态(inactived, normal, blocked) |

1. 创建用户
   - Http Request
   **POST** `/users` 

   - Body Parameters
   
      |Parameter|Description|
      |---|---|
      |`nickname`_string_| 昵称 |

   - Response
 
      |code|description|
      |---|---|
      |200-(用户对象)|创建成功|
      |400-(错误信息)|创建失败|
    
   - example

       ```json
       >>> POST /users
       {
           nickname:"chenjingyu"
       }
       ```
       ```json
       <<< 200
       {
           "data": {
               "id": 421,
               "nickname":"chenjingyu",
               "description":"", 
               "gender":"", 
               "birthday":"",
               "name":"",
               "studentNumber":"",
               "phone":"",
               "email":"",
               "qq":"",
               "wechat":""
           },
           "error":"",
           "message":""
       }
       ```
       ```json
       <<< 400
       {
           "data": null,
           "error": "invalid " + 实际错误信息,
           "message": "yyyy/mm/dd" + "修改失败，请再次尝试。"
       }
       ```

2. 更改信息
   - Http Request
   **PUT** `/users/{id}`

   - Query - Path Parameters
   
      |Parameter|Description|
      |---|---|
      |`id`_int_| 用户ID |

   - Body Parameters
   
      |Parameter|Description|
      |---|---|
      |`nickname`_string_| 昵称 |
      |`description`_string_| 个人描述 |
      |`gender`_int_| (可选)个人信息-性别 |
      |`birthday`_string_| (可选)个人信息-生日 |
      |`name`_string_| (可选)个人信息-真实姓名 |
      |`studentNumber`_string_| (可选)个人信息-学号 |
      |`phone`_string_| (可选)联系方式-手机 |
      |`email`_string_| (可选)联系方式-邮箱 |
      |`qq`_string_| (可选)联系方式-qq号 |
      |`wechat`_string_| (可选)联系方式-微信号 |
   
   - Response
 
      |code|description|
      |---|---|
      |200-(用户对象)|创建成功|
      |400-(错误信息)|创建失败|
    
   - example

       ```json
       >>> POST /users
       {
           "nickname":"cjy",
           "description":"a little boy",
           "gender":1,
           "birthday":"1999-03-27",
           "name":"chenjingyu",
           "studentNumber":"517030910206",
           "phone":"127346483929",
           "email":"634247843@qq.com",
           "qq":"634247843",
           "wechat":"cjy634247843"
       }
       ```
       ```json
       <<< 200
       {
           "data": {
               "id": 421,
               "nickname":"cjy",
               "description":"a little boy",
               "gender":1,
               "birthday":"1999-03-27",
               "name":"chenjingyu",
               "studentNumber":"517030910206",
               "phone":"127346483929",
               "email":"634247843@qq.com",
               "qq":"634247843",
               "wechat":"cjy634247843"
           },
           "error":"",
           "message":""
       }
       ```
       ```json
       <<< 400
       {
           "data": null,
           "error": "invalid " + 实际错误信息,
           "message": "yyyy/mm/dd" + "修改失败，请再次尝试。"
       }
       ```

3. 激活用户
   - Http Request
   **PATCH** `/users/activate`

   - Body Parameters
   
      |Parameter|Description|
      |---|---|
      |`id`_int_| 用户ID |

   - Response
 
      |code|description|
      |---|---|
      |200-（无）|激活成功|
      |400-(错误信息)|激活失败|
    
   - example

       ```json
       >>> PATCH /users
       {
           "id":123
       }
       ```
       ```json
       <<< 200
       {
           "data": {},
           "error":"",
           "message":""
       }
       ```
       ```json
       <<< 400
       {
           "data": null,
           "error": "invalid " + 实际错误信息,
           "message": "yyyy/mm/dd" + "修改失败，请再次尝试。"
       }
       ```

4. 禁用用户
   - Http Request
   **PATCH** `/users/block`

   - Body Parameters
   
      |Parameter|Description|
      |---|---|
      |`id`_int_| 用户ID |

   - Response
 
      |code|description|
      |---|---|
      |200-（无）|禁用成功|
      |400-(错误信息)|禁用失败|
    
   - example

       ```json
       >>> PATCH /users
       {
           "id":321
       }
       ```
       ```json
       <<< 200
       {
           "data": {},
           "error":"",
           "message":""
       }
       ```
       ```json
       <<< 400
       {
           "data": null,
           "error": "invalid " + 实际错误信息,
           "message": "yyyy/mm/dd" + "修改失败，请再次尝试。"
       }
       ```

5. 恢复用户
   - Http Request
   **PATCH** `/users/recover`

   - Body Parameters
   
      |Parameter|Description|
      |---|---|
      |`id`_int_| 用户ID |

   - Response
 
      |code|description|
      |---|---|
      |200-（无）|恢复成功|
      |400-(错误信息)|恢复失败|
    
   - example

       ```json
       >>> PATCH /users
       {
           "id":312
       }
       ```
       ```json
       <<< 200
       {
           "data": {},
           "error":"",
           "message":""
       }
       ```
       ```json
       <<< 400
       {
           "data": null,
           "error": "invalid " + 实际错误信息,
           "message": "yyyy/mm/dd" + "修改失败，请再次尝试。"
       }
       ```
