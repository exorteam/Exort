
<template>
	<div class="article-editor">
		<Card>
			<div>
				<div v-if="isUpdating">
					<h1 style="display:inline">编辑文章</h1>
					<p v-if="isUpdating">[{{article.id}}]
						创建于 {{article.createTime && article.createTime.toLocaleString()}}
						最近修改于 {{article.lastModifyTime && article.lastModifyTime.toLocaleString()}}
					</p>
				</div>
				<h1 v-else>创建新文章</h1>
			</div>
			<br>
			<Form :model="article" :label-width="40">
				<FormItem label="标题">
					<Input v-model="article.title" style="width: 30%" />
				</FormItem>
			</Form>
			<MdEditor ref="md" v-model="article.content" />
			<Button @click="commitChanges">提交</Button>
		</Card>
	</div>
</template>

<script>
import {api} from '@/http'
import { mapState, mapMutations, mapActions } from 'vuex'

import MdEditor from '@/components/MdEditor'

export default {
	name: "ArticleEditor",
	components: { MdEditor },
	props:['id'],
	data: function(){
		return {
			isUpdating: true,
			article: {}
		}
	},
	computed:{
		...mapState('associationAdmin/currentAssociation', {assoId:'id'}),
	},
	methods: {
		loadArticle: function(id){
			// load article through axios
			api({
				method: 'get',
				url:'/articles/'+id,
			}).then((res)=>{
				this.article = res.data.data;
				this.article.createTime = new Date(this.article.createTime);
				this.article.lastModifyTime = new Date(this.article.lastModifyTime);
				this.$nextTick(()=>{this.$refs.md.$reload().then(res => console.log(res))});
			}).catch((err)=>{
				console.log(err);
			})
		},
		commitChanges: function(){
			this.$refs.md.$save().then(() => {
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
							associationId: this.assoId
						}
					}).then((res)=>{
						console.log(res);
						this.$router.push({name: 'AssociationAdminArticleList'});
					})
				}
			})
		}
	},
	mounted:function(){
		//console.log(this.assoId);
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

<style>
.article-editor .md-editor {
	height: 480px;
}
</style>
