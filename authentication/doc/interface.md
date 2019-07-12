# 登录模块接口设计

## 邮箱注册登录

流程：

- 账号
   - 邮箱
   - 是否已验证
   - 关联用户
   - 创建时间
   - 验证时间

### 创建账号

- 输入
   - 邮箱
   - 密码

- 输出
   - 账号

### 更换密码

- 输入
   - 邮箱
   - 密码

- 输出
   - 是否成功

### 关联用户

- 输入
   - 邮箱
   - 用户

- 输出
   - 是否成功

### 验证登录

- 输入
   - 邮箱
   - 密码

- 输出
   - 是否通过

### 获取邮箱验证码

- 输入
   - 邮箱

- 输出
   - 验证码

### 验证邮箱

- 输入
   - 邮箱
   - 验证码

- 输出
   - 是否通过

## OAuth2

TODO: 添加第三方 OAuth2 登录功能