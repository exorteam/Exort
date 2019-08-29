<template>
	<div class="article-reader">
		<Card>
			<h2>{{article.title}}</h2>
			<hr>
			<p>作者: {{article.associationId}}, 创建时间: {{article.createTime}}, 上次修改时间: {{article.lastModifyTime}}</p>
			<br>
			<p>{{article.content}}</p>
		</Card>
	</div>
</template>

<script>
import {api} from '@/http'
export default {
	name: "SysArticleReader",
	props:['id'],
	data: function(){
		return {
			article:{}
		}
	},
	methods: {
		loadArticle: function(id){
			// load article through axios
			api({
				method: 'get',
				url:'/articles/'+id,
			}).then((res)=>{
				console.log(res);
				if(res.data.data){
					this.article = res.data.data;
					const cTime = new Date(this.article.createTime);
					this.article.createTime = cTime.toLocaleString();
					const lmTime = new Date(this.article.lastModifyTime);
					this.article.lastModifyTime = lmTime.toLocaleString();
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
