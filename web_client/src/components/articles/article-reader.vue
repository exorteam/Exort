<template>
	<div class="article-reader">
		<Card>
			<h3>title: {{article.title}}</h3>
			<p>content: {{article.content}}</p>
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

