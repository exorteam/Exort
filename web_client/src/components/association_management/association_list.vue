<template>
    <Tabs active-key="tab_key">
        <Tab-pane label="社团管理" key="tabpane_key1">
        <div id="AssoList">
            <div id=SearchAsso>
                <i-input v-model="assoSearch.keyword" placeholder="请输入关键词" style="width: 300px" />
                <Button type="info" @click="getAssociationList">搜索</Button>
                <Button type="info" @click="showCreateForm" style="  position:relative ;left:20px;">创建社团申请</Button>
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
        <b-form-checkbox-group @input="get" v-model="assoStateSelected" :options="assoStateList" switches></b-form-checkbox-group>
        </div>
        <div id="Divide">
           <Divider />
        </div>

        <div id="CardList"  style="display: flex;justify-content: space-around;flex-wrap: wrap">
            <Card v-for="item in AssoList" :key="item.id" :row="item" style="width:350px;height:350px" >
            <p slot="title">
                <Icon type="ios-film-outline"></Icon>
                {{item.name}}（{{StateList(item.state)}}）
            </p>
            <div style="text-align: center;height:100px">
                <img :src="item.logo" style="width:80px;height:80px;"/>
            </div>
            <div style= "min-height: 100%;">
                <p>{{ item.description }}</p>
                <ul v-for="tag in item.tags" :key="tag.id" :row="tag" style="color:#5cadff">
                    <li>
                        <p>{{ tag }}</p>
                    </li>
                </ul>
            </div>

            <div style="position:absolute;bottom:10px;">
                <Button type="info" v-on:click="showEditForm(item)">编辑</Button>
                <Button style= " position:relative ;left:200px;" type="warning" v-on:click="deleteAssociation(item)">删除</Button>
            </div>
            </Card>
        </div>
        <div style="margin-top:15px;text-align: center">
        <Page id = "page" show-elevator show-total
        :total="pageProp.totalSize" :page-size.sync="pageProp.pageSize" :page-size-opts="pageProp.pageSizeOpt"
        :current.sync = "pageProp.pageNum"  @on-change="getAssociationList"></Page>
        </div>
        </div>
        </Tab-pane>
        <Application :pendingAppData  ="pendingAppData" :handledAppData  ="handledAppData"/>



    </Tabs>
</template>

<script>
import Solid from '../../assets/AssociationLogo/solid.jpg'
import CreateAssociation from './create_association.vue'
import TagChoose from '../activity/tag_choose.vue'
import Application from './application.vue'
import axios from 'axios'
export default {

    name:'associationList',
    components:{CreateAssociation,TagChoose,Application},
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
                assoState:null,
                showState:false,
                materials: "",
                type:""
            },


            pageProp:{
                totalSize : 50,
                pageSize : 6,
                pageNum : 1
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
                    applicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate:'管理'
                },
                {
                    applicant_Id: 'wangxiaoming',
                    applicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate:'管理'
                },
                {
                    applicant_Id: 'wangxiaoming',
                    applicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate:'管理'
                },
                {
                    applicant_Id: 'wangxiaoming',
                    applicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate:'管理'
                },
                {
                    applicant_Id: 'wangxiaoming',
                    applicant_name: '王小明',
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
            handledAppData: [
                {
                    applicant_Id: 'wangxiaoming',
                    applicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate_time:'2019/7/5 17:25',
                    operate_result:'未通过'
                },
                {
                    applicant_Id: 'wangxiaoming',
                    applicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate_time:'2019/7/5 17:25',
                    operate_result:'未通过'
                },
                {
                    applicant_Id: 'wangxiaoming',
                    applicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate_time:'2019/7/5 17:25',
                    operate_result:'未通过'
                },
                {
                    applicant_Id: 'wangxiaoming',
                    applicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate_time:'2019/7/5 17:25',
                    operate_result:'未通过'
                },
                {
                    applicant_Id: 'wangxiaoming',
                    applicant_name: '王小明',
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
                    name:"Test",
                    description:"描述",
                    tags:["q","w"],
                    logo:Solid

                },
                {
                    name:"Test",
                    description:"描述",
                    tags:["q","w"],
                    logo:Solid

                },
                {
                    name:"Test",
                    description:"描述",
                    tags:["q","w"],
                    logo:Solid

                },
                {
                    name:"Test",
                    description:"描述",
                    tags:["q","w"],
                    logo:Solid

                },
                {
                    name:"Test",
                    description:"描述",
                    tags:["q","w"],
                    logo:Solid

                },
                {
                    name:"Test",
                    description:"描述",
                    tags:["q","w"],
                    logo:Solid

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
            this.form.type = "create"
            this.form.onshow=true
        },
        showEditForm(item){
            this.form.assoState = item.state
            this.form.showState = true
            this.form.assoId=item.id
            this.form.name=item.name
            this.form.description=item.description
            this.form.tag.tagList=item.tags
            this.form.type = "edit"
            this.form.onshow=true
        },
        deleteAssociation(item){
            axios
            .delete('http://localhost:8080/associations/'+item.id
            )
            .then(response => {
                console.log(this.tags)
                this.$Message.info('删除成功');
                this.getAssociationList();
            })
            .catch(e => {
                console.log(e)
            });
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

        getState(){
            if(this.assoStateSelected.length==2) {
                this.assoSearch.state = 2
            }
            else if (this.assoStateSelected[0] == "active"){
                this.assoSearch.state = 1
            }
            else if (this.assoStateSelected[0]=="blocked"){
                this.assoSearch.state = 0
            }
            else{
                this.assoSearch.state = -1
            }
        },
        get(){
            this.getAssociationList()
        },

        getAssociationList() {
            console.log(this.assoStateSelected)
            this.getState();
            this.assoSearch.pageNum = this.pageProp.pageNum - 1;
            this.assoSearch.pageSize = this.pageProp.pageSize;
            this.assoSearch.tags = this.tag.tagList.join();
            console.log(this.assoSearch.state)
            this.getState();


            axios
            .get('http://localhost:8080/associations',{
                params: {
                    pageNum:this.assoSearch.pageNum,
                    pageSize:this.assoSearch.pageSize,
                    keyword:this.assoSearch.keyword,
                    tags:this.tag.tagList.join(),
                    state:this.assoSearch.state
                }
            })
            .then(response => {
                this.AssoList = response.data.data.content
                this.pageProp.totalSize = response.data.data.totalSize
            })
            .catch(e => {
                console.log(e)
            });
        }
    },
    mounted() {
            axios
            .get('http://localhost:8080/associations',{
                params: {
                    pageNum:0,
                    pageSize:6,
                    keyword:"",
                    tags:"",
                    state:2
                }
            })
            .then(response => {
                this.AssoList = response.data.data.content
                this.pageProp.totalSize = response.data.data.totalSize
            })
            .catch(e => {
                console.log(e)
            });
    }
}
</script>
<style>

</style>
