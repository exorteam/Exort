import {api} from '@/http'

const state = {
	subscribed:[]
}

const mutations = {
	setSubscribed(state,{ids}) {
		state.subscribed = ids;
	}
}

const actions = {
	commitSubscription: ({commit} , {ids}) => new Promise((resolve,reject)=>{
		api({
			method: 'post',
			url: '/com/sub',
			data: ids
		}).then(res => {
			commit('setSubscribed',{
				ids:res.data.data
			});
			resolve(res);
		}).catch(err => reject(err));
	}),
	removeSubscription: ({commit} , {ids}) => new Promise((resolve,reject)=>{
		api({
			method: 'delete',
			url: '/com/sub',
			data: ids
		}).then(res => {
			commit('setSubscribed',{
				ids:res.data.data
			});
			resolve(res);
		}).catch(err => reject(err));
	}),
	refreshSubscription: ({commit}) => new Promise((resolve,reject)=>{
		api({
			method: 'get',
			url: '/com/sub'
		}).then(res => {
			commit('setSubscribed',{
				ids:res.data.data
			});
			resolve(res);
		}).catch(err => reject(err));
	})
}

export default {
    namespaced: true,
    state,
    actions,
    mutations
}
