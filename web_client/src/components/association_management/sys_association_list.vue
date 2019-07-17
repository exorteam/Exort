<template>
    <Tabs active-key="tab_key">
        <Tab-pane label="社团管理" key="tabpane_key1">
        <div id="AssoList">
            <div id=SearchAsso>
                <i-input v-model="assoSearch.keyword" placeholder="请输入关键词" style="width: 300px" />
                <Button type="info" @click="getAssociationList">搜索</Button>
                <Button type="info" @click="showCreateForm" style="  position:relative ;left:20px;">创建社团</Button>
                <CreateAssociation :form ="form"/>
                <div>
                    <div style="display:inline">
                        <Button @click="tag.tag_show=true" style="width: 80px;position:relative ;top:5px">选择标签</Button>
                        <TagChoose :tag="tag"/>
                    </div>
                    <div v-if="tag.tagList.length" style="display:inline">
                        <Tag v-for="tag in tag.tagList" :key="tag.id" :row="tag" color="blue">{{ tag }}</Tag>
                    </div>
                </div>
            </div>
        <div>
        <div id="Divide">
           <Divider />
        </div>
        <b-form-checkbox-group v-model="assoStateSelected" :options="assoStateList" switches></b-form-checkbox-group>
        </div>
        <div id="Divide">
           <Divider />
        </div>

        <div id="CardList"  style="display: flex;justify-content: space-around;flex-wrap: wrap">
            <Card v-for="item in AssoList" :key="item.id" :row="item" style="width:350px;height:300px" >
            <p slot="title">
                <Icon type="ios-film-outline"></Icon>
                {{item.name}}（{{StateList(item.state)}}）
            </p>

            <a href="#" slot="extra"  v-on:click="showEditForm(item)"  >
                <Icon type="ios-loop-strong"></Icon>
                Edit
            </a>
            <!-- <CreateAssociation :form ="item"/> -->
            <div style="text-align: center;height:100px">
                <img :src="item.logo" style="width:80px;height:80px;"/>
            </div>
                    <p>{{ item.description }}</p>
            <ul v-for="tag in item.tags" :key="tag.id" :row="tag" style="color:#5cadff">
                <li>
                    <p>{{ tag }}</p>
                </li>
            </ul>
            </Card>
        </div>
        <div style="margin-top:15px;text-align: center">
        <Page show-sizer show-elevator show-total :page-size="pageProp.pageSize" :current="pageProp.pageNum" :total="pageProp.totalSize"></Page>
        </div>
        </div>
        </Tab-pane>
        <Tab-pane label="社团申请管理" key="tabpane_key2">
            <b-tabs content-class="mt-3">
            <b-tab title="尚未处理" active>
            <div id="AssoAppList">
                <div id=SearchAsso>
                  <Input v-model="inputDefaultValue" placeholder="请输入社团名称" style="width: 300px" />
                  <Button type="info">搜索</Button>
                </div>
                <div>
                  <div id="Divide">
                    <Divider />
                  </div>
                  <b-form-checkbox-group v-model="pendingAppTypeSelected" :options="pendingAppTypeList" switches>
                  </b-form-checkbox-group>
                </div>
                <div id="Divide">
                  <Divider />
                </div>
                <div>
                  <i-table :columns="pendingAppColumn" :data="pendingAppData"></i-table>
                  </div>
              </div>
            </b-tab>
            <b-tab title="已处理">

              <div id="AssoAppList">
                <div id=SearchAsso>
                  <Input v-model="inputDefaultValue" placeholder="请输入社团名称" style="width: 300px" />
                  <Button type="info">搜索</Button>
                </div>
                <div>
                  <div id="Divide">
                    <Divider />
                  </div>
                  <b-form-checkbox-group v-model="handledAppTypeSelected" :options="handledAppTypeList" switches>
                  </b-form-checkbox-group>
                </div>
                <div id="Divide">
                  <Divider />
                </div>
                <div>
                  <i-table :columns="handledAppColumns" :data="pendingAppData"></i-table>
                  </div>
              </div>

            </b-tab>
            </b-tabs>
        </Tab-pane>


    </Tabs>
</template>

<script>
import Solid from '../../assets/AssociationLogo/solid.jpg'
import axios from '../../http-common.js'
import CreateAssociation from './create_association.vue'
import EditAssociation from './edit_association.vue'
import TagChoose from '../activity/tag_choose.vue'
export default {

    name:'associationList',

    components:{CreateAssociation,TagChoose},
    data () {
        return {
            tagrepo : ["运动", "讲座", "讲座", "讲座", "讲座", "讲座", "讲座", "讲座","zxc","asd"],
            form:{
                assoId:"",
                onshow: false,
                name: "",
                description: "",
                tag:{
                    tag_show: false,
                    tagList:[],
                },
                logo:"",
                needMaterial:false,
                materials: "",
                type:""
            },
            pageProp:{
                totalSize : 50,
                pageSize : 6,
                pageNum : 1,

            },
            tag:{
                tag_show: false,
                tagList:[],
            },
            pendingAppColumn: [
                {
                    title: '申请人Id',
                    key: 'applicant_Id'
                },
                {
                    title: '申请人',
                    key: 'applicant_name'
                },
                {
                    title: '社团名称',
                    key: 'asso_name'
                },
                {
                    title: '提交时间',
                    key: 'submit_time'
                },
                {
                    title: '申请类型',
                    key: 'apply_type'
                },
                {
                    title: '处理',
                    key: 'operate',
                }
            ],
            pendingAppData: [
                {
                    applicant_Id: 'wangxiaoming',
                    aoolicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate:'管理'
                },
                {
                    applicant_Id: 'wangxiaoming',
                    aoolicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate:'管理'
                },
                {
                    applicant_Id: 'wangxiaoming',
                    aoolicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate:'管理'
                },
                {
                    applicant_Id: 'wangxiaoming',
                    aoolicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate:'管理'
                },
                {
                    applicant_Id: 'wangxiaoming',
                    aoolicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate:'管理'
                },
            ],
            handledAppColumns: [
                {
                    title: '申请人Id',
                    key: 'applicant_Id'
                },
                {
                    title: '申请人',
                    key: 'applicant_name'
                },
                {
                    title: '社团名称',
                    key: 'asso_name'
                },
                {
                    title: '提交时间',
                    key: 'submit_time'
                },
                {
                    title: '申请类型',
                    key: 'apply_type'
                },
                {
                    title: '处理时间',
                    key: 'operate_time',
                },
                {
                    title: '处理结果',
                    key: 'operate_result',
                }
            ],
            pendingAppData: [
                {
                    applicant_Id: 'wangxiaoming',
                    aoolicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate_time:'2019/7/5 17:25',
                    operate_result:'未通过'
                },
                {
                    applicant_Id: 'wangxiaoming',
                    aoolicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate_time:'2019/7/5 17:25',
                    operate_result:'未通过'
                },
                {
                    applicant_Id: 'wangxiaoming',
                    aoolicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate_time:'2019/7/5 17:25',
                    operate_result:'未通过'
                },
                {
                    applicant_Id: 'wangxiaoming',
                    aoolicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate_time:'2019/7/5 17:25',
                    operate_result:'未通过'
                },
                {
                    applicant_Id: 'wangxiaoming',
                    aoolicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate_time:'2019/7/5 17:25',
                    operate_result:'未通过'
                },
            ],
            assoStateSelected: ['active','blocked'], // Must be an array reference!
            assoStateList: [
                { text: '已激活', value: 'active' },
                { text: '已锁定', value: 'blocked' },
            ],
            pendingAppTypeSelected:['applying','applying_unblocked'],
            pendingAppTypeList: [
                { text: '申请创建社团', value: 'applying' },
                { text: '申请取消锁定', value: 'applying_unblocked' }
            ],
            handledAppTypeSelected:['pass','canceled','refused'],
            handledAppTypeList: [
                { text: '已通过申请', value: 'pass' },
                { text: '已取消申请', value: 'canceled' },
                { text: '已拒绝申请', value: 'refused' },
            ],
            inputDefaultValue : "",
            AssoList: [
                {
                    name: 'Team Liquid',
                    description: "欧洲老牌战队，成立于2012年12月。在2016年在2016 Shanghai Major和2016ESL ONE这两项赛事中都取得了亚军。紧接着队伍人员进行调整，引进了Miracle-等数名天梯9000高分选手。在 2017 年成為 TI 冠軍。",
                    logo: Solid,
                    state:0,
                    tags:[
                      "Dota2",
                      "Ti冠军"
                    ]
                },
                {
                  name: "Team Solid",
                  description: "B神、龙神、跳刀、二冰他们和yyf一起组建的Ti9海选战队。",
                  logo: Solid,
                  state:1,
                  tags:[
                    "Dota2",
                    "2届Ti冠军("
                  ]
                },
                              {
                  name: "Team Solid",
                  description: "B神、龙神、跳刀、二冰他们和yyf一起组建的Ti9海选战队。",
                  logo: Solid,
                  state:2,
                  tags:[
                    "Dota2",
                    "2届Ti冠军("
                  ]
                },
                              {
                  name: "Team Solid",
                  description: "B神、龙神、跳刀、二冰他们和yyf一起组建的Ti9海选战队。",
                  logo: Solid,
                  state:3,
                  tags:[
                    "Dota2",
                    "2届Ti冠军("
                  ]
                },
                {
                  name: 'Team Gas',
                  description: "气体战队成员为：一号位：ZSMJ 二号位：水瓶座（之前担任固体战队的工具人中单） 三号位：Axx 四号位：Ch 五号位：狗哥SanSheng",
                  logo: Solid,
                  state:4,
                  tags:[
                    "Dota2"
                  ]
                },
                {
                  name: 'Exort',
                  description: "使用微服务架构独立开发的在线社团管理与社交工具",
                  logo: Solid,
                  state:4,
                  tags:[
                    "社团管理",
                    "实用"
                  ]
                }
            ],
            assoSearch:{
                keyword:"",
                tags:"asd",
                state:2,
                pageNum:0,
                pageSize:7

            }
        }
    },
    methods: {
        showCreateForm(){
            this.type = "create"
            this.form.onshow=true
        },
        showEditForm(item){
            console.log(item)
            this.form.assoId=item.id
            this.form.name=item.name
            this.form.description=item.description
            this.form.tag.tagList=item.tags
            this.type = "edit"
            this.form.onshow=true
        },
        ok () {
            this.$Message.info('点击了确定');
        },
        cancel () {
            this.$Message.info('点击了取消');
        },
        StateList(state){
            switch(state)
            {
              case 0:return "已锁定";
              case 1:return "已激活";
              case 2:return "申请中";
              case 3:return "已取消申请";
              case 4:return "已删除";
              case 5:return "已拒绝申请";
            }
        },

        getAssociationList() {
            console.log(this.assoSearch);
            this.assoSearch.pageNum = this.pageProp.pageNum -1
            this.assoSearch.pageSize = this.pageProp.pageSize
            this.assoSearch.tags =
            this.assoSearch.state =

            axios
            .get('associations',{
                params: {
                    pageNum:this.assoSearch.pageNum,
                    pageSize:this.assoSearch.pageSize,
                    keyword:this.assoSearch.keyword,
                    tags:this.assoSearch.tags,
                    state:this.assoSearch.state
                }
            })
            .then(response => {
                console.log(response.data)
                 this.AssoList = response.data.data.content
            })
            .catch(e => {
                console.log(e)
            });
        }
    },
    mounted() {
            axios
            .get('associations',{
                params: {
                    pageNum:0,
                    pageSize:6,
                    keyword:"qw",
                    tags:"",
                    state:2
                }
            })
            .then(response => {
                console.log(response.data)
                 this.AssoList = response.data.data.content
            })
            .catch(e => {
                console.log(e)
            });
    }
}
</script>
<style>

</style>
