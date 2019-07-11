## 活动管理
- **Activity**

   - **NewDateTime** 活动时间范围
      - 范围类型 _int_ `timeType`
      - 时间范围 _String_ `time`, _String[]_ `time`...

   |属性|说明|
   |---|---|
   |`id`_int_| |
   |`associationIds`_int[]_| 组织者|
   |`createTime`_Datetime_| 创建时间 |
   |`publishTime`_Datetime_| 第一次发布时间 |
   |`lastPublishTime`_Datetime_| 最后一次发布时间 |
   |`lastModifyTime`_Datetime_| 最后一次修改时间 |
   |`title`_string_| 活动标题 |
   |`content`_string_| 活动内容 |
   |`publishState`_int_| 活动状态, unpublished, published |
   |`signupTime`_NewDateTime_| 活动报名时间范围 |
   |`time`_NewDateTime_| 活动时间范围 |
   |`signupState`_int_| 报名状态(0报名未开始，1报名中，2报名已结束) |
   |`state`_int_| 活动状态(0未开始，1进行中，2已结束) |
   |`ifReview`_bool_| 报名是否需要审核 |
   |`ifOnlyMem`_bool_| 是否只有社团成员可以报名 |
   |`maxParticipants`_int_| 最大参与人数 |
   |`materialTemplateIds`_int[]_| 报名材料模板 |
   |`participantIds`_int[]_| 参与者ID列表 |
   |`actualParticipantIds`_int[]_| 实际参加者ID列表 |
   |`tags`__String[]__| 标签列表 |

   - **AvtivityList** 活动列表
      - `pagenum` _int_  实际的页码
      - `pagesize` _int_   min(MaxPageSize, 请求的pageSize)
      - `totalsize` _int_  总的活动数量
      - `content` _Activity[]_  实际活动内容列表 

1. 创建活动（社团管理员）
   - Http Request  
   **POST** `/activities`

   - Body Parameters

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

   - Response  
   
      |code|description|
      |---|---|
      |200-(活动对象)|创建成功|
      |400-(错误信息)|创建失败|

   - example
      ```json
      >>> POST /activities
      {
         "associationIds": [432,424],
         "title": "abv",
         "content": "qwujyshdg",
         "signupTime": {
             "timeType": 0,
             "time":{
                 "start": "2019-07-11 09:31",
                 "end": "2019-07-11 09:31"
             }
         },
         "time": {
             "timeType": 0,
             "time":{
                 "start": "2019-07-11 09:31",
                 "end": "2019-07-11 09:31"
             }
         },
         "ifReview": true,
         "ifOnlyMem": false,
         "numberOfParticipants": 30,
         "materialTemplateIds": [3,4,5,6],
         "tags": ["运动"]
      }
      ```
      ```json   
      <<< 200
         {
             "data": {
                 "id": 21,
                 "associationIds": [1,2,...],
                 "createTime": "2019-07-11",
                 "publishTime": "2019-07-11",
                 "lastPublishTime": "2019-07-11",
                 "lastModifyTime": "2019-07-11",
                 "title": "abc",
                 "content": "qewretrytretyjdhgeewfwqdw",
                 "publishState": 0,
                 "signupTime": {
                      "timeType": 0/1/2,
                      "time":{
                          "start": "2019-07-11 09:31",
                          "end": "2019-07-11 09:31"
                      }
                 },
                 "time": {
                      "timeType": 0/1/2,
                      "time": {
                          "start": "2019-07-11 09:31",
                          "end": "2019-07-11 09:31"
                      }
                 },
                 "signupState": 0,
                 "state": 0,
                 "ifReview": true,
                 "ifOnlyMem": true,
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
      ```json
      <<< 400
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
      |`associationIds`_int[]_| 社团IDS |
      |`title`_string_|标题|
      |`content`_string_|内容（简介）|
      |`signupTime`_NewDateTime_|报名时间范围|
      |`time`_NewDateTime_|活动时间范围|
      |`ifReview`_bool_|报名是否需要审核|
      |`ifOnlyMem`_bool_|是否仅社团成员参加|
      |`numberOfParticipants`_int_|最大参加人数|
      |`materialTemplateIds`_int[]_|材料模板|
      |`tags`_string[]_|标签|

   - Response

      |code|description|
      |---|---|
      |200-(活动对象)|修改成功|
      |400-(错误信息)|修改失败|
   
      ```json
      >>> PUT /activities
      {
         "associationIds": [432,424],
         "title": "abv",
         "content": "qwujyshdg",
         "signupTime": {
             "timeType": 0,
             "time":{
                 "start": "2019-07-11 09:31",
                 "end": "2019-07-11 09:31"
             }
         },
         "time": {
             "timeType": 0,
             "time":{
                 "start": "2019-07-11 09:31",
                 "end": "2019-07-11 09:31"
             }
         },
         "ifReview": true,
         "ifOnlyMem": false,
         "numberOfParticipants": 30,
         "materialTemplateIds": [3,4,5,6],
         "tags": ["运动"]
      }
      ```
      ```json
      <<< 200
         {
             "data":{
                 "id": 21,
                 "associationIds": [1,2,...],
                 "createTime": "2019-06-11",
                 "publishTime": "2019-07-11",
                 "lastPublishTime": "2019-07-11",
                 "lastModifyTime": "2019-07-11",
                 "title": "abc",
                 "content": "qewretrytretyjdhgeewfwqdw",
                 "publishState": 0,
                 "signupTime": {
                     "timeType": 0,
                     "time": "2019-07-11 09:31 - 2019-07-11 09:31"
                 },
                 "time": {
                     "timeType": 0,
                     "time": "2019-07-11 09:31 - 2019-07-11 09:31"
                 },
                 "signupState": 0,
                 "state": 0,
                 "ifReview": true,
                 "ifOnlyMem": true,
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
      ```json
      <<< 400
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
      |`pagesize`|每页数量|8|
      |`pagenum`|页号|0|
      |`sortby`|排序方式|0|

   - Body Parameters

      |Parameter|Description|default Value|
      |---|---|---|
      |`associationId`_int_| 社团ID |创建者|
      |`tags`_string[\]_|标签|[](空列表)|
      |`keyword`_string_|内容（简介）| null |
      |`createTime`_string_|创建时间| null |
      |`signupTime`_string_|报名时间| null |
      |`startTime`_string_|开始时间| null |
      |`publishState`_int_| 活动状态(0/unpublished, 1/published)| -1 |
      |`signupState`_int_| 报名状态(0报名未开始，1报名中，2报名已结束)| -1 |
      |`state`_int_| 活动状态(0未开始，1进行中，2已结束) | -1 |
      |`ifReview`_bool_|报名是否需要审核| -1 |
      |`ifOnlyMem`_bool_|活动是否仅社团成员可参加| -1 |

   - Response  

      |code|description|
      |---|---|
      |200-(对应的活动列表)|创建成功|
      |400-(错误信息)|创建失败|
   
      ```json
      >>> GET /activitiespagesize=10&pagenum=2&osrtby=1
      {
          "associationId":[2,3],
          "tags":["运动"],
          "keyword":"jhsd",
          "createTime": "2019-07-11 00:00",
          "signupTime": "2019-07-11 00:00",
          "startTime": "2019-07-11 00:00",
          "publishState": 0,
          "signupState": 0,
          "state": 0,
          "ifReview": true,
          "ifOnlyMem": true
      }
      ```

      ```json
      <<< 200
         {
            "data": {
                "pagenum": 8,
                "pagesize":1,
                "totalnum":1,
                "content":[
                    {
                        "id": 21,
                        "associationIds": [1,2,...],
                        "createTime": "2019-07-11",
                        "publishTime": "2019-07-11",
                        "lastPublishTime": "2019-07-11",
                        "lastModifyTime": "2019-07-11",
                        "title": "abc",
                        "content": "qewretrytretyjdhgeewfwqdw",
                        "publishState": 0,
                        "signupTime": {
                            "timeType": 0,
                            "time": "2019-07-11 09:31 - 2019-07-11 09:31"
                        },
                        "time": {
                            "timeType": 0,
                            "time": "2019-07-11 09:31 - 2019-07-11 09:31"
                        },
                        "signupState": 0, 
                        "state": 0,
                        "ifReview": true,
                        "ifOnlyMem": true,
                        "maxParticipants":30,
                        "materialTemplateIds": [1,2,3],
                        "participantIds": [1,2,3,...],
                        "actualParticipantIds": [1,2,3,...], 
                        "tags": ["tag1", "tag2", ...]
                    },
                    {
                        "id": 21,
                        "associationIds": [1,2,...],
                        "createTime": "2019-07-11",
                        "publishTime": "2019-07-11",
                        "lastPublishTime": "2019-07-11",
                        "lastModifyTime": "2019-07-11",
                        "title": "abc",
                        "content": "qewretrytretyjdhgeewfwqdw",
                        "publishState": 0,
                        "signupTime": {
                            "timeType": 0,
                            "time": "2019-07-11 09:31 - 2019-07-11 09:31"
                        },
                        "time": {
                            "timeType": 0,
                            "time": "2019-07-11 09:31 - 2019-07-11 09:31"
                        },
                        "signupState": 0, 
                        "state": 0,
                        "ifReview": true,
                        "ifOnlyMem": true,
                        "maxParticipants":30,
                        "materialTemplateIds": [1,2,3],
                        "participantIds": [1,2,3,...],
                        "actualParticipantIds": [1,2,3,...], 
                        "tags": ["tag1", "tag2", ...]
                    }
                ]
            },
            "error": "",
            "message": ""
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

4. 发布、撤回活动
   即修改活动的状态

   - Http Request
      **PATCH** `/activities/{activityid}`

   - Body Parameters

      |Parameter|Description|
      |---|---|
      |`type`_string_| 修改类型（publish，withdraw） |

   - Response  

      |code|description|
      |---|---|
      |200-(无实际返回值)|操作成功|
      |400-(错误信息)|操作失败|
   ```json
   >>> PATCH /activities/32
   {
      "type":"publish"
   }
   ```
   ```json
   <<< 200
      {
          "data": {
              "pagenum":2,
              "pagesize":0,
              "totalsize":78,
              "content":[]
          },
          "error": "",
          "massage": "",
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

5. 添加参加者

   - Http Request
      **POST** `/activities/{activityid}/participants`

   - Body Parameters

      |Parameter|Description|
      |---|---|
      |`participantIds`_int[]_|待参加者列表|

   - Response

      |code|description|
      |---|---|
      |200-(无实际返回值)|添加成功|
      |400-(错误信息)|添加失败|

      ```json
      >>> POST /activities/32/participants
      {
          "participantIds":[1,2,3]
      }
      ```
      ```json
      <<< 200
      {
          "data": {
              "pagenum":2,
              "pagesize":0,
              "totalsize":78,
              "content":[]
          },
          "error": "",
          "massage": "",
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

6. 添加实际参加者

   - Http Request
      **POST** `/activities/{activityid}/realparticipants`

   - Body Parameters

      |Parameter|Description|
      |---|---|
      |`realParticipantsIds`_int[]_|待加入的实际参加者列表|

   - Response

      |code|description|
      |---|---|
      |200-(无实际返回值)|添加成功|
      |400-(错误信息)|添加失败|

      ```json
      >>> POST /activities/32/realParticipants
      {
         "realParticipantsIds":[1,2,3]
      }
      ```
      ```json
      <<< 200
      {
          "data": {
              "pagenum":2,
              "pagesize":0,
              "totalsize":78,
              "content":[]
          },
          "error": "",
          "massage": "",
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

7. 移除参加者

   - Http Request
      **DELETE** `/activities/{activityid}/participants`

   - Body Parameters

      |Parameter|Description|
      |---|---|
      |`participantsIds`_int[]_|待移除参加者列表|

   - Response

      |code|description|
      |---|---|
      |200-(无实际返回值)|移除成功|
      |400-(错误信息)|移除失败|

      ```json
      >>> DELETE /activities/32/participants
      {
         "participantsIds":[1,2,3]
      }
      ```
      ```json
      <<< 200         
      {
          "data": {
              "pagenum":2,
              "pagesize":0,
              "totalsize":78,
              "content":[]
          },
          "error": "",
          "massage": "",
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

8. 查询参加者

   - Http Request
      **GET** `/activities/{activityid}/participants`

   - Query Parameters

      |Parameter|Description|
      |---|---|
      |`pagenum`_int_|页码|
      |`pagesize`_int_|每页数量|

   - Body Parameters

      |Parameter|Description|
      |---|---|
      |`userId`_int_|用户ID|

   - Response  

      |code|description|
      |---|---|
      |200-（参加者ID列表）|查询成功|
      |400|查询失败|

      ```json
      >>> GET /activities/32/participants?pagenum=32&pagesize=12
      {
          "userId": 21
      }   
      ```
      ```json
      <<< 200
      {
          "data": [1,2,3,...],
          "error": "",
           "massage": "",
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

9. 查询实际参加者

   - Http Request  
   **GET** `/activities/{activityid}/realparticipants`

   - Query Parameters

      |Parameter|Description|
      |---|---|
      |`pagenum`_int_|页码|
      |`pagesize`_int_|每页数量|

   - Body Parameters

      |Parameter|Description|
      |---|---|
      |`userId`_int_|用户ID|

   - Response  

      |code|description|
      |---|---|
      |200-（实际参加者ID列表）|查询成功|
      |400-（空列表）|查询失败|

      ```json
      >>> GET /activities/32/realparticipants?pagenum=32&pagesize=12
      {
         "userId": 21
      }   
      ```
      ```json
      <<< 200
      {
          "data": [1,2,3,...],
          "error": "",
           "massage": "",
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

10. 活动报名申请回调
    - **Application**
       |Field|Description|
       |--|--|
       |`id`_int_|申请ID|
       |`applicantId`_int_|申请者ID|
       |`type`_string_|申请类型|
       |`object`_Signup_| 加入活动申请信息|
       |`materialIds`_int[]_|申请材料ID列表|
       |`createdTime`_string_|申请时间|
       |`handledTime`_string_|处理时间|
       |`state`_string_|状态,可以是 _pnding_, _accepted_, _refused_, _canceled_|

   - **Signup**
       |`activityId`_int_|活动ID|

   - Http Request  
   **POST** `/callback/acceptsignup`

   - Body Parameters

       |Parameter|Description|
       |---|---|
       |`operatorId`_int_| 操作者ID |
       |`action`_string_| 操作 |
       |`application`_Application_|申请|

   - Response

       |code|description|
       |---|---|
       |200-(无实际返回值)|回调成功|
       |400-(错误信息)|回调失败|

      ```json
      >>> POST /callback/acceptsignup
      {
          "operatorId": 32,
          "action": "accept",
          "signup": {
              "id": 271,
              "applicantId": 111,
              "type": "signupActivity",
              "object": {
                  "activityId": 55
              },
              "materialIds": [],
              "createdTime": "2019-07-10T03:19:06.618Z",
              "handledTime": "2019-07-10T03:24:49.797Z",
              "state": "accepted"
           }
         }
      }
      ```
      ```json
      <<< 200
      {
          "data": {
              "pagenum":2,
              "pagesize":0,
              "totalsize":78,
              "content":[]
          },
          "error": "",
          "massage": "",
      }
      ```
      ```json
      <<< 400
      {
          "data": null,
          "error": "invalid " + 实际错误信息,
          "message": "yyyy/mm/dd" + "操作者没有权限"
      }
      ```

11. 查询单个活动