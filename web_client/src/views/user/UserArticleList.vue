<template>
	<div class="article-list">
		<Card>
			<Input search @on-search="onClickSearch" placeholder="Search in articles" style="width:300px"/>
			<br><br>
			<Table :columns="columns" :data="list" @on-row-click="onClickView"></Table>
			<br>
			<Page :total="this.pageTotalSize" :current="this.pageCurrentNum" :page-size="this.pageSize" @on-change="onChangePage"/>
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
			pageTotalSize: 0,
			pageCurrentNum: 1,
			pageSize: 5,
			columns:[
				{title:'标题',key:'title'},
				{title:'作者',key:'associationId'},
				{title:'上次修改时间',key:'lastModifyTime'},
			],
			searchKeyword:'',
		}
	},
	computed: {
		...mapState('common/currentUserSubscription',['subscribed']),
        ...mapState('common/currentUser', ['uid'])
	},
	methods: {
		...mapActions('common/currentUserSubscription',['refreshSubscription']),
		...mapActions('common/articleOps',['queryPagedArticlesWithFilter']),
		loadArticles(keyword,pageNum) {
			this.searchKeyword = keyword;
			this.queryPagedArticlesWithFilter({
				keyword,
				authorIds: this.subscribed,
				state: 1,
				pageNum: pageNum?pageNum-1:this.pageCurrentNum -1,
				pageSize: this.pageSize
			}).then(res => {
				this.list = res.data.data.content;
				this.pageTotalSize = res.data.data.totalSize;
			}).catch((err)=>{
				console.log(err);
			})
		},
		onChangePage(newValue){
			console.log(newValue);
			this.loadArticles(this.searchKeyword,newValue);
		},
		onClickView(row){
			//console.log(row);
			this.$router.push({ name: 'UserArticleReader', params: { id: row.id }});
		},
		onClickSearch(search){
			this.loadArticles(search)
		}
	},
	mounted(){
		if (this.uid) {
			this.refreshSubscription().then(()=>{
				this.loadArticles();
			})
		}
	},
    watch: {
        'uid': function(newUid, oldUid) {
            if (newUid) {
				this.refreshSubscription().then(()=>{
					this.loadArticles();
				})
            }
        }
    }
}
</script>
