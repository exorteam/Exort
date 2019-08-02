## Activity Manager
1.  POST 创建活动
    - 输入
      |Parameter|Description|
      |---|---|
      |`associationIds`_int[]_| 社团IDs |
      |`title`_string_|标题|
      |`content`_string_|内容（简介）|
      |`signupTime`_NewDateTime_|报名时间范围|
      |`time`_NewDateTime_|活动时间范围|
      |`ifReview`_bool_|报名是否需要审核|
      |`ifOnlyMem`_bool_|是否仅社团成员参加|
      |`numberOfParticipants`_int_|最大参加人数|
      |`materialTemplateIds`_int[]_|材料模板|
      |`tags`_string[]_|标签|
      |`image`_String_| 活动图片 |
   
   - 输出 
      - 活动

2.  POST 修改活动
    - 输入

      |Parameter|Description|
      |---|---|
      |`associationIds`_int[]_| 社团IDs |
      |`title`_string_|标题|
      |`content`_string_|内容（简介）|
      |`signupTime`_NewDateTime_|报名时间范围|
      |`time`_NewDateTime_|活动时间范围|
      |`ifReview`_bool_|报名是否需要审核|
      |`ifOnlyMem`_bool_|是否仅社团成员参加|
      |`numberOfParticipants`_int_|最大参加人数|
      |`materialTemplateIds`_int[]_|材料模板|
      |`tags`_string[]_|标签|
      |`image`_String_| 活动图片 |

   - 输出
      - 活动

3.  POST 发布活动
    - 输入
       - 活动ID
       - 动作类型  "publish"
    - 输出
        - 无/(true, false)
4.  POST 取消发布活动
    - 输入
       - 活动ID
       - 动作类型  "withdraw"
    - 输出
       - 无/(true, false)
5. POST 筛选活动（分页）
    - 输入
       - Query Parameters

         |Parameter|Description|default Value|
         |---|---|---|
         |`pagesize`|每页数量|8|
         |`pagenum`|页号|0|
         |`sortby`|排序方式|0|

      - Body Parameters

         |Parameter|Description|default Value|
         |---|---|---|
         |`associationId`_int_| 社团ID |创建者|
         |`tags`_string[\]_|标签|[](空列表)|
         |`keyword`_string_|内容（简介）| null |
         |`createTime`_TimeRange_|创建时间| null |
         |`signupTime`_TimeRange_|报名时间| null |
         |`startTime`_TimeRange_|开始时间| null |
         |`publishState`_int_| 活动状态(0/unpublished, 1/published)| -1 |
         |`signupState`_int_| 报名状态(0报名未开始，1报名中，2报名已结束)| -1 |
         |`state`_int_| 活动状态(0未开始，1进行中，2已结束) | -1 |
         |`ifReview`_bool_|报名是否需要审核| -1 |
         |`ifOnlyMem`_bool_|活动是否仅社团成员可参加| -1 |

    - 输出
       - 活动列表

6.  GET 查询单个活动（详情）
    - 输入
       - 活动ID
    - 输出
       - 活动

7.  POST 通过审核
    - 输入
       - 活动ID
       - 用户ID
       - 操作 （string: "accept"）
    - 输出
       - 无/(true/false)

8.  POST 不通过审核
    - 输入
       - 活动ID
       - 用户ID
       - 操作 （string: "refused"）
    - 输出
       - 无/(true/false)

9.  POST 筛选标签列表（根据keyword，分页）
    - 输入
       - keyword
    - 输出
       - tag列表

10.  POST 存储标签
    - 输入
       - 待标签列表
    - 输出
       - 无/（true/false）

11.  POST 查询参加者（分页）
    - 输入
       - pagesize
       - pagenum
       - 用户类型（participant是1，realParticipant是2, 这里是1）
    - 输出
       - 参加者列表

12.  POST 查询实际参加者（分页）
    - 输入
       - pagesize
       - pagenum
       - 用户类型（participant是1，realParticipant是2, 这里是2）
    - 输出
       - 参加者列表
