## 用户管理
- **User**

   |属性|说明|
   |---|---|
   |int id | |
   |string account| 账户邮箱 |
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
   |int status | 账号状态(inactived, normal, blocked) |

1. 创建用户
    - 输入
        - 账户邮箱
    - 输出
        - 用户

2. 更改信息
    - 输入
        - 用户ID
        - 昵称
        - 个人描述
        - 性别
        - 生日
        - 真实姓名
        - 学号
        - 联系方式-手机
        - 联系方式-邮箱
        - 联系方式-qq号
        - 联系方式-微信号
    - 输出
        - 用户

3. 禁用用户
    - 输入
        - 用户ID
    - 输出
        - 暂无

4. 恢复用户
    - 输入
        - 用户ID
    - 输出
        - 暂无

5. 回调函数
    - 输入
        - 操作者
        - 操作
        - 申请
    - 输出
        - 无

6. 查询用户信息
    - 输入
        - 用户ID
    - 输出
        - 用户