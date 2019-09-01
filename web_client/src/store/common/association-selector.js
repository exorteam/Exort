import {api} from '@/http'

const state = {
    associations: [],
    pageNum: 0,
    pageSize: 1,
    totalSize: 0
}

const mutations = {
    setAssociations(state, {associations, pageable}) {
        state.associations = associations || [];
        pageable = pageable || {pageNum: 0, pageSize: 1, totalSize: 0};
        state.pageNum = pageable.pageNum;
        state.pageSize = pageable.pageSize;
        state.totalSize = pageable.totalSize;
    }
}

const actions = {
    listByIds: ({commit}, ids) => new Promise((resolve, reject) => {
        if (ids && ids.length) {
            api({
                method: 'post',
                url: '/associations/batch?pageNum=0&pageSize=10',
				data: ids
            }).then(res => {
				//console.log(res);
                commit('setAssociations', {associations:res.data.data.content});
                resolve();
            }).catch(err => reject(err));
        } else {
            commit('setAssociations', {associations: []});
            resolve();
        }
    }),
    listByFilters: ({commit}, filter) => new Promise((resolve, reject) => {
		console.log(filter);
		api({
			method: 'post',
			url: '/associations/list?pageNum=0&pageSize=10',
			data: filter
		}).then(res => {
			//console.log(res);
			commit('setAssociations', {
				associations: res.data.data.content,
				pageable: res.data.data
			});
			resolve();
		}).catch(err => reject(err));
		
    })
}

export default {
    namespaced: true,
    state,
    actions,
    mutations
}
