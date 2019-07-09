<template>
<Modal v-model="form.create_show" @on-ok="info_ok" @on-cancel="info_cancel" loading :closable="false">
    <Form v-model="form" :label-width="112">
        <FormItem label="活动标题">
            <Input v-model="form.title"/>
        </FormItem>
        <FormItem label="选择活动时间类型:">
            <Select v-model="form.timeType" style="width:200px" disabled>
                <Option v-for="item in timeTypeList" :value="item" :key="item">{{ item }}</Option>
            </Select>
        </FormItem>
        <FormItem label="报名时间">
            <DatePicker type="daterange" v-model="form.signup_time" placeholder="yyyy-mm-dd"></DatePicker>            
            <TimePicker format="HH:mm" type="timerange" placement="bottom-end" placeholder="Select time" style="width: 172px"></TimePicker>
        </FormItem>
        <FormItem label="比赛时间">
            <DatePicker v-model="form.time" type="daterange" placeholder="yyyy-mm-dd"></DatePicker>
            <TimePicker format="HH:mm" type="timerange" placement="bottom-end" placeholder="Select time" style="width: 172px"></TimePicker>
        </FormItem>
        <FormItem label="报名是否需要审核:">
            <Checkbox v-model="form.checked1"/>
        </FormItem>
        <FormItem label="是否仅社团或组织成员可以参加:">
            <Checkbox v-model="form.checked2"/>
        </FormItem>
        <FormItem label="最大人数">
            <Input v-model="form.numberOfPeople"/>
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
            <Input v-model="form.description"/>
        </FormItem>
        <FormItem label="报名材料">
            <Input v-model="form.materials"/>
        </FormItem>
    </Form>
</Modal>
</template>

<script>
export default {
    props: {
        form: Object
    }
}
</script>