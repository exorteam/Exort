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
            commit('setActivities', {
                activities: resdata.content,
                totalSize: resdata.totalSize,
                pageNum: resdata.pageNum,
                pageSize: resdata.pageSize
            });
            resolve();
        }).catch(err => {
            commit('setActivities', {activity: [], totalSize: 0, pageNum: 1, pageSize: 0});
            reject(err);
        })
    }),
    getCurActivity: ({commit, state,dispatch}, id) => new Promise((resolve, reject) => {
        api({
            method: "get",
            url: "/activities/" + id,
        }).then(res => {
            commit('updateCurActivity', resdata);
            if(state.curActivity.participantIds){
                dispatch('getParticipants');
            }
            if(state.curActivity.realParticipantIds){
                dispatch('getRealParticipants');
            }
            // console.log(state.curActivity);
            resolve();
        }).catch(err => {
            commit('updateCurActivity', {});
            reject(err);
        })
    }),
    createActivtity: ({commit}, activityInfo) => new Promise((resolve, reject) => {
        api({
            method: "post",
            url: "/activities",
            data: activityInfo
        }).then(res => {
            resolve();
        }).catch(err => reject(err))
    }),
    updateActivity: ({commit}, {activityInfo, activityId}) => new Promise((resolve, reject) => {
        api({
            method: 'put',
            url: "/activities/" + activityId,
            data: activityInfo
        }).then(res => {
            resolve()
        }).catch(err => reject(err))
    }),
    changeActivityState: ({commit, state}, stateChangeInfo) => new Promise((resolve, reject) => {
        api({
            method: 'put',
            url: "/activities/" + state.curActivity.id + "/state",
            data: stateChangeInfo
        }).then(res => {
            resolve()
        }).catch(err => reject(err))
    }),
    getParticipants: ({commit, state}) => new Promise((resolve, reject) => {
        let participantsIds=state.curActivity.participantIds;
        api({
            method: "post",
            url: "/users",
            data: participantsIds,
        }).then((res) => {
            commit("setParticipants",res.data.data);
            resolve();
        }).catch(err => {
            commit("setParticipants",[]);
            reject(err);
        })
    }),
    getRealParticipants: ({commit, state}) => new Promise((resolve, reject) => {
        let realParticipantsIds=state.curActivity.realParticipantIds;
        // console.log(realParticipantsIds);
        api({
            method: "post",
            url: "/users",
            data: realParticipantsIds
        }).then((res) => {
            commit("setRealParticipants",res.data.data);
            resolve();
        }).catch(err => {
            commit("setRealParticipants",[]);
            reject(err);
        })
    }),
}

export default {
    namespaced: true,
    state,
    actions,
    mutations
}
