import {api} from '@/http'

const state = {
    activities: {
        cardList: [],
        totalSize: 0,
        pageNum: 0,
        pageSize: 6
    },
    curActivityId: null,
    participants: [],
    realParticipants: [],
    curActivity: {
        id: null,
        associationIds: [],
        createTime: null,
        publishTime: null,
        lastPublishTime: null,
        lastModifyTime: null,
        title: null,
        content: null,
        signupTime: null,
        time: null,
        publishState: 0,
        signupState: 0,
        state: 0,
        ifReview: true,
        ifOnlyMem: false,
        maxParticipants: 10,
        materialTemplateIds: [],
        participantIds: [],
        realParticipantIds: [],
        tags: [],
        image: ""
    }
}

const mutations = {
    setCurActivityId(state, id) {
        state.curActivityId = id;
    },
    setActivities(state, {activities, totalSize, pageNum, pageSize}) {
        state.activities.cardList = activities;
        state.activities.totalSize = totalSize;
        state.activities.pageNum = pageNum;
        state.activities.pageSize = pageSize;
    },
    updateCurActivity(state, activityInfo) {
        state.curActivity = activityInfo;
    },
    setParticipants(state, participants) {
        state.participants = participants;
    },
    setRealParticipants(state, realParticipants) {
        state.realParticipants = realParticipants;
    }
}

const actions = {
    updateCardlist: ({commit, state}, {select, pageSize, pageNum}) => new Promise((resolve, reject) => {
        api({
            method: "post",
            url: "/activities/filter",
            params: {
                'pagesize': pageSize || 0,
                'pagenum': pageNum || 0,
                'sortby': "0",
            },
            data: select
        }).then(res => {
            let resdata = res.data.data;
            if (resdata) {
                // console.log(resdata);
                commit('setActivities', {
                    activities: resdata.content,
                    totalSize: resdata.totalSize,
                    pageNum: resdata.pageNum,
                    pageSize: resdata.pageSize
                });

                resolve();
            } else {
                commit('setActivities', {activity: [], totalSize: 0, pageNum: 1, pageSize: 0});
                reject(res.data.message);
            }
        }).catch(error => {
            commit('setActivities', {activity: [], totalSize: 0, pageNum: 1, pageSize: 0});
            reject(error);
        })
    }),
    getCurActivity: ({commit, state,dispatch}, id) => new Promise((resolve, reject) => {
        api({
            method: "get",
            url: "/activities/" + id,
        }).then(res => {
            let resdata = res.data.data;
            if (resdata) {
                // console.log(resdata);
                commit('updateCurActivity', resdata);
                if(state.curActivity.participantIds){
                    dispatch('getParticipants');
                }
                if(state.curActivity.realParticipantIds){
                    dispatch('getRealParticipants');
                }
                // console.log(state.curActivity);
                resolve();
            } else {
                commit('updateCurActivity', {});
                reject(res.data.message);
            }
        }).catch(error => {
            commit('updateCurActivity', {});
            reject(error);
        })
    }),
    createActivtity: ({commit}, activityInfo) => new Promise((resolve, reject) => {
        api({
            method: "post",
            url: "/activities",
            data: activityInfo
        }).then(res => {
            if (res.data.data !== null) {
                resolve();
            } else {
                reject(res.data);
            }
        }).catch(error => {
            console.log(error);
        })
    }),
    updateActivity: ({commit}, {activityInfo, activityId}) => new Promise((resolve, reject) => {
        api({
            method: 'put',
            url: "/activities/" + activityId,
            data: activityInfo
        }).then(res => {
            if (res.data.data !== null) {
                resolve()
            } else {
                reject(res.data);
            }
        }).catch(error => {
            reject(error);
        })
    }),
    changeActivityState: ({commit, state}, stateChangeInfo) => new Promise((resolve, reject) => {
        api({
            method: 'put',
            url: "/activities/" + state.curActivity.id + "/state",
            data: stateChangeInfo
        }).then(res => {
            if (res.data.data !== null) {
                // console.log(res.data);
                resolve()
            } else {
                reject(res.data.message);
            }
        }).catch(error => {
            reject(error);
        })
    }),
    getParticipants: ({commit, state}) => new Promise((resolve, reject) => {
        let participantsIds=state.curActivity.participantIds;
        api({
            method: "post",
            url: "/users",
            data: participantsIds,
        }).then((response) => {
            if(response.data.data){
                commit("setParticipants",response.data.data);
                resolve();
            }else{
                commit("setParticipants",[]);
                reject(response.data.message);
            }
        }).catch(error => {
            commit("setParticipants",[]);
            reject(error);
        })
    }),
    getRealParticipants: ({commit, state}) => new Promise((resolve, reject) => {
        let realParticipantsIds=state.curActivity.realParticipantIds;
        // console.log(realParticipantsIds);
        api({
            method: "post",
            url: "/users",
            data: realParticipantsIds
        }).then((response) => {
            // console.log(response.data.data);
            if(response.data.data){
                commit("setRealParticipants",response.data.data);
                resolve();
            }else{
                commit("setRealParticipants",[]);
                reject(response.data.message);
            }
        }).catch(error => {
            commit("setRealParticipants",[]);
            reject(error);
        })
    }),
}

export default {
    namespaced: true,
    state,
    actions,
    mutations
}
