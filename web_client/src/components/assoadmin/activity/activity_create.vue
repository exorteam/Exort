<template>
    <Modal v-model="form.onshow" @on-ok="info_ok" @on-cancel="info_cancel" loading :closable="false">
        <Form :model="form.data" :label-width="112">
            <FormItem label="活动标题">
                <Input v-model="form.data.title"/>
            </FormItem>
            <FormItem label="报名时间">
                <Date-picker v-model="form.data.signupTime.time" type="datetimerange" format="yyyy-MM-dd HH:mm" placeholder="选择日期和时间" style="width: 300px"></Date-picker>
            </FormItem>
            <!-- 当前只有一种时间实现 -->
            <!-- <FormItem label="选择活动时间类型:">
                <Select v-model="form.data.time.type" style="width:200px">
                    <Option v-for="item in timeTypeList" :value="item.value" :key="item.value">{{ item.text }}</Option>
                </Select>
            </FormItem> -->

            <FormItem label="活动时间" v-if="form.data.time.type==0" >
                <Date-picker v-model="form.data.time.time" type="datetimerange" format="yyyy-MM-dd HH:mm" placeholder="选择日期和时间" style="width: 300px"></Date-picker>
            </FormItem>

            <!-- <FormItem label="活动时间" v-if="form.data.time.type==1">
                <TimePicker format="HH:mm" type="timerange" placement="bottom-end" placeholder="Select time" style="width: 172px"></TimePicker>
                <div v-for="timeStamp in form.data.time.time" :key="timeStamp.start" :row="timeStamp">
                    <DatePicker  type="date" placeholder="yyyy-mm-dd"></DatePicker>
                    <Button type="dashed" @click="delete_timeStamp(form.data.time.time)">-</Button>
                </div>
                <Button type="dashed" @click="more_timeStamp">+</Button>
            </FormItem>

            <FormItem label="活动时间" v-if="form.data.time.type==2">
                <div>
                    <Date-picker type="datetimerange" format="yyyy-MM-dd HH:mm" placeholder="选择日期和时间" style="width: 300px"></Date-picker>
                </div>
            </FormItem>

            <FormItem label="活动时间" v-if="form.data.time.type==3">
                <div v-for="timeStamp in form.data.time.time" :key="timeStamp.start" :row="timeStamp">
                    <TimeRange :timeStamp="timeStamp"/>
                    <Button type="dashed" @click="delete_timeStamp(form.data.time.time)">-</Button>
                </div>
                <Button type="dashed" @click="more_timeStamp">+</Button>
            </FormItem> -->
            <FormItem label="所属社团:">
                <b-form-select v-model="associationIndex" :options="associationList" style="width: 200px; height: 40px; "></b-form-select>
            </FormItem>
            <FormItem label="报名是否需要审核:">
                <Checkbox v-model="form.data.ifReview"/>
            </FormItem>
            <FormItem label="是否仅社团或组织成员可以参加:">
                <Checkbox v-model="form.data.ifOnlyMem"/>
            </FormItem>
            <FormItem label="最大人数">
                <Input v-model="form.data.maxParticipants"/>
            </FormItem>
            <FormItem label="上传活动宣传图" >
                <div>
                    <b-form-file v-model="form.data.image" ref="file-input" style="width: 310px; "></b-form-file>
                    <b-button @click="clearFile" style="height: 33px; margin-bottom: 8px">clear</b-button>
                    <img  :src="form.data.image"  style="width: 360px; height: 200px"/>
                </div>
            </FormItem>
            <FormItem label="活动简介">
                <Input v-model="form.data.content"/>
            </FormItem>
            <FormItem label="选择标签" >
                <Button @click="form.data.tag.tag_show=true" style="width: 80px">选择标签</Button>
                <TagChoose :tag="form.data.tag" />
                <div v-if="form.data.tag.tagList.length" style="display:inline">
                    <Tag v-for="tag in form.data.tag.tagList" :key="tag.id" :row="tag">{{ tag }}</Tag>
                </div>
            </FormItem>
        </Form>
    </Modal>
</template>

<script>
import Axios from 'axios';
import TimeRange from './time_range'
import TagChoose from '../../common/tag_choose'
import Messgae, { Message } from 'iview'

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
    components: { TimeRange , TagChoose},
    props: {
            form: Object
    },
    data(){
        return {
            fileList: [],
            associationIndex: "",
            associationList:[{
                value:"",
                text:"请选择所属社团"
            }],
            // tag:{
            //     tag_show: false,
            //     tagList:[],
            // }
        }
    },
    methods: {
        // more_timeStamp() {
        //     this.form.time.time.push({start: "", end: ""})
        // },
        // delete_timeStamp(value){
        //     let times = this.form.time.time
        //     let length = this.form.time.time.length
        //     for(let i=0; i<length; i++){
        //         if(times[i]==value){
        //             this.form.time.time.splice(i, 1)
        //             break
        //         }
        //     }
        // },

        // 删除回调
        clearFile(file, fileList) {
            this.$refs['file-input'].reset();
        },
        info_ok(){
            console.log(this.form)
            let data = {
                title: this.form.data.title,
                content: this.form.data.content,
                signupTime: {
                    type: 0,
                    time:[{
                        start: this.form.data.signupTime.time[0],
                        end: this.form.data.signupTime.time[1],
                    }]
                },
                time: {
                    type: 0,
                    time:[{
                        start: this.form.data.time.time[0],
                        end: this.form.data.time.time[1],
                    }]
                },
                ifReview: this.form.data.ifReview,
                ifOnlyMem: this.form.data.ifOnlyMem,
                maxParticipants: this.form.data.maxParticipants,
                materialTemplateId: this.form.data.materials,
                tags: this.form.data.tag.tagList,
            }

            let assos = []
            assos.push(this.associationIndex)
            data.associationIds = assos
            
            if (this.form.data.image!="") {
                let reader = new FileReader()
                reader.readAsDataURL(this.form.data.image)
                reader.onload=(e)=>{
                    data.image = e.target.result
                    console.log(data)
                    this.axios({
                        method:"post",
                        url:"/activities",
                        data:data
                    })
                    .then(response => {
                        console.log("Successfully!")
                        console.log(response.data.data)
                    })
                    .catch(e => {
                        console.log(e)
                    })
                }
            }
            this.form.onshow = false
        },
        info_cancel(){
            this.form.onshow = false
        },
        getAssociations(){
            let userid = "1"
            this.axios({
                method: "get",
                url: "/associations/users/"+userid+"/associations",
            })
            .then(response => {
                console.log(response.data)
                // if(response.data.data!=null){
                //     let originList = response.data.data.content
                //     this.setAssociationList(originList)
                // }s
            })
            .catch(e => {
                console.log(e)
            })
        },
        setAssociationList(originList){
            if(originList.length==0){
                return
            }
            for(let i=0;i<originList.length;i++){
                let data = {
                    value: originList[i].id,
                    text: originList[i].name
                }
                this.associationList.push(data)
            }
            // console.log(this.associationList)
        }
    },
    mounted() {
        this.getAssociations()
    },
}
</script>
