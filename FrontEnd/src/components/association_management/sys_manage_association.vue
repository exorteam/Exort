<template>
    <i-form :model="formItem" :label-width="80">
        <Form-item label="社团名称">
            <i-input :value.sync="formItem.input" placeholder="请输入"></i-input>
        </Form-item>
        <Form-item label="社团图片">
            <i-input :value.sync="formItem.input" placeholder="请输入"></i-input>
        </Form-item>
        <Form-item label="社团描述">
            <i-input :value.sync="formItem.textarea" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..."></i-input>
        </Form-item>
        <Form-item label="社团标签">
            <i-input :value.sync="formItem.input" placeholder="请输入"></i-input>
        </Form-item>
        
        <Form-item label="禁用">
            <Radio-group :model.sync="formItem.radio">
                <Radio value="male">是</Radio>
                <Radio value="female">否</Radio>
            </Radio-group>
        </Form-item>




        <Form-item>
            <i-button type="primary">提交</i-button>
            <i-button type="default" style="margin-left: 8px">取消</i-button>
            <i-button type="warning" style="margin-left: 8px">删除</i-button>
        </Form-item>
    </i-form>
</template>
<script>
    export default {
        data () {
            return {
                formItem: {
                    input: '',
                    select: '',
                    radio: 'male',
                    checkbox: [],
                    switch: true,
                    date: '',
                    time: '',
                    slider: [20, 50],
                    textarea: ''
                }
            }
        }
    }
</script>