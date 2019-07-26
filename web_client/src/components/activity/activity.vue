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
                <Input v-model="select.keyword" placeholder="请输入社团名称" style="width: 300px; margin-right: 40px"/>
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
                        <p style="float: left" slot="title">{{card.name}}</p>
                        <Badge style="float: right" :text="card.text" :status="card.state"/>
                    </div>
                    <Divider/>
                    <Button @click="to_detail(card.id)" type="text" style="height: 280px; width: 100%;">
                        <img :src="card.image" style="height: 280px; width: 100%;"/>
                    </Button>
                    <Divider/>
                    <p>Time:{{card.time}}</p>
                    <p>Brief Description:{{card.bd}}</p>
                </Card>
            </div>
            <div>
                <Page :total="totalSize" :current="pageNum" :page-size="pageSize" simple @on-change="handlePage" style="text-align: center;"></Page>
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

let cardLists=[
    {
        id: "5d302c76a5fabe1702283262",
        name:"五一长跑节",
        text:"已结束",
        image: image,
        state:"processing",
        time: "2019-05-01",
        bd:"法规发生的加法的方式上电视看大家都老夫妇可发生了骄傲的叫法可怜死啦开发顾问费即可收到"
    },
    {
        id: "5d302c76a5fabe1702283263",
        name:"MVIG大型数据集标注",
        text:"未开始",
        image: image,
        state: "success",
        time:"2019-07-18 22:17 - 2019-07-31 23:59",
        bd:"我们根据描述人体动作的标签，你们要做的，就是给图片贴上最适合的标签。",
    },
    {
        id: "5d302b3ba5fabe1702283264",
        name:"联名文化衫Line Up",
        text:"进行中",
        image: image,
        state: "warning",
        time:"2019-07-23 08:00 - 2019-07-31 08:00",
        bd:"明天，你就要去很远的地方，带上我们的故事。"
    },
    {
        id: "5d302b3ba5fabe1702283265",
        name:"校园带队志愿者招募",
        text:"未发布",
        image: image,
        state:"default",
        time:"2019-07-11 15:00 - 2019-07-11 18:30",
        bd:"招募校园带队讲解志愿者",
    }
]

    import ActivityCreate from './activity_create.vue'
    import TagChoose from './tag_choose'
    import axios from 'axios'
    import image from '../../assets/activity/cover1.jpeg'

export default {
    name: 'activity',
    components: { ActivityCreate, TagChoose },
    data () {
        return{
            currentAvtivityId: 0,
            publishSelected: null,
            publishSelectList: publishSelectLists,
            signupSelected: null,
            signupSelectList: signupSelectLists,
            startSelected: null,
            startSelectList: startSelectLists,
            cardList: cardLists,
            form:{
                onshow: false,
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
                tags: []
            },
            tag:{
                tag_show: false,
                tagList:[],
            },
            handleSelect() {
                let data = this.select
                data.tags = this.tag.tagList
                axios.get('http://localhost:8000/activities', {
                    params: {
                        pagenum: 0,
                        pagesize: 10,
                        _body: btoa(JSON.stringify({data}))
                    }
                })
                    .then(response => {
                        this.cardList = response.data
                    })
                    .catch(e => {
                        console.log(e)
                    })
            }
        },
        mounted() {
            let data = {
                tagList: [],
                association: [],
                keyword: "",
                createTime: "",
                signupTime: "",
                startTime: "",
                publishSelected: null,
                signupSelected: null,
                startSelected: null,
                ifReview: false,
                ifOnlyMem: false,
            },
            totalSize: 103,
            pageNum: 2,
            pageSize: 9,
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
            axios.
                get('http://202.120.40.8:30727/activities', {
                    params:{
                        pagenum:0,
                        pagesize:9,
                        _body: btoa(JSON.stringify({data}))
                    }
                })
                .then(response => {
                    this.cardList = response.data.content
                    this.totalSize = response.data.totalSize
                    this.pageNum = response.data.pageNum
                    this.pageSize = response.data.pageSize
                })
                .catch(e => {
                    console.log(e)
                })
        }
    },
    mounted() {
        let data = {
            tagList:[],
            association: [],
            keyword: "",
            createTime: "",
            signupTime: "",
            startTime: "",
            publishSelected: null,
            signupSelected: null,
            startSelected: null,
            ifReview: 0,
            ifOnlyMem: 0,
        }
        axios
            .get('http://202.120.40.8:30727/activities', {
                params:{
                    pagenum:0,
                    pagesize:9,
                    _body: btoa(JSON.stringify({data}))
                }
            })
            .then(response => {
                this.cardList = response.data.content
                this.totalSize = response.data.totalSize
                this.pageNum = response.data.pageNum
                this.pageSize = response.data.pageSize
            })
            .catch(e => {
                console.log(e)
            })
    },
}
</script>

<style scoped>
</style>
