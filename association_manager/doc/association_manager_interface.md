# 社团管理模块接口设计

## 相关数据结构

1. 社团(Association)
   |属性|说明|
   |---|---|
   |int id | |
   |string name | 社团名 |
   |string description | 社团描述 |
   |string logo | 社团图标 |
   |string[] tags | 社团标签 |
   |int state|社团封禁状态|

2. 社团申请(AssociationApplication)
   |属性|说明|
   |---|---|
   |int id | |
   |int user_id | 申请者 |
   |Association asso | 社团名 |
   |Datetime apply_time | 提交时间 |
   |int state | 申请状态, unhandled, accepted, refused, canceled |
   |int type | 申请类型（创建，解禁） |
   |string description | 申请说明 |
3. 社团封禁（AssociationBlock）
   |属性|说明|
   |---|---|
   |int id| |
   |int asso_id|社团id|
   |string description|封禁理由|
   |string unblockDescription|解禁理由，为空表示未解禁|


## 接口

1. 查找指定社团
   - 输入:
      - 关键字
   - 输出:
      - 名称、描述、标签中带有关键字的社团列表
2. 创建社团（系统管理员）
   - 输入:
      - 社团名称
      - 社团描述
      - 社团标签
      - 社团Logo
   - 输出:
      - 已创建的社团对象
3. 删除社团（系统管理员）
   - 输入
      - 社团id
   - 输出
      - 是否成功
4. 封禁社团（系统管理员）
   - 输入
      - 社团id
      - 封禁理由
   - 输出
      - 是否成功
6. 编辑社团信息
   - 输入
      - 社团id
      - 社团名称
      - 社团描述
      - 社团标签
      - 社团Logo
   - 输出
      - 编辑后的社团对象
7. 申请创建社团
   - 输入
      - 社团名称
      - 社团描述
      - 社团标签
      - 社团Logo
      - 申请材料
   - 输出
      - 是否提交成功
9. 查询申请
   - 输入
      - 申请id
      - 申请人id
      - 社团id
      - 申请时间
      - 申请状态
   - 输出
      - 符合筛选条件的申请列表
10. 申请解除封禁
      - 社团id
      - 理由
      - 申请人id
   - 输出
      - 是否成功
11. 批准社团创建（系统管理员）
   - 输入
      - 申请id
   - 输出
      - 是否成功
12. 拒绝社团创建（系统管理员）
   - 输入
      - 申请id
   - 输出
      - 是否成功
13. 取消社团创建（社团管理员）
   - 输入
      - 申请id
   - 输出
      - 是否成功