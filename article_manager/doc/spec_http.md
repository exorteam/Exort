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
   |200 <br>_articleId_|文章ID|

- Examples

## 解引用标签

- HTTP Request

   **DELETE** `/categories/{category}/tags/{name}/refs/{referrer}`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`category` _string_|标签分类|
   |`name` _string_|标签名|
   |`referrer` _string_|解引用者|

- Response

   |Code|Description|
   |--|--|
   |200 [_Tag_](#Tag)|精简标签|

- Examples

## 查询标签

- HTTP Request

   **GET** `/categories/{category}/tags/{name}`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`category` _string_|标签分类|
   |`name` _string_|标签名|

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`simple` _bool_|若为 _true_, 则返回精简标签. 默认为 _false_|

- Response

   |Code|Description|
   |--|--|
   |200 [_Tag_](#Tag)|根据 `simple` 返回精简或完整的标签|

- Examples

## 搜索标签

- HTTP Request

   **GET** `/categories/{category}/tags`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`category` _string_|标签分类|

- Query Parameters

   |Parameter|Description|
   |--|--|
   |`pageNum` _int_|页码, 默认为0|
   |`pageSize` _int_|每页数量, 默认为10|
   |`sortBy` _string_|默认为 _default_, 不作排序. 也可以是 _totalRefs_ 按总引用数降序|

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`keyword` _string_|关键词|
   |`simple` _bool_|指定返回的是否精简标签, 默认为 _true_|

- Response

   |Code|Description|
   |--|--|
   |200 [_TagList_](#TagList)|查询成功|

- Examples
