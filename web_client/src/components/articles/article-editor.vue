
<template>
	<div class="article-editor">
		<Card>
			<h1>ID: {{article.id}}</h1>
			<p>Edited by: {{article.associationId}}, Created: {{article.createTime}}, Last modified: {{article.lastModifyTime}}</p>
			<Form :model="article" :label-width="80">
				<FormItem label="Title">
					<Input v-model="article.title" style="width: 30%"></Input>
				</FormItem>
				<FormItem label="Content">
					<Input v-model="article.content" type="textarea"></Input>
				</FormItem>
				<FormItem>
					<Button @click="commitChanges">Commit</Button>
				</FormItem>
			</Form>
		</Card>
	</div>
</template>

<script>

export default {
	name: "ArticleEditor",
	props:['id'],
	data: function(){
		return {
			article:{}
		}
	},
	methods: {
		loadArticle: function(id){
			// load article through axios
			this.axios({
				method: 'get',
				url:'/articles/'+id,
			}).then((res)=>{
				console.log(res);
				if(res.data.data){
					this.article = res.data.data;
					this.article.createTime = this.article.createTime.substring(0,10);
					this.article.lastModifyTime = this.article.lastModifyTime.substring(0,10);
				}
				else{
					// error
				}
			}).catch((err)=>{
				console.log(err);
			})
		},
		commitChanges: function(){
			this.axios({
				method: 'put',
				url: '/articles/' + this.id,
				data: {
					id: this.id,
					title: this.article.title,
					content: this.article.content
				}
			}).then((res)=>{
				console.log(res);
			})
		}
	},
	mounted:function(){
		this.loadArticle(this.id);
	}
}
</script>


