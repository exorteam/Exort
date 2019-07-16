# 标签管理模块 HTTP RESTful 接口设计

## 统一返回结果格式

- <a id='ResponseBody'></a>**ResponseBody**

   |Field|Description|
   |--|--|
   |`data` _any_|对应接口约定好的返回类型, 为 `null` 时请检查 `error` 和 `message`|
   |`error` _string_|错误码|
   |`message` _string_|人类友好的错误信息|

## 标签

- <a id='Tag'></a>**Tag**

   |Field|Description|
   |--|--|
   |`name` _string_|标签名|
   |`category` _string_|标签分类|
   |`totalRefs` _int_|总引用数|
   |`refs` [_RefMap_](#RefMap)|引用表|

   精简标签：`refs` 为 _null_

- <a id='RefMap'></a>**RefMap**

   ||Type|Description|
   |--|--|--|
   |Key|_string_|引用者|
   |Value|_int_|引用次数|

- <a id='TagList'></a>**TagList**

   |Field|Description|
   |--|--|
   |`pageNum` _int_|所返回的列表的实际的页码|
   |`pageSize` _int_|所返回的列表的实际的标签数量|
   |`totalSize` _int_|搜索到的总的标签数量|
   |`content` [_Tag[]_](#Tag)|标签列表|

## 引用标签

- HTTP Request

   **POST** `/categories/{category}/tags/{name}/refs`

- Path Parameters

   |Parameter|Description|
   |--|--|
   |`category` _string_|标签分类|
   |`name` _string_|标签名|

- Body Parameters

   |Parameter|Description|
   |--|--|
   |`referrer` _string_|引用者|

- Response

   |Code|Description|
   |--|--|
   |200 [_Tag_](#Tag)|精简标签|

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
