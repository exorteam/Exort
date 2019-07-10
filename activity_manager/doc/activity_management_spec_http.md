## 活动管理
- **Activity**

   - 活动时间范围 NewDateTime
      - 范围类型 int timeType
      - 时间范围 String time, String[] time...

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
      |400|创建失败|

   - example
      - 200 
         ```json
         {
             "id"： 21，
             "associationIds": [1,2,...],
             "createTime": "yyyy-mm-dd",
             "publishTime": "yyyy-mm-dd",
             "lastPublishTime": "yyyy-mm-dd",
             "lastModifyTime": "yyyy-mm-dd",
             "title": "abc",
             "content": "qewretrytretyjdhgeewfwqdw",
             "state": 0/1, (unpublished, published)
             "signupTime": {
                 "timeType": 0/1/2,
                 "time": "yyyy-mm-dd hh:mm - yyyy-mm-dd hh:mm"
             },
             "time": {
                 "timeType": 0/1/2,
                 "time": "yyyy-mm-dd hh:mm - yyyy-mm-dd hh:mm"
             },
             "state": 0/1/2/3, (preparing, signup, doing, done)
             "ifReview": true,
             "onlyMembers": true,
             "maxParticipants":30,
             "materialTemplateIds": [1,2,3],
             "participantIds": [1,2,3,...],
             "actualParticipantIds": [1,2,3,...], 
             "tags": ["tag1", "tag2", ...]
         }
         ```
      - 400
         - error: "invalid " + 实际错误信息
         - message: "yyyy/mm/dd" + "参数时间设置错误，请修改时间参数后重新操作。"

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
      |200|修改成功|
      |400|修改失败|
   
   - example
      - 200
         ```json
         {
             "id"： 21，
             "associationIds": [1,2,...],
             "createTime": "yyyy-mm-dd",
             "publishTime": "yyyy-mm-dd",
             "lastPublishTime": "yyyy-mm-dd",
             "lastModifyTime": "yyyy-mm-dd",
             "title": "abc",
             "content": "qewretrytretyjdhgeewfwqdw",
             "state": 0/1, (unpublished, published)
             "signupTime": {
                 "timeType": 0/1/2,
                 "time": "yyyy-mm-dd hh:mm - yyyy-mm-dd hh:mm"
             },
             "time": {
                 "timeType": 0/1/2,
                 "time": "yyyy-mm-dd hh:mm - yyyy-mm-dd hh:mm"
             },
             "state": 0/1/2/3, (preparing, signup, doing, done)
             "ifReview": true,
             "onlyMembers": true,
             "maxParticipants":30,
             "materialTemplateIds": [1,2,3],
             "participantIds": [1,2,3,...],
             "actualParticipantIds": [1,2,3,...], 
             "tags": ["tag1", "tag2", ...]
         }
         ```
      - 400
         - error: "invalid " + 实际错误信息
         - message: "yyyy/mm/dd" + "参数时间设置错误，请修改时间参数后重新操作。"

3. 查询所有活动
   将筛选条件作为参数传进函数，将符合的所有活动作为结果输出  

   - Http Request  
      **GET** `/activities`

   - Query Parameters

      |Parameter|Description|default Value|
      |---|---|---|
      |associationId| 社团ID |创建者|
      |tags|标签|[](空列表)|
      |keyword|内容（简介）|""|
      |createTime|创建时间|""|
      |signupTime|报名时间|""|
      |start_time|开始时间|""|
      |page_size|每页数量|10|
      |page_num|页号|1|
      |string[]|大小为2的列表[活动进度，活动报名进度]|["",""]|
      |int sort|排序方式|0|
      |bool ifReview|报名是否需要审核|true|
      |bool ifOnlyAsso|活动是否仅社团成员可参加|false|

   - Response  

      |code|description|
      |---|---|
      |200-(对应的活动列表)|创建成功|
      |400-(空列表)|创建失败|
   
   - example
      - 200
         ```json
            {
               [
                  {
                     "id"： 21，
                     "associationIds": [1,2,...],
                     "createTime": "yyyy-mm-dd",
                     "publishTime": "yyyy-mm-dd",
                     "lastPublishTime": "yyyy-mm-dd",
                     "lastModifyTime": "yyyy-mm-dd",
                     "title": "abc",
                     "content": "qewretrytretyjdhgeewfwqdw",
                     "state": 0/1, (unpublished, published)
                     "signupTime": {
                        "timeType": 0/1/2,
                        "time": "yyyy-mm-dd hh:mm - yyyy-mm-dd hh:mm"
                     },
                     "time": {
                        "timeType": 0/1/2,
                        "time": "yyyy-mm-dd hh:mm - yyyy-mm-dd hh:mm"
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
                     "createTime": "yyyy-mm-dd",
                     "publishTime": "yyyy-mm-dd",
                     "lastPublishTime": "yyyy-mm-dd",
                     "lastModifyTime": "yyyy-mm-dd",
                     "title": "abc",
                     "content": "qewretrytretyjdhgeewfwqdw",
                     "state": 0/1, (unpublished, published)
                     "signupTime": {
                        "timeType": 0/1/2,
                        "time": "yyyy-mm-dd hh:mm - yyyy-mm-dd hh:mm"
                     },
                     "time": {
                        "timeType": 0/1/2,
                        "time": "yyyy-mm-dd hh:mm - yyyy-mm-dd hh:mm"
                     },
                     "state": 0/1/2/3, (preparing, signup, doing, done)
                     "ifReview": true,
                     "onlyMembers": true,
                     "maxParticipants":30,
                     "materialTemplateIds": [1,2,3],
                     "participantIds": [1,2,3,...],
                     "actualParticipantIds": [1,2,3,...], 
                     "tags": ["tag1", "tag2", ...]
                  }
               ]
            }
         ```
      - 400
         - error: "invalid " + 实际错误信息
         - message: "yyyy/mm/dd" + "参数时间设置错误，请修改时间参数后重新操作。"

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
      |200|操作成功|
      |400|操作失败|

   - example
      - 200
         ```json
            {
               "id"： 21，
               "associationIds": [1,2,...],
               "createTime": "yyyy-mm-dd",
               "publishTime": "yyyy-mm-dd",
               "lastPublishTime": "yyyy-mm-dd",
               "lastModifyTime": "yyyy-mm-dd",
               "title": "abc",
               "content": "qewretrytretyjdhgeewfwqdw",
               "state": 0/1, (unpublished, published)             "signupTime": {
                  "timeType": 0/1/2,
                  "time": "yyyy-mm-dd hh:mm - yyyy-mm-dd hh:mm"
               },
               "time": {
                  "timeType": 0/1/2,
                  "time": "yyyy-mm-dd hh:mm - yyyy-mm-dd hh:mm"
               },
               "state": 0/1/2/3, (preparing, signup, doing, done)
               "ifReview": true,
               "onlyMembers": true,
               "maxParticipants":30,
               "materialTemplateIds": [1,2,3],
               "participantIds": [1,2,3,...],
               "actualParticipantIds": [1,2,3,...], 
               "tags": ["tag1", "tag2", ...]
            }
         ```
      - 400
         - error: "invalid " + 实际错误信息
         - message: "yyyy/mm/dd" + "修改失败，请再次尝试。"

5. 添加参加者

   - Http Request
      **POST** `/activities/{activityid}/participants`

   - Body Parameters

      |Parameter|Description|
      |---|---|
      |int[] participantsIds|待参加者列表|

   - Response  

      |code|description|
      |---|---|
      |200|添加成功|
      |400|添加失败|

   - example
      - 200
         ```json
            activity实例，见上
         ```
      - 400
         - error: "invalid " + 实际错误信息
         - message: "yyyy/mm/dd" + "添加失败，请再次尝试。"

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
      |200|添加成功|
      |400|添加失败|

   - example
      - 200
         ```json
         activity实例，见上
         ```
      - 400
         - error: "invalid " + 实际错误信息
         - message: "yyyy/mm/dd" + "添加失败，请再次尝试。"

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
      |200|移除成功|
      |400|移除失败|

   - example
      - 200
         ```json
         activity实例，见上
         ```
      - 400
         - error: "invalid " + 实际错误信息
         - message: "yyyy/mm/dd" + "参数时间设置错误，请修改时间参数后重新操作。"

8. 查询参加者

   - Http Request
      **GET** `/activities/{activityid}/participants`

   - Query Parameters

      |Parameter|Description|
      |---|---|
      |user_id|用户ID|
      |page_num|页码|
      |page_size|每页数量|

   - Response  

      |code|description|
      |---|---|
      |200-（参加者ID列表）|查询成功|
      |400|查询失败|

   - example
      - 200
         ```json
         [1,2,3,...]
         ```
      - 400
         ```
         []
         ```

9. 查询实际参加者

   - Http Request  
   **GET** `/activities/{activityid}/real_participants`

   - Query Parameters

      |Parameter|Description|
      |---|---|
      |user_id|用户ID|
      |page_num|页码|
      |page_size|每页数量|

   - Response  

      |code|description|
      |---|---|
      |200-（实际参加者列表）|查询成功|
      |400-（空列表）|查询失败|

   - example
      - 200
         ```json
         [1,2,3,...]
         ```
      - 400
         ```json
         []
         ```

10. 活动报名申请回调

   - Http Request  
   **POST** `/callback/accept_signup`

   - Body Parameters

      |Parameter|Description|
      |---|---|
      |int operatorId| 操作者ID |
      |string action| 操作 |
      |SignUp signup|申请|

   - Response

      |code|description|
      |---|---|
      |200|回调成功|
      |400|回调失败|

   - example
      - 200
         - 
      - 403
         - error: "ywe"
         - message: "操作者没有权限"

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
      - 200
         - 
      - 403
         - error: "ywe"
         - message: "操作者没有权限"
