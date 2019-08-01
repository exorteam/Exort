<template>
	<div class="article-list">
		<Card>
			<h1>Article List</h1>
			<Table :columns="columns" :data="list">
				<template slot-scope="{ row, index }" slot="action">
					<Button @click="onClickView(row.id)">View</Button>
					<Button @click="onClickEdit(row.id)">Edit</Button>
					<Button @click="onClickDelete(row.id)">Delete</Button>
				</template>
			</Table>
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
				{title:'Modify',key:'lastModifyTime'},
				{title:'Action',slot:'action'},
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
				//console.log(res);
				if(res.data.data){
					this.list = res.data.data.map((e)=>{
						e.published = (e.state!=0);
						e.lastModifyTime = e.lastModifyTime.substring(0,10);
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
		}
	},
	mounted:function(){
		this.loadArticles('');
	}
}
</script>


