import {api, fe} from '@/http'

const state = {
    token: null,
    uid: null,
    username: null,
    admin: {
        isSysAdmin: false,
        assoAdmins: []
    }
}

const mutations = {
    logout(state) {
        delete api.defaults.headers.common.Authorization;

        state.token = null;
        state.uid = null;
        state.username = null;

        state.admin = {
            isSysAdmin: false,
            assoAdmins: []
        }
    },
    login(state, {token, uid, username}) {
        state.token = token;
        state.uid = uid;
        state.username = username;

        api.defaults.headers.common.Authorization = "Bearer " + token;
    },
    setAdmin(state, admin) {
        state.admin = admin || {
            isSysAdmin: false,
            assoAdmins: []
        };
    }
}

const actions = {
    logout: ({commit}) => {
        fe({
            method: 'post',
            url: '/auth/logout'
        }).catch(() => {
            console.log('[WARNING] failed to logout on server side.');
        }).finally(() => {
            commit('logout');
        });
    },
    login: ({commit}, {username, password}) => new Promise((resolve, reject) => {
        fe({
            method: 'post',
            url: '/auth/login',
            data: {
                'username': username,
                'password': password
            }
        }).then(res => {
            commit('login', {
                token: res.data.token,
                uid: res.data.uid,
                username: username
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
    getToken: ({commit}) => new Promise((resolve, reject) => {
        fe({
            method: 'get',
            url: '/auth/token'
        }).then(res => {
            commit('login', res.data);
            resolve();
        }).catch(err => {
            commit('logout');
            console.log('[ERROR] getToken failed. Logout.');
            reject(err);
        })
    }),
    getAdmin: ({commit, state}) => new Promise((resolve, reject) => {
        if (state.uid) {
            api({
                method: 'get',
                url: '/users/' + state.uid + '/admin'
            }).then(res => {
                commit('setAdmin', res.data.data);
                resolve();
            }).catch(err => reject(err));
        } else {
            reject({ error: 'not_login', message: '未登录.' });
        }
    })
}

export default {
    namespaced: true,
    state,
    actions,
    mutations
}
