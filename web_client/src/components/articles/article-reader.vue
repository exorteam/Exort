<template>
	<div class="article-reader">
		<Card>
			<h2>{{article.title}}</h2>
			<p>Author: {{article.associationId}} Created: {{article.createTime}}, Last modified: {{article.lastModifyTime}}</p>
			<hr>
			<p>{{article.content}}</p>
		</Card>
	</div>
</template>

<script>

export default {
	name: "ArticleReader",
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
		}
	},
	mounted:function(){
		this.loadArticle(this.id);
	}
}
</script>

