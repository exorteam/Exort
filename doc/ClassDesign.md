## 社团管理

- Long AssociationId
- String AssociationName
- String Description
- DateTime CreateTime
- int Leader_ID
- int Type

- 创建社团

Association CreateAssociation(int Association_ID, String AssociationName, String Description, int Leader_ID, int Type)

- 查询社团信息
By ID
Association GetAssociationByID(int ID)

By Name
AssociationInfomation GetAssociationByID(String Name)

- 修改社团信息
Association ChangeAssociationInfo(int ID, String AssociationName,String Description,int LeaderID,String Type)

## 社团申请 AssociationApply

|属性|说明|
|---|---|
|int ApplyID|申请事件编号|
|int creator_id|申请人|
|int[] material_ids|提交的报名材料|
|Datetime ApplyTime|提交时间|
|int State|申请状态，有unhandled,accepted, refused, canceled|

- 创建申请

Association createAssociation(int user_id, int[] material_id)

- 查询申请

Association[] getAssociationsApplies(int user_id, DateTime after, DateTime before, int status)

- 改

bool CancelAssociationApply(int association_apply_id)

bool AcceptAssociationApply(int association_apply_id)

bool RefuseAssociationApply(int association_apply_id)

## 用户管理

- UserInfomation
- int ID
- String FirstName
- String LastName
- Bool Gender
- String Type
- DateTime DateOfBirth
- int PhoneNumber
- String Email
- int QQNumber
- String Description 个人陈述
- int[] Subscription_ID 订阅的社团
- int[] AffiliatedAssociation_ID 加入的社团

- 创建用户信息

UserInfomation CreateUserInfomation(int ID,String FirstName,String LastName,Bool Gender,String DateOfBirth, int PhoneNumber,String Email,int QQNumber,String Description)

- 得到全名

String GetFullName(int ID)

- 查询用户信息

UserInformation GetUserInfoByID(int ID)
UserInformation GetUserInfoByName(String Name)

- 修改用户信息

UserInformation ChangeUserInfo(int ID,String FirstName,String LastName,Boolean Gender,String Type,java.util.Date DateOfBirth,int PhoneNumber,String Email,int QQNumber,String Description)

- 加入社团

Bool JoinAssociation(int UserID,int AssociationID)

- 订阅社团

Bool SubscribAssociation(int UserID,int AssociationID)

## 文章管理 Article

- Long id
- Long[] association_id
- DateTime createTime
- DateTime publishTime
- DateTime LastPublishTime
- DateTime LastModifyTime
- String title
- String content (created, updated)
- int state (unpublished, published)
- 创建文章

boolean createAticle(Long[] association_id, String title, String content)

- 删除文章

deleteArticles(Long id)

- 修改文章

boolean modifyArticles(Long id, String title, String content)
-getArticle(Long id)
-modifyTitles(Long id, String title)
-modifyContent(Long id, String content)

- 查询文章

Article getArticle(Long id)
-getTitle()
-getContent()
-getComment()

- 查询多篇文章

Article[] ListArticles(int type，Int ID)

- 发布文章

boolean publishArticle(Long id)

- 撤回文章

boolean withdrawArticle(Long id)

## 活动管理 Activity

- Long activity_id
- Association[] organizers
- string name
- string description
- DateTime signup_begin_at
- DateTime signup_end_at
- DateTime begin_at
- DateTime end_at
- int state
- bool review
- bool only_members
- string[] material_types
- int max_participants
- ActivityRate[] rates

- 活动创建

Activity(Association[] organizers, string name, string description,
DateTime signup_begin_at, DateTime signup_end_at,
DateTime begin_at, DateTime end_at,
bool require_review, string[] require_material_types)

- 查询活动创建者

Association[] getOrganizers()

- 添加创建人

void addOrganizer(Association association)

- 移除创建人

void removeOrganizer(Association association)

- 查看评分

int[] getRates()

- 修改活动信息

bool ChangeActivityInfo(string name, string description,
DateTime signup_begin_at, DateTime signup_end_at,
DateTime begin_at, DateTime end_at,
bool require_review, string[] require_material_types)

- 查询活动状态

int GetActivityState(int ActivityID)

- 查询活动

Activity getActivity(int activity_id)

- 筛选活动

Activity[] getActivities(...filter)

## 活动报名 ActivitySignup

- int activity_signup_id
- User user
- Activity activity
- Material[] materials
- DateTime applyTime
- DateTime resonseTime
- int status // pending, submited, success, failed

- 报名

Signup createSignUp(User user, Activity activity, Material[] materials)

- 报名通过

void success(int activity_signup_id)

- 报名失败

void failed(int activity_signup_id)

- 查询申请材料

Material[] getMaterials()

- 查询报名

s ActivitySignup getSignup(int activity_signup_id)

s ActivitySignup[] getSignup(User user, Activity activity)

s ActivitySignup[] getSignups(User user, int[] status)

s ActivitySignup[] getSignups(Activity activity, int[] status)

## 报名材料 Material

- Long material_id
- int type
- string content

- 查询报名材料

material[] getMaterial(int type, string content)

Material getMaterial(int material_id)

- 修改申请材料

boolean setMaterial(int material_id, String content)

## 评论 Comment

- int comment_id
- User user
- int starCount(点赞)
- string content
- DateTime time
- int state

- 发表评论

commentActivity(Long activity_id)

- 回复评论

commentComment(User user, string content)

- 查询评论

string[] getContent()

- 折叠评论

boolean HideComment(Int comment_id)

- 显示评论

boolean ShowComment(Int comment_id)

