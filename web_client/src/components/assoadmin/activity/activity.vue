<template>
    <div>
        <!-- 搜索方式 -->
        <div>
            <div style="margin-top: 15px; margin-left: 20px">
                创建时间<!-- 活动创建时间搜索 -->
                <DatePicker v-model="select.createTime" type="daterange" split-panels placeholder="Select date" style="width: 180px; margin-right: 45px"></DatePicker>
                报名时间<!-- 活动报名时间搜索 -->
                <DatePicker v-model="select.signupTime" type="daterange" split-panels placeholder="Select date" style="width: 180px; margin-right: 45px"></DatePicker>
                开始时间<!-- 活动开始时间搜索 -->
                <DatePicker v-model="select.startTime" type="daterange" split-panels placeholder="Select date" style="width: 180px; margin-right: 45px"></DatePicker>
                发布状态<!-- 根据活动发布状态搜索 -->
                <b-form-select v-model="select.publishSelected" :options="publishSelectList" style="width: 120px; height: 32px; margin-right: 45px"></b-form-select>
                报名状态<!-- 根据活动报名状态搜索 -->
                <b-form-select v-model="select.signupSelected" :options="signupSelectList" style="width: 120px; height: 32px; margin-right: 45px"></b-form-select>
                活动状态<!-- 根据活动状态搜索 -->
                <b-form-select v-model="select.startSelected" :options="startSelectList" style="width: 120px; height: 32px; margin-right: 25px"></b-form-select>
                <!-- 新建活动 -->
                <Button type="info" @click="form.onshow=true" style="float:right; margin-right:29px">新建</Button>
            </div>
            <div style="margin-top:15px; margin-left: 20px">
                发起社团<!-- 直接输入社团名称 -->
                <Input v-model="select.association" placeholder="请输入社团名称" style="width: 300px; margin-right: 40px"/>
                关键词<!-- keyword -->
                <Input v-model="select.keyword" placeholder="请输入搜索关键词" style="width: 300px; margin-right: 40px"/>
                是否需要审核
                <Checkbox v-model="select.ifReview" style="margin-right: 40px"/>
                是否允许非本组织成员参加
                <Checkbox v-model="select.ifOnlyMem" style="margin-right: 40px"/>

                <div style="display:inline">
                    <Button @click="tag.tag_show=true" style="width: 80px">选择标签</Button>
                    <TagChoose :tag="tag"/>
                </div>
                <div v-if="tag.tagList.length" style="display:inline">
                    <Tag v-for="tag in tag.tagList" :key="tag.id" :row="tag">{{ tag }}</Tag>
                </div>
                <!-- 按照条件进行搜索 -->
                <Button type="info" @click="handleSelect" style="float:right; margin-right:29px">搜索</Button>
            </div>
            <div style="margin-top:15px; margin-left: 20px">
            </div>

            <ActivityCreate :form="form"/>
        </div>

        <Divider style="width: 100%;"/>

        <!-- 活动排列 -->
        <div>
            <div span="11" style="display: flex;justify-content: space-around;flex-wrap: wrap">
                <Card v-for="card in cardList" :key="card.id" :row="card" style="width: 465px; margin: 30px;"
                      :bordered="false">
                    <div style="height: 30px">
                        <p style="float: left" slot="title">{{card.title}}</p>
                        <Badge style="float: right" :text="stateList[card.activityState]" :status="statusList[card.activityState]"/>
                    </div>
                    <Divider/>
                    <Button @click="to_detail(card.id)" type="text" style="height: 280px; width: 100%;">
                        <img :src="card.image" style="height: 280px; width: 100%;"/>
                    </Button>
                    <Divider/>
                    <p>Time:{{card.time.time.start + card.time.time.end}}</p>
                    <p>Brief Description:{{card.content}}</p>
                </Card>
            </div>
            <div>
                <Page show-elevator show-total :total="page.totalSize" :current.sync="page.pageNum+1" :page-size.sync="page.pageSize" simple @on-change="handleSelect" style="text-align: center;"></Page>
            </div>
        </div>
    </div>
</template>

<script>
    let publishSelectLists = [
        {
            value: null,
            text: '请选择发布状态',
        },
        {
            value: '未发布',
            text: '未发布'
        },
        {
            value: '已发布',
            text: '已发布'
        }
    ]

    let signupSelectLists = [
        {
            value: null,
            text: '请选择报名状态',
        },
        {
            value: '报名未开始',
            text: '报名未开始'
        },
        {
            value: '报名中',
            text: '报名中'
        },
        {
            value: '报名已结束',
            text: '报名已结束'
        }
    ]

    let startSelectLists = [
        {
            value: null,
            text: '请选择活动状态',
        },
        {
            value: '未开始',
            text: '未开始'
        },
        {
            value: '进行中',
            text: '进行中'
        },
        {
            value: '已结束',
            text: '已结束'
        }
    ]

import ActivityCreate from './activity_create.vue'
import TagChoose from '../../common/tag_choose.vue'
import axios from 'axios'
import image from '../../../assets/activity/cover1.jpeg'

export default {
    name: 'activity',
    components: { ActivityCreate, TagChoose },
    data () {
        return{
            stateList: [ '未发布', '报名未开始', '报名中', '未开始', '进行中', '已结束' ],
            statusList: [ "default", "default", "processing", "warning", "error", "success" ],
            currentAvtivityId: 0,
            publishSelected: null,
            publishSelectList: publishSelectLists,
            signupSelected: null,
            signupSelectList: signupSelectLists,
            startSelected: null,
            startSelectList: startSelectLists,
            cardList: [],
            page:{
                totalSize: 9,
                pageNum: 0,
                pageSize: 9,
            },
            select:{
                association: "",
                keyword: "",
                createTime: "",
                signupTime: "",
                startTime: "",
                publishSelected: null,
                signupSelected: null,
                startSelected: null,
                ifReview: 0,
                ifOnlyMem: 0
            },
            form:{
                onshow: false,
                data:{
                    title: "",
                    content: "",
                    signupTime: {
                        type: 0,
                        time:[{
                            start: "",
                            end: "",
                        }]
                    },
                    time: {
                        type: 0,
                        time:[{
                            start: "",
                            end: ""
                        }]
                    },
                    ifReview: false,
                    ifOnlyMem: false,
                    maxParticipants: '',
                    materials: [],
                    image: "",
                    tag:{
                        tag_show: false,
                        tagList:[],
                    }
                },
            },
            tag:{
                tag_show: false,
                tagList:[],
            }      
        }
    },
    methods: {
        handlePage(){

        },
        to_detail(value){
            // console.log(value)
            sessionStorage.setItem('activityid', value)
            this.$store.dispatch('setStatus')

            this.$router.replace({path: "/admin/activity/about"})
        },
        handleSelect(){
            let data = this.select
            data.tags = this.tag.tagList

            if(data.createTime!=null && data.createTime.length==2){
                if(data.createTime[0]==""){
                    data.createTime=null
                }else{
                    let create  = {
                        start: data.createTime[0],
                        end:data.createTime[1]
                    }
                    data.createTime = create
                }
            }
            if(data.signupTime!=null && data.signupTime.length==2){
                if(data.signupTime[0]==""){
                    data.signupTime=null
                }else{
                    let signup  = {
                        start: data.signupTime[0],
                        end: data.signupTime[1]
                    }
                    data.signupTime = signup
                }
            }
            if(data.startTime!=null && data.startTime.length==2){
                if(data.startTime[0]==""){
                    data.startTime=null                    
                }else{
                        let time = {
                        start: data.startTime[0],
                        end: data.startTime[1]
                    }
                    data.startTime = time
                }
            }
            if(data.ifReview===true){
                data.ifReview=2
            }else if(data.ifReview===false){
                data.ifReview=1
            }
            if(data.ifOnlyMem===true){
                data.ifOnlyMem=2
            }else if(data.ifOnlyMem===false){
                data.ifOnlyMem=1
            }
            if(data.keyword==""){
                data.keyword=null
            }

            console.log(data)
            this.axios({
                method: "post",
                url: "/activities/filter",
                params:{
                    'pagesize': this.page.pageSize,
                    'pagenum':this.page.pageNum,
                    'sortby':"0",
                },
                data: data
            })
            .then((response) => {
                let responseData = response.data.data
                console.log(responseData)
                this.cardList = responseData.content
                this.page.totalSize = responseData.totalSize
                this.page.pageNum = responseData.pageNum
                this.page.pageSize = responseData.pageSize
                console.log(this.page.totalSize, this.page.pageNum, this.page.pageSize)
                for(let i=0; i< this.cardList.length; i++){
                    this.setData(this.cardList[i])
                }
            })
            .catch(e => {
                console.log(e)
            })
        },
        setData(value) {
            if (value.publishState == 0) {
                value.activityState = 0
            } else if (value.signupState != 2) {
                value.activityState = value.signupState + 1
            } else {
                value.activityState = value.signupState + 3
            }
        },
    },
    mounted() {
        this.handleSelect()
        // let data = this.select
        // data.tagList = this.tag.tagList

        // this.axios({
        //     method:"get",
        //     url:"activities",
        //     params:{
        //         'pagesize': this.page.pageSize,
        //         'pagenum':this.page.pageNum,
        //         'sortby':"0",
        //     },
        //     data: data
        // })
        // .then(response => {
        //     this.cardList = response.data.data.content
        //     this.totalSize = response.data.data.totalSize
        //     this.pageNum = response.data.data.pageNum
        //     this.pageSize = response.data.data.pageSize
        // })
        // .catch(e => {
        //     console.log(e)
        // })
    },
}
</script>
