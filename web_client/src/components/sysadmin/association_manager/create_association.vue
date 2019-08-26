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
                <div>
                <b-form-file v-model="file" ref="file-input" style="width:270px"></b-form-file>
                <b-button @click="clearFiles" style="height:33px ; margin-bottom:8px;margin-left:11px" >Reset</b-button>
                </div>

            </FormItem>
            <FormItem label="报名材料" v-if="form.needMaterial">
                <Input placeholder="请输入材料Id，用,分割"  v-model="form.materials"/>
            </FormItem>
            <FormItem label="社团状态" v-if="form.showState">
                <Input placeholder="请输入新的状态" v-model="form.assoState"/>
            </FormItem>
        </Form>
    </Modal>
</template>

<script>
import TagChoose from '../../common/tag_choose.vue'
import {api} from '@/http'
export default {
    components:{TagChoose},
    props: {
        form: Object
    },
    data(){
        return {
            file: null
        }
    },
    methods: {
        clearFiles() {
            this.$refs['file-input'].reset();
        },
        info_ok(){
            const _self = this;
            console.log(_self.form.type);

            if(_self.form.type=="create"){
                var imgFile;
                let reader = new FileReader();
                if(this.file!=null){
                    reader.readAsDataURL(this.file);
                }
                reader.onload=function(e) {        //读取完毕后调用接口
                    imgFile = e.target.result;
                    console.log(_self.form.name);
                    console.log(_self.form.description);
                    console.log(_self.form.tag.tagList);
                    console.log(imgFile);
                    api
                    .post('http://localhost:8080/associations',
                        {
                            name:_self.form.name,
                            description:_self.form.description,
                            tags:_self.form.tag.tagList,
                            logo:imgFile
                        }
                    )
                    .then(response => {
                        console.log(response.data)
                    })
                    .catch(e => {
                        console.log(e)
                    })
                };

                _self.form.onshow = false
                _self.$Message.info('创建成功');

            }
            else{
                var imgFile;
                let reader = new FileReader();
                if(this.file!=null){
                    reader.readAsDataURL(this.file);
                }
                reader.onload=function(e) {
                    imgFile = e.target.result;
                    api
                    .put('http://localhost:8080/associations/'+_self.form.assoId,{
                        name:_self.form.name,
                        description:_self.form.description,
                        tags:_self.form.tag.tagList,
                        logo:imgFile
                    })
                    .then(response => {
                        console.log(response.data)
                    })
                    .catch(e => {
                        console.log(e)
                    })

                }
                _self.form.onshow = false
                _self.$Message.info('修改成功');
            }
            // this.activeSon()
        },
        info_cancel(){
            console.log("I'm here")
            this.form.name=""
            this.form.description=""
            this.form.tagList=""
            this.form.materials=""
            this.form.type=""
            this.form.onshow = false
            // this.activeSon()
        }
    },
}
</script>
