import {api} from '@/http'

const state = {
    users: {
        list: [],
        pageNum: 0,
        pageSize: 20,
        totalSize: 0
    },
    curUser: {
        user: null,
        scope: 'sys',
        roles: []
    }
}

const mutations = {
    setUserList(state, {list, pageNum, pageSize, totalSize}) {
        state.users.list = list;
        state.users.pageNum = pageNum;
        state.users.pageSize = pageSize;
        state.users.totalSize = totalSize;
    },
    setUserState(state, {index, disabled}) {
        state.users.list[index].enabled = !disabled;
    },
    setCurUser(state, {user, scope, roles}) {
        state.curUser.user = user || null;
        state.curUser.scope = scope || 'sys';
        state.curUser.roles = roles || [];
    }
}

const actions = {
    updateUserList: ({commit}, {scope, role, pageNum, pageSize}) => new Promise((resolve, reject) => {
        api({
            method: 'get',
            url: '/users',
            params: {
                scope: scope || 'sys',
                role: (role && role.name) || null,
                pageNum: pageNum || null,
                pageSize: pageSize || null
            }
        }).then(res => {
			console.log(res);
            commit('setUserList', {
                list: res.data.data.content,
                pageNum: res.data.data.pageNum,
                pageSize: res.data.data.pageSize,
                totalSize: res.data.data.totalSize
            });
            resolve();
        }).catch(err => {
            commit('setUserList'), {
                list: [],
                pageNum: 0,
                pageSize: 20,
                totalSize: 0
            };
            reject(err);
        });
    }),
    changeUserState: ({commit, state}, {index, disabled}) => new Promise((resolve, reject) => {
        let uid = state.users.list[index].id;
        if (uid) {
            api({
                method: 'put',
                url: '/users/' + uid + '/state',
                params: {
                    disabled
                }
            }).then(res => {
                if (res.data.data) {
                    commit('setUserState', {index, disabled});
                    resolve();
                } else {
                    reject(res);
                }
            }).catch(err => {
                reject(err);
            });
        } else {
            reject(uid);
        }
    }),
    updateCurUser: ({commit}, {user, scope}) => new Promise((resolve, reject) => {
        scope = scope || 'sys';
        if (user && user.id) {
            api({
                method: 'get',
                url: '/users/' + user.id + '/roles/' + scope
            }).then(res => {
                commit('setCurUser', {
                    user,
                    scope,
                    roles: res.data.data
                });
                resolve();
            }).catch(err => {
                reject(err);
            })
        } else {
            commit('setCurUser', {
                user: null,
                scope: 'sys',
                roles: []
            });
            resolve();
        }
    }),
    grantCurUser: ({commit}, {user, scope, role}) => new Promise((resolve, reject) => {
        scope = scope || 'sys';
        if (user && user.id && role && role.name) {
            api({
                method: 'post',
                url: '/users/' + user.id + '/grant/' + scope,
                data: [role.name]
            }).then(res => {
                commit('setCurUser', {
                    user,
                    scope,
                    roles: res.data.data
                });
                resolve();
            }).catch(err => {
                reject(err);
            })
        } else {
            reject(user);
        }
    }),
    revokeCurUser: ({commit}, {user, scope, role}) => new Promise((resolve, reject) => {
        scope = scope || 'sys';
        if (user && user.id && role && role.name) {
            api({
                method: 'post',
                url: '/users/' + user.id + '/revoke/' + scope,
                data: [role.name]
            }).then(res => {
                commit('setCurUser', {
                    user,
                    scope,
                    roles: res.data.data
                });
                resolve();
            }).catch(err => {
                reject(err);
            })
        } else {
            reject(user);
        }
    })
}

export default {
    namespaced: true,
    state,
    actions,
    mutations
}
