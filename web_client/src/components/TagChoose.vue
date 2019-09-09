<template>
    <Modal v-model="tag.tag_show" @on-ok="OkSelect" @on-cancel="handleCancel" loading :closable="false">
        <Form :label-width="80">
            <FormItem label="选择标签">
                <Button @click="cancelSelect" v-for="tag in tag.tagList" :key="tag.id" :row="tag">{{ tag }}</Button>
            </FormItem>
            <FormItem label="创建标签">
                <Input v-model="tag_name" style="width:310px"/>
                <Button @click="handleCreate" style="width: 77px; margin-left: 3px; margin-right:-3px">创建标签</Button>
            </FormItem>
            <FormItem label="选择标签">
                <Input v-model="tag_select" style="width:390px"/>
            </FormItem>
            <FormItem label="标签库">
                <Button @click="confirmSelect" v-for="item in tagRepository" :key="item.id" :row="item">{{ item }}
                </Button>
            </FormItem>
        </Form>
    </Modal>
</template>

<script>
import {api} from '@/http';

// let tagrepo = ["运动", "讲座", "讲座", "讲座", "讲座", "讲座", "讲座", "讲座"]

export default {
    props: {
        tag: Object
    },
    data() {
        return {
            tag_name: "",
            tag_select: "",
            tagRepository: []
        }
    },
    methods: {
        handleCreate() {
            let name = this.tag_name
            if (name != "") {
                this.tag.tagList.push(name)
                this.tag_name = ""
            }
        },
        //将标签存起来
        store(value) {
        },
        cancelSelect(value) {
            let str = value.toElement.innerText
            let list = this.tag.tagList
            for (let i = 0; i < list.length; i++) {
                if (list[i] == str) {
                    this.tag.tagList.splice(i, 1)
                    break
                }
            }
        },
        confirmSelect(value) {
            let str = value.toElement.innerText
            this.tag.tagList.push(str)
        },
        OkSelect() {
            this.tag.tag_show = false
            this.store(name)
        },
        handleCancel() {
            this.tag.tagList = []
            this.tag.tag_show = false
        }
    },
    mounted() {
        // axios.
        //     get('/')
        this.tagRepository = this.tag.tagrepo
    },
}
</script>

<style>

</style>
