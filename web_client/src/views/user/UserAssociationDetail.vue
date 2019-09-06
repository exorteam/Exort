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
					<Button>申请加入</Button>
					<Button v-if="isAdmin" @click="routeToAssoAdmin" style="margin-left:10px">社团管理</Button>
				</div>
			</Col>
			<Col span="8">
				<img :src="asso.logo"  style="width:120px;height:120px"/>
			</Col>
		</Row>
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

		}
	},
	computed: {
		...mapState('common/currentUser',['admin']),
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
		...mapActions('common/articleOps',['queryPagedArticlesWithFilter']),
		routeToAssoAdmin(){
			this.queryAssociationById({
				assoId: this.assoId
			}).then(res=>{
				this.setAssociation({
					id: res.data.data.id,
					name: res.data.data.name
				})
				this.$router.push({name:'AssociationMemList'});
			});
		},
		searchArticle(search,newPageNum){
			if(search){this.articlePageAttr.keyword = search;}
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

	},
	mounted() {
		this.queryAssociationById({
			assoId: this.assoId
		}).then(res => {
			this.asso = res.data.data
		})

		this.searchArticle();
	}
}

</script>

