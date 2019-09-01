import {api} from '@/http'

const state = {
    nodePerm: {
        data: [],
        targetKeys: [],
        listStyle: {
            width: '250px',
            height: '300px'
        }
    },

    departments: [],
    tree: [{title: "Loading", department: {departmentId: 0}}],

    deptUserList: [],
    specdept: {
        associationId: null,
        departmentId: null,
        name: null,
        description: null,
        parentId: null
    },
}

const mutations = {
    setNodePermTargetkeys: (state, targetKey) => {
        state.nodePerm.targetKeys = targetKey;
    },
    getMockData: (state, data) => {
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
    getTargetKeys: (state, data) => {
        let target = data;
        // console.log(target);
        let ret = [];
        for (let i = 0; i < target.length; i++) {
            ret.push(target[i].name);
        }
        state.nodePerm.targetKeys = ret;
    },
    setDepartments: (state, data) => {
        let departments = {};
        let treenodes = {};

        for (let i in data) {
            let item = data[i];
            departments[item.departmentId] = item;
            treenodes[item.departmentId] = {
                title: item.name,
                department: item,
                expend: true,
                children: []
            }
        }

        for (let i in departments) {
            if (departments[i].parentId !== 0) {
                treenodes[departments[i].parentId].children.push(treenodes[i]);
            }
        }

        state.tree = [treenodes[1], treenodes[2]];
        state.departments = departments;
    },
    setSpecDept: (state, {associationId, departmentId, name, description, parentId}) => {
        if (associationId != null) {
            state.specdept.associationId = associationId;
        }
        if (departmentId != null) {
            state.specdept.departmentId = departmentId;
        }
        if (name != null) {
            state.specdept.name = name;
        }
        if (description != null) {
            state.specdept.description = description;
        }
        if (parentId != null) {
            state.specdept.parentId = parentId;
        }
    },
    setDeptUserList: (state, data) => {
        state.deptUserList = data;
    },
}

const actions = {
    getParentPermList: (commit, nodedata) => new Promise((resolve, reject) => {
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
    getChildPermList: (commit, nodedata) => new Promise((resolve, reject) => {
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
    deleteTargetkeys: (commit, {nodedata, data}) => new Promise((resolve, reject) => {
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
    addTargetkeys: (commit, {nodedata, data}) => new Promise((resolve, reject) => {
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
    getDepartmentInfo: (commit, {associationId, departmentId}) => new Promise((resolve, reject) => {
        let url = "/associations/" + associationId + "/departments/" + departmentId;
        api({
            method: "get",
            url: url
        }).then((res) => {
            if (res.data.data) {
                let department = res.data.data;
                commit('setSpecDept', {
                    associationId: associationId,
                    departmentId: departmentId,
                    name: department.name,
                    description: department.description,
                    parentId: department.parentId
                });
                resolve();
            } else {
                reject(res.data.message);
            }
        }).catch(error => {
            reject(error);
        })
    }),
    createDept: ({commit, dispatch}, {associationId, name, description, parentId}) => new Promise((resolve, reject) => {
        let url = "/associations/" + associationId + "/departments";
        let departinfo = {
            "name": name,
            "description": description,
            "parentId": parentId
        };
        api({
            method: 'post',
            url: url,
            data: departinfo
        }).then((res) => {
            if (res.data.data !== null) {
                this.$Message.info("添加成功！");
                dispatch('gettree', associationId);
            } else {
                this.$Message.error("添加失败！" + res.data.message);
            }
        })
    }),
    editDeptInfo: ({commit, dispatch}, {url, data}) => new Promise((resolve, reject) => {
        api({
            method: "put",
            data: data,
            url: url
        }).then((res) => {
            if (res.data.data) {
                this.$Message.info("修改成功！");
                dispatch('gettree', data.department.associationId);
                resolve();
            } else {
                this.$Message.error("修改失败！" + res.data.message);
                reject(res.data.message);
            }
        }).catch(error => {
            reject(error);
        })
    }),
    removeDept: (commit, data, dispatch) => new Promise((resolve, reject) => {
        let url = "/associations/" + data.department.associationId + "/departments/" + data.department.departmentId;
        api({
            method: "delete",
            url: url
        }).then(() => {
            dispatch('gettree', data.department.associationId);
            this.$Message.info("删除成功！");
        }).catch(error => {
            reject(error);
        })
    }),
    gettree: (commit, associationId) => new Promise((resolve, reject) => {
        let url = "/associations/" + associationId + "/departments";
        api({
            method: "get",
            url: url
        }).then((res) => {
            if (res.data.data === null) {
                this.$Message.error("社团不存在");
                reject(res.data.message);
            } else {
                commit("setDepartments", res.data.data);
                resolve();
            }
        }).catch((error) => {
            reject(error);
            // this.$Message.error("社团不存在");
        })
    }),
    getDepartmentUsers: (commit, {associationId, departmentId}) => new Promise((resolve, reject) => {
        let url = "/associations/" + associationId + "/departments/" + departmentId + "/members";
        api({
            method: "get",
            url: url
        }).then((res) => {
            let ret = [];
            let retdata = [];
            retdata = res.data.data;
            for (let i = 0; i < retdata.length; i++) {
                ret.push(retdata[i])
            }

            api({
                method: "post",
                url: "/users",
                data: ret,
            }).then((res) => {
                commit("setDeptUserList", res.data)
            })

        }).catch((error) => {
            console.log(error.response.status);
        })
    }),
    addDeptUser: ({commit, state, dispatch}, userId) => new Promise((resolve, reject) => {
        let addUserInfo = {
            userId: userId,
        };
        api({
            method: 'post',
            url: "/associations/" + state.specdept.associationId + "/departments/" + state.specdept.departmentId + "/members",
            data: addUserInfo
        }).then((res) => {
            if (res.data.data === true) {
                dispatch('getDepartmentUsers', {
                    associationId: state.specdept.associationId,
                    departmentId: state.specdept.departmentId
                });
                this.$Message.info("添加成功！");
            } else {
                this.$Message.error("添加失败！" + res.data.message);
            }
        })
    }),
    deleteDeptUser: ({commit, state, dispatch}, index) => new Promise((resolve, reject) => {
        let url = "/associations/" + state.specdept.associationId + "/departments/" + state.specdept.departmentId + "/members/" + state.deptUserList[index].id;
        api({
            method: 'delete',
            url: url
        }).then((res) => {
            console.log(res);
            if (res.data.data) {
                dispatch('getDepartmentUsers', {
                    associationId: this.specdept.associationId,
                    departmentId: this.specdept.departmentId
                });
                this.$Message.info("移除成功！");
            }
            else {
                this.$Message.error("移除失败!" + res.data.message);
            }
        })
    }),
};

export default {
    namespaced: true,
    state,
    actions,
    mutations
}
