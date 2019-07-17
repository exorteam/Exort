## 活动管理
- **Activity**

    - 活动时间范围 NewDateTime
        - 范围类型 int timeType
        - 时间范围 String time, String[] time...

   |属性|说明|
   |---|---|
   |string id | |
   |int[] association_ids | 组织者|
   |Datetime create_time | 创建时间 |
   |Datetime publish_time | 第一次发布时间 |
   |Datetime last_publish_time | 最后一次发布时间 |
   |Datetime last_modify_time | 最后一次修改时间 |
   |string title | 活动标题 |
   |string content | 活动内容 |
   |int state | 活动状态, unpublished, published |
   |NewDateTime signup_time | 活动报名时间范围 |
   |NewDateTime time | 活动时间范围 |
   |int state | 活动状态(preparing, signup, doing, done) |
   |bool need_review | 报名是否需要审核 |
   |bool only_members | 是否只有社团成员可以报名 |
   |int max_participants | 最大参与人数 |
   |int[] material_template_ids | 报名材料模板 |
   |int[] participant_ids | 参与者 |
   |int[] actualParticipant_ids | 实际参加者 |
   |String[] tags| 标签s |

1. 创建活动（社团管理员）
    - 输入
        - 社团IDs
        - 标题
        - 内容（简介）
        - 报名时间范围
        - 活动时间范围
        - 报名是否需要审核
        - 活动是否仅社团成员课参加
        - 最大参与人数
        - 材料模板
        - 标签
    - 输出
        - 活动

2. 修改活动
    - 输入
        - 活动ID
        - 标题
        - 内容（简介）
        - 报名时间范围
        - 活动时间范围
        - 报名是否需要审核
        - 活动是否仅社团成员参加
        - 最大参与人数
        - 材料模板
        - 标签
    - 输出

3. 查询所有活动
    - 输入
        - keyword
        - 标签
        - 社团
        - 活动进度，活动报名进度
        - 报名是否需要审核
        - 活动是否仅社团成员可参加
        - 创建时间
        - 报名时间
        - 开始时间
        - 每页数量、页号
        - 排序方式 (创建时间、报名时间、开始时间)
    - 输出
        - 对应的活动列表

4. 发布活动
    - 输入
        - 活动ID
    - 输出

5. 撤回活动
    - 输入
        - 活动ID
    - 输出

6. 添加参加者
    - 输入
        - 活动ID
        - 要加入的参加者的IDs
    - 输出

7. 添加实际参加者
    - 输入
        - 活动ID
        - 要加入的实际参加者的IDs
    - 输出

8. 移除参加者
    - 输入
        - 活动ID
        - 要移除的参加者的IDs
    - 输出

9. 查询参加者
    - 输入
        - 活动ID
        - 页码
        - 每页大小
    - 输出
        - 参加者列表

10. 查询实际参加者
    - 输入
        - 活动ID
        - 页码
        - 每页大小
    - 输出
        - 实际参加者列表

11. 查询参加者数量
    - 输入
        - 活动ID
    - 输出
        - 参加者数量

12. 是否是参加者
    - 输入
        - 活动ID
        - 用户ID
    - 输出
        - 是、否

13. 是否是实际参加者
    - 输入
        - 活动ID
        - 用户ID
    - 输出
        - 是、否

14. 活动报名申请回调
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