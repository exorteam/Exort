<template>
    <div>
        <Badge class="text" :text="stateList[activityState]" :status="statusList[activityState]"/>
        <!-- <img :src="form.image" style="width: 40%; height: 400px; margin: 10px 10px 20px 40px; float: right"/> -->
        <div style="margin-top: 10px; height: 600px">
            <p slot="title">名称： {{form.title}}</p>
            <p>创建时间： {{form.createTime}} </p>
            <p>发布时间： {{form.publishTime}}</p>
            <p>报名起始时间：{{form.signupTime.time[0].start}}</p>
            <p>报名截止时间：{{form.signupTime.time[0].end}}</p>
            <p>开始时间： {{form.time.time[0].start}}</p>
            <p>结束时间： {{form.time.time[0].end}}</p>
            <p>简介： {{form.content}}</p>
        </div>
        <div>
            <activityCreate :form="newform"/>
            <b-button @click="newform.onshow=true" variant="primary">修改</b-button>
            <b-button v-if="form.publishState" @click="handlePublish" type="submit" variant="danger">发布</b-button>
            <b-button v-if="!form.publishState" @click="handleWithdraw" type="submit" variant="danger">发布</b-button>
        </div>
        <div style="margin-top: 100px">
            <p style="margin-top: 200px">参加者</p><Table :columns="attribute" :data="realParticipants"></Table>
        </div>
        <div style="margin-top: 50px">
            <p>申请者</p><Table :columns="attribute" :data="participants"></Table>
        </div>
    </div>
</template>

<script>

    let signUpLists = [
        {
            学号: "517030910200",
            姓名: "xxx",
            年级: "大二",
            学院: "电子信息与电气工程学院",
            联系方式: "13216254132",
            更多信息: "http://github.com/exorteam/exort"
        },
        {
            学号: "517030910200",
            姓名: "xxx",
            年级: "大二",
            学院: "电子信息与电气工程学院",
            联系方式: "13216254132",
            更多信息: "http://github.com/exorteam/exort"
        },
        {
            学号: "517030910200",
            姓名: "xxx",
            年级: "大二",
            学院: "电子信息与电气工程学院",
            联系方式: "13216254132",
            更多信息: "http://github.com/exorteam/exort"
        },
        {
            学号: "517030910200",
            姓名: "xxx",
            年级: "大二",
            学院: "电子信息与电气工程学院",
            联系方式: "13216254132",
            更多信息: "http://github.com/exorteam/exort"
        },
        {学号: "", 姓名: "", 年级: "", 学院: "", 联系方式: "", 更多信息: ""}
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
    import Axios from 'axios';

    export default {
        name: "about",
        components: {expandRow, activityCreate},
        data() {
            return {
                stateList: ['未发布', '报名未开始', '报名中', '未开始', '进行中', '已结束'],
                statusList: ["default", "default", "processing", "warning", "error", "success"],
                form: {},
                newform: {
                    onshow: false
                },
                onshow: false,
                activityState: 0,
                signUpList: signUpLists,

                participants: [],
                realParticipants: [],
                attribute: [
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
                ],
                signUpList: signUpLists,


            }
        },
        methods: {
            handlePublish() {
                let data = {type: "publish"}
                let activityId = (this.$store.getters.get_activityid).toString()
                Axios
                    .post("http://202.120.40.8/activities/" + activityId, data)
                    .then(response => {
                        console.log(response.data)
                    })
                    .catch(e => {
                        console.log(e)
                    })
            }
            ,
            handleWithdraw() {
                let data = {type: "withdraw"}
                let activityId = (this.$store.getters.get_activityid).toString()
                Axios
                    .post("http://202.120.40.8/activities/" + activityId, data)
                    .then(response => {
                        console.log(response.data)
                    })
                    .catch(e => {
                        console.log(e)
                    })
            }
            ,
            setData(value) {
                if (value.publishState == 0) {
                    this.activityState = 0
                } else if (value.signupState != 2) {
                    this.activityState = value.signupState + 1
                } else {
                    this.activityState = value.signupState + 3
                }

                this.newform.title = value.title
                this.newform.content = value.content
                this.newform.signupTime = value.signupTime
                this.newform.time = value.time
                this.newform.ifReview = value.ifReview
                this.newform.ifOnlyMem = value.ifOnlyMem
                this.newform.maxParticipants = value.maxParticipants
                this.newform.materials = value.materials
                this.newform.tags = value.tags
            }
            ,
            getActivity() {
                let data = (this.$store.getters.get_activityid).toString()
                //请求activity信息
                Axios
                    .get('http://202.120.40.8:30727/activities/' + data)
                    .then(response => {
                        this.form = response.data.data
                        console.log(this.form)
                        this.setData(this.form)
                    })
                    .catch(e => {
                        console.log(e)
                    })
            }
            ,
            getParticipants() {
                let data = (this.$store.getters.get_activityid).toString()
                //请求activity的participants
                Axios
                    .get('http://202.120.40.8:30727/activities/' + data + "/participants")
                    .then(response => {
                        this.participants = response.data.data
                    })
                    .catch(e => {
                        console.log(e)
                    })
            }
            ,
            getRealParticipants() {
                let data = (this.$store.getters.get_activityid).toString()
                //请求activity的participants
                Axios
                    .get('http://202.120.40.8:30727/activities/' + data + "/realparticipants")
                    .then(response => {
                        this.realParticipants = response.data.data
                    })
                    .catch(e => {
                        console.log(e)
                    })
            }
        }
    }
</script>

<style>

</style>
