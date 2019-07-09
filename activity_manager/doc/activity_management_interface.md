
## 活动管理
- **Activity**

    - 活动时间范围 NewDateTime
        - 范围类型 int timeType
        - 时间范围 String time, String[] time...

   |属性|说明|
   |---|---|
   |int id | |
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

    <!-- Activity CreateActivity(int[] association_ids, string title, string content, Datetime signup_begin_time, Datetime signup_end_time, Datetime begin_time, Datetime end_time, bool need_review, bool only_members, int max_participants, int[] material_template_ids) -->

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
        - 暂无，可考虑输出bool
   <!-- void UpdateActivity(int activity_id, string title, string content, string title, string content, Datetime signup_begin_time, Datetime signup_end_time, Datetime begin_time, Datetime end_time, bool need_review, bool only_members, int max_participants, int[] material_template_ids) -->

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
   <!-- Activity[] GetActivities() -->

4. 发布活动
    - 输入
        - 活动ID
    - 输出
        - 考虑输出bool
   <!-- void PublishActivity(int activity_id) -->

5. 撤回活动
    - 输入
        - 活动ID
    - 输出
        - 考虑输出bool
   <!-- void WithdrawActivity(int activity_id) -->

6. 添加参加者
    - 输入
        - 活动ID
        - 要加入的参加者的IDs
    - 输出
        - 暂无，可考虑输出bool
   <!-- void AddParticipants(int activity_id, int[] participant_ids) -->

7. 移除参加者
    - 输入
        - 活动ID
        - 要移除的参加者的IDs
    - 输出
        - 暂无，可考虑输出bool
   <!-- void RemoveParticipants(int activity_id, int[] participant_ids) -->

8. 查询参加者
    - 输入
        - 活动ID
        - 页码
        - 每页大小
    - 输出
        - 参加者列表

9. 查询参加者数量
    - 输入
        - 活动ID
    - 输出
        - 参加者数量

- **ActivitySighup** 活动报名申请

   |属性|说明|
   |---|---|
   |int id | |
   |int user_id | 报名者 |
   |int activity_id | 活动 |
   |int[] material_ids | 报名材料 |
   |Datetime create_time | 报名时间 |
   |Datetime close_time | 处理时间 |
   |int state | 状态，pending, success, failed, canceled |

1. 报名活动(普通用户)
    - 输入
        - 用户ID
        - 活动ID
        - 用户的申请材料
    - 输出
        - 报名（对象）
   <!-- Signup CreateSignup(int user_id, int activity_id, int[] material_ids) -->

2. 状态变更(审核通过，社团管理员)
    - 输入
        - 报名ID
    - 输出
        - 仅更改状态，可输出为bool
   <!-- void AcceptSignup(int activity_signup_id) -->

3. 状态变更(审核不通过，社团管理员)
    - 输入
        - 报名ID
    - 输出
        - 仅更改状态，可输出为bool
   <!-- void RefuseSignup(int activity_signup_id) -->

4. 状态变更(审核取消，用户本身)
    - 输入
        - 报名ID
    - 输出
        - 仅更改状态，可输出为bool
   <!-- void CancelSignup(int activity_signup_id) -->

5. 针对活动查询报名者（社团管理员）
    - 输入
        - 活动ID
    - 输出
        - 报名列表
   <!-- ActivitySignup[] ListActivitySignups(int activity_id) -->

6. 查询报名（用户）
    - 输入
        - 报名ID
    - 输出
        - 报名（详细信息）
   <!-- ActivitySignup GetActivitySignup(int user_id, int activity_id) -->

7. 查询所有报名（用户）
    - 输入
        - 用户ID
    - 输出
        - 报名列表
    <!-- ActivitySignup[] ListActivitySignups(int user_id) -->