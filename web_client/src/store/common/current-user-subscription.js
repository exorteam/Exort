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
			resolve();
		}).catch(err => {
            if (err.response && err.response.data) {
                reject(err.response.data);
            } else {
                reject({ error: 'unknown', message: err });
            }
        });
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
			resolve();
		}).catch(err => {
            if (err.response && err.response.data) {
                reject(err.response.data);
            } else {
                reject({ error: 'unknown', message: err });
            }
        });
	}),
	refreshSubscription: ({commit}) => new Promise((resolve,reject)=>{
		api({
			method: 'get',
			url: '/com/sub'
		}).then(res => {
			commit('setSubscribed',{
				ids:res.data.data
			});
			resolve();
		}).catch(err => {
            if (err.response && err.response.data) {
                reject(err.response.data);
            } else {
                reject({ error: 'unknown', message: err });
            }
        });
	})
}

export default {
    namespaced: true,
    state,
    actions,
    mutations
}

