<template>
	<div class="article-reader">
		<Card>
			<h2>{{article.title}}</h2>
			<hr>
			<p>作者: {{article.associationId}}, 创建时间: {{article.createTime}}, 上次修改时间: {{article.lastModifyTime}}</p>
			<br>
			<MdEditor ref="md" :value="article.content" read-mode />
		</Card>
		<Comment type="article"></Comment>
	</div>
</template>

<script>
import {api} from '@/http'
import MdEditor from '@/components/MdEditor'
import Comment from '@/components/Comment'

export default {
	name: "UserArticleReader",
	props:['id'],
    components: {Comment,MdEditor},
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
					this.article = res.data.data;
				const cTime = new Date(this.article.createTime);
				this.article.createTime = cTime.toLocaleString();
				const lmTime = new Date(this.article.lastModifyTime);
				this.article.lastModifyTime = lmTime.toLocaleString();
				this.$nextTick(()=>{this.$refs.md.$reload()});
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
