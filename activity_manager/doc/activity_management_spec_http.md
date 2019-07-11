## 活动管理
- **Activity**

   - 活动时间范围 NewDateTime
      - 范围类型 int timeType
      - 时间范围 String time, String[] time...

   |属性|说明|
   |---|---|
   |int id | |
   |int[] associationIds | 组织者|
   |Datetime createTime | 创建时间 |
   |Datetime publishTime | 第一次发布时间 |
   |Datetime lastPublishTime | 最后一次发布时间 |
   |Datetime lastModifyTime | 最后一次修改时间 |
   |string title | 活动标题 |
   |string content | 活动内容 |
   |int state | 活动状态, unpublished, published |
   |NewDateTime signupTime | 活动报名时间范围 |
   |NewDateTime time | 活动时间范围 |
   |int state | 活动状态(preparing, signup, doing, done) |
   |bool ifReview | 报名是否需要审核 |
   |bool onlyMembers | 是否只有社团成员可以报名 |
   |int maxParticipants | 最大参与人数 |
   |int[] materialTemplateIds | 报名材料模板 |
   |int[] participantIds | 参与者ID列表 |
   |int[] actualParticipantIds | 实际参加者ID列表 |
   |String[] tags| 标签列表 |

1. 创建活动（社团管理员）
   - Http Request  
   **POST** `/activities`

   - Body Parameters

      |Parameter|Description|
      |---|---|
      |int[] associationIds| 社团IDs |
      |string title|标题|
      |string content|内容（简介）|
      |NewDateTime signupTime|报名时间范围|
      |NewDateTime time|活动时间范围|
      |bool ifReview|报名是否需要审核|
      |bool ifOnlyAsso|是否仅社团成员参加|
      |int numberOfParticipants|最大参加人数|
      |int[] materialTemplateIds|材料模板|
      |string[] tags|标签|

   - Response  
   
      |code|description|
      |---|---|
      |200-(活动对象)|创建成功|
      |400-(错误信息)|创建失败|

   - example
      - 200 
         ```json
         {
             "data": {
                 "id"： 21，
                 "associationIds": [1,2,...],
                 "createTime": "2019-07-11",
                 "publishTime": "2019-07-11",
                 "lastPublishTime": "2019-07-11",
                 "lastModifyTime": "2019-07-11",
                 "title": "abc",
                 "content": "qewretrytretyjdhgeewfwqdw",
                 "state": 0,
                 "signupTime": {
                     "timeType": 0/1/2,
                     "time": "2019-07-11 09:31 - 2019-07-11 09:31"
                 },
                 "time": {
                     "timeType": 0/1/2,
                     "time": "2019-07-11 09:31 - 2019-07-11 09:31"
                 },
                 "state": 0,
                 "ifReview": true,
                 "onlyMembers": true,
                 "maxParticipants":30,
                 "materialTemplateIds": [1,2,3],
                 "participantIds": [1,2,3,...],
                 "actualParticipantIds": [1,2,3,...], 
                 "tags": ["tag1", "tag2", ...]
             },
             "error":"",
             "message":""
         }
         ```
      - 400
         ```json
         {
             "data": null,
             "error": "invalid " + 实际错误信息,
             "message": "yyyy/mm/dd" + "修改失败，请再次尝试。"
         }
         ```

2. 修改活动
   - Http Request
      **PUT** `/activities/{activityid}`

   - Body Parameters

      |Parameter|Description|
      |---|---|
      |int[] associationIds| 社团IDS |
      |string title|标题|
      |string content|内容（简介）|
      |NewDateTime signupTime|报名时间范围|
      |NewDateTime time|活动时间范围|
      |bool ifReview|报名是否需要审核|
      |bool ifOnlyAsso|是否仅社团成员参加|
      |int numberOfParticipants|最大参加人数|
      |int[] materialTemplateIds|材料模板|
      |string[] tags|标签|
    
   - Response  
    
      |code|description|
      |---|---|
      |200-(活动对象)|修改成功|
      |400-(错误信息)|修改失败|
   
   - example
      - 200
         ```json
         {
             "data":{
                 "id"： 21，
                 "associationIds": [1,2,...],
                 "createTime": "2019-06-11",
                 "publishTime": "2019-07-11",
                 "lastPublishTime": "2019-07-11",
                 "lastModifyTime": "2019-07-11",
                 "title": "abc",
                 "content": "qewretrytretyjdhgeewfwqdw",
                 "state": 0,
                 "signupTime": {
                     "timeType": 0,
                     "time": "2019-07-11 09:31 - 2019-07-11 09:31"
                 },
                 "time": {
                     "timeType": 0,
                     "time": "2019-07-11 09:31 - 2019-07-11 09:31"
                 },
                 "state": 0,
                 "ifReview": true,
                 "onlyMembers": true,
                 "maxParticipants":30,
                 "materialTemplateIds": [1,2,3],
                 "participantIds": [1,2,3,...],
                 "actualParticipantIds": [1,2,3,...], 
                 "tags": ["tag1", "tag2", ...]
             },
             "error": "",
             "message": ""
         }
         ```
      - 400
         ```json
         {
             "data": null,
             "error": "invalid " + 实际错误信息,
             "message": "yyyy/mm/dd" + "修改失败，请再次尝试。"
         }
         ```

3. 查询所有活动
   将筛选条件作为参数传进函数，将符合的所有活动作为结果输出  

   - Http Request  
      **GET** `/activities`

   - Query Parameters

      |Parameter|Description|default Value|
      |---|---|---|
      |pageSize|每页数量|10|
      |pageNum|页号|1|
      |int sortBy|排序方式|0|

   - Body Parameters

      |Parameter|Description|default Value|
      |---|---|---|
      |associationId| 社团ID |创建者|
      |tags|标签|[](空列表)|
      |keyword|内容（简介）|""|
      |createTime|创建时间|""|
      |signupTime|报名时间|""|
      |startTime|开始时间|""|
      |string[]|大小为2的列表[活动进度，活动报名进度]|["",""]|
      |bool ifReview|报名是否需要审核|true|
      |bool ifOnlyAsso|活动是否仅社团成员可参加|false|

   - Response  

      |code|description|
      |---|---|
      |200-(对应的活动列表)|创建成功|
      |400-(错误信息)|创建失败|
   
   - example
      - 200
         ```json
         {
            "data": [
                {
                    "id"： 21，
                    "associationIds": [1,2,...],
                    "createTime": "2019-07-11",
                    "publishTime": "2019-07-11",
                    "lastPublishTime": "2019-07-11",
                    "lastModifyTime": "2019-07-11",
                    "title": "abc",
                    "content": "qewretrytretyjdhgeewfwqdw",
                    "state": 0/1, (unpublished, published)
                    "signupTime": {
                        "timeType": 0/1/2,
                        "time": "2019-07-11 09:31 - 2019-07-11 09:31"
                },
                "time": {
                    "timeType": 0/1/2,
                    "time": "2019-07-11 09:31 - 2019-07-11 09:31"
                },
                    "state": 0/1/2/3, (preparing, signup, doing, done)
                    "ifReview": true,
                    "onlyMembers": true,
                    "maxParticipants":30,
                    "materialTemplateIds": [1,2,3],
                    "participantIds": [1,2,3,...],
                    "actualParticipantIds": [1,2,3,...], 
                    "tags": ["tag1", "tag2", ...]
                },
                {
                    "id"： 21，
                    "associationIds": [1,2,...],
                    "createTime": "2019-07-11",
                    "publishTime": "2019-07-11",
                    "lastPublishTime": "2019-07-11",
                    "lastModifyTime": "2019-07-11",
                    "title": "abc",
                    "content": "qewretrytretyjdhgeewfwqdw",
                    "state": 0,
                    "signupTime": {
                        "timeType": 0,
                        "time": "2019-07-11 09:31 - 2019-07-11 09:31"
                    },
                    "time": {
                        "timeType": 0,
                        "time": "2019-07-11 09:31 - 2019-07-11 09:31"
                    },
                    "state": 0,
                    "ifReview": true,
                    "onlyMembers": true,
                    "maxParticipants":30,
                    "materialTemplateIds": [1,2,3],
                    "participantIds": [1,2,3,...],
                    "actualParticipantIds": [1,2,3,...], 
                    "tags": ["tag1", "tag2", ...]
                }
            ],
            "error": "",
            "message": ""
         }
         ```
      - 400
         ```json
         {
             "data": null,
             "error": "invalid " + 实际错误信息,
             "message": "yyyy/mm/dd" + "修改失败，请再次尝试。"
         }

4. 发布、撤回活动
   即修改活动的状态

   - Http Request
      **PATCH** `/activities/{activityid}`

   - Body Parameters

      |Parameter|Description|
      |---|---|
      |string type| 修改类型（publish，withdraw） |

   - Response  

      |code|description|
      |---|---|
      |200-(无实际返回值)|操作成功|
      |400-(错误信息)|操作失败|

   - example
      - 200
         ```json
         {
             "data": 1,
             "error": "",
             "massage": "",
         }
         ```
      - 400
         ```json
         {
             "data": null,
             "error": "invalid " + 实际错误信息,
             "message": "yyyy/mm/dd" + "修改失败，请再次尝试。"
         }
         ```

5. 添加参加者

   - Http Request
      **POST** `/activities/{activityid}/participants`

   - Body Parameters

      |Parameter|Description|
      |---|---|
      |int[] participantIds|待参加者列表|

   - Response

      |code|description|
      |---|---|
      |200-(无实际返回值)|添加成功|
      |400-(错误信息)|添加失败|

   - example
      - 200
         ```json
         {
             "data": 1,
             "error": "",
             "massage": "",
         }
         ```
      - 400
         ```json
         {
             "data": null,
             "error": "invalid " + 实际错误信息,
             "message": "yyyy/mm/dd" + "修改失败，请再次尝试。"
         }
         ```

6. 添加实际参加者

   - Http Request
      **POST** `/activities/{activityid}/realparticipants`

   - Body Parameters

      |Parameter|Description|
      |---|---|
      |int[] realParticipantsIds|待加入的实际参加者列表|

   - Response

      |code|description|
      |---|---|
      |200-(无实际返回值)|添加成功|
      |400-(错误信息)|添加失败|

   - example
      - 200
         ```json
         {
             "data": 1,
             "error": "",
             "massage": "",
         }
         ```
      - 400
         ```json
         {
             "data": null,
             "error": "invalid " + 实际错误信息,
             "message": "yyyy/mm/dd" + "修改失败，请再次尝试。"
         }
         ```

7. 移除参加者

   - Http Request
      **DELETE** `/activities/{activityid}/participants`

   - Body Parameters

      |Parameter|Description|
      |---|---|
      |int[] participantsIds|待移除参加者列表|

   - Response

      |code|description|
      |---|---|
      |200-(无实际返回值)|移除成功|
      |400-(错误信息)|移除失败|

   - example
      - 200
         ```json
         {
             "data": 1,
             "error": "",
             "massage": "",
         }
         ```
      - 400
         ```json
         {
             "data": null,
             "error": "invalid " + 实际错误信息,
             "message": "yyyy/mm/dd" + "修改失败，请再次尝试。"
         }
         ```

8. 查询参加者

   - Http Request
      **GET** `/activities/{activityid}/participants`

   - Query Parameters

      |Parameter|Description|
      |---|---|
      |pageNum|页码|
      |pageSize|每页数量|

   - Body Parameters

      |Parameter|Description|
      |---|---|
      |userId|用户ID|

   - Response  

      |code|description|
      |---|---|
      |200-（参加者ID列表）|查询成功|
      |400|查询失败|

   - example
      - 200
         ```json
         {
             "data": [1,2,3,...],
             "error": "",
             "massage": "",
         }
         ```
      - 400
         ```json
         {
             "data": null,
             "error": "invalid " + 实际错误信息,
             "message": "yyyy/mm/dd" + "修改失败，请再次尝试。"
         }
         ```

9. 查询实际参加者

   - Http Request  
   **GET** `/activities/{activityid}/realparticipants`

   - Query Parameters

      |Parameter|Description|
      |---|---|
      |pageNum|页码|
      |pageSize|每页数量|

   - Body Parameters

      |Parameter|Description|
      |---|---|
      |userId|用户ID|

   - Response  

      |code|description|
      |---|---|
      |200-（实际参加者ID列表）|查询成功|
      |400-（空列表）|查询失败|

   - example
      - 200
         ```json
         {
             "data": [1,2,3,...],
             "error": "",
             "massage": "",
         }
         ```
      - 400
         ```json
         {
             "data": null,
             "error": "invalid " + 实际错误信息,
             "message": "yyyy/mm/dd" + "修改失败，请再次尝试。"
         }
         ```

10. 活动报名申请回调

   - Http Request  
   **POST** `/callback/acceptsignup`

   - Body Parameters

      |Parameter|Description|
      |---|---|
      |int operatorId| 操作者ID |
      |string action| 操作 |
      |SignUp signup|申请|

   - Response

      |code|description|
      |---|---|
      |200-(无实际返回值)|回调成功|
      |400-(错误信息)|回调失败|

   - example
      - 200
         ```json
         {
             "data": 1,
             "error": "",
             "massage": "",
         }
         ```
      - 400
         ```json
         {
             "data": null,
             "error": "invalid " + 实际错误信息,
             "message": "yyyy/mm/dd" + "操作者没有权限"
         }
         ```

   - 输入
      - 操作者ID
      - 操作
      - 申请
         - 申请ID
         - 申请者ID
         - 申请类型
         - Object
            - 活动ID
         - 材料列表
         - 申请时间
         - 处理时间
         - 申请状态   
   - 输出
      - 403
         - error: "ywe"
         - message: "操作者没有权限"
