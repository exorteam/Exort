import {api} from '@/http'

const state = {
    permCategories: {'': []},
    roles: {
        curCategory: '',
        roleList: []
    },
    curRole: {
        role: null,
        perms: []
    }
}

const getters = {
    permCatList: state => Object.keys(state.permCategories).sort().reduce(
        (a, c) => { a[c] = state.permCategories[c]; return a; }, {}
    )
}

const mutations = {
    modifyPerm(state, {oldPerm, newPerm}) {
        if (oldPerm) {
            let oldCat = state.permCategories[oldPerm.category];
            for (let i in oldCat) {
                if (oldPerm.name == oldCat[i].name) {
                    oldCat.splice(i, 1);
                    if (oldCat.length == 0 && oldPerm.category != '') {
                        delete state.permCategories[oldPerm.category];
                    }
                    break;
                }
            }
        }
        if (newPerm) {
            if (!state.permCategories[newPerm.category]) {
                state.permCategories[newPerm.category] = [];
            }
            state.permCategories[newPerm.category].splice(0, 0, newPerm);
        }
        state.permCategories = {...state.permCategories};
    },
    setCategories(state, permList) {
        let cat = {'': []};
        for (let perm of permList) {
            let name = perm.category;
            if (!cat[name]) {
                cat[name] = [];
            }
            cat[name].push(perm);
        }
        state.permCategories = cat;
    },
    modifyRole(state, {oldRole, newRole}) {
        if (oldRole) {
            if (oldRole.category == state.roles.curCategory) {
                let list = state.roles.roleList;
                for (let i in list) {
                    if (oldRole.name == list[i].name) {
                        list.splice(i, 1);
                        break;
                    }
                }
            }
        }
        if (newRole && newRole.category == state.roles.curCategory) {
            state.roles.roleList.splice(0, 0, newRole);
        }
    },
    setRoleList(state, {category, roleList}) {
        state.roles.curCategory = category || '';
        state.roles.roleList = roleList || [];
    },
    updateCurRole(state, {role, perms}) {
        state.curRole.role = role || null;
        state.curRole.perms = perms || [];
    }
}

const actions = {
    updatePermList: ({commit}) => new Promise((resolve, reject) => {
        api({
            method: 'get',
            url: '/permission/list_perm'
        }).then(res => {
            commit('setCategories', res.data.data);
            resolve();
        }).catch(err => {
            commit('setCategories', []);
            reject(err);
        });
    }),
    updateRoleList: ({commit}, category) => new Promise((resolve, reject) => {
        api({
            method: 'get',
            url: '/permission/list_role',
            params: {
                category
            }
        }).then(res => {
            commit('setRoleList', {category: category, roleList: res.data.data});
            resolve();
        }).catch(err => {
            commit('setRoleList', {category: '', roleList: []});
            reject(err);
        });
    }),
    createRole: ({commit}, role) => new Promise((resolve, reject) => {
        api({
            method: 'post',
            url: '/permission/create_role',
            data: role
        }).then(res => {
            commit('modifyRole', {oldRole: null, newRole: res.data.data});
            resolve();
        }).catch(err => {
            reject(err);
        })
    }),
    updateRole: ({commit}, {oldRole, newRole}) => new Promise((resolve, reject) => {
        api({
            method: 'post',
            url: '/permission/update_role',
            data: newRole
        }).then(res => {
            commit('modifyRole', {oldRole, newRole: res.data.data});
            resolve();
        }).catch(err => {
            reject(err);
        })
    }),
    deleteRole: ({commit}, role) => new Promise((resolve, reject) => {
        api({
            method: 'post',
            url: '/permission/delete_role',
            data: role
        }).then(res => {
            commit('modifyRole', {oldRole: role, newRole: null});
            resolve();
        }).catch(err => {
            reject(err);
        });
    }),
    createPerm: ({commit}, perm) => new Promise((resolve, reject) => {
        api({
            method: 'post',
            url: '/permission/create_perm',
            data: perm
        }).then(res => {
            commit('modifyPerm', {oldPerm: null, newPerm: perm});
            resolve();
        }).catch(err => {
            reject(err);
        });
    }),
    updatePerm: ({commit}, {oldPerm, newPerm}) => new Promise((resolve, reject) => {
        api({
            method: 'post',
            url: '/permission/update_perm',
            data: newPerm
        }).then(res => {
            commit('modifyPerm', {oldPerm, newPerm: res.data.data});
            resolve();
        }).catch(err => {
            reject(err);
        })
    }),
    deletePerm: ({commit}, perm) => new Promise((resolve, reject) => {
        api({
            method: 'post',
            url: '/permission/delete_perm',
            data: perm
        }).then(res => {
            commit('modifyPerm', {oldPerm: perm, newPerm: null});
            resolve();
        }).catch(err => {
            reject(err);
        });
    }),
    updateCurRole: ({commit}, role) => new Promise((resolve, reject) => {
        if (role && role.name) {
            api({
                method: 'get',
                url: '/permission/list_perm/' + role.name
            }).then(res => {
                commit('updateCurRole', {role, perms: res.data.data});
                resolve();
            }).catch(err => {
                reject(err);
            });
        } else {
            commit('updateCurRole', {role: null, perms: []});
            resolve();
        }
    }),
    grantPerms: ({commit}, {role, permNames}) => new Promise((resolve, reject) => {
        if (role && role.name) {
            api({
                method: 'post',
                url: '/permission/grant/' + role.name,
                data: permNames
            }).then(res => {
                commit('updateCurRole', {role, perms: res.data.data});
                resolve();
            }).catch(err => {
                reject(err);
            });
        } else {
            reject({ error: 'unknow', message: '不正确的role'});
        }
    }),
    revokePerms: ({commit}, {role, permNames}) => new Promise((resolve, reject) => {
        if (role && role.name) {
            api({
                method: 'post',
                url: '/permission/revoke/' + role.name,
                data: permNames
            }).then(res => {
                commit('updateCurRole', {role, perms: res.data.data});
                resolve();
            }).catch(err => reject(err));
        } else {
            reject({ error: 'unknow', message: '不正确的role'});
        }
    })
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}
