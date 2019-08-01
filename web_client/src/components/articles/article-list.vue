<template>
	<div class="article-list">
		<Card>
			<h1>Article List</h1>
			<Table 
				:columns="columns" 
				:data="list"
		        @on-row-click="onClickArticleEntry"
			></Table>
		</Card>
	</div>
</template>

<script>

export default {
	name: "ArticleList",
	data: function(){
		return {
			list:[],
			columns:[
				{title:'Title',key:'title'},
				{title:'Author',key:'associationId'},
				{title:'Published',key:'published'},
				{title:'Modify',key:'lastModifyTime'}
			]
		}
	},
	methods: {
		loadArticles: function(keyword){
			this.axios({
				method: 'post',
				url:'/articles/list',
				data: {
					keyword: keyword
				}
			}).then((res)=>{
				console.log(res);
				if(res.data.data){
					this.list = res.data.data.map((e)=>{e.published = (e.state!=0);return e;});
				}
				else{
					// error
				}
			}).catch((err)=>{
				console.log(err);
			})
		},
		onClickArticleEntry(data){
			console.log(data);
			this.$router.push({ name: 'ArticleReader', params: { id: data.id }});
		}
	},
	mounted:function(){
		this.loadArticles('');
	}
}
</script>


