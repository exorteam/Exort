<template>
    <Form v-model="assoInfo" :label-width="112" >
        <FormItem label="社团名称">
            <p>{{assoInfo.name}}</p>
        </FormItem>
        <FormItem label="社团Logo">
            <img :src="assoInfo.logo"/>
        </FormItem>
        <FormItem label="社团简介">
            <p>{{assoInfo.description}}</p>
        </FormItem>
        <FormItem label="社团标签">
            <Tag v-for="item in assoInfo.tags" :key="item" :name="item" >{{ item  }}</Tag>
        </FormItem>
        <FormItem label="社团状态">
            <Icon type="ios-checkmark-circle" size="30" color="green" v-if="assoInfo.state"/>
            <Icon type="ios-close-circle" size="30" color="red" v-if="!assoInfo.state"/>
        </FormItem>
        <FormItem label="封禁理由" v-if="!assoInfo.state">
            <p>{{assoInfo.reason}}</p>
        </FormItem>
        <FormItem>
            <Button type="info" v-on:click="showEditForm()">编辑社团信息</Button>
            <Modal v-model="form.onshow" @on-ok="info_ok" @on-cancel="info_cancel" loading :closable="false" :rules="ruleValidate">
                    <Form :model="form" :label-width="112" :rules="ruleValidate">
                        <FormItem label="社团名称" prop="name">
                            <Input v-model="form.name"/>
                        </FormItem>
                        <FormItem label="社团描述"  prop="description">
                            <Input v-model="form.description" type="textarea" :rows="4"/>
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
                            <div style="margin-left:70px">
                                <img :src="assoInfo.logo"/>
                            </div>
                            <div style="margin-top:20px">
                                <b-form-file v-model="file" ref="file-input" style="width:270px"></b-form-file>
                                <Button type="primary" @click="clearFiles" style="height:33px ; margin-bottom:8px;margin-left:11px" >重设</Button>
                            </div>

                        </FormItem>
                    </Form>
                </Modal>
        </FormItem>
    </Form>
</template>

<script>
import Solid from '../../../assets/AssociationLogo/solid.jpg'
import TagChoose from '../../common/tag_choose.vue'
import axios from 'axios'
export default {
    components:{TagChoose},
    data(){
        return {
            ruleValidate: {
                name: [
                    { required: true, message: '社团名称不能为空', trigger: 'blur' }
                ],
                description: [
                    { required: true, message: '社团描述不能为空', trigger: 'blur' }
                ]
            },

            file:null,
            form:{
                id:"5d42930c9ded9d0001d815e8",
                onshow: false,
                name: "",
                description: "",
                tag:{
                    tag_show: false,
                    tagList:[],
                },
                logo:"",
                needMaterial:false,
                state:null,
                showState:false,
            },
            assoInfo:{
                id:"5d42930c9ded9d0001d815e8",
                name: "无敌的测试社团",
                description: "给你们一个信息这波我很强",
                tags:[
                    "DS",
                    "BAT",
                    "黄浦江"
                ],
                logo:Solid,
                state:true,
                reason:""
            },
        }
    },
    methods: {
        clearFiles() {
            this.$refs['file-input'].reset();
        },
        showEditForm(){
            this.form.state = this.assoInfo.state
            this.form.id=this.assoInfo.id
            this.form.name=this.assoInfo.name
            this.form.description=this.assoInfo.description
            this.form.tag.tagList=this.assoInfo.tags
            this.form.onshow=true
        },
        info_ok(){
            const _self = this;
            if(_self.form.name=="" | _self.form.description==""){
                _self.form.onshow = false
                _self.$Message.error('创建失败，请完善表单信息');
                return
            }
            var imgFile;
            let reader = new FileReader();
            if(this.file != null){
                reader.readAsDataURL(this.file);
                reader.onload=function(e) {        //读取完毕后调用接口
                    imgFile = e.target.result;
                    console.log(imgFile)
                    axios
                    .put('http://localhost:8080/associations/'+_self.form.id,
                        {
                            name:_self.form.name,
                            description:_self.form.description,
                            tags:_self.form.tag.tagList,
                            logo:imgFile
                        }
                    )
                    .then(response => {
                        _self.getAssociation()
                        console.log(response.data)
                    })
                    .catch(e => {
                        console.log(e)
                    })
                };
            }
            else{
                axios
                .put('http://localhost:8080/associations/'+_self.form.id,
                    {
                        name:_self.form.name,
                        description:_self.form.description,
                        tags:_self.form.tag.tagList,
                        logo:_self.assoInfo.logo
                    }
                )
                .then(response => {
                    _self.getAssociation()
                    console.log(response.data)
                })
                .catch(e => {
                    console.log(e)
                })
            }
            _self.form.onshow = false
            _self.$Message.info('修改成功');
        },
        info_cancel(){
            console.log("I'm here")
            this.form.name=""
            this.form.description=""
            this.form.tagList=""
            this.form.onshow = false
        },
        getAssociation(){
            this.axios({
				method:'get',
                url:'/associations/'+this.assoInfo.id
			})
            .then(response => {
                console.log("我请求了")
                this.assoInfo = response.data.data
                console.log(response.data.data)
            })
            .catch(e => {
                console.log(e)
            });

        }
    },
    mounted() {
        this.axios({
			method:'get',
            url:'/associations/'+this.assoInfo.id
		})
        .then(response => {
            console.log("我请求了")
            this.assoInfo = response.data.data
            console.log(response.data.data)
        })
        .catch(e => {
            console.log(e)
        });
    },
}
</script>
