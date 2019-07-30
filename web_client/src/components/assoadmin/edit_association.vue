<template>
    <Modal v-model="form.onshow" @on-ok="info_ok" @on-cancel="info_cancel" loading :closable="false">
        <Form v-model="form" :label-width="112">
            <FormItem label="社团名称">
                <Input v-model="form.name"/>
            </FormItem>
            <FormItem label="社团描述">
                <Input v-model="form.description"/>
            </FormItem>
            <FormItem label="社团Logo">
                <Upload multiple type="drag" action="//jsonplaceholder.typicode.com/posts/" style="text-align: center;">
                    <div style="padding: 20px 0">
                        <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
                        <p>点击或将社团Logo拖拽到这里上传</p>
                    </div>
                </Upload>
            </FormItem>
            <FormItem label="社团简介">
                <Input v-model="form.description"/>
            </FormItem>
        </Form>
    </Modal>
</template>

<script>

export default {
    props: {
        form: Object
    },
    data(){
        return {
        }
    },
    methods: {
        info_ok(){
            this.form.onshow = false
        },
        info_cancel(){
            this.form.onshow = false
        }
    },
}
</script>
