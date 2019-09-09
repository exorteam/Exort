<template>
	<Card>
		<Row type="flex" justify="space-between">
			<Col span="8">
				<div>
					<p>#{{assoId}}</p>
					<h1>{{asso.name}}</h1>
				</div>
				<div style="margin-top:20px">
					<p>"{{asso.description}}"</p>
				</div>
				<div style="margin-top:20px">
					<Button v-if="!isMember" @click="showSignup">申请加入</Button>
					<Button v-else>退出社团</Button>
					<Button v-if="isAdmin" @click="routeToAssoAdmin" style="margin-left:10px">社团管理</Button>
				</div>
			</Col>
			<Col span="8">
				<img :src="asso.logo"  style="width:120px;height:120px"/>
			</Col>
		</Row>
		<Modal v-model="signuping" :title="'申请加入社团 '+asso.name" @on-ok="sighup">
			<Form :label-width="80">
				<Spin v-if="loading">加载部门中</Spin>
				<FormItem label="选择部门">
					<Select multiple v-model="selectedDepartments">
						<Option v-for="dept in departments" :key="dept.departmentId"
								:value="dept.departmentId" :label="dept.name" />
					</Select>
				</FormItem>
			</Form>
		</Modal>
		<br>
		<Row type="flex" justify="space-between">
			<Col span="10">
				<Card>
					<Input search @on-search="searchArticle" placeholder="Search in articles" style="width:80%"/>
					<br><br>
					<Table :columns="articleColumns" :data="articleList" @on-row-click="viewArticle"></Table>
					<br>
					<Page :total="articlePageAttr.totalSize" :current="articlePageAttr.currentNum" :page-size="articlePageAttr.pageSize" @on-change="onChangeArticlePage"/>
				</Card>
			</Col>
			<Col span="10">
				<Card>
					<p>activity list</p>
				</Card>
			</Col>
		</Row>
	</Card>
</template>

<script>
import {api} from '@/http'
import { mapState, mapMutations, mapActions } from 'vuex'

export default {
	name: 'UserAssociationDetail',
	props: ['assoId'],
	data() {
		return {
			asso: {},

			articleList:[],
			articlePageAttr:{
				totalSize: 0,
				currentNum: 1,
				pageSize: 5,
				keyword: ''
			},
			articleColumns:[
				{title:'标题',key:'title'},
				{title:'作者',key:'associationId'},
				{title:'上次修改时间',key:'lastModifyTime'},
			],
			signuping: false,
			loading: false,
			departments: [],
			selectedDepartments: [],
			isMember: false
		}
	},
	computed: {
		...mapState('common/currentUser',[
			'admin',
			'uid'
		]),
		isAdmin() {
			//TODO: replace return true after debug
			return true;
			return this.admin.assoAdmins.includes(this.assoId);
		}
	},
	methods: {
        ...mapMutations('associationAdmin/currentAssociation', [
			'setAssociation'
		]),
		...mapActions('common/associationOps',[
			'queryAssociationById'
		]),
		...mapActions('common/currentUser',[
			'getUserAssociations'
		]),
		...mapActions('common/articleOps',['queryPagedArticlesWithFilter']),
		routeToAssoAdmin(){
			this.queryAssociationById({
				assoId: this.assoId
			}).then(res=>{
				this.setAssociation({
					id: res.data.data.id,
					name: res.data.data.name
				})
				this.$router.push({name:'AssociationMemList',params:{assoId:this.assoId}});
			});
		},
		searchArticle(search,newPageNum){
			this.articlePageAttr.keyword = search;
			this.queryPagedArticlesWithFilter({
				authorIds: [this.assoId],
				keyword: search,
				state: 1,
				pageNum: newPageNum?newPageNum-1:this.articlePageAttr.currentNum-1,
				pageSize: this.articlePageAttr.pageSize
			}).then(res => {
				this.articleList = res.data.data.content;
				this.articlePageAttr.totalSize = res.data.data.totalSize;
			})

		},
		onChangeArticlePage(newValue) {
			this.searchArticle(this.articlePageAttr.keyword,newValue);
		},
		viewArticle(row){
			this.$router.push({ name: 'UserArticleReader', params: { id: row.id }});
		},
		showSignup() {
			this.signuping = true;
			this.loading = true;
			api({
				method: 'get',
				url: '/associations/' + this.assoId + '/departments'
			}).then(res => {
				this.departments = res.data.data;
			}).catch(err => {
				this.$Notice.error({
					title: '获取部门失败',
					desc: err.message || err
				});
			}).finally(() => {
				this.loading = false;
			});
		},
		sighup() {
			api({
				method: 'post',
				url: '/applications/signup_association_member',
				data: {
					associationId: this.assoId,
					departmentId: this.selectedDepartments
				}
			}).then(res => {
				this.$Notice.success({
					title: '申请加入社团成功',
					desc: '请等待社团管理员审核'
				});
			}).catch(err => {
				this.$Notice.error({
					title: '申请失败',
					desc: err.message || err
				})
			})
		}
	},
	mounted() {
		this.queryAssociationById({
			assoId: this.assoId
		}).then(res => {
			this.asso = res.data.data
		})

		this.getUserAssociations({
			uid: this.uid
		}).then(res => {
			console.log(res)
			const assos = res.data.data;
			this.isMember = assos.includes(this.assoId);
		})

		this.searchArticle();
	}
}

</script>
