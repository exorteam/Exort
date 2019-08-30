<template>
	<div class="article-list">
		<Card>
			<h1>文章列表</h1>
			<br>
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

export default {
	name: "SysArticleList",
	data: function(){
		return {
			list:[],
			columns:[
				{title:'标题',key:'title'},
				{title:'作者',key:'associationId'},
				{title:'已发布',key:'published'},
				{title:'上次修改时间',key:'lastModifyTime'},
				{title:'操作',slot:'action'},
			]
		}
	},
	methods: {
		loadArticles: function(keyword){
			api({
				method: 'post',
				url:'/articles/list',
				data: {
					keyword: keyword
				}
			}).then((res)=>{
				//console.log(res);
				if(res.data.data){
					this.list = res.data.data.map((e)=>{
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
			this.$router.push({ name: 'ArticleReader', params: { id: id }});
		},
		onClickEdit(id){
			//console.log(id);
			this.$router.push({ name: 'ArticleEditor', params: { id: id }});
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
				name: 'ArticleEditor',
				params: { id: 0}
			});
		}
	},
	mounted:function(){
		this.loadArticles('');
	}
}
</script>
