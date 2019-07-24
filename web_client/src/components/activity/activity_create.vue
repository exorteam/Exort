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
            <FormItem label="选择活动时间类型:">
                <Select v-model="form.time.type" style="width:200px">
                    <Option v-for="item in timeTypeList" :value="item.value" :key="item.value">{{ item.text }}</Option>
                </Select>
            </FormItem>

            <FormItem label="活动时间" v-if="form.time.type==0">
                <div>
                    <Date-picker type="datetimerange" format="yyyy-MM-dd HH:mm" placeholder="选择日期和时间" style="width: 300px"></Date-picker>
                </div>
            </FormItem>

            <FormItem label="活动时间" v-if="form.time.type==1">
                <TimePicker format="HH:mm" type="timerange" placement="bottom-end" placeholder="Select time" style="width: 172px"></TimePicker>
                <div v-for="timeStamp in form.time.time" :key="timeStamp.start" :row="timeStamp">
                    <!-- <Date-picker v-model="timeStamp" type="date" placeholder="选择日期" style="width: 200px"></Date-picker> -->
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
                <Upload ref="upload"
                                :limit="1"
                                :on-error="uploadError"
                                :on-success="uploadSuccess"
                                :on-remove="handleRemove"
                                :before-upload="beforeAvatarUpload"
                                :show-upload-list="true"
                                :default-file-list="fileList"
                                :format="['jpg','jpeg','png']"
                                :max-size="2048"
                                multiple 
                                type="drag"
                                action="//jsonplaceholder.typicode.com/posts/"
                                style="text-align: center;">
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
import TimeRange from './time_range'

export default {
    components: { TimeRange },
    props: {
            form: Object
    },
    data(){
            return {
                    signupTime:'',
                    signupDate:'',
                    picture: '',
                    fileList: [],
                    timeTypeList: [
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
            }
    },
    methods: {
    // 上传成功后的回调
        uploadSuccess (response, file, fileList) {
            console.log('上传文件', response, file, fileList)

            let reader = new FileReader();
            reader.readAsDataURL(file.raw)
            reader.onload = (e) => {
                this.picture = e.target.result
                console.log(this.picture)
            }
        },
        // 上传错误
        uploadError (response, file, fileList) {
            console.log(response)
            console.log('上传失败，请重试！')
        },
        // 删除回调
        handleRemove(file, fileList) {
            console.log(file)
            // 删除总展示文件里的当前文件
            this.file = this.file.filter(item => {
                return item.name != name
            })
            // 删除需要上传文件数据里的当前文件
            this.uploadFile = this.uploadFile.filter(item => {
                return item.KeyID != keyID
            })
        },
        // 上传前对文件的大小的判断
        beforeAvatarUpload (file) {
            // 选择文件后 这里判断文件类型 保存文件 自定义一个keyid 值 方便后面删除操作
            let keyID = Math.random().toString().substr(2);
            file['keyID'] = keyID;
            // 保存文件到总展示文件数据里
            this.file.push(file);
            // 保存文件到需要上传的文件数组里
            this.uploadFile.push(file)
            // 返回 falsa 停止自动上传 我们需要手动上传
            return false;
        },
        more_timeStamp() {
            this.form.time.time.push({start: "", end: ""})
        },
        delete_timeStamp(value){
            let times = this.form.time.time
            let length = this.form.time.time.length
            for(let i=0; i<length; i++){
                if(times[i]==value){
                    this.form.time.time.splice(i, 1)
                    break
                }
            }
        },
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
                tags: this.form.tags,
                image: this.picture
            }
            // Axios
                // .post('http://202.120.40.8:30727/activities', data)
                // .then(response => {
                    // console.log("Successfully!")
            //         console.log(response.data.data)
            //     })
            //     .catch(e => {
            //         console.log(e)
            //     })
            // console.log(data)
        },
        info_cancel(){
                this.form.onshow = false
        }
    },
    mounted() {
    },
}
</script>