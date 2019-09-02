<template>
    <div>
        <Card>
            <Badge class="text" :text="stateList[activityState]" :status="statusList[activityState]"/>
            <img :src="form.image" style="width: 40%; height: 90%; margin: 10px 10px 20px 40px; float: right"/>
            <div style="margin-top: 10px;height: 600px">
                <p style="margin: 10px 10px 20px 40px; ">标签：
                    <Tag v-for="tag in form.tags" :key="tag.id" :row="tag">{{ tag }}</Tag>
                </p>
                <p slot="title" style="margin: 10px 10px 20px 40px; ">名称： {{form.title}}</p>
                <p style="margin: 10px 10px 20px 40px; ">创建时间： {{showTime.createTime}} </p>
                <p style="margin: 10px 10px 20px 40px; ">发布时间： {{showTime.publishTime}}</p>
                <p style="margin: 10px 10px 20px 40px; ">报名起始时间：{{showTime.signupTimeStart}}</p>
                <p style="margin: 10px 10px 20px 40px; ">报名截止时间：{{showTime.signupTimeEnd}}</p>
                <p style="margin: 10px 10px 20px 40px; ">开始时间： {{showTime.timeStart}}</p>
                <p style="margin: 10px 10px 20px 40px; ">结束时间： {{showTime.timeEnd}}</p>
                <p style="margin: 10px 10px 20px 40px; ">简介： {{form.content}}</p>

            </div>
            <div>
                <activityCreate :form="newform"/>
                <Button  @click="newform.onshow=true" type="primary" style="margin: 10px 10px 20px 40px; ">修改</Button>
                <Button v-if="!form.publishState" @click="handlePublish" type="warning" style="margin: 10px 10px 20px 40px; ">发布</Button>
                <Button v-if="form.publishState" @click="handleWithdraw" type="warning" style="margin: 10px 10px 20px 40px; ">撤回</Button>
            </div>
            <!-- <div v-if="form.participants.length" style="margin-top: 100px">
                <p style="margin-top: 200px">
                参加者
                    <Table :columns="attribute" :data="participants"></Table>
                </p>
            </div> -->
            <!-- <div v-if="form.realParticipants.length" style="margin-top: 50px">
                <p>
                申请者
                    <Table :columns="attribute" :data="realParticipants"></Table>
                </p>
            </div> -->
        </Card>
    </div>
</template>

<script>
import expandRow from './ExpandTable'
import activityCreate from './ActivityCreate'
import Axios from 'axios';

export default {
    name: "about",
    components: { expandRow , activityCreate },
    data() {
        return{
            stateList: [ '未发布', '报名未开始', '报名中', '未开始', '进行中', '已结束' ],
            statusList: [ "default", "default", "processing", "warning", "error", "success" ],
			form:{},
			newform:{
            	onshow: false,
                data: {}
            },
            showTime:{
                createTime: "",
                publishTime:"",
                signupTimeStart: "",
                signupTimeEnd: "",
                timeStart: "",
                timeEnd: ""
            },
            participantPage:{
                pageSize: 8,
                pageNum: 0,
                totalSize: 0,
            },
            activityState: 0,
            signUpList: [],
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
                {
                    title: '学院',
                    key: 'department'
                }
            ]
        }
    },
    methods: {
        handlePublish(){
            let data = { type:"publish" }
            let activityId = (this.$store.getters.get_activityid).toString()
			this.axios({
                method:"put",
                url: "/activities/"+activityId + "/state",
                data: data
            })
            .then(response => {
                console.log(response.data)
                this.$router.go(0)
            })
            .catch(e => {
                console.log(e)
            })
        },
        handleWithdraw(){
            let data = {type:"withdraw"}
            let activityId = (this.$store.getters.get_activityid).toString()
			this.axios({
                method: "put",
                url: "/activities/"+activityId + "/state",
                data: data
            })
            .then(response => {
                console.log(response.data)
                this.$router.go(0)
            })
            .catch(e => {
                console.log(e)
            })
        },
        setShowTime(value){
            this.showTime.createTime = (new Date(this.form.createTime)).toLocaleString(),
            this.showTime.publishTime = (new Date(this.form.publishTime)).toLocaleString(),
            this.showTime.signupTimeStart = (new Date(this.form.signupTime.time[0].start)).toLocaleString(),
            this.showTime.signupTimeEnd = (new Date(this.form.signupTime.time[0].end)).toLocaleString(),
            this.showTime.timeStart = (new Date(this.form.time.time[0].start)).toLocaleString(),
            this.showTime.timeEnd = (new Date(this.form.time.time[0].end)).toLocaleString()
            console.log(this.showTime)
        },
        setData(value) {
            if (value.publishState == 0) {
                this.activityState = 0
            } else if (value.signupState != 2) {
                this.activityState = value.signupState + 1
            } else {
                this.activityState = value.signupState + 3
            }

            this.newform.data.title = value.title
			this.newform.data.content = value.content
            this.newform.data.ifReview = value.ifReview
            this.newform.data.ifOnlyMem = value.ifOnlyMem
            this.newform.data.maxParticipants = value.maxParticipants
            this.newform.data.materials = value.materials
            this.newform.data.image = value.image

            let tag = {
                tag_show : false,
                tagList : value.tags
            } 
			this.newform.data.tag = tag

            let time = {
                type:0, 
                time:[value.time.time[0].start, value.time.time[0].end]
            }
            this.newform.data.time= time
            let signupTime = {
                type:0,
                time:[value.signupTime.time[0].start, value.signupTime.time[0].end]
            }
            this.newform.data.signupTime = signupTime
        },
        getActivity(){
            let data = (this.$store.getters.get_activityid).toString()
            //请求activity信息
            this.axios({
                method:"get",
                url: "/activities/" + data,
            })
            .then(response => {
                this.form = response.data.data
                this.setData(this.form)
                this.setShowTime(this.form)
            })
            .catch(e => {
                console.log(e)
            })
        },
        getParticipants(){
            let data = (this.$store.getters.get_activityid).toString()
            //请求activity的participants
            this.axios({
                method:"post",
                url: "/activities/"+data+"/participants/message",
                params: {
                    'pagesize': this.participantPage.pageSize,
                    'pagenum': this.participantPage.pageNum,
                    'sortby': "0",
                },
                data:{
                    userId: 0
                }
            })
            .then(response => {
                console.log(response.data)
                this.participants = response.data.data
            })
            .catch(e => {
                console.log(e)
            })
        },
        getRealParticipants(){
            let data = (this.$store.getters.get_activityid).toString()
            //请求activity的participants
            Axios
                .get('/activities/' + data+"/realparticipants")
                .then(response => {
                    this.realParticipants = response.data.data
                })
                .catch(e => {
                    console.log(e)
                })
        }
    },
    mounted() {
        this.getActivity()
        // this.getParticipants()
        // this.getRealParticipants()
    },
}
</script>

<style>

</style>
