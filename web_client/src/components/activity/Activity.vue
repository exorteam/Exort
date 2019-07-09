<template>
    <div>
        <!-- 搜索方式 -->
        <div>
            <!-- 直接输入活动名称 -->
            <Input v-model="organizationName" placeholder="请输入社团名称" style="width: 300px" />
            <Button type="info" @click="handleClick">搜索</Button>
            <!-- 根据活动状态搜索 -->
            <b-form-select v-model="selected" @change="handleSelect" :options="selectList" style="width: 120px; height: 32px; margin-left: 30px"></b-form-select>
            <!-- 活动时间搜索，具体按什么时间待定 -->
            <DatePicker v-model="time" type="daterange" @on-change="handleTime" split-panels placeholder="Select date" style="width: 180px; margin-left: 30px"></DatePicker>
            <!-- 新建活动 -->
            <Button type="info" @click="form.create_show=true" style="position:relative; float: right;">新建</Button>
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
                        <Badge style="float: right" :text="textList[card.state]" :status="stateList[card.state]"/>
                    </div>
                    <Divider />
                    <router-link :to="{path: '/activity/about'}">
                        <img src="../../assets/activity/cover1.jpeg" style="height: 280px; width: 100%;"/>
                    </router-link>
                    <p>时间:{{card.time}}</p>
                    <p>发起:{{card.organizationName}}</p>
                    <p>简介:{{card.bd}}</p>
                </Card>
            </div>
        </div>

        <activityCreate :form="form"/>

    </div>
</template>

<script>
let selectLists=[
    {
        value: -1,
        text: '请选择分类',
    },
    {
        value: 0,
        text: '未发布'
    },
    {
        value: 1,
        text: '未开始'
    },
    {
        value: 2,
        text: '进行中'
    },
    {
        value: 3,
        text: '已结束'
    }
]

let cardLists=[
    {
        name:"五一长跑节",
        text:"已结束",
        state: 3,
        organizationName: "体总",
        time: "2019-05-01 22:17 - 2019-07-31 23:59",
        bd:"法规发生的加法的方式上电视看大家都老夫妇可发生了骄傲的叫法可怜死啦开发顾问费即可收到"
    },
    {
        name:"MVIG大型数据集标注",
        text:"未开始",
        state: 1,
        organizationName: "MVIG",
        time:"2019-07-18 22:17 - 2019-07-31 23:59",
        bd:"我们根据描述人体动作的标签，你们要做的，就是给图片贴上最适合的标签。",
    },
    {
        name:"联名文化衫Line Up",
        text:"进行中",
        state: 2,
        organizationName: "学联",
        time:"2019-07-23 08:00 - 2019-07-31 08:00",
        bd:"明天，你就要去很远的地方，带上我们的故事。"
    },
    {
        name:"校园带队志愿者招募",
        text:"未发布",
        state: 0,
        organizationName: "源源",
        time:"2019-07-11 15:00 - 2019-07-11 18:30",
        bd:"招募校园带队讲解志愿者",
    }
]

let originCardLists=[
    {
        name:"五一长跑节",
        text:"已结束",
        state: 3,
        organizationName: "体总",
        time: "2019-05-01 22:17 - 2019-07-31 23:59",
        bd:"法规发生的加法的方式上电视看大家都老夫妇可发生了骄傲的叫法可怜死啦开发顾问费即可收到"
    },
    {
        name:"MVIG大型数据集标注",
        text:"未开始",
        state: 1,
        organizationName: "MVIG",
        time:"2019-07-18 22:17 - 2019-07-31 23:59",
        bd:"我们根据描述人体动作的标签，你们要做的，就是给图片贴上最适合的标签。",
    },
    {
        name:"联名文化衫Line Up",
        text:"进行中",
        state: 2,
        organizationName: "学联",
        time:"2019-07-23 08:00 - 2019-07-31 08:00",
        bd:"明天，你就要去很远的地方，带上我们的故事。"
    },
    {
        name:"校园带队志愿者招募",
        text:"未发布",
        state: 0,
        organizationName: "源源",
        time:"2019-07-11 15:00 - 2019-07-11 18:30",
        bd:"招募校园带队讲解志愿者",
    }
]

import activityCreate from './activity_create'
export default {
    name: 'activity',
    components: { activityCreate },
    data () {
        return{
            time: '',
            organizationName: '',
            monthTable: [],
            cardList: cardLists,
            originCardList: originCardLists,
            selectList: selectLists,
            selected: -1,
            stateList: ["default", "success", "warning", 'processing'],
            textList: ["未发布", "未开始", "进行中", "已结束"],
            
            form: {
                info_editing_show: false,
                create_show: false,
                timeTypeList: [1, 2, 3, 4, 5],
                title: '',
                timeType: 1,
                description: '',
                signup_time: '',
                time: '',
                checked1: false,
                checked2: false,
                numberOfPeople: 30,
                materials: ''
            }
        }
    },
    methods: {
        handleSelect(value){
            // console.log(value)
            if(value==-1){
                this.cardList = this.originCardList
                return
            }
            this.cardList = []
            for(let i=0; i<this.originCardList.length; i++){i
                let state = this.originCardList[i].state
                if(state==value){
                    this.cardList.push(this.originCardList[i])
                }
            }
        },
        handleTime(){
            if(this.time==""){
                this.cardList = this.originCardList
                return
            }

            this.cardList = []
            let startTime = ((this.time[0]).toString()).split(' ')
            let endTime = ((this.time[1]).toString()).split(' ')
            let start = startTime[3] + "-" + this.monthTable[startTime[1]] + "-" + startTime[2]
            let end = endTime[3] + "-" + this.monthTable[endTime[1]] + "-" + endTime[2]

            for(let i=0; i<this.originCardList.length; i++){
                let time = (this.originCardList[i].time).split(' ')
                if (start <= time[0] && time[0] <= end) {
                    this.cardList.push(this.originCardList[i])
                }
            }
        },
        handleClick(){
            if(this.organizationName==""){
                this.cardList = this.originCardList
                return
            }
            this.cardList = []
            for(let i=0; i<this.originCardList.length; i++){
                let oname = (this.originCardList[i].organizationName).toString()
                if(oname.search(this.organizationName)!=-1){
                    this.cardList.push(this.originCardList[i])
                }
            }
        },
        info_ok() {
            this.info_editing_show = false;
            this.create_show = false;
        },
        info_cancel() {
            this.info_editing_show = false;
            this.create_show = false;

            this.form.title = '',
            this.form.signup_time = '',
            this.form.signup_time = '',
            this.form.time = '',
            this.form.time = '',
            this.form.checked1 = false,
            this.form.checked2 = false,
            this.form.numberOfPeople = 30
        },
        setMonthTable(){
            this.monthTable['Jan'] = '01'
            this.monthTable['Feb'] = '02'
            this.monthTable['Mar'] = '03'
            this.monthTable['Apr'] = '04'
            this.monthTable['May'] = '05'
            this.monthTable['Jun'] = '06'
            this.monthTable['Jul'] = '07'
            this.monthTable['Aug'] = '08'
            this.monthTable['Sep'] = '09'
            this.monthTable['Oct'] = '10'
            this.monthTable['Nov'] = '11'
            this.monthTable['Dec'] = '12'
        }
    },
    mounted() {
        this.setMonthTable()
    }
}
</script>

<style scoped>
</style>