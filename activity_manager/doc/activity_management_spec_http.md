## 活动管理
- **Activity**

    - 活动时间范围 NewDateTime
        - 范围类型 int timeType
        - 时间范围 String time, String[] time...

1. 创建活动（社团管理员）
    - Http Request  
    POST /activityManagement/createNewActivity

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
    |int numberOfPeople|最大参加人数|
    |int[] materialTemplateIds|材料模板|
    |string[] tags|标签|
    
    - Response  
    
    |code|description|
    |---|---|
    |200|创建成功|
    |400|创建失败|

2. 修改活动
    - Http Request  
    POST /activityManagement/modifyActivity

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
    |int numberOfPeople|最大参加人数|
    |int[] materialTemplateIds|材料模板|
    |string[] tags|标签|
    
    - Response  
    
    |code|description|
    |---|---|
    |200|修改成功|
    |400|修改失败|

3. 查询所有活动
    将筛选条件作为参数传进函数，将符合的所有活动作为结果输出  

    - Http Request  
    POST /activityManagement/getActivities

    - Body Parameters

    |Parameter|Description|
    |---|---|
    |associationId| 社团ID |
    |tags|标签|
    |keyword|内容（简介）|
    |createTime|创建时间|
    |signupTime|报名时间|
    |startTime|开始时间|
    |numberForEachPage|每页数量|
    |pageNumber|页号|
    |string[]|大小为2的列表[活动进度，活动报名进度]|
    |int sort|排序方式|
    |bool ifReview|报名是否需要审核|
    |bool ifOnlyAsso|活动是否仅社团成员可参加|

    - Response  
    
    |code|description|
    |---|---|
    |200-(对应的活动列表)|创建成功|
    |400-(空列表)|创建失败|

4. 发布活动
    即修改活动的发布状态

    - Http Request
    POST /activityManagement/publishActivity

    - Body Parameters

    |Parameter|Description|
    |---|---|
    |int activityId| 活动ID |

    - Response  
    
    |code|description|
    |---|---|
    |200|创建成功|
    |400|创建失败|

5. 撤回活动
    - Http Request
    POST /activityManagement/cancelActivity

    - Body Parameters

    |Parameter|Description|
    |---|---|
    |int activityId| 活动ID |

    - Response  
    
    |code|description|
    |---|---|
    |200|撤回成功|
    |400|撤回失败|

6. 添加参加者

    - Http Request
    POST /activityManagement/addParticipants

    - Body Parameters

    |Parameter|Description|
    |---|---|
    |int activityId| 活动ID |
    |int[] participantsIds|待参加者列表|

    - Response  
    
    |code|description|
    |---|---|
    |200|添加成功|
    |400|添加失败|

7. 添加实际参加者

    - Http Request
    POST /activityManagement/addRealParticipants

    - Body Parameters

    |Parameter|Description|
    |---|---|
    |int activityId| 活动ID |
    |int[] realParticipantsIds|待加入的实际参加者列表|

    - Response  
    
    |code|description|
    |---|---|
    |200|添加成功|
    |400|添加失败|

8. 移除参加者

    - Http Request
    POST /activityManagement/removeParticipants

    - Body Parameters

    |Parameter|Description|
    |---|---|
    |int activityId| 活动ID |
    |int[] participantsIds|待移除参加者列表|

    - Response  
    
    |code|description|
    |---|---|
    |200|移除成功|
    |400|移除失败|

9. 查询参加者

    - Http Request
    POST /activityManagement/getParticipants

    - Body Parameters

    |Parameter|Description|
    |---|---|
    |int activityId| 活动ID |

    - Response  
    
    |code|description|
    |---|---|
    |200-（参加者列表）|查询成功|
    |400-（空列表）|查询失败|

10. 查询实际参加者

    - Http Request
    POST /activityManagement/getRealParticipants

    - Body Parameters

    |Parameter|Description|
    |---|---|
    |int activityId| 活动ID |

    - Response  
    
    |code|description|
    |---|---|
    |200-（实际参加者列表）|查询成功|
    |400-（空列表）|查询失败|

11. 查询参加者数量
    - Http Request
    POST /activityManagement/getParticipantsNumber

    - Body Parameters

    |Parameter|Description|
    |---|---|
    |int activityId| 活动ID |

    - Response  

    |code|description|
    |---|---|
    |200-（数量）|查询成功|
    |400-（-1）|查询失败|

12. 是否是参加者
    - Http Request
    POST /activityManagement/ifParticipant

    - Body Parameters

    |Parameter|Description|
    |---|---|
    |int activityId| 活动ID |
    |int userId| 用户ID |

    - Response  

    |code|description|
    |---|---|
    |200-（是否）|查询成功|
    |400-（-1）|查询失败|

13. 是否是实际参加者
    - Http Request
    POST /activityManagement/ifRealParticipant

    - Body Parameters

    |Parameter|Description|
    |---|---|
    |int activityId| 活动ID |
    |int userId| 用户ID |

    - Response  

    |code|description|
    |---|---|
    |200-（是否）|查询成功|
    |400-（-1）|查询失败|

14. 活动报名申请回调

    - Http Request
    POST /activityManagement/acceptSignUp

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
        - 403
            - error: "ywe"
            - message: "操作者没有权限"
