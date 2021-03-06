import {api} from '@/http'

const actions = {
	queryPagedArticlesByAssociation: ({commit},{assoIds}) => new Promise((resolve,reject) => {
			api({
				method: 'post',
				url:'/articles/list/asso?pageNum=0&pageSize=10',
				data: assoIds
			}).then( res =>{
				var ret = [];
				if(res.data.data.content){
					ret = res.data.data.content.map((e)=>{
						e.published = (e.state!=0);
						const d = new Date(e.lastModifyTime);
						e.lastModifyTime = d.toLocaleString();
						return e;
					}).filter(e => {
						if(e.published)return true;
						return false;
					})
				}
				else{
					ret = [];
				}
				resolve(ret);
			}).catch(err => reject(err));
	}),
	queryPagedArticlesWithFilter: ({commit},filter) => new Promise((resolve,reject) => {
		api({
			method: 'post',
			url:'/articles/list',
			data: filter,
			params: {
				pageNum: filter.pageNum?filter.pageNum:0,
				pageSize: filter.pageSize?filter.pageSize:5
			}
		}).then( res =>{
			res.data.data.content = res.data.data.content.map((e)=>{
				e.published = (e.state!=0);
				const d = new Date(e.lastModifyTime);
				e.lastModifyTime = d.toLocaleString();
				return e;
			})
			resolve(res);
		}).catch(err => reject(err));
	}),
	publishArticleById: ({commit},{id,publish}) =>new Promise((resolve,reject) => {
		api({
			method: 'post',
			url: '/articles/'+id+'/publish',
			params: {
				publish
			}
		}).then(res => {
			resolve(res);
		}).catch(err => reject(err));
	}),


}

export default {
    namespaced: true,
    actions,
}
