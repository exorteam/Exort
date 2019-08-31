
<template>
	<div class="article-editor">
		<Card>
			<div>
				<h1 v-if="isUpdating">文章ID: {{article.id}}</h1>
				<h1 v-else>创建新文章</h1>
			</div>
			<br>
			<Form :model="article" :label-width="80">
				<FormItem>
					<p v-if="isUpdating">作者: {{article.associationId}}, 创建时间: {{article.createTime}}, 上次修改时间: {{article.lastModifyTime}}</p>
				</FormItem>
				<FormItem label="标题">
					<Input v-model="article.title" style="width: 30%"></Input>
				</FormItem>
				<FormItem label="正文内容">
					<Input v-model="article.content" type="textarea"></Input>
				</FormItem>
				<FormItem>
					<Button @click="commitChanges">提交</Button>
				</FormItem>
			</Form>
		</Card>
	</div>
</template>

<script>
import {api} from '@/http'

export default {
	name: "ArticleEditor",
	props:['id'],
	data: function(){
		return {
			isUpdating: true,
			article: {}
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
			if(this.isUpdating){
				api({
					method: 'put',
					url: '/articles/' + this.id,
					data: {
						id: this.id,
						title: this.article.title,
						content: this.article.content
					}
				}).then((res)=>{
					console.log(res);
					this.$router.push({name: 'AssociationAdminArticleList'});
				})
			}
			else{
				api({
					method: 'post',
					url: '/articles',
					data: {
						title: this.article.title,
						content: this.article.content,
						associationId: 'test'
					}
				}).then((res)=>{
					console.log(res);
					this.$router.push({name: 'AssociationAdminArticleList'});
				})
			}
		}
	},
	mounted:function(){
		if(this.id != 0){
			this.isUpdating = true;
			this.loadArticle(this.id);
		}
		else{
			this.isUpdating = false;
		}
	}
}
</script>
