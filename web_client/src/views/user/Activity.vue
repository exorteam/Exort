<template>
    <div>
        <Card>
            <!-- 搜索方式 -->
            <div>
                <Card>
                    <p slot="title">
                        搜索筛选
                    </p>
                    <div style="margin-top: 15px; margin-left: 20px">
                        <Row>
                            <Col span="8">创建时间<!-- 活动创建时间搜索 -->
                                <DatePicker v-model="select.createTime" type="daterange" split-panels
                                            placeholder="Select date"
                                            style="width: 180px; margin-right: 45px"></DatePicker>
                            </Col>
                            <Col span="8">
                                报名时间<!-- 活动报名时间搜索 -->
                                <DatePicker v-model="select.signupTime" type="daterange" split-panels
                                            placeholder="Select date"
                                            style="width: 180px; margin-right: 45px"></DatePicker>
                            </Col>
                            <Col span="8">
                                开始时间<!-- 活动开始时间搜索 -->
                                <DatePicker v-model="select.startTime" type="daterange" split-panels
                                            placeholder="Select date"
                                            style="width: 180px; margin-right: 45px"></DatePicker>
                            </Col>
                        </Row>
                        <br/>
                        <Row>
                            <Col span="8">
                                报名状态<!-- 根据活动报名状态搜索 -->
                                <Select v-model="select.signupState" style="width:200px">
                                    <Option v-for="item in signupSelectList" :value="item.value" :key="item.value">{{
                                        item.text }}
                                    </Option>
                                </Select>
                            </Col>
                            <Col span="8">
                                活动状态<!-- 根据活动状态搜索 -->
                                <!--<b-form-select v-model="select.startSelected" :options="startSelectList"-->
                                <!--style="width: 120px; height: 32px; margin-right: 25px"></b-form-select>-->
                                <Select v-model="select.state" style="width:200px">
                                    <Option v-for="item in startSelectList" :value="item.value" :key="item.value">{{
                                        item.text }}
                                    </Option>
                                </Select>
                            </Col>
                            <Col span="8">
                                发布状态<!-- 根据活动发布状态搜索 -->
                                <!--<b-form-select v-model="select.publishSelected" :options="publishSelectList"-->
                                <!--style="width: 120px; height: 32px; margin-right: 45px"></b-form-select>-->
                                <Select v-model="select.publishState" style="width:200px">
                                    <Option v-for="item in publishSelectList" :value="item.value" :key="item.value">{{
                                        item.text }}
                                    </Option>
                                </Select>
                            </Col>
                        </Row>
                    </div>
                    <div style="margin-top:15px; margin-left: 20px">
                        <Row>
                            <Col span="12">
                                发起社团<!-- 直接输入社团名称 -->
                                <Input v-model="select.associationId" placeholder="请输入社团ID"
                                       style="width: 300px; margin-right: 40px"/>
                            </Col>
                            <Col span="12">
                                关键词<!-- keyword -->
                                <Input v-model="select.keyword" placeholder="请输入搜索关键词"
                                       style="width: 300px; margin-right: 40px"/>
                            </Col>
                        </Row>
                        <br/>
                        <Row>
                            <Col span="8">
                                是否需要审核
                                <!--<b-form-select v-model="select.ifReview" :options="ifReviewSelectList"-->
                                <!--style="width: 120px; height: 32px; margin-right: 45px"></b-form-select>-->
                                <Select v-model="select.ifReview" style="width:200px">
                                    <Option v-for="item in ifReviewSelectList" :value="item.value" :key="item.value">{{
                                        item.text }}
                                    </Option>
                                </Select>
                                <!--<Checkbox v-model="select.ifReview" style="margin-right: 40px"/>-->
                            </Col>
                            <Col span="8">
                                是否允许非本组织成员参加
                                <!--<b-form-select v-model="select.ifOnlyMem" :options="ifOnlyMemSelectList"-->
                                <!--style="width: 120px; height: 32px; margin-right: 45px"></b-form-select>-->
                                <Select v-model="select.ifOnlyMem" style="width:200px">
                                    <Option v-for="item in ifOnlyMemSelectList" :value="item.value" :key="item.value">{{
                                        item.text }}
                                    </Option>
                                </Select>
                                <!--<Checkbox v-model="select.ifOnlyMem" style="margin-right: 40px"/>-->
                            </Col>
                            <Col span="8">
                                <div style="display:inline">
                                    <Button @click="tag.tag_show=true" style="width: 80px">选择标签</Button>
                                    <TagChoose :tag="tag"/>
                                </div>
                                <div v-if="tag.tagList.length" style="display:inline">
                                    <Tag v-for="tag in tag.tagList" :key="tag.id" :row="tag">{{ tag }}</Tag>
                                </div>
                            </Col>
                        </Row>
                        <Row>
                            <!-- 按照条件进行搜索 -->
                            <Button type="info" label="large" @click="handleSelect"
                                    style="float:right; margin-right:29px">搜索
                            </Button>
                        </Row>
                    </div>
                </Card>
            </div>

            <Divider style="width: 100%;"/>

            <!-- 活动排列 -->
            <div>
                <div span="11" style="display: flex;justify-content: space-around;flex-wrap: wrap">
                    <Card v-for="card in this.activities.cardList" :key="card.id" :row="card"
                          style="width: 465px; margin: 30px;"
                          :bordered="false">
                        <div style="height: 30px">
                            <p style="float: left" slot="title">{{card.title}}</p>
                            <Badge style="float: right" :text="stateList[card.activityState]"
                                   :status="statusList[card.activityState]"/>
                        </div>
                        <Divider/>
                        <Button @click="to_detail(card.id)" type="text" style="height: 280px; width: 100%;">
                            <img :src="card.image" style="height: 280px; width: 100%;"/>
                        </Button>
                        <Divider/>
                        <p>开始时间: {{card.time.time[0].start}}</p>
                        <p>结束时间: {{card.time.time[0].end}}</p>
                        <p>简介:{{card.content}}</p>
                    </Card>
                </div>
                <div>
                    <Page show-elevator show-total :total="page.totalSize" :current.sync="page.pageNum+1"
                          :page-size.sync="page.pageSize" simple @on-change="changePage"
                          style="text-align: center;"></Page>
                </div>
            </div>
        </Card>
    </div>
</template>

<script>
    let publishSelectLists = [
        {
            value: 0,
            text: '--',
        },
        {
            value: 1,
            text: '未发布'
        },
        {
            value: 2,
            text: '已发布'
        }
    ];

    let signupSelectLists = [
        {
            value: 0,
            text: '--',
        },
        {
            value: 1,
            text: '报名未开始'
        },
        {
            value: 2,
            text: '报名中'
        },
        {
            value: 3,
            text: '报名已结束'
        }
    ];

    let startSelectLists = [
        {
            value: 0,
            text: '--',
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
    ];

    let ifReviewSelectLists = [
        {
            value: 0,
            text: '--',
        },
        {
            value: 1,
            text: '不需要'
        },
        {
            value: 2,
            text: '需要'
        }
    ];

    let ifOnlyMemSelectLists = [
        {
            value: 0,
            text: '--',
        },
        {
            value: 1,
            text: '不允许'
        },
        {
            value: 2,
            text: '允许'
        }
    ];

    import TagChoose from '../../components/TagChoose'
    import {mapMutations, mapState, mapActions} from 'vuex'

    export default {
        name: 'activity',
        components: {TagChoose},
        data() {
            return {
                stateList: ['未发布', '报名未开始', '报名中', '未开始', '进行中', '已结束'],
                statusList: ["default", "default", "processing", "warning", "error", "success"],
                currentAvtivityId: "",
                publishSelected: 0,
                publishSelectList: publishSelectLists,
                signupSelected: 0,
                signupSelectList: signupSelectLists,
                startSelected: 0,
                startSelectList: startSelectLists,
                ifReview: 0,
                ifReviewSelectList: ifReviewSelectLists,
                ifOnlyMem: 0,
                ifOnlyMemSelectList: ifOnlyMemSelectLists,

                page: {
                    totalSize: 0,
                    pageNum: 0,
                    pageSize: 6
                },
                select: {
                    associationId: null,
                    keyword: null,
                    createTime: null,
                    signupTime: null,
                    startTime: null,
                    publishState: 0,
                    signupState: 0,
                    state: 0,
                    ifReview: 0,
                    ifOnlyMem: 0
                },
                form: {
                    onshow: false,
                    data:null
                },
                tag: {
                    tag_show: false,
                    tagList: [],
                }
            }
        },
        computed: {
            ...mapState('associationAdmin/activity', [
                'activities'
            ])
        },
        methods: {
            ...mapActions('associationAdmin/activity', [
                'updateCardlist',
                'getCurActivity'
            ]),
            ...mapMutations('associationAdmin/activity', [
                'setCurActivityId'
            ]),
            to_detail(value) {
                this.getCurActivity(value);
                this.setCurActivityId(value);
                this.$router.replace({path: "/activities/"+value})
            },
            changePage(e){
                this.page.pageNum=e-1;
                this.handleSelect();
            },
            handleSelect() {
                let data = this.select;
                data.tags = this.tag.tagList;
                this.select.associationId

                if (data.createTime != null && data.createTime.length == 2) {
                    if (data.createTime[0] == "") {
                        data.createTime = null
                    } else {
                        let create = {
                            start: data.createTime[0],
                            end: data.createTime[1]
                        }
                        data.createTime = create
                    }
                }
                if (data.signupTime != null && data.signupTime.length == 2) {
                    if (data.signupTime[0] == "") {
                        data.signupTime = null
                    } else {
                        let signup = {
                            start: data.signupTime[0],
                            end: data.signupTime[1]
                        }
                        data.signupTime = signup
                    }
                }
                if (data.startTime != null && data.startTime.length == 2) {
                    if (data.startTime[0] == "") {
                        data.startTime = null
                    } else {
                        let time = {
                            start: data.startTime[0],
                            end: data.startTime[1]
                        }
                        data.startTime = time
                    }
                }
                if (data.ifReview === true) {
                    data.ifReview = 2
                } else if (data.ifReview === false) {
                    data.ifReview = 1
                }
                if (data.ifOnlyMem === true) {
                    data.ifOnlyMem = 2
                } else if (data.ifOnlyMem === false) {
                    data.ifOnlyMem = 1
                }
                if (data.keyword == "") {
                    data.keyword = null
                }

                // console.log(data)
                this.updateCardlist({
                    select: data,
                    pageSize: this.page.pageSize,
                    pageNum: this.page.pageNum
                }).then().catch(error => {
                    console.log(error);
                }).then(() => {
                    this.page.pageSize = this.activities.pageSize;
                    this.page.totalSize = this.activities.totalSize;
                    this.page.pageNum = this.activities.pageNum;
                });
            }
        },
        mounted() {
            this.handleSelect();
        },
    }
</script>
