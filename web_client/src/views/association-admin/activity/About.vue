<template>
    <div>
        <Card>
            <Badge class="text" :text="stateList[activityState]" :status="statusList[activityState]"/>
            <img :src="this.curActivity.image"
                 style="width: 40%; height: 90%; margin: 10px 10px 20px 40px; float: right"/>
            <div style="margin-top: 10px">
                <p style="margin: 10px 10px 20px 40px; ">标签：
                    <Tag v-for="tag in this.curActivity.tags" :key="tag.id" :row="tag">{{ tag }}</Tag>
                </p>
                <p slot="title" style="margin: 10px 10px 20px 40px; ">名称： {{this.curActivity.title}}</p>
                <p style="margin: 10px 10px 20px 40px; ">创建时间： {{this.curActivity.createTime}} </p>
                <p style="margin: 10px 10px 20px 40px; ">发布时间： {{this.curActivity.publishTime}}</p>
                <p style="margin: 10px 10px 20px 40px; ">报名起始时间：{{this.curActivity.signupTime.time[0].start}}</p>
                <p style="margin: 10px 10px 20px 40px; ">报名截止时间：{{this.curActivity.signupTime.time[0].end}}</p>
                <p style="margin: 10px 10px 20px 40px; ">开始时间： {{this.curActivity.time.time[0].start}}</p>
                <p style="margin: 10px 10px 20px 40px; ">结束时间： {{this.curActivity.time.time[0].end}}</p>
                <p style="margin: 10px 10px 20px 40px; ">简介： {{this.curActivity.content}}</p>
                <!--<p style="margin: 10px 10px 20px 40px; ">预计参加者名单： {{this.participants}}</p>-->
                <!--<p style="margin: 10px 10px 20px 40px; ">实际参加者名单： {{this.realParticipants}}</p>-->
            </div>
            <div v-if="this.participants.length">
                <div style="margin-top: 200px">申请者</div>
                <Table border ref="selection" height="200" :columns="columns" :data="this.participants">
                    <template slot-scope="{ row }" slot="name">
                        <strong>{{ row.name }}</strong>
                    </template>
                </Table>
            </div>
            <br/>
            <div v-if="this.realParticipants.length">
                <div>参加者</div>
                <Table border ref="selection" height="200" :columns="columns" :data="this.realParticipants">
                    <template slot-scope="{ row }" slot="name">
                        <strong>{{ row.name }}</strong>
                    </template>
                </Table>
            </div>
            <br/>
            <div>
                <activityCreate :onshow="newform.onshow" v-on:close="newform.onshow=false" :initform="newform.data"/>
                <Button @click="edit" type="primary" style="margin: 10px 10px 20px 40px; ">修改</Button>
                <Button v-if="this.curActivity.publishState===1" @click="handlePublish" type="warning"
                        style="margin: 10px 10px 20px 40px; ">发布
                </Button>
                <Button v-if="this.curActivity.publishState===2" @click="handleWithdraw" type="warning"
                        style="margin: 10px 10px 20px 40px; ">撤回
                </Button>
            </div>
        </Card>
        <comment type="activity"></comment>
    </div>
</template>

<script>
    import expandRow from './ExpandTable'
    import activityCreate from './ActivityCreate'
    import comment from '../../../components/Comment'
    import {mapState, mapActions,mapMutations} from 'vuex'

    export default {
        name: "about",
        components: {expandRow, activityCreate,comment},
        data() {
            return {
                stateList: ['未发布', '报名未开始', '报名中', '未开始', '进行中', '已结束'],
                statusList: ["default", "default", "processing", "warning", "error", "success"],
                form: {},
                newform: {
                    onshow: false,
                    data: null
                },
                participantPage: {
                    pageSize: 8,
                    pageNum: 0,
                    totalSize: 0,
                },
                signUpList: [],
                columns: [
                    {
                        title: 'UserId',
                        key: 'id',
                    },
                    {
                        title: 'Name',
                        key: 'name'
                    },
                    {
                        title: 'Age',
                        key: 'age'
                    },
                    {
                        title: "StudentID",
                        key: "studentId"
                    },
                    {
                        title: "Gender",
                        key: "gender"
                    }
                ],
            }
        },
        computed: {
            ...mapState('associationAdmin/activity', [
                'curActivity',
                'curActivityId',
                'participants',
                'realParticipants'
            ]),
            activityState: function () {
                if (this.curActivity.publishState === 1) {
                    return 0;
                } else if (this.curActivity.signupState !== 2) {
                    return this.curActivity.signupState + 1
                } else {
                    return this.curActivity.signupState + 3
                }
            },
        },
        methods: {
            ...mapActions('associationAdmin/activity', [
                'changeActivityState',
                'getCurActivity',
                'getParticipants',
                'getRealParticipants'
            ]),
            edit(){
                this.changeOnShow();
            },
            changeOnShow(){
              this.newform.onshow=!this.newform.onshow;
            },
            handlePublish() {
                let data = {type: "publish"};
                this.changeActivityState(data).then(() => {
                    this.getCurActivity(this.curActivity.id);
                }).catch(error => {
                    console.log(error);
                });
            },
            handleWithdraw() {
                let data = {type: "withdraw"};
                this.changeActivityState(data).then(() => {
                    this.getCurActivity(this.curActivity.id);
                }).catch(error => {
                    console.log(error);
                });
            },
            // setData(value) {
            //     if (value.publishState == 0) {
            //         this.activityState = 0
            //     } else if (value.signupState != 2) {
            //         this.activityState = value.signupState + 1
            //     } else {
            //         this.activityState = value.signupState + 3
            //     }
            //
            //     this.newform.data.title = value.title
            //     this.newform.data.content = value.content
            //     this.newform.data.ifReview = value.ifReview
            //     this.newform.data.ifOnlyMem = value.ifOnlyMem
            //     this.newform.data.maxParticipants = value.maxParticipants
            //     this.newform.data.materials = value.materials
            //     this.newform.data.image = value.image
            //
            //     let tag = {
            //         tag_show: false,
            //         tagList: value.tags
            //     }
            //     this.newform.data.tag = tag
            //
            //     let time = {
            //         type: 0,
            //         time: [value.time.time[0].start, value.time.time[0].end]
            //     }
            //     this.newform.data.time = time
            //     let signupTime = {
            //         type: 0,
            //         time: [value.signupTime.time[0].start, value.signupTime.time[0].end]
            //     }
            //     this.newform.data.signupTime = signupTime
            // },
            ...mapMutations('associationAdmin/activity', [
                'setCurActivityId'
            ]),
        },
        created() {
            this.setCurActivityId(this.$route.params.id);
            this.getCurActivity(this.$route.params.id);
        },
    }
</script>

<style>

</style>
