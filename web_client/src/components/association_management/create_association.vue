<template>
    <Modal v-model="form.onshow" @on-ok="info_ok" @on-cancel="info_cancel" loading :closable="false">
        <Form v-model="form" :label-width="112">
            <FormItem label="社团名称">
                <Input v-model="form.name"/>
            </FormItem>
            <FormItem label="社团描述">
                <Input v-model="form.description"/>
            </FormItem>
            <FormItem label="社团标签">
                <div>
                    <div style="display:inline">
                        <Button @click="form.tag.tag_show=true" style="width: 80px;position:relative ;top:5px">选择标签</Button>
                        <TagChoose :tag="form.tag"/>
                    </div>
                    <div v-if="form.tag.tagList.length" style="display:inline">
                        <Tag v-for="tag in form.tag.tagList" :key="tag.id" :row="tag" color="blue">{{ tag }}</Tag>
                    </div>
                </div>
            </FormItem>
            <FormItem label="社团Logo">
                <Upload multiple type="drag" action="//jsonplaceholder.typicode.com/posts/" style="text-align: center;">
                    <div style="padding: 20px 0">
                        <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
                        <p>点击或将社团Logo拖拽到这里上传</p>
                    </div>
                </Upload>
            </FormItem>
            <FormItem label="报名材料" v-if="form.needMaterial">
                <Input v-model="form.materials"/>
            </FormItem>
        </Form>
    </Modal>
</template>

<script>
import TagChoose from '../activity/tag_choose.vue'
import axios from '../../http-common.js'
export default {
    components:{TagChoose},
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
            if(this.form.type="create"){

                axios
                .post('associations',{
                    data:{
                        name:this.form.name,
                        description:this.form.description,
                        tags:this.form.tag.tagList,
                        logo:this.form.logo
                    }
                })
                .then(response => {
                    console.log(response.data)
                    // this.AssoList = response.data.data.content
                })
                .catch(e => {
                    console.log(e)
                })
                this.$Message.info('创建成功');
            }
            else{
                axios
                .put('associations/${this.form.assoId}',{
                    data: {
                        name:this.form.name,
                        description:this.form.description,
                        tags:this.form.tag.tagList,
                        logo:this.form.logo
                    }
                })
                .then(response => {
                    console.log(response.data)
                    // this.AssoList = response.data.data.content
                })
                .catch(e => {
                    console.log(e)
                })
                this.$Message.info('修改成功');
            }
        },
        info_cancel(){
            this.form.name=""
            this.form.description=""
            this.form.tagList=""
            this.form.materials=""
            this.form.type=""
            this.form.onshow = false
        }
    },
}
</script>
