<template>
	<Card>
		<!--<Tabs active-key="tab_key">                           -->
		<!--    <Tab-pane label="社团管理" key="tabpane_key1">-->
			<div id="AssoList">
				<div id=SearchAsso>
					<i-input v-model="assoSearch.keyword" placeholder="请输入关键词" style="width: 300px" />
					<Button @click="getAssociationList">搜索</Button>
					<Button @click="showCreateForm">创建社团</Button>
					<div style="display:inline">
						<Button @click="tag.tag_show=true" style="width: 80px">选择标签</Button>
						<TagChoose :tag="tag"/>
					</div>
					<Modal v-model="form.onshow" @on-ok="info_ok()" @on-cancel="info_cancel" loading :closable="false">
						<Form ref="form" :model="form"  :label-width="112" :rules="ruleValidate" >
							<FormItem label="社团名称" prop="name">
								<Input v-model="form.name"/>
							</FormItem>
							<FormItem label="社团描述" prop="description">
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
								<div>
								<b-form-file v-model="file" ref="file-input" style="width:270px"></b-form-file>
								<Button type="primary" @click="clearFiles" style="height:33px ; margin-bottom:8px;margin-left:11px" >重设</Button>
								</div>
							</FormItem>
							<FormItem label="报名材料" v-if="form.needMaterial">
								<Input placeholder="请输入材料Id，用,分割"  v-model="form.materials"/>
							</FormItem>
							<FormItem label="社团状态" v-if="form.showState">
								<Icon type="ios-checkmark-circle" size="30" color="green" v-if="form.assoState"/>
								<Icon type="ios-close-circle" size="30" color="red" v-if="!form.assoState"/>
								<Button type="warning" @click="changeState" style="height:33px ; margin-bottom:8px ; margin-left:230px" >变更状态</Button>
							</FormItem>
						</Form>

					</Modal>
					<div>
						<div v-if="tag.tagList.length" style="display:inline">
							<Tag v-for="tag in tag.tagList" :key="tag.id" :row="tag" color="blue">{{ tag }}</Tag>
						</div>
					</div>
				</div>
			<div>
			<div id="Divide">
			   <Divider />
				</div>
				<b-form-checkbox-group @input="get" v-model="assoStateSelected" :options="assoStateList" switches></b-form-checkbox-group>
				</div>
				<div id="Divide">
				   <Divider />
				</div>

				<div id="CardList"  style="display: flex; flex-wrap: wrap">
					<Card v-for="item in AssoList" :key="item.id" :row="item" style="width:350px;height:350px;margin-left:5px;margin-top:5px" >
					<p slot="title">
						<Icon type="ios-people" ></Icon>

						{{item.name}}
						<Icon type="ios-checkmark-circle" style="position:absolute;right:10px;" size="30" color="green" v-if="item.state"/>
						<Icon type="ios-close-circle" style="position:absolute;right:10px;" size="30" color="red" v-if="!item.state"/>
					</p>


					<div style="text-align: center;height:100px">
						<img :src="item.logo" style="width:80px;height:80px;"/>
					</div>
					<div style= "min-height: 100%; ">
						<p>{{ item.description }}</p>
					</div>
					<div style="margin-top:80px">
						<Tag v-for="tag in item.tags" :key="tag.id" :row="tag"  color="geekblue">
							{{ tag }}
						</Tag>
					</div>
					<div >
						<Button type="info" v-on:click="showEditForm(item)">编辑</Button>
						<Button type="warning" v-on:click="deleteAssociation(item)">删除</Button>
					</div>
					</Card>
				</div>
				<div style="margin-top:15px;text-align: center">
					<Page id = "page" show-elevator show-total
					:total="pageProp.totalSize" :page-size.sync="pageProp.pageSize" :page-size-opts="pageProp.pageSizeOpt"
					:current.sync = "pageProp.pageNum"  @on-change="getAssociationList"></Page>
				</div>
			</div>
			<!--</Tab-pane>-->
			<!--<Application :pendingAppData  ="pendingAppData" :handledAppData  ="handledAppData"/>-->
		<!--</Tabs>-->
	</Card>
</template>

<script>
import AssociationCreatingModal from './AssociationCreatingModal'
import TagChoose from '@/components/TagChoose'
import {api} from '@/http'
export default {
    name:'associationList',
    components:{AssociationCreatingModal, TagChoose},
    data () {
        return {
            ruleValidate: {
                name: [
                    { required: true, message: '社团名称不能为空', trigger: 'blur' }
                ],
                description: [
                    { required: true, message: '社团描述不能为空', trigger: 'blur' }
                ]
            },
            file: null,
            form:{
                assoId:"",
                onshow: false,
                name: "",
                description: "",
                tag:{
                    tag_show: false,
                    tagList:[],
                    tagrepo : ["运动", "饮食", "音乐", "舞蹈", "历史", "游戏", "户外", "天文","航模","动漫"]
                },
                logo:"",
                needMaterial:false,
                assoState:null,
                showState:false,
                materials: "",
                type:""
            },
            pageProp:{
                totalSize : 0,
                pageSize : 6,
                pageNum : 1
            },
            tag:{
                tag_show: false,
                tagList:[],
                tagrepo : ["运动", "饮食", "音乐", "舞蹈", "历史", "游戏", "户外", "天文","航模","动漫"]
            },
            handledAppData: [],
            assoStateSelected: ['active','blocked'], // Must be an array reference!
            assoStateList: [
                { text: '已激活', value: 'active' },
                { text: '已锁定', value: 'blocked' },
            ],
            pendingAppTypeSelected:['applying','applying_unblocked'],
            pendingAppTypeList: [
                { text: '申请创建社团', value: 'applying' },
                { text: '申请取消锁定', value: 'applying_unblocked' }
            ],
            handledAppTypeSelected:['pass','canceled','refused'],
            handledAppTypeList: [
                { text: '已通过申请', value: 'pass' },
                { text: '已取消申请', value: 'canceled' },
                { text: '已拒绝申请', value: 'refused' },
            ],
            inputDefaultValue : "",
            AssoList: [],
            assoSearch:{
                keyword:"",
                tags:"asd",
                state:2,
                pageNum:0,
                pageSize:7

            }
        }
    },
    methods: {
        showCreateForm(){
            this.form.type = "create"
            this.form.showState=false
            this.form.onshow=true
        },
        showEditForm(item){
            this.form.assoState = item.state
            this.form.showState = true
            this.form.assoId=item.id
            this.form.name=item.name
            this.form.description=item.description
            this.form.tag.tagList=item.tags
            this.form.logo=item.logo
            this.form.type = "edit"
            this.form.onshow=true
        },
        deleteAssociation(item){
            api({
				method:'delete',
				url:'/associations/'+item.id,
			})
            .then(response => {
                this.$Message.info('删除成功');
                this.getAssociationList();
            })
            .catch(e => {
                console.log(e)
            });
        },
        changeState(){
            var state = this.form.assoState
            var type
            if(state){
                type = "block"
            }
            else{
                type = "unblock"
            }

            this.form.assoState=!this.form.assoState

            console.log(this.form.assoState)

            api({
				method:'put',
                url:'/associations/'+this.form.assoId+'/state',
                data:{
                    Arg:"",
                    operation:type,
                }
			})
            .then(response => {
                console.log(response.data.data)
                this.getAssociationList()
            })
            .catch(e => {
                console.log(e)
            });


        },
        StateList(state){
            switch(state)
            {
              case 0:return "已锁定";
              case 1:return "已激活";
              case 2:return "申请中";
              case 3:return "已取消申请";
              case 4:return "已删除";
              case 5:return "已拒绝申请";
            }
        },

        getState(){
            if(this.assoStateSelected.length==2) {
                this.assoSearch.state = 2
            }
            else if (this.assoStateSelected[0] == "active"){
                this.assoSearch.state = 1
            }
            else if (this.assoStateSelected[0]=="blocked"){
                this.assoSearch.state = 0
            }
            else{
                this.assoSearch.state = -1
            }
        },
        get(){
            this.getAssociationList()
        },

        getAssociationList() {
            this.assoSearch.pageNum = this.pageProp.pageNum - 1;
            this.assoSearch.pageSize = this.pageProp.pageSize;
            this.assoSearch.tags = this.tag.tagList.join();
            this.getState();

			api({
				method:'post',
				url:'/associations/list',
				data: {
					pageNum:this.assoSearch.pageNum,
					pageSize:this.assoSearch.pageSize-1,
					keyword:this.assoSearch.keyword,
					tags:this.tag.tagList.join(),
					state:this.assoSearch.state
				}
			})
			.then(response => {
				console.log(response);
				this.AssoList = response.data.data.content
				this.pageProp.totalSize = response.data.data.totalSize
			})
			.catch(e => {
				console.log(e)
			});
        },
        clearFiles() {
            this.$refs['file-input'].reset();
        },
        info_ok(){
            const _self = this;

            if(this.form.name === "" || this.form.name === null || this.form.description === ""){
                this.form.onshow = false
                this.$Message.error('创建失败，请完善表单信息');
                return
            }
            if(_self.form.type=="create"){
                var imgFile;
                let reader = new FileReader();
                console.log(this.form)
                console.log("_self.form.name:" + _self.form.name);
                if(this.file != null){
                    reader.readAsDataURL(this.file);
                    reader.onload=function(e) {        //读取完毕后调用接口
                        imgFile = e.target.result;
                        console.log("_self.form.name:" + _self.form.name);
                        api.post('/associations',
                            {
                                name:_self.form.name,
                                description:_self.form.description,
                                tags:_self.form.tag.tagList,

								//TODO: bind logo attr to md5 following logo image file stored by api server
                                logo:imgFile
                            }
                        ).then(response => {
                            _self.
                            _self.form.name=""
                            _self.form.description=""
                            _self.form.tag.taglist=[]
                            _self.clearFiles()
                            _self.getAssociationList()
                        }).catch(e => {
                            console.log(e)
                        })
                    };
                }
                else{
                    _self.form.onshow = false
                    _self.$Message.error('创建失败，请上传图片');
                    return
                }
                _self.form.onshow = false
                _self.$Message.success('创建成功');
                _self.getAssociationList()
            }
            else{
                var imgFile;
                let reader = new FileReader();
                if(this.file != null){
                    reader.readAsDataURL(this.file);
                    reader.onload=function(e) {        //读取完毕后调用接口
                        imgFile = e.target.result;
                        console.log(imgFile)
                        api
                        .put('/associations/'+_self.form.assoId,
                            {
                                name:_self.form.name,
                                description:_self.form.description,
                                tags:_self.form.tag.tagList,
                                logo:imgFile
                            }
                        )
                        .then(response => {
                            _self.getAssociationList()
                            _self.form.name=""
                            _self.form.description=""
                            _self.form.tag.taglist=[]
                            _self.clearFiles()
                            console.log(response.data)
                        })
                        .catch(e => {
                            console.log(e)
                        })
                    };
                }
                else{
                    api
                    .put('/associations/'+_self.form.assoId,
                        {
                            name:_self.form.name,
                            description:_self.form.description,
                            tags:_self.form.tag.tagList,
                            logo:_self.form.logo
                        }
                    )
                    .then(response => {
                        _self.getAssociationList()
                        _self.form.name=""
                        _self.form.description=""
                        _self.form.tag.taglist=[]
                        _self.clearFiles()
                        console.log(response.data)
                    })
                    .catch(e => {
                        console.log(e)
                    })
                }
                _self.form.onshow = false
                _self.$Message.success('修改成功');
            }
        },
        info_cancel(){
            this.form.name=""
            this.form.description=""
            this.form.tag.tagList=[]
            this.form.materials=[]
            this.form.type=""
            this.form.onshow = false
        }
    },
    mounted() {
		this.getAssociationList();
    }
}
</script>
