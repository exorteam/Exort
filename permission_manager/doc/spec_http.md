# 权限管理模块 HTTP RESTful 接口设计

该模块维护了一个 `(用户, 域) 1->* 角色 1->* 权限` 的关系模型。

执行系统操作需要权限, 这些权限依靠角色组织起来。每个角色相当于是一系列权限的集合，同时角色也是系统向用户提供权限的粒度。

考虑到用户在系统的不同情景下(不同的模块、模块内不同的逻辑分区如社团等)会有不同的角色，在用户和角色的关系中加入了域作为限定。

## 统一返回结果格式

- <a id='ResponseBody'></a>**ResponseBody**

   |Field|Description|
   |--|--|
   |`data` _any_|对应接口约定好的返回类型, 为 `null` 时请检查 `error` 和 `message`|
   |`error` _string_|错误码|
   |`message` _string_|人类友好的错误信息|

- <a id='PagedData'></a>**PagedData**

   |Field|Description|
   |--|--|
   |`pageNum` _int_|返回的数据的实际页码|
   |`pageSize` _int_|返回的数据的实际每页数量|
   |`totalSize` _int_|所查询的数据的总数量|
   |`content` _T[]_|数据列表, 根据筛选条件可能为空|

## 基本结构

- <a id='Permission'></a>**Permission**

   |Field|Description|
   |--|--|
   |`name` _string_|权限名、权限ID|
   |`description` _string_|权限描述|
   |`category` _string_|分类, 方便用于展示的组织形式|

- <a id='Role'></a>**Role**

   |Field|Description|
   |--|--|
   |`name` _string_|角色名、角色ID|
   |`description` _string_|角色描述|

## 用户

### 获取用户的所有域

_用户在这些域中有至少一个角色_

- HTTP Request

   **GET** `/users/{userId}/scopes`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`userId` _int_|用户ID|

- Response

   |Code|Description|
   |--|--|
   |200 _string[]_|用户的域列表|

- Examples

### 获取用户在指定域的所有角色

- HTTP Request

   **GET** `/users/{userId}/scopes/{scope}/roles`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`userId` _int_|用户ID|
   |`scope` _string_|域|

- Response

   |Code|Description|
   |--|--|
   |200 [_Role[]_](#Role)|用户在指定域的角色列表, 可以为空|

- Examples

### 查询用户在指定域是否有指定角色

- HTTP Request

   **GET** `/users/{userId}/scopes/{scope}/roles/{roleName}`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`userId` _int_|用户ID|
   |`scope` _string_|域|
   |`roleName` _string_|角色名|

- Response

   |Code|Description|
   |--|--|
   |200 _{}_|用户在该域有指定角色|
   |404 "roleNotFound"|用户在该域没有指定角色|

- Examples

### 查询用户在指定域是否由指定权限

- HTTP Request

   **GET** `/users/{userId}/scopes/{scope}/permissions/{permissionName}`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`userId` _int_|用户ID|
   |`scope` _string_|域|
   |`permissionName` _string_|权限名|

- Response

   |Code|Description|
   |--|--|
   |200 _{}_|用户在该域有指定权限|
   |404 "permissionNotFound"|用户在该域没有指定权限|

- Examples

### 批量添加、移除角色

- HTTP Request

   **PUT** `/users/{userId}/scopes/{scope}/roles`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`userId` _int_|用户ID|
   |`scope` _string_|域|

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`operation` _string_|添加或移除, 可以是 _add_, _remove_, 默认为 _add_|
   |`args` _string[]_|角色名列表|

- Response

   |Code|Description|
   |--|--|
   |200 [_Role[]_](#Role)|添加、移除角色后, 用户在指定域的角色列表|
   |404 "userNotFound"|用户不存在|

- Examples

### 获取指定域上的所有用户

- HTTP Request

   **GET** `/scopes/{scope}/users`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`scope` _string_|域|

- Query Parameters

   |Parameter|Description|
   |--|--|
   |`pageNum` _int_|页码, 默认为0|
   |`pageSize` _int_|每页数量, 默认为20, 不能超过100, 否则按100查询|

- Response

   |Code|Description|
   |--|--|
   |200 [_PagedData\<int\>_](#PagedData)|用户ID列表|

- Examples

### 获取指定域上有指定角色的所有用户

- HTTP Request

   **GET** `/scopes/{scope}/roles/{roleName}/users`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`scope` _string_|域|
   |`roleName` _string_|角色名|

- Response

   |Code|Description|
   |--|--|
   |200 [_PagedData\<int\>_](#PagedData)|用户ID列表|

- Examples


## 角色

### 创建角色

- HTTP Request

   **POST** `/roles`

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`name` _string_|角色名、角色ID|
   |`description` _string_|角色描述|

- Response

   |Code|Description|
   |--|--|
   |200 [_Role_](#Role)|创建成功|
   |400 "invalidName"|无效的角色名，请参照`[a-zA-Z][1-9a-zA-Z_]*`|
   |409 "conflict"|角色已存在|

- Examples

### 删除角色

- HTTP Request

   **DELETE** `/roles/{name}`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`name` _string_|要删除的角色名|

- Response

   |Code|Description|
   |--|--|
   |200 _{}_|删除成功|

- Examples

### 编辑角色

- HTTP Request

   **PUT** `/roles/{name}`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`name` _string_|要编辑的角色名|

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`description` _string_|角色描述|

- Response

   |Code|Description|
   |--|--|
   |200 [_Role_](#Role)|编辑成功|
   |404 "roleNotFound"|角色不存在|

- Examples

### 查看角色

- HTTP Request

   **GET** `/roles/{name}`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`name` _string_|角色名|

- Response

   |Code|Description|
   |--|--|
   |200 [_Role_](#Role)|查询成功|
   |404 "roleNotFound"|角色不存在|

- Examples

### 添加、移除角色权限

- HTTP Request

   **PUT** `/roles/{name}/permissions`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`name` _string_|角色名|

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`operation` _string_|添加或者移除, 可以是 _add_, _remove_, 默认为 _add_|
   |`args` _string[]_|权限名列表|

- Response

   |Code|Description|
   |--|--|
   |200 [_Permission[]_](#Permission)|变更后的角色权限列表|
   |400 "invalidOperation"|无效的操作, 只能是 _add_, _remove_ 之一|

- Examples


### 查询角色权限列表

- HTTP Request

   **GET** `/roles/{name}/permissions`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`name` _string_|角色名|

- Response

   |Code|Description|
   |--|--|
   |200 [_Permission[]_](#Permission)|角色的权限列表|

- Examples

## 权限

### 创建权限

- HTTP Request

   **POST** `/permissions`

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`name` _string_|权限名|
   |`description` _string_|权限描述|
   |`category` _string_|分类|

- Response

   |Code|Description|
   |--|--|
   |200 [_Permission_](#Permission)|创建成功|
   |400 "invalidName"|无效的权限名，请参照`[a-zA-Z][1-9a-zA-Z_]*`|
   |409 "conflict"|权限已存在|

- Examples

### 删除权限

- HTTP Request

   **DELETE** `/permissions/{name}`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`name` _string_|要删除的权限名|

- Response

   |Code|Description|
   |--|--|
   |200 _{}_|删除成功|

- Examples

### 编辑权限

- HTTP Request

   **PUT** `/permissions/{name}`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`name` _string_|要编辑的权限名|

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`description` _string_|权限描述|
   |`category` _string_|权限所属分类|

- Response

   |Code|Description|
   |--|--|
   |200 [_Permission_](#Permission)|编辑成功|
   |404 "permissionNotFound"|权限不存在|

- Examples

### 查看权限

- HTTP Request

   **GET** `/permissions/{name}`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`name` _string_|要查询的权限名|

- Response

   |Code|Description|
   |--|--|
   |200 [_Permission_](#Permission)|查询成功|
   |404 "permissionNotFound"|权限不存在|

- Examples

### 获取权限列表

- HTTP Request

   **GET** `/permissions`

- Response

   |Code|Description|
   |--|--|
   |200 [_Permission[]_](#Permission)|按分类排序的权限列表, 可以为空|

- Examples
