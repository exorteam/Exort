<template>
	<div class="article-list">
		<Card>
			<Input type="text" v-model="searchContent" placeholder="Search in articles" style="width:300px"/>
			<Button @click="onClickSearch">搜索</Button>
			<br><br>
			<Table :columns="columns" :data="list">
				<template slot-scope="{ row, index }" slot="action">
					<Button @click="onClickView(row.id)">查看</Button>
					<Button @click="onClickEdit(row.id)">编辑</Button>
					<Button @click="onClickDelete(row.id)">删除</Button>
				</template>
			</Table>
			<br>
			<Button @click="onClickCreate">创建</Button>
		</Card>
	</div>
</template>

<script>
import {api} from '@/http'
import { mapState, mapMutations, mapActions } from 'vuex'

export default {
	name: "ArticleList",
	data: function(){
		return {
			list:[],
			columns:[
				{title:'标题',key:'title'},
				{title:'作者',key:'associationId'},
				{title:'已发布',key:'published'},
				{title:'上次修改时间',key:'lastModifyTime'},
				{title:'操作',slot:'action'},
			],
			searchContent:''
		}
	},
	computed:{
		...mapState('associationAdmin/currentAssociation', {
			assoId:'id',
			assoName:'name'
		}),
	},
	methods: {
		loadArticles: function(keyword){
			api({
				method: 'post',
				url:'/articles/list?pageNum=0&pageSize=10',
				data: {
					keyword: keyword
				}
			}).then((res)=>{
				//console.log(res);
				if(res.data.data.content){
					this.list = res.data.data.content.map((e)=>{
						e.published = (e.state!=0);
						const d = new Date(e.lastModifyTime);
						e.lastModifyTime = d.toLocaleString();
						return e;
					});
				}
				else{
					// error
				}
			}).catch((err)=>{
				console.log(err);
			})
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
		onClickSearch(){
			this.loadArticles(this.searchContent);
		}
	},
	mounted:function(){
		//console.log(this.id);
		this.loadArticles(this.assoId);
	}
}
</script>
