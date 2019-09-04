<template>
    <Modal
            v-model="onshow"
            @on-ok="info_ok"
            @on-cancel="cancel"
            loading :closable="false"
    >
        <Form :model="formData" :label-width="112">
            <FormItem label="活动标题">
                <Input v-model="formData.title"/>
            </FormItem>
            <FormItem label="报名时间">
                <DatePicker v-model="signupTime" type="datetimerange" format="yyyy-MM-dd HH:mm"
                            placeholder="选择日期和时间" style="width: 300px"></DatePicker>
            </FormItem>
            <!-- 当前只有一种时间实现 -->
            <!-- <FormItem label="选择活动时间类型:">
                <Select v-model="form.data.time.type" style="width:200px">
                    <Option v-for="item in timeTypeList" :value="item.value" :key="item.value">{{ item.text }}</Option>
                </Select>
            </FormItem> -->

            <FormItem label="活动时间">
                <DatePicker v-model="activityTime" type="datetimerange" format="yyyy-MM-dd HH:mm"
                            placeholder="选择日期和时间" style="width: 300px"></DatePicker>
            </FormItem>

            <FormItem label="所属社团:">
                <!--<b-form-select v-model="associationIndex" :options="associationList" style="width: 200px; height: 40px; "></b-form-select>-->
                <Select v-model="associationIndex" style="width:200px">
                    <Option v-for="item in associationList" :value="item.value" :key="item.value">{{ item.text }}
                    </Option>
                </Select>
            </FormItem>
            <FormItem label="报名是否需要审核:">
                <Select v-model="formData.ifReview" style="width:200px">
                    <Option v-for="item in ifReviewSelectList" :value="item.value" :key="item.value">{{
                        item.text }}
                    </Option>
                </Select>
            </FormItem>
            <FormItem label="是否仅社团或组织成员可以参加:">
                <Select v-model="formData.ifOnlyMem" style="width:200px">
                    <Option v-for="item in ifOnlyMemSelectList" :value="item.value" :key="item.value">{{
                        item.text }}
                    </Option>
                </Select>
            </FormItem>
            <FormItem label="最大人数">
                <InputNumber :min="0" :step="1" v-model="formData.numberOfParticipants"></InputNumber>
            </FormItem>
            <FormItem label="上传活动宣传图">
                <div>
                    <!--<b-form-file v-model="form.data.image" ref="file-input" style="width: 310px; "></b-form-file>-->
                    <input type="file" accept="image/jpeg,image/png,image/gif" @change="chooseImage"/>
                    <!--<Button @click="clearFile" style="height: 33px; margin-bottom: 8px">clear</Button>-->
                    <img v-if="formData.image" :src="formData.image" style="width: 360px; height: 200px"/>
                </div>
            </FormItem>
            <FormItem label="活动简介">
                <Input v-model="formData.content" type="textarea" :autosize="{minRows: 2,maxRows: 5}"
                       placeholder=""></Input>
            </FormItem>
            <!--<FormItem label="选择标签">-->
            <!--<Button @click="formData.tag.tag_show=true" style="width: 80px">选择标签</Button>-->
            <!--<TagChoose :tag="formData.tag"/>-->
            <!--<div v-if="formData.tag.length" style="display:inline">-->
            <!--<Tag v-for="tag in formData.tag" :key="tag.id" :row="tag">{{ tag }}</Tag>-->
            <!--</div>-->
            <!--</FormItem>-->
        </Form>
    </Modal>
</template>

<script>
    import TimeRange from './TimeRange'
    import TagChoose from '../../../components/TagChoose'
    import {mapActions, mapState} from 'vuex'

    let ifReviewSelectLists = [
        {
            value: 0,
            text: '不需要'
        },
        {
            value: 1,
            text: '需要'
        }
    ];

    let ifOnlyMemSelectLists = [
        {
            value: 0,
            text: '不允许'
        },
        {
            value: 1,
            text: '允许'
        }
    ];

    let timeTypeList = [
        {
            value: 0,
            text: '开展一次，时间连续'
        },
        {
            value: 1,
            text: '多次开展，日期不连续'
        },
        {
            value: 2,
            text: '连续多天开展，每天时间固定相同'
        },
        {
            value: 3,
            text: '多天开展，每天时间不相同'
        }
    ]

    export default {
        name: 'activity_create',
        components: {TimeRange, TagChoose},
        props: ['onshow', 'initform'],
        data() {
            return {
                ifReviewSelectList: ifReviewSelectLists,
                ifOnlyMemSelectList: ifOnlyMemSelectLists,

                initdata: {},

                fileList: [],
                associationIndex: "",
                associationList: [{
                    value: "",
                    text: "请选择所属社团"
                }],
                img: "",
                imageFile: null,
                signupTime: null,
                activityTime: null,
                // tag:{
                //     tag_show: false,
                //     tagList:[],
                // }
                formData: {
                    associationIds: [],
                    title: null,
                    content: null,
                    signupTime: {
                        type: 0,
                        time: [
                            {
                                start: null,
                                end: null
                            }
                        ]
                    },
                    time: {
                        type: 0,
                        time: [
                            {
                                start: null,
                                end: null
                            }
                        ]
                    },
                    ifReview: 0,
                    ifOnlyMem: 0,
                    numberOfParticipants: 0,
                    materialTemplateIds: [],
                    tags: [],
                    image: ""
                }
            }
        },
        computed: {
            ...mapState('associationAdmin/activity', [
                'curActivity'
            ]),
        },
        methods: {
            ...mapActions('associationAdmin/activity', [
                'getCurActivity',
                'createActivtity',
                'updateActivity'
            ]),
            cancel(){
              this.$emit('changeOnShow');
            },
            info_ok() {
                // console.log(this.form)
                if (this.initdata) {
                    //修改
                    this.initdata.signupTime.time[0].start = this.signupTime[0];
                    this.initdata.signupTime.time[0].end = this.signupTime[1];
                    this.initdata.time.time[0].start = this.activityTime[0];
                    this.initdata.time.time[0].end = this.activityTime[1];

                    this.initdata.title = this.formData.title;
                    this.initdata.content = this.formData.content;

                    this.initdata.ifReview = (this.formData.ifReview === 1);
                    this.initdata.ifOnlyMem = (this.formData.ifOnlyMem === 1);

                    this.initdata.maxParticipants = this.formData.numberOfParticipants;
                    this.initdata.materialTemplateIds = this.formData.materialTemplateIds;
                    this.initdata.tags = this.formData.tags;


                    if (this.imageFile) {
                        let reader = new FileReader();
                        reader.readAsDataURL(this.imageFile);
                        reader.onload = (e) => {
                            this.initdata.image = e.target.result;

                            this.updateActivity({
                                activityInfo: this.initdata,
                                activityId: this.$route.params.id
                            }).then(()=>{
                                this.$router.go(0);
                            });
                        }
                    } else {
                        this.updateActivity({
                            activityInfo: this.initdata,
                            activityId: this.$route.params.id
                        }).then(()=>{
                            this.$router.go(0);
                        });
                    }

                } else {
                    //新建
                    this.formData.signupTime.time[0].start = this.signupTime[0];
                    this.formData.signupTime.time[0].end = this.signupTime[1];
                    this.formData.time.time[0].start = this.activityTime[0];
                    this.formData.time.time[0].end = this.activityTime[1];

                    let reader = new FileReader();
                    reader.readAsDataURL(this.imageFile);
                    reader.onload = (e) => {
                        this.formData.image = e.target.result;

                        this.createActivtity(this.formData);
                    }
                }
            },
            chooseImage(event) {
                let files = event.target.files || event.dataTransfer.files;
                this.imageFile = files[0];
            },
            setInitData(initData) {
                // console.log(initData);
                if (this.$route.params.id) {
                    this.formData.title = initData.title;
                    this.formData.content = initData.content;
                    this.signupTime = new Array();
                    this.signupTime.push(initData.signupTime.time[0].start, initData.signupTime.time[0].end);
                    this.activityTime = new Array();
                    this.activityTime.push(initData.time.time[0].start, initData.time.time[0].end);

                    this.formData.ifReview = (initData.ifReview) ? 1 : 0;
                    this.formData.ifOnlyMem = (initData.ifOnlyMem) ? 1 : 0;
                    this.formData.numberOfParticipants = initData.maxParticipants;
                    this.formData.materialTemplateIds = initData.materialTemplateIds;
                    this.formData.tags = initData.tags;
                    this.formData.image = initData.image;
                } else {
                    this.formData = {
                        associationIds: [],
                        title: null,
                        content: null,
                        signupTime: {
                            type: 0,
                            time: [
                                {
                                    start: null,
                                    end: null
                                }
                            ]
                        },
                        time: {
                            type: 0,
                            time: [
                                {
                                    start: null,
                                    end: null
                                }
                            ]
                        },
                        ifReview: 0,
                        ifOnlyMem: 0,
                        numberOfParticipants: 0,
                        materialTemplateIds: [],
                        tags: [],
                        image: ""
                    }
                }
            },
        },
        mounted() {
            if (this.$route.params.id) {
                this.getCurActivity(this.$route.params.id).then(() => {
                    this.initdata = this.curActivity;
                    this.setInitData(this.initdata);
                });
            } else {
                this.setInitData(this.initdata);
            }
            // if(this.form.data){
            //     this.setInitData(this.form.data);
            // }
        },
    }
</script>
