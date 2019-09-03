import {api} from '@/http'

const state = {
	info: null
}

const mutations = {
	setInfo(state,{info}) {
		state.info = info;

		const tt = new String(state.info.birthday);
		state.info.birthday = tt.substr(0,10);

		switch(state.info.gender){
			case 0:
				state.info.gender = 'Secret';break;
			case 1:
				state.info.gender = 'Male';break;
			case 2:
				state.info.gender = 'Female';
		}
	}
}

const actions = {
	refreshInfo: ({commit}) => new Promise((resolve,reject)=>{
		api({
			method: 'get',
			url: '/users/self'
		}).then(res => {
			commit('setInfo', {
				info: res.data.data
			});
			resolve(res);
		}).catch(err => {
            if (err.response && err.response.data) {
                reject(err.response.data);
            } else {
                reject({ error: 'unknown', message: err });
            }
        });
	}),
	updateInfo: ({commit},{uinfo}) => new Promise((resolve,reject) => {
		api({
			method: 'post',
			url: '/users/'+uinfo.id,
			data: uinfo
		}).then(res => {
			commit('setInfo', {
				info: res.data.data
			});
			resolve(res);
		}).catch(err => {
            if (err.response && err.response.data) {
                reject(err.response.data);
            } else {
                reject({ error: 'unknown', message: err });
            }
        });
	}),
}

export default {
    namespaced: true,
    state,
    actions,
    mutations
}

