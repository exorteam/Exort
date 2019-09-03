<template>
	<div class="article-list">
		<Card>
			<Input type="text" v-model="searchContent" placeholder="Search in articles" style="width:300px"/>
			<Button @click="onClickSearch">搜索</Button>
			<br><br>
			<Table :columns="columns" :data="list" @on-row-click="onClickView"></Table>
		</Card>
	</div>
</template>

<script>
import {api} from '@/http'
import { mapState, mapMutations, mapActions } from 'vuex'

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
			searchContent:''
		}
	},
	computed: {
		...mapState('common/currentUserSubscription',['subscribed']),
		...mapState('associationAdmin/currentAssociation', {
			assoId:'id',
			assoName:'name'
		}),
	},
	methods: {
		...mapActions('common/currentUserSubscription',['refreshSubscription']),
		loadArticles() {
			api({
				method: 'post',
				url:'/articles/list/asso?pageNum=0&pageSize=10',
				data: this.subscribed
			}).then((res)=>{
				//console.log(res);
				if(res.data.data.content){
					this.list = res.data.data.content.map((e)=>{
						e.published = (e.state!=0);
						const d = new Date(e.lastModifyTime);
						e.lastModifyTime = d.toLocaleString();
						return e;
					}).filter(e => {
						if(e.published)return true;
						return false;
					})
				}
				if(!this.list)this.list = [];
				else{
					// error
				}
			}).catch((err)=>{
				console.log(err);
			})
		},
		onClickView(row){
			//console.log(row);
			this.$router.push({ name: 'UserArticleReader', params: { id: row.id }});
		},
		onClickSearch(){
			this.loadArticles(this.searchContent);
		}
	},
	mounted:function(){
		//console.log(this.id);
		this.refreshSubscription();
		this.loadArticles();
	}
}
</script>
