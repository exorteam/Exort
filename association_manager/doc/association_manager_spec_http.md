# 社团管理模块http接口设计
## 数据结构
   - 社团(Association)

      |field | description |
      |---|---|
      |int id | |
      |string name | 社团名 |
      |string description | 社团描述 |
      |string logo | 社团图标 |
      |string[] tags | 社团标签 |
      |int block_state | 社团是否封禁 |
      |String reason | 封禁理由 | 
## 接口
1. 查找指定社团

  将筛选条件作为参数传进函数，将符合条件的所有社团作为结果输出  

- Http Request  

   GET /associations

- Body Parameters

   |Parameter | Description |
   |---|---|
   |string keyword | 搜索关键词 |
   |string[] tags | 标签 | 
   |int block_state | 封禁状态 |
    
- Response  

   |code|description|
   |---|---|
   |200  (返回社团列表) | 查询成功 |
   |400  (返回空列表) | 查询失败 |
   - 200 用户查询成功
      ```
      {
         
      }
      ```


- 返回的社团列表描述
   |field | description |
   |---|---|
   |Association[] associations | 社团列表 |
- 例子
   - 输入

      ```
      GET /associations
      {
            keyword:"keyword",
            tags:[
                  "T1",
                  "T2",
            ],
            block_state:1
      }
      ```
   - 输出
      ```
      {
            associations:[
                  {
                        id:1,
                        name:"Association1",
                        description:"nothing to say",
                        logo:"image1(base64)",
                        tags:[
                              "T1",
                              "T2",
                        ],
                        block_state:1,
                        reason:"18+"
                  },
                  {
                        id:2,
                        name:"Association2",
                        description:"nothing to say,too",
                        logo:"image2(base64)",
                        tags:[
                              "T3",
                              "T4",
                        ],
                        block_state:0,
                        reason:""
                  },
            ]
      }
      ```

2. 创建社团（系统管理员）
   输入社团信息，创建并返回社团对象
- Http Request    
   POST /association
- Body Parameters
   |Parameter | Description |
   |---|---|
   |string name | 社团名称 |
   |string description | 社团描述 | 
   |string[] tags | 社团标签 |
   |string logo | 社团Logo |
- Response
   |code|description|
   |---|---|
   |200 （返回创建后的社团） | 创建成功 |
   |400 （返回创建失败） | 创建失败 |
- 例子
   - 输入
      ```
      POST /association
      {
         name:"Association1",
         description:"lalala",
         tags:[
            "T1",
            "T2",
            "T3"
         ]
         logo:"image(base64)"
      }
      ```
   - 输出:
      ```
      {
         id:1,
         name:"Association1",
         description:"nothing to say",
         logo:"image1(base64)",
         tags:[
            "T1",
            "T2",
         ],
         block_state:1,
         reason:"18+"
      },
      ```

3. 删除社团（系统管理员）
   - 输入
      - 社团id
   - 输出
      - 是否成功
 
4. 编辑社团信息
   - 输入
      - 社团id
      - 社团名称
      - 社团描述
      - 社团标签
      - 社团Logo
   - 输出
      - 编辑后的社团对象 

5. 封禁社团
   - 输入
      - 社团id
      - 封禁理由
      - 封禁时间
   - 输出
      - 是否成功

6. 解封社团
   - 输入
      - 社团id
   - 输出 
      - 是否成功
7. 社团创建申请处理
   - 输入
      - 操作者ID   
      - 动作  
      - 申请
         - 申请ID
         - 申请者ID
         - 申请类型
         - Association
            -社团id 
            -...
         - 材料列表
         - 申请时间
         - 处理时间
         - 申请状态
   - 输出 
      - 是否操作成功

8. 社团解禁申请处理
   - 输入
      - 操作者ID
      - 动作
      - 申请
         - 申请ID
         - 申请者ID
         - 申请类型
         - Association
            -社团id 
            -...
         - 材料列表
         - 申请时间
         - 处理时间
         - 申请状态
   - 输出 
      - 是否操作成功


