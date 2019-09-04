<template>
	<div class="article-list">
		<Card>
			<Input search @on-search="onClickSearch" placeholder="Search in articles" style="width:300px"/>
			<br><br>
			<Table :columns="columns" :data="list" @on-row-click="onClickView"></Table>
		</Card>
	</div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
	name: "UserArticleList",
	data: function(){
		return {
			list:[],
			columns:[
				{title:'标题',key:'title'},
				{title:'作者',key:'associationId'},
				{title:'上次修改时间',key:'lastModifyTime'},
			],
		}
	},
	computed: {
		...mapState('common/currentUserSubscription',['subscribed']),
	},
	methods: {
		...mapActions('common/currentUserSubscription',['refreshSubscription']),
		...mapActions('common/articleOps',['queryPagedArticlesByAssociation']),
		loadArticles() {
			this.queryPagedArticlesByAssociation({
				assoIds: this.subscribed
			}).then(res => {
				this.list = res;
			}).catch((err)=>{
				console.log(err);
			})
		},
		onClickView(row){
			//console.log(row);
			this.$router.push({ name: 'UserArticleReader', params: { id: row.id }});
		},
		onClickSearch(search){
			//this.loadArticles(search)
			console.log(search);
		}
	},
	mounted(){
		//console.log(this.id);
		this.refreshSubscription().then(()=>{
			this.loadArticles();
		})
	}
}
</script>
