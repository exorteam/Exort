<template>
    <Modal v-model="form.onshow" @on-ok="info_ok" @on-cancel="info_cancel" loading :closable="false">
        <Form v-model="form" :label-width="112">
            <FormItem label="活动标题">
                <Input v-model="form.title"/>
            </FormItem>
            <FormItem label="报名时间">
                <DatePicker type="daterange" v-model="signupDate" placeholder="yyyy-mm-dd"></DatePicker>            
                <TimePicker format="HH:mm" v-model="signupTime" type="timerange" placement="bottom-end" placeholder="Select time" style="width: 172px"></TimePicker>
            </FormItem>
            <!-- <FormItem label="选择活动时间类型:">
                <Select v-model="form.timeType" style="width:200px">
                    <Option v-for="item in timeTypeList" :value="item.value" :key="item.value">{{ item.text }}</Option>
                </Select>
            </FormItem>
            <FormItem label="活动时间">
                <div>
                    <DatePicker v-model="form.time" type="daterange" placeholder="yyyy-mm-dd"></DatePicker>
                    <TimePicker format="HH:mm" type="timerange" placement="bottom-end" placeholder="Select time" style="width: 172px"></TimePicker>
                </div>
            </FormItem> -->
            <FormItem label="报名是否需要审核:">
                <Checkbox v-model="form.ifReview"/>
            </FormItem>
            <FormItem label="是否仅社团或组织成员可以参加:">
                <Checkbox v-model="form.ifOnlyMem"/>
            </FormItem>
            <FormItem label="最大人数">
                <Input v-model="form.maxParticipants"/>
            </FormItem>
            <FormItem label="上传活动宣传图">
                <Upload multiple type="drag" action="//jsonplaceholder.typicode.com/posts/" style="text-align: center;">
                    <div style="padding: 20px 0">
                        <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
                        <p>点击或将活动宣传图拖拽到这里上传</p>
                    </div>
                </Upload>
            </FormItem>
            <FormItem label="活动简介">
                <Input v-model="form.content"/>
            </FormItem>
            <!-- <FormItem label="报名材料">
                <Input v-model="form.materials"/>
            </FormItem> -->
        </Form>
    </Modal>
</template>

<script>
import Axios from 'axios';
let timeTypeLists=[
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
    props: {
            form: Object
    },
    data(){
            return {
                    signupTime:'',
                    signupDate:'',
                    timeTypeList: timeTypeLists
            }
    },
    methods: {
        info_ok(){
                this.form.onshow = false
                if(this.signupTime[0]!=""){
                    let a = this.signupTime[0].split(':')
                    let b = this.signupTime[1].split(':')
                    console.log(a,b)
                    this.signupDate[0].setHours(parseInt(a[0]), parseInt(a[1]), 0, 0)
                    this.signupDate[1].setHours(parseInt(b[0]), parseInt(b[1]), 0, 0)
                    console.log(this.signupDate)
                }
                let data={
                    title: this.form.title,
                    content: this.form.content,
                    signupTime: {
                        type: 0,
                        time:[{
                            start: this.signupDate[0],
                            end: this.signupDate[1],
                        }]
                    },
                    time: {
                        type: 0, 
                        time:[{
                            start: "",
                            end: "",
                        }]
                    },
                    ifReview: this.form.ifReview,
                    ifOnlyMem: this.form.ifOnlyMem,
                    maxParticipants: this.form.maxParticipants,
                    materials: this.form.materials,
                    tags: this.form.tags
                }
                Axios
                    .post('http://202.120.40.8:30727/activities', data)
                    .then(response => {
                        console.log("Successfully!")
                        console.log(response.data.data)
                    })
                    .catch(e => {
                        console.log(e)
                    })
                console.log(data)
        },
        info_cancel(){
                this.form.onshow = false
        }
    },
}
</script>