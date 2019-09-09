<template>
<ContentPage>
	<template #header>
		<h2>{{article.title}}</h2>
		<div style="line-height:normal">
		作者: {{article.associationId}}
		<br>
		创建时间: {{article.createTime}}
		<br>
		上次修改时间: {{article.lastModifyTime}}
		</div>
	</template>
	<MdEditor ref="md" :value="article.content" read-mode />
	<Comment type="article"></Comment>
</ContentPage>
</template>

<script>
import {api} from '@/http'
import MdEditor from '@/components/MdEditor'
import ContentPage from '@/components/ContentPage'
import Comment from '@/components/Comment'

export default {
	name: "UserArticleReader",
	props:['id'],
    components: {Comment,MdEditor,ContentPage},
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
