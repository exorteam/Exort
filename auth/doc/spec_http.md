# 登录模块 HTTP RESTful 接口设计

## 统一返回结果格式

- <a id='ResponseBody'></a>**ResponseBody**

   |Field|Description|
   |--|--|
   |`data` _any_|对应接口约定好的返回类型, 为 `null` 时请检查 `error` 和 `message`|
   |`error` _string_|错误码|
   |`message` _string_|人类友好的错误信息|

## 邮箱注册登录

- <a id='Account'></a>**Account**

   |Field|Description|
   |--|--|
   |`email` _string_|邮箱|
   |`verified` _string_|是否已验证, 可以是 _true_, _false_|
   |`userId` _int_|关联用户|
   |`createdTime` _string_|创建时间|
   |`verifiedTime` _string_|通过验证时间|
   |`protected` _bool_|关键操作是否可以绕过密码保护, _true_ 或 _false_|

- <a id='Code'></a>**Code**

   |Field|Description|
   |--|--|
   |`code` _string_|验证码|
   |`expiredTime` _string_|在此时间后失效, ISO格式时间字符串|

### 创建账号

- HTTP Request

   **POST** `/accounts`

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`email` _string_|注册邮箱|
   |`password` _string_|密码|

- Response

   |Code|Description|
   |--|--|
   |200 [_Account_](#Account)|创建成功|
   |400 "invalidPassword"|密码格式错误, 格式要求:...|
   |409 "conflict"|邮箱已被占用|

- Examples

   ```json
   >>> POST /accounts
   {
       "email": "hello@exort.com",
       "password": "password"
   }

   <<< 200
   {
       "data": {
           "email": "hello@exort.com",
           "verified": "false",
           "userId": null,
           "createdTime": "2019-07-12T01:24:59.353Z",
           "verifiedTime": null,
           "protected": true,
       },
       "error": "",
       "message": ""
   }

   >>> POST /accounts
   {
       "email": "hello@exort.com",
       "password": "password"
   }

   <<< 409
   {
       "data": null,
       "error": "conflict",
       "message": "邮箱已被占用"
   }

   >>> POST /accounts
   {
       "email": "world@exort.com",
       "password": "123"
   }

   <<< 400
   {
       "data": null,
       "error": "invalidPassword",
       "message": "密码格式错误, 格式要求:..."
   }
   ```

### 更换密码

- HTTP Request

   **PUT** `/accounts/{email}/password`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`email` _string_|要更改密码的账号邮箱|

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`password` _string_|旧密码, 在账号的 `protected` 为 _false_ 时忽略该参数|
   |`newPassword` _string_|新密码|

- Response

   |Code|Description|
   |--|--|
   |200 [_Account_](#Account)|更换成功|
   |400 "invalidPassword"|新密码格式错误, 格式要求:...|
   |401 "wrongPassword"|旧密码错误|
   |404 "notFound"|账号不存在|

- Examples

   ```json
   >>> PUT /accounts/hello%40exort.com/password
   {
       "password": "a",
       "newPpassword": "newPassword"
   }

   <<< 401
   {
       "data": null,
       "error": "wrongPassword",
       "message": "旧密码错误"
   }
   ```

   ```json
   >>> PUT /accounts/hello%40exort.com/password
   {
       "password": "password",
       "newPassword": "wihfkznkfdl"
   }

   <<< 200
   {
       "data": {
           "email": "hello@exort.com",
           "verified": "false",
           "userId": null,
           "createdTime": "2019-07-12T01:24:59.353Z",
           "verifiedTime": null,
           "protected": true
       },
       "error": "",
       "message": ""
   }
   ```

   ```json
   >>> PUT /accounts/world%40exort.com/password
   {
       "newPassword": "fasflajwof"
   }

   <<< 200
   {
       "data": {
           "email": "world@exort.com",
           "verified": "false",
           "userId": null,
           "createdTime": "2019-07-12T01:24:59.353Z",
           "verifiedTime": null,
           "protected": false
       },
       "error": "",
       "message": ""
   }
   ```

### 关联用户

- HTTP Request

   **POST** `/accounts/{email}/user`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`email` _string_|要关联用户的账号邮箱|

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`userId` _int_|要关联的用户ID|

- Response

   |Code|Description|
   |--|--|
   |200 [_Account_](#Account)|关联成功|
   |400 "invalidUserId"|用户不存在, 或用户已被其他账号绑定|
   |404 "notFound"|账号不存在|
   |409 "conflict"|账号已绑定了用户, 更换关联用户请使用 PUT 方法|

- Examples

   ```json
   >>> POST /accounts/hello%40exort.com/user
   {
       "userId": 1
   }

   <<< 200
   {
       "data": {
           "email": "hello@exort.com",
           "verified": "false",
           "userId": 1,
           "createdTime": "2019-07-12T01:24:59.353Z",
           "verifiedTime": null,
           "protected": true
       },
       "error": "",
       "message": ""
   }

   >>> POST /accounts/hello%40exort.com/user
   {
       "userId": 2
   }

   <<< 409
   {
       "data": null,
       "error": "conflict",
       "message": "账号已绑定了用户"
   }
   ```

   ```json
   >>> POST /accounts/world%40exort.com/user
   {
       "userId": 1
   }

   <<< 400
   {
       "data": null,
       "error": "invalidUserId",
       "message": "用户不存在, 或用户已被其他账号绑定"
   }
   ```

   ```json
   >>> POST /accounts/ajlfjsjdl%40exort.com/user
   {
       "userId": 1000
   }

   <<< 400
   {
       "data": null,
       "error": "notFound",
       "message": "账号不存在"
   }
   ```

### 生成邮箱验证码

- HTTP Request

   **POST** `/accounts/{email}/code`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`email` _string_|要生成验证码的账号邮箱|

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`secondsToRefresh` _int_|若现有验证码在此秒数后已经过期, 则刷新验证码, 设置为负数表示无论如何都刷新验证码, 默认为120|
   |`secondsToExpire` _int_|若刷新了验证码, 则设置它在此秒数后失效, 默认为300|

- Response

   |Code|Description|
   |--|--|
   |200 [_Code_](#Code)|邮箱验证码|

- Examples

   ```json
   >>> PUT /accounts/hello%40exort.com/code

   <<< 200
   {
       "data": {
           "code": "72476809-0e1d-4c89-bc12-2e30bdbaa719",
           "expiredTime": "2019-07-12T03:28:39.591Z"
       },
       "error": "",
       "message": ""
   }
   ```

### 检验邮箱验证码

- HTTP Request

   **DELETE** `/accounts/{email}/code/{code}`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`email` _string_|要生成验证码的账号邮箱|
   |`code` _string_|要检验的邮箱验证码|

- Response

   |Code|Description|
   |--|--|
   |200 _{}_|邮箱验证码|
   |400 "invalidAccount"|账号不存在|
   |404 "notFound"|验证码失效或不存在|

- Examples

   ```json
   >>> DELETE /accounts/hello%40exort.com/code/72476809-0e1d-4c89-bc12-2e30bdbaa719

   <<< 200
   {
       "data": {},
       "error": "",
       "message": ""
   }

   >>> DELETE /accounts/hello%40exort.com/code/72476809-0e1d-4c89-bc12-2e30bdbaa719
   
   <<< 404
   {
       "data": null,
       "error": "notFound",
       "message": "验证码失效或不存在"
   }
   ```

   ```json
   >>> DELETE /accounts/asjdlfjasdj%40exort.com/code/72476809-0e1d-4c89-bc12-2e30bdbaa719

   <<< 400
   {
       "data": null,
       "error": "invalidAccount",
       "message": "账号不存在"
   }
   ```

## 第三方 OAuth2 登录

TODO:
