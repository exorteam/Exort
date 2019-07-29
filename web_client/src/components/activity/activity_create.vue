<template>
    <Modal v-model="form.onshow" @on-ok="info_ok" @on-cancel="info_cancel" loading :closable="false">
        <Form v-model="form" :label-width="112">
            <FormItem label="活动标题">
                <Input v-model="form.title"/>
            </FormItem>
            <FormItem label="报名时间">
                <Date-picker v-model="form.signupTime.time" type="datetimerange" format="yyyy-MM-dd HH:mm" placeholder="选择日期和时间" style="width: 300px"></Date-picker>
            </FormItem>
            <!-- 当前只有一种时间实现 -->
            <!-- <FormItem label="选择活动时间类型:">
                <Select v-model="form.time.type" style="width:200px">
                    <Option v-for="item in timeTypeList" :value="item.value" :key="item.value">{{ item.text }}</Option>
                </Select>
            </FormItem> -->

            <FormItem label="活动时间" v-if="form.time.type==0">
                <Date-picker v-model="form.time.time" type="datetimerange" format="yyyy-MM-dd HH:mm" placeholder="选择日期和时间" style="width: 300px"></Date-picker>
            </FormItem>

            <!-- <FormItem label="活动时间" v-if="form.time.type==1">
                <TimePicker format="HH:mm" type="timerange" placement="bottom-end" placeholder="Select time" style="width: 172px"></TimePicker>
                <div v-for="timeStamp in form.time.time" :key="timeStamp.start" :row="timeStamp">
                    <DatePicker  type="date" placeholder="yyyy-mm-dd"></DatePicker>
                    <Button type="dashed" @click="delete_timeStamp(form.time.time)">-</Button>
                </div>
                <Button type="dashed" @click="more_timeStamp">+</Button>
            </FormItem>

            <FormItem label="活动时间" v-if="form.time.type==2">
                <div>
                    <Date-picker type="datetimerange" format="yyyy-MM-dd HH:mm" placeholder="选择日期和时间" style="width: 300px"></Date-picker>
                </div>
            </FormItem>

            <FormItem label="活动时间" v-if="form.time.type==3">
                <div v-for="timeStamp in form.time.time" :key="timeStamp.start" :row="timeStamp">
                    <TimeRange :timeStamp="timeStamp"/>
                    <Button type="dashed" @click="delete_timeStamp(form.time.time)">-</Button>
                </div>
                <Button type="dashed" @click="more_timeStamp">+</Button>
            </FormItem> -->
            <FormItem label="所属社团:">
                <!-- <Checkbox v-model="associationList[associationIndex]"/> -->
                <b-form-select v-model="associationIndex" :options="associationList" style="width: 120px; height: 32px; margin-right: 45px"></b-form-select>
            </FormItem>            
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
                <div>
                    <b-form-file v-model="file" ref="file-input" style="width: 310px; "></b-form-file>
                    <b-button @click="clearFile" style="height: 33px; margin-bottom: 8px">clear</b-button>
                </div>
            </FormItem>
            <FormItem label="活动简介">
                <Input v-model="form.content"/>
            </FormItem>
            <FormItem label="选择标签">
                <Button @click="tag.tag_show=true" style="width: 80px">选择标签</Button>
                <TagChoose :tag="tag" />
                <div v-if="tag.tagList.length" style="display:inline">
                    <Tag v-for="tag in tag.tagList" :key="tag.id" :row="tag">{{ tag }}</Tag>
                </div>
            </FormItem>
        </Form>
    </Modal>
</template>

<script>
import Axios from 'axios';
import TimeRange from './time_range'
import TagChoose from './tag_choose'
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
            associationIndex: "",
            associationList:[],
            file: '',
            fileList: [],
            tag:{
                tag_show: false,
                tagList:[],
            }
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
            let data = {
                title: this.form.title,
                content: this.form.content,
                signupTime: {
                    type: 0,
                    time:[{
                        start: this.form.signupTime.time[0],
                        end: this.form.signupTime.time[1],
                    }]
                },
                time: {
                    type: 0, 
                    time:[{
                        start: this.form.time.time[0],
                        end: this.form.time.time[1],
                    }]
                },
                ifReview: this.form.ifReview,
                ifOnlyMem: this.form.ifOnlyMem,
                maxParticipants: this.form.maxParticipants,
                materials: this.form.materials,
                tags: this.tag.tagList,
            }
            data.associationIds = []
            data.associationIds.push(associationIndex)

            let reader = new FileReader()
            reader.readAsDataURL(this.file)
            reader.onload=function(e){
                data.image = e.target.result
            }

            this.form.onshow = false
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
        },
        info_cancel(){
            this.form.onshow = false
        },
        getAssociations(){
            this.axios({
                method: "get",
                url: "/associations/",
                params: {
                    pageNum: 0,
                    pageSize: 9999,
                    keyword: "",
                    tags: "",
                    state: 1,
                }
            })
            .then(response => {
                let originList = response.da.data.content
                console.log(originList)
                this.setAssociationList(originList) 
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
                    value: originList.id,
                    text: originList.name
                }
                this.associationList.push(data)
            }
            console.log(this.associationList)
        }
    },
    mounted() {
        this.getAssociations()
    },
}
</script>
