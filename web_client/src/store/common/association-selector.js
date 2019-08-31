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
            // TODO: use correct api
            let associations = [];
            for (let i in ids) {
                let id = ids[i];
                associations[i] = {id, name: '社团'+id};
            }
            commit('setAssociations', {associations});
            resolve();
            /*
            api({
                method: 'get',
                url: '/associations',
                params: { ids }
            }).then(res => {
                commit('setAssociations', {associations:res.data});
                resolve();
            }).catch(err => reject(err));
            */
        } else {
            commit('setAssociations', {associations: []});
            resolve();
        }
    }),
    listByFilters: ({commit}, filter) => new Promise((resolve, reject) => {
        // TODO: use correct api
        commit('setAssociations', {
            associations: [
                {id: 1, name: '社团1'/*, ...*/},
                {id: 2, name: '社团2'/*, ...*/},
                {id: 3, name: '社团3'/*, ...*/}
            ],
            pageable: {
                pageNum: 0,
                pageSize: 10,
                totalSize: 3
            }
        })
        resolve();
        /*
        api({
            method: 'get',
            url: '/associations',
            params: filter
        }).then(res => {
            commit('setAssociations', {
                associations: res.data.content,
                pageable: res.data
            });
            resolve();
        }).catch(err => reject(err));
        */
    })
}

export default {
    namespaced: true,
    state,
    actions,
    mutations
}
