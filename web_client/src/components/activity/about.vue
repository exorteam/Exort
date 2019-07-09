<template>
    <div>
        <Badge class="text" text="已结束" status="success"/>
        <img src="../../assets/activity/cover1.jpeg" style="width: 40%; height: 400px; margin: 10px 10px 20px 40px; float: right"/>
        <div style="margin-top: 10px height: 600px">
            <p slot="title">名称： {{form.title}}</p>
            <p>创建时间： {{form.create_time}} </p>
            <p>发布时间： {{form.publish_time}}</p>
            <p>报名起始时间：{{form.signup_begin_time}}</p>
            <p>报名截止时间：{{form.signup_end_time}}</p>
            <p>开始时间： {{form.begin_time}}</p>
            <p>结束时间： {{form.end_time}}</p>
            <p>简介： {{form.content}}</p>  
        </div>
        <div>
            <activityCreate :form="form"/>
            <b-button type="submit" @click="form.create_show=true" variant="primary">修改</b-button>
            <b-button type="submit" variant="danger">发布</b-button>
        </div>
        <div style="margin-top: 100px">
            <p style="margin-top: 200px">
            参加者
                <Table :columns="columns10" :data="data9"></Table>
            </p>
        </div>
        <div style="margin-top: 100px">
            <p>
            申请者
                <Table :columns="columns10" :data="data9"></Table>
            </p>
        </div>
    </div>
</template>

<script>

let signUpLists=[
    { 学号: "517030910200", 姓名: "xxx", 年级: "大二", 学院: "电子信息与电气工程学院", 联系方式: "13216254132", 更多信息: "http://github.com/exorteam/exort"},
    { 学号: "517030910200", 姓名: "xxx", 年级: "大二", 学院: "电子信息与电气工程学院", 联系方式: "13216254132", 更多信息: "http://github.com/exorteam/exort"},
    { 学号: "517030910200", 姓名: "xxx", 年级: "大二", 学院: "电子信息与电气工程学院", 联系方式: "13216254132", 更多信息: "http://github.com/exorteam/exort"},
    { 学号: "517030910200", 姓名: "xxx", 年级: "大二", 学院: "电子信息与电气工程学院", 联系方式: "13216254132", 更多信息: "http://github.com/exorteam/exort"},
    { 学号: "", 姓名: "", 年级: "", 学院: "", 联系方式: "", 更多信息: ""}
]

let data = [
    {
        name: 'John Brown',
        grade: 18,
        department: 'New York No. 1 Lake Park',
        job: 'Data engineer',
        interest: 'badminton',
        studentID: '5170391029194',
        book: 'Steve Jobs',
        movie: 'The Prestige',
        music: 'I Cry'
    },
    {
        name: 'Jim Green',
        grade: 25,
        department: 'London No. 1 Lake Park',
        job: 'Data Scientist',
        interest: 'volleyball',
        studentID: '5170391029198',
        book: 'My Struggle',
        movie: 'Roman Holiday',
        music: 'My Heart Will Go On'
    },
    {
        name: 'Joe Black',
        grade: 30,
        department: 'Sydney No. 1 Lake Park',
        job: 'Data Product Manager',
        interest: 'tennis',
        studentID: '5170391029191',
        book: 'Win',
        movie: 'Jobs',
        music: 'Don’t Cry'
    },
    {
        name: 'Jon Snow',
        grade: 26,
        department: 'Ottawa No. 2 Lake Park',
        job: 'Data Analyst',
        interest: 'snooker',
        studentID: '517039102919',
        book: 'A Dream in Red Mansions',
        movie: 'A Chinese Ghost Story',
        music: 'actor'
    }
]

import expandRow from './expand_table'
import activityCreate from './activity_create'
export default {
    name: "about",
    components: { expandRow , activityCreate },
    data() {
        return{
            form: {
                create_time:'2019-01-01',
                publish_time: '2019-01-02',
                last_publish_time: '2019-01-04',
                last_modify_time: '2019-01-05',
                title:'五一长跑节',
                content:'法规发生的加法基本符合规范是动漫明星的负担和顾客全五分我都发文件文件为个玩儿好计划规范地方更符合规范的外国热播的飞洒外国人范德玩儿个体和人工费等四大放弃而外国人的飞洒发生过的方潍坊人个百分点大青蛙所大我的奋斗的方式上电视看大家都老夫妇可发生了骄傲的叫法可怜死啦开发顾问费即可收到',
                state: 0, // unpublished, published 
                signup_begin_time: '2019-04-07',
                signup_end_time: '2019-04-27', 
                begin_time: '2019-05-01', 
                end_time: '2019-05-01', 
                state: 0, //  活动状态(preparing, signup, doing, done) 
                need_review: false,
                only_members: false, //  是否只有社团成员可以报名
                max_participants: 10,  //  最大参与人数 
                material_template_ids: "",  // 报名材料模板 
                participant_ids: [], // 参与者
            },
            signUpList: signUpLists,
            
            data9: data,
            columns10: [
                {
                    type: 'expand',
                    width: 50,
                    render: (h, params) => {
                        return h(expandRow, {
                            props: {
                                row: params.row
                            }
                        })
                    }
                },
                {
                    title: '学号',
                    key: 'studentID'
                },
                {
                    title: '姓名',
                    key: 'name'
                },
                {
                    title: '年级',
                    key: 'grade'
                },
                {
                    title: '学院',
                    key: 'department'
                }
            ]
        }
    }
}
</script>

<style>

</style>
