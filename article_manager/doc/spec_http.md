# 文章管理模块 HTTP RESTful 接口设计

## 统一返回结果格式

- <a id='ResponseBody'></a>**ResponseBody**

   |Field|Description|
   |--|--|
   |`data` _any_|对应接口约定好的返回类型, 为 `null` 时请检查 `error` 和 `message`|
   |`error` _string_|错误码|
   |`message` _string_|人类友好的错误信息|

## 文章

- <a id='Article'></a>**Article**

   |Field|Description|
   |--|--|
   |`id` _int_|文章ID|
   |`associationIds` _int[]_|作者|
   |`createTime` _Date_|创建时间|
   |`publishTime` _Date_|第一次发布时间|
   |`lastPublishTime` _Date_|最后一次发布时间|
   |`lastModifyTime` _Date_|最后一次修改时间|
   |`title` _String_|文章标题|
   |`content` _String_|文章内容|
   |`state` _int_|文章状态|
   |`createMethod` _int_|文章创建方式|

## 创建文章

- HTTP Request

   **POST** `/articles`

- Path Parameters

   |Parameter|Description|
   |--|--|

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`title` _String_|文章标题|
   |`content` _String_|文章内容|
   |`associationIds` _int[]_|作者|

- Response

   |Code|Description|
   |--|--|
   |200 <br>articleId|文章ID|

- Examples

## 查看文章

- HTTP Request

   **GET** `/articles/{articleId}`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`articleId` _int_|文章ID|

- Response

   |Code|Description|
   |--|--|
   |200 <br>[Article](#Article)|文章信息|

- Examples

## 更新文章

- HTTP Request

   **PUT** `/articles/{articleId}`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`articleId` _int_|文章ID|

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`title` _String_|文章标题|
   |`content` _String_|文章内容|

- Response

   |Code|Description|
   |--|--|
   |200 <br>[Article](#Article)|文章信息|

- Examples

## 删除文章

- HTTP Request

   **DELETE** `/articles/{articleId}`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`articleId` _int_|文章ID|

- Body Parameters

   |Parameter|Description|
   |--|--|

- Response

   |Code|Description|
   |--|--|
   |200 <br>Empty Object|删除成功返回空对象|

- Examples

## 发布或撤回文章

- HTTP Request

   **PATCH** `/articles/{articleId}`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`articleId` _int_|文章ID|

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`publish` _boolean_|是则发布，否则撤回|

- Response

   |Code|Description|
   |--|--|
   |200 <br>[Article](#Article)|文章信息|

- Examples

