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
    login(state, {token, uid, username}) {
        state.token = token;
        state.uid = uid;
        state.username = username;
        api.defaults.headers.common.Authorization = "Bearer " + token;
    },
    logout(state) {
        state.token = null;
        state.uid = null;
        state.username = null;
        delete api.defaults.headers.common.Authorization;
        $cookies.remove('sess_id');
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
        }).then(() => {
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
            reject(err);
        });
    }),
    getToken: ({commit}) => new Promise((resolve, reject) => {
        return fe({
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
                commit('setAdmin', res.data);
                resolve();
            }).catch(err => {
                reject(err);
            });
        } else {
            reject();
        }
    })
}

export default {
    namespaced: true,
    state,
    actions,
    mutations
}
