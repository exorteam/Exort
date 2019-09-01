import {api} from '@/http'

const state = {
    nodePerm: {
        data: [],
        targetKeys: [],
        lifeStyle
    }
}

const mutations = {
    setNodePermTargetkeys(state, targetKey) {
        state.nodePerm.targetKeys = targetKey;
    },
    getMockData(state, data) {
        let mockData = [];
        let parent = data;
        for (let i = 0; i < parent.length; i++) {
            mockData.push({
                key: parent[i].name,
                label: parent[i].name,
                description: parent[i].description,
            });
        }
        state.nodePerm.data = mockData;
    },
    getTargetKeys(state, data) {
        let target = data;
        // console.log(target);
        let ret = [];
        for (let i = 0; i < target.length; i++) {
            ret.push(target[i].name);
        }
        state.nodePerm.targetKeys = ret;
    },
}

const actions = {
    getParentPermList: ({commit}, nodedata) => new Promise((resolve, reject) => {
        api({
            method: 'get',
            url: '/associations/' + nodedata.department.associationId + "/departments/" + nodedata.department.parentId + '/permissions'
        }).then(res => {
            if (res.data.data) {
                commit('getMockData', res.data.data);
                resolve();
            } else {
                commit('getMockData', []);
                reject(res.data);
            }
        }).catch(err => {
            reject(err);
        });
    }),
    getChildPermList: ({commit}, nodedata) => new Promise((resolve, reject) => {
        api({
            method: 'get',
            url: '/associations/' + nodedata.department.associationId + "/departments/" + nodedata.department.parentId + '/permissions'
        }).then(res => {
            if (res.data.data) {
                commit('getTargetKeys', res.data.data);
                resolve();
            } else {
                commit('getTargetKeys', []);
                reject(res.data);
            }
        }).catch(err => {
            reject(err);
        });
    }),
    deleteTargetkeys: ({commit}, {nodedata, data}) => new Promise((resolve, reject) => {
        api({
            method: 'delete',
            data: data,
            url: '/associations/' + nodedata.department.associationId + "/departments/" + nodedata.department.parentId + '/permissions'
        }).then(res => {
            if (res.data.data) {
                commit('setNodePermTargetkeys', res.data.data);
                resolve();
            } else {
                reject(res.data.message);
                this.$Message.error(res.data.message);
            }
        }).catch(err => {
            reject(err);
        });
    }),
    addTargetkeys: ({commit}, {nodedata, data}) => new Promise((resolve, reject) => {
        api({
            method: 'post',
            data: data,
            url: '/associations/' + nodedata.department.associationId + "/departments/" + nodedata.department.parentId + '/permissions'
        }).then(res => {
            if (res.data.data) {
                commit('setNodePermTargetkeys', res.data.data);
                resolve();
            } else {
                reject(res.data.message);
                this.$Message.error(res.data.message);
            }
        }).catch(err => {
            reject(err);
        });
    }),
};

export default {
    namespaced: true,
    state,
    actions,
    mutations
}
