<template>
<Modal v-model="form.create_show" @on-ok="info_ok" @on-cancel="info_cancel" loading :closable="false">
    <Form v-model="form" :label-width="112">
        <FormItem label="社团名称">
            <Input v-model="form.name"/>
        </FormItem>
        <FormItem label="社团描述">
            <Input v-model="form.description"/>
        </FormItem>
        <FormItem label="社团标签">
            <Tag v-for="item in form.tags" :key="item.key" type="border" closable color="blue">{{ item  }}</Tag>
            <i-input v-model="newtag" placeholder="请输入"></i-input>
            <i-button icon="ios-plus-empty" type="primary" size="small" @click="AddNewTag">添加标签</i-button>
        </FormItem>
        <FormItem label="社团Logo">
            <Upload v-model= "form.logo" multiple type="drag" action="//jsonplaceholder.typicode.com/posts/" style="text-align: center;">
                <div style="padding: 20px 0">
                    <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
                    <p>点击或将社团logo拖拽到这里上传</p>
                </div>
            </Upload>
        </FormItem>
    </Form>
</Modal>
</template>

<script>
export default {
    props: {
        form: Object
    },
    data () {
    return {
        newtag:''
        }
    },
    methods: {
        AddNewTag(){
            this.formItem.tags.push(this.newtag);
        },
    },
}
</script>
