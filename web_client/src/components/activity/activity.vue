<template>
    <div>
        <!-- 搜索方式 -->
        <div>
            创建时间<!-- 活动创建时间搜索 -->
            <DatePicker v-model="select.createTime" type="daterange" split-panels placeholder="Select date" style="width: 180px; margin-left: 30px"></DatePicker>
            报名时间<!-- 活动报名时间搜索 -->            
            <DatePicker v-model="select.signupTime" type="daterange" split-panels placeholder="Select date" style="width: 180px; margin-left: 30px"></DatePicker>
            开始时间<!-- 活动开始时间搜索 -->            
            <DatePicker v-model="select.startTime" type="daterange" split-panels placeholder="Select date" style="width: 180px; margin-left: 30px"></DatePicker>
            发布状态<!-- 根据活动发布状态搜索 -->
            <b-form-select v-model="select.publishSelected" :options="publishSelectList" style="width: 120px; height: 32px; margin-left: 30px"></b-form-select>
            报名状态<!-- 根据活动报名状态搜索 -->
            <b-form-select v-model="select.signupSelected" :options="signupSelectList" style="width: 120px; height: 32px; margin-left: 30px"></b-form-select>
            活动状态<!-- 根据活动状态搜索 -->
            <b-form-select v-model="select.startSelected" :options="startSelectList" style="width: 120px; height: 32px; margin-left: 30px"></b-form-select>
            是否需要审核
            <Checkbox v-model="select.ifReview"/>
            是否允许非本组织成员参加
            <Checkbox v-model="select.ifOnlyMem"/>
            发起社团<!-- 直接输入社团名称 -->
            <Input v-model="select.value" placeholder="请输入社团名称" style="width: 300px"/>
            <Button type="info" @click="handleSelect">搜索</Button>
            <!-- 新建活动 -->
            <Button type="info" @click="form.onshow=true" style="position:relative; float: right;">新建</Button>

            <ActivityCreate :form ="form"/>
        </div>

        <div id="Divide" style="width: 100%;">
            <Divider />
        </div>

        <!-- 活动排列 -->
        <div>
            <div span="11" style="display: flex;justify-content: space-around;flex-wrap: wrap">
                <Card v-for="card in cardList" :key="card.id" :row="card" style="width: 465px; margin: 30px;" :bordered="false">
                    <div style="height: 30px">
                        <p style="float: left" slot="title">{{card.name}}</p>
                        <Badge style="float: right" :text="card.text" :status="card.state"/>
                    </div>
                    <Divider />
                    <router-link :to="{path: '/admin/activity/about'}">
                        <img src="../../assets/activity/cover1.jpeg" style="height: 280px; width: 100%;"/>
                    </router-link>
                    <p>Time:{{card.time}}</p>
                    <p>Brief Description:{{card.bd}}</p>
                </Card>
            </div>
        </div>
    </div>
</template>

<script>
let publishSelectLists=[
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

let signupSelectLists=[
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

let startSelectLists=[
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
        name:"五一长跑节",
        text:"已结束",
        state:"processing",
        time: "2019-05-01",
        bd:"法规发生的加法的方式上电视看大家都老夫妇可发生了骄傲的叫法可怜死啦开发顾问费即可收到"
    },
    {
        name:"MVIG大型数据集标注",
        text:"未开始",
        state: "success",
        time:"2019-07-18 22:17 - 2019-07-31 23:59",
        bd:"我们根据描述人体动作的标签，你们要做的，就是给图片贴上最适合的标签。",
    },
    {
        name:"联名文化衫Line Up",
        text:"进行中",
        state: "warning",
        time:"2019-07-23 08:00 - 2019-07-31 08:00",
        bd:"明天，你就要去很远的地方，带上我们的故事。"
    },
    {
        name:"校园带队志愿者招募",
        text:"未发布",
        state:"default",
        time:"2019-07-11 15:00 - 2019-07-11 18:30",
        bd:"招募校园带队讲解志愿者",
    }
]

import ActivityCreate from '../activity/activity_create.vue'

export default {
    name: 'activity',
    components: { ActivityCreate },
    data () {
        return{
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
                description: "",
                signupTime: "",
                timeType: "",
                time: "",
                checked1: "",
                checked2: "",
                numberOfPeople: 30,
                materials: ""
            },
            select:{
                createTime:"",
                signupTime:"",
                startTime:"",
                publishSelected: null,
                signupSelected: null,
                startSelected: null,
                ifReview: false,
                ifOnlyMem: false,
                value: '',
            }
        }
    },
    methods: {
        handleSelect(){

        }
    },
    mounted() {
        
    },
}
</script>

<style scoped>
</style>