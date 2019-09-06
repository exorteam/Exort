<template>
	<div class="article-list">
		<Card>
			<Row type="flex" justify="space-between">
				<Col span="6">
					<Input search @on-search="onClickSearch" placeholder="Search in articles" style="width:300px"/>
				</Col>
				<Col span="2">
					<Button @click="onClickCreate">创建</Button>
				</Col>
			</Row>
			<br><br>
			<Table :columns="columns" :data="list">
				<template slot-scope="{ row,index}" slot="publish">
					<Checkbox v-model="row.published" @on-change="togglePublish(row.id, row.published)">发布</Checkbox>
				</template>
				<template slot-scope="{ row, index }" slot="action">
					<Button @click="onClickView(row.id)">查看</Button>
					<Button @click="onClickEdit(row.id)">编辑</Button>
					<Button @click="onClickDelete(row.id)">删除</Button>
				</template>
			</Table>
			<br>
			<Page :total="this.pageTotalSize" :current="this.pageCurrentNum" :page-size="this.pageSize" @on-change="onChangePage"/>
		</Card>
	</div>
</template>

<script>
import { mapState, mapMutations, mapActions } from 'vuex'

export default {
	name: "ArticleList",
	data: function(){
		return {
			list:[],
			pageTotalSize: 0,
			pageCurrentNum: 1,
			pageSize: 4,
			columns:[
				{title:'标题',key:'title'},
				{title:'作者',key:'associationId'},
				{title:'发布状态',slot:'publish'},
				{title:'上次修改时间',key:'lastModifyTime'},
				{title:'操作',slot:'action'},
			],
			searchKeyword:'',
		}
	},
	computed:{
		...mapState('associationAdmin/currentAssociation', {
			assoId:'id',
			assoName:'name'
		}),
	},
	methods: {
		...mapActions('common/articleOps',[
			'queryPagedArticlesWithFilter',
			'publishArticleById'
		]),
		togglePublish(id,publish) {
			this.publishArticleById({
				id,
				publish
			})
		},
		loadArticles(keyword,pageNum) {
			if(keyword){
				this.searchKeyword = keyword;
			}
			this.queryPagedArticlesWithFilter({
				keyword,
				authorIds: [this.assoId],
				pageNum: pageNum?pageNum-1:this.pageCurrentNum -1,
				pageSize: this.pageSize
			}).then(res => {
				this.list = res.data.data.content;
				this.pageTotalSize = res.data.data.totalSize;
			}).catch((err)=>{
				console.log(err);
			})
		},
		onChangePage(newValue){
			console.log(newValue);
			this.loadArticles(this.searchKeyword,newValue);
		},
		onClickView(id){
			//console.log(id);
			this.$router.push({ name: 'AssociationAdminArticleReader', params: { id: id }});
		},
		onClickEdit(id){
			//console.log(id);
			this.$router.push({ name: 'AssociationAdminArticleEditor', params: { id: id }});
		},
		onClickDelete(id){
			//console.log(id);
			api({
				method: 'delete',
				url: '/articles/' + id,
			}).then((res)=>{
				console.log(res);
				this.loadArticles();
			})
		},
		onClickCreate(){
			this.$router.push({
				name: 'AssociationAdminArticleEditor',
				params: { id: 0}
			});
		},
		onClickSearch(search){
			this.loadArticles(search);
		},
	},
	mounted() {
		this.loadArticles();
	}
}
</script>
