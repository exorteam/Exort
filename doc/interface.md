# 接口定义

[TODO] 待设计模块：
- 评论管理
- 事件
- 通知
- 动态管理
- 资产管理
- 材料管理

## 材料管理 (TODO)
- **Material**

   |属性|说明|
   |---|---|
   |int id | |
   |int type | 材料类型 |
   |string content | 材料内容 |

- 创建材料

   Material Create
   ...

## 文章管理
- **Article**

   |属性|说明|
   |---|---|
   |int id | |
   |int[] association_ids | 作者 |
   |Datetime create_time | 创建时间 |
   |Datetime publish_time | 第一次发布时间 |
   |Datetime last_publish_time | 最后一次发布时间 |
   |Datetime last_modify_time | 最后一次修改时间 |
   |string title | 标题 |
   |string content | 文章内容 |
   |int state | 文章状态, unpublished, published |
   |int create_method | 创建方式，用户手动创建、活动模块创建、... |

- 创建文章

   Article CreateArticle(int[] association_ids, string title, string content)

- 删除文章

   void DeleteArticle(int article_id)

- 修改文章

   void UpdateArticle(int article_id, string title, string content)

- 查询文章

   Article GetArticle(int article_id)

- 列出文章(通过关键字、作者、时间区间、状态、创建方式)

   Article[] ListArticle(string keyword, int association_id, before- after-..., int state, int create_method)

- 文章状态变更(发布文章, 撤回文章)

   void PublishArticle(int article_id)

   void WithdrawArticle(int article_id)

## 活动管理
- **Activity**

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
   |Datetime signup_begin_time | 活动开始报名时间 |
   |Datetime signup_end_time | 活动结束报名时间 |
   |Datetime begin_time | 活动开始时间 |
   |Datetime end_time | 活动结束时间 |
   |int state | 活动状态(preparing, signup, doing, done) |
   |bool need_review | 报名是否需要审核 |
   |bool only_members | 是否只有社团成员可以报名 |
   |int max_participants | 最大参与人数 |
   |int[] material_template_ids | 报名材料模板 |
   |int[] participant_ids | 参与者 |

- 创建活动

   Activity CreateActivity(int[] association_ids, string title, string content, Datetime signup_begin_time, Datetime signup_end_time, Datetime begin_time, Datetime end_time, bool need_review, bool only_members, int max_participants, int[] material_template_ids)

- 修改活动

   void UpdateActivity(int activity_id, string title, string content, string title, string content, Datetime signup_begin_time, Datetime signup_end_time, Datetime begin_time, Datetime end_time, bool need_review, bool only_members, int max_participants, int[] material_template_ids)

- 查询活动

   Activity GetActivity(int activity_id)

   Activity[] ListActivity(int association_id, string keyword, after- before-...)

- 发布、撤回活动

   void PublishActivity(int activity_id)

   void WithdrawActivity(int activity_id)

- 添加、移除参加者

   void AddParticipants(int activity_id, int[] participant_ids)

   void RemoveParticipants(int activity_id, int[] participant_ids)

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

- 报名活动

   Signup CreateSignup(int user_id, int activity_id, int[] material_ids)

- 状态变更(审核通过、不通过、取消)

   void AcceptSignup(int activity_signup_id)

   void RefuseSignup(int activity_signup_id)

   void CancelSignup(int activity_signup_id)

- 查询报名者

   ActivitySignup GetActivitySignup(int activity_signup_id)

   ActivitySignup[] ListActivitySignups(int user_id, int activity_id, int state)

## 社团管理
- **Association**

   |属性|说明|
   |---|---|
   |int id | |
   |string name | 社团名 |
   |string description | 社团描述 |
   |string logo | 社团图标 |
   |string[] tags | 社团标签 |

- 创建社团

   Association CreateAssociation(int leader_id, string name, string description, string logo, string[] tags)

- 查询社团信息

   Association GetAssociation(int association_id)

   Association ListAssociations(string keyword, string[] tags)

- 修改社团信息

   void UpdateAssociation(int association_id, string name, string description, string logo, string[] tags, ...)

- **AssociationApplication** 创建社团申请

   |属性|说明|
   |---|---|
   |int id | |
   |int user_id | 申请者 |
   |string name | 社团名 |
   |string description | 社团描述 |
   |string logo | 社团图标 |
   |string[] tags | 社团标签 |
   |Datetime apply_time | 提交时间 |
   |int state | 申请状态, unhandled, accepted, refused, canceled |

- 创建申请

   AssociationApplication CreateAssociationApplication(int user_id, int[] material_ids)

- 查询申请

   AssociationApplication[] ListAssociationApplications(int user_id, Datetime after, Datetime before, int state)

- 修改申请状态

   bool CancelAssociationApplication(int association_application_id)

   bool AcceptAssociationApplication(int association_application_id)

   bool RefuseAssociationApplication(int association_application_id)

## 用户管理
- **User**

   |属性|说明|
   |---|---|
   |int id | |
   |string username | 用户名 |
   |string nickname | 昵称 |
   |string description | 个人描述 |
   |int gender | (可选)个人信息-性别 |
   |Datetime birthday | (可选)个人信息-生日 |
   |string name | (可选)个人信息-真实姓名 |
   |string student_number | (可选)个人信息-学号 |
   |string phone | (可选)联系方式-手机 |
   |string email | (可选)联系方式-邮箱 |
   |string qq | (可选)联系方式-qq号 |
   |string wechat | (可选)联系方式-微信号 |
   |...
   |int status | 账号状态(inactived, normal, blocked) |

- 创建用户

   User CreateUser(string username)

- 更改信息

   void UpdateUser(int user_id, string nickname, string description, ......)

- 更改状态

   void ActivateUser(int user_id)

   void BlockUser(int user_id)

   void RecoverUser(int user_id)

## 社团成员、组织架构管理
- **AssociationSignup** 社团加入申请

   |属性|说明|
   |---|---|
   |int id |  |
   |int user_id | 申请者 |
   |int association_id | 报名的社团 |
   |int[] material_ids | 提交的报名材料列表 |
   |Datetime created_at | 申请时间 |
   |int state | 申请状态，有unhandled, accepted, refused, canceled |

- 创建申请

   AssociationSignup CreateAssociationSignup(int user_id, int association_id, int[] material_ids)

- 查询申请 (按社团、用户、时间、状态搜索)

   AssociationSignup[] ListAssociationSignups(int association_id, int user_id, Datetime after, Datetime before, int state)

   AssociationSignup GetAssociationSignup(int association_signup_id)

- 改 (状态变更)

   bool CancelAssociationSignup(int association_signup_id)

   bool AcceptAssociationSignup(int association_signup_id)

   bool RefuseAssociationSignup(int association_signup_id)

- **Node** 部门/职位树节点

   |属性|说明|
   |---|---|
   |int id | |
   |int associate_id | 所属社团 |
   |int parent_id | 父节点, 上级部门, 职位所属部门, 直属上司 |
   |string name | 节点名字, 部门名字, 职位名字 |
   |string description | 节点描述, 部门描述, 职位描述 |

- 创建部门

   Node CreateNode(int associate_id, int parent_id, string name, string description, int[] permission_ids)

- 删除部门

   void DeleteTree(int associate_id, int node_id)

- 获取部门树

   Node[] GetTree(int associate_id)

- 编辑部门 (上级部门、名字、描述、权限)

   void UpdateNode(int associate_id, int node_id, int parent_id, string name, string description, int[] permission_ids)

- 添加成员到指定部门

   void AddMembers(int association_id, int node_id, int[] user_ids, string details)

- 从指定节点移除成员

   void RemoveMembers(int association_id, int node_id, int[] user_ids,string details)

- 列出社团成员

   int[] ListAllMembers(int association_id)

   int[] ListMembers(int association_id, int node_id)

- 列出用户参与的所有社团

   int[] ListUserAssociations(int user_id)

- 列出用户在指定社团中所有职位

   Node[] ListUserNodes(int user_id, int association_id)

- 判断用户在社团中是否有指定权限

   bool HasPermission(int user_id, int association_id, int permission_id)

## 用户权限管理
- **Scope** 域

   |属性|说明|
   |---|---|
   |int id |
   |string description | 描述域|

- 创建域

   Scope CreateScope(string description)

- 删除域

   void DeleteScope(int scope_id)

- 获取域

   Scope GetScope(int scope_id)

- **Permission** 权限

   |属性|说明|
   |---|---|
   |int id |
   |string name | 权限名 |
   |string category | 所属分类，组织管理用 |
   |string description | 描述权限内容 |

- 创建权限

   Permission CreatePermission(string name, string category, string description)

- 删除权限

   void DeletePermission(int permission_id)

- 获取权限

   Permission GetPermission(int permission_id)

- 列出所有权限

   Permission[] ListPermissions()

   {string category -> Permission[]} ListPermissionsByCategory()

- 编辑权限

  void UpdatePermission(int permission_id, string name, string category, string description)

- **Role** 角色

   |属性|说明|
   |---|---|
   |int id |
   |string name | 角色名 |
   |string description | 描述角色 |

- 创建角色

   Role CreateRole(string name, string description, int[] permission_ids)

- 删除角色

   void DeleteRole(int role_id)

- 获取角色

   Role GetRole(int role_id)

- 编辑角色

   void UpdateRole(int role_id, string name, string description, int[] permission_ids)

- 赋予角色权限

   void GrantRole(int role_id, int[] permission_ids)

- 移除角色权限

   void RevokeRole(int role_id, int[] permission_ids)

- 列出角色权限

   Permission[] ListRolePermissions(int role_id)

- 判断用户在指定域是否有指定权限

   bool HasPermission(int user_id, int scope_id, int permission_id)

- 判断用户在指定域是否有指定角色

   bool HasRole(int user_id, int scope_id, int role_id)

- 列出用户在指定域中所有角色

   Role[] ListUserRoles(int user_id, int scope_id)

- 赋予用户角色

   void GrantUser(int user_id, int[] role_id)

- 移除用户角色

   void RevokeUser(int user_id, int[] role_id)

- 列出在指定域中有指定角色的所有用户

   int[] ListUsers(int scope_id, int role_id)

- 列出所有在指定域中的用户

   int[] ListUsers(int scope_id)
