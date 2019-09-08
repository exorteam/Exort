<template>
	<Card>
		<div id="AssoList">
			<div id=SearchAsso>
				<Row type="flex" justify="space-between">
				<Col span="4">
				<Input search @on-search="getAssociationList" placeholder="请输入关键词" style="width: 300px" />
				</Col>
				<Col span="2">
				<Button @click="showCreateForm">创建社团</Button>
				</Col>
				</Row>
				<Modal v-model="form.onshow" @on-ok="info_ok()" @on-cancel="info_cancel" loading :closable="false">
					<Form ref="form" :model="form"  :label-width="112" :rules="ruleValidate" >
						<FormItem label="社团名称" prop="name">
							<Input v-model="form.name"/>
						</FormItem>
						<FormItem label="社团描述" prop="description">
							<Input v-model="form.description" type="textarea" :rows="4"/>
						</FormItem>
						<FormItem label="社团Logo">
                            <ImageUploader v-model="form.logo" />
						</FormItem>
						<FormItem label="报名材料" v-if="form.needMaterial">
							<Input placeholder="请输入材料Id，用,分割"  v-model="form.materials"/>
						</FormItem>
						<FormItem label="社团状态" v-if="form.showState">
							<Button @click="changeState">{{form.assoState?"未禁用":"已禁用"}}</Button>
						</FormItem>
					</Form>

				</Modal>
				<div>
					<div v-if="tag.tagList.length" style="display:inline">
						<!--<Tag v-for="tag in tag.tagList" :key="tag.id" :row="tag" color="blue">{{ tag }}</Tag>-->
					</div>
				</div>
			</div>

			<!--<br>-->
			<!--<div>-->
			<!--<b-form-checkbox-group @input="get" v-model="assoStateSelected" :options="assoStateList" switches></b-form-checkbox-group>-->
			<!--    <CheckboxGroup v-model="assoStateSelected">-->
			<!--        <Checkbox label="active">              -->
			<!--            <span>已激活</span>             -->
			<!--        </Checkbox>                            -->
			<!--        <Checkbox label="blocked">             -->
			<!--            <span>已锁定</span>             -->
			<!--        </Checkbox>                            -->
			<!--    </CheckboxGroup>                           -->
			<!--</div>                                         -->
			<br>

			<div id="CardList"  style="display: flex; flex-wrap: wrap">
				<Card v-for="item in AssoList" :key="item.id" :row="item" style="width:350px;height:350px;margin-left:5px;margin-top:5px" >
				<p slot="title">
					<Icon type="ios-people" ></Icon>

					{{item.name}}
					<Icon type="ios-checkmark-circle" style="position:absolute;right:10px;" size="30" color="green" v-if="item.state"/>
					<Icon type="ios-close-circle" style="position:absolute;right:10px;" size="30" color="grey" v-if="!item.state"/>
				</p>


				<div style="text-align: center;height:100px">
					<img :src="associationIcon(item.logo)" style="width:80px;height:80px;"/>
				</div>
				<div style= "min-height: 100%; ">
					<p>{{ item.description }}</p>
				</div>
				<div style="margin-top:80px">
					<!--<Tag v-for="tag in item.tags" :key="tag.id" :row="tag"  color="geekblue">-->
					<!--    {{ tag }}                                                            -->
					<!--</Tag>                                                                   -->
				</div>
				<div >
					<Button @click="showEditForm(item)">编辑</Button>
					<Button @click="deleteAssociation(item)" style="margin-left:20px">删除</Button>
				</div>
				</Card>
			</div>
			<div style="margin-top:15px;text-align: center">
				<Page id = "page" show-total
				:total="pageProp.totalSize" :page-size.sync="pageProp.pageSize" :page-size-opts="pageProp.pageSizeOpt"
				:current.sync = "pageProp.pageNum"  @on-change="onChangePage"></Page>
			</div>
		</div>
	</Card>
</template>

<script>
import AssociationCreatingModal from './AssociationCreatingModal'
import TagChoose from '@/components/TagChoose'
import ImageUploader from '@/components/uploader/ImageUploader'
import {api} from '@/http'
import { mapState, mapActions, mapMutations } from 'vuex'
import { associationIcon } from '@/const'

export default {
    name:'associationList',
    components:{AssociationCreatingModal, TagChoose, ImageUploader},
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
                assoId:'',
                onshow: false,
                name: '',
                description: '',
                tag:{
                    tag_show: false,
                    tagList:[],
                    tagrepo : ["运动", "饮食", "音乐", "舞蹈", "历史", "游戏", "户外", "天文","航模","动漫"]
                },
                logo:'',
                needMaterial:false,
                assoState:null,
                showState:false,
                materials: '',
                type:''
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
            inputDefaultValue : '',
            AssoList: [],
            assoSearch:{
                keyword:'',
                tags:[],
                state:1,
                pageNum:0,
                pageSize:7

            },
			assoSearchKeyword:'',
        }
    },
    methods: {
        associationIcon,
		...mapActions('common/associationOps',[
			'queryPagedAssociationsWithFilter',
			'deleteAssociationById'
		]),
        showCreateForm(){
            this.form.type = 'create'
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
			this.$Modal.confirm({
				title: '提示',
				content: '<p>确认删除社团？(ID:'+item.id+')</p>',
				onOk: () => {
					this.deleteAssociationById({
						assoId: item.id
					}).then(response => {
						this.$Message.info('删除成功');
						this.getAssociationList();
					}).catch(err => {
						this.$Notice.error({
							title: '删除消息时出现错误ID: ' + msgId ,
							desc: err
						})
					})

				},
				onCancel: () => {

				}
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
                    Arg:'',
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
		onChangePage(newPageNum) {
			this.getAssociationList(this.assoSearchKeyword,newPageNum);
		},
        getAssociationList(search,newPageNum) {
			this.assoSearchKeyword = search;
            this.getState();

			this.queryPagedAssociationsWithFilter({
				filter: {
					keyword: search == ''?null:search,
					tags: [],
				},
				pageArgs: {
					pageNum: newPageNum?newPageNum:this.pageProp.pageNum,
					pageSize: this.pageProp.pageSize
				}
			}).then(response => {
				this.AssoList = response.data.data.content
				this.pageProp.totalSize = response.data.data.totalSize
			})
			.catch(e => {
				console.log(e)
			});

        },
        info_ok(){
            const _self = this;

            if(!this.form.name || !this.form.description){
                this.$Message.error('创建失败，请完善表单信息');
                return
            }
            if(_self.form.type=="create"){
                api.post('/associations',{
                    name:_self.form.name,
                    description:_self.form.description,
                    tags:_self.form.tag.tagList,
                    logo:_self.form.logo
                }).then(response => {
                    _self.getAssociationList()
                    _self.$Message.success('创建成功');
                    _self.info_cancel();
                }).catch(e => {
                    console.log(e)
                    _self.$Message.error('创建失败: ' + e.message);
                })
            }
            else{
                api.put('/associations/'+_self.form.assoId, {
                    name:_self.form.name,
                    description:_self.form.description,
                    tags:_self.form.tag.tagList,
                    logo:_self.form.logo
                }).then(response => {
                    _self.getAssociationList()
                    _self.$Message.success('修改成功');
                    _self.info_cancel();
                })
                .catch(e => {
                    console.log(e)
                    _self.$Message.error('修改失败: ' + e.message);
                })
            }
        },
        info_cancel(){
            this.form.name=''
            this.form.description=''
            this.form.tag.tagList=[]
            this.form.materials=[]
            this.form.type=''
            this.form.logo=''
            this.form.onshow = false
        }
    },
    mounted() {
		this.getAssociationList();
    }
}
</script>
