import {api} from '@/http'

const state = {
    financeList: [],
}

const mutations = {
    setFinanceList(state, list) {
        state.financeList = list;
    }
}

const actions = {
    getAssociationBalance: ({commit, state}, associationId) => new Promise((resolve, reject) => {
        api({
            method: "get",
            url: "/associations/"+associationId+"/balance"
        }).then((res) => {
            resolve(res.data.data);
        }).catch(error => {
            reject(error);
        })
    }),
    createFinance: ({commit, state}, data) => new Promise((resolve, reject) => {
        api({
            method: "post",
            url: "/finances",
            data: data
        }).then((res) => {
            resolve(res.data.data);
        }).catch(error => {
            reject(error);
        })
    }),
    deleteFinance: ({commit, state}, id) => new Promise((resolve, reject) => {
        api({
            method: "delete",
            url: "/finances/"+id
        }).then((res) => {
            resolve(res.data.data);
        }).catch(error => {
            reject(error);
        })
    }),
    getAssociationFiances: ({commit, state}, {select,page}) => new Promise((resolve, reject) => {
        api({
            method: "post",
            url: "/finances/filters",
            data:select,
            params: {
                'pagesize': page.pageSize || 0,
                'pagenum': page.pageNum || 0,
                'sortby': "0",
            },
        }).then((res) => {
            console.log(res.data.data);
            if(res.data.data.content===null){
                commit('setFinanceList', []);
            }else{
                commit('setFinanceList', res.data.data.content);
            }
            resolve(res.data);
        }).catch(error => {
            reject(error);
        })
    })
}

export default {
    namespaced: true,
    state,
    actions,
    mutations
}
