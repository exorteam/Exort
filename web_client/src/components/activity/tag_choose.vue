<template>
<Modal v-model="tag.tag_show" @on-ok="tag_show=false" @on-cancel="handleCancel" loading :closable="false">
    <Form :label-width="80">
        <FormItem label="选择标签">
            <Button @click="cancelSelect" v-for="tag in tag.tagList" :key="tag.id" :row="tag">{{ tag }}</Button>
        </FormItem>
        <FormItem label="选择标签">
            <Input v-model="tag_select"/>
        </FormItem>
        <FormItem label="标签库">
            <Button @click="confirmSelect" v-for="item in tagRepository" :key="item.id" :row="item">{{ item }}</Button>
        </FormItem>
    </Form>
</Modal>
</template>

<script>

let tagrepo = ["运动", "讲座", "讲座", "讲座", "讲座", "讲座", "讲座", "讲座"]

import http from '../../http-common'

export default {
    props: {
        tag: Object
    },
    data () {
        return{
            tag_select:"",
            tagRepository:[]
        }
    },
    methods: {
        cancelSelect(value){
            let str = value.toElement.innerText
            let list = this.tag.tagList
            for (let i=0; i<list.length; i++){
                if(list[i]==str){
                    this.tag.tagList.splice(i, 1)
                    break
                }
            }
        },
        confirmSelect(value){
            let str = value.toElement.innerText            
            this.tag.tagList.push(str)
        },
        handleCancel(){
            this.tag.tagList = []
            this.tag.tag_show=false
        }
    },
    mounted() {
        // http.
        //     get('/')
        this.tagRepository = tagrepo
    },
}
</script>
