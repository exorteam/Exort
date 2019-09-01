import {api} from '@/http'

const state = {
    activities: {
        cardList: [],
        totalSize: 0,
        pageNum: 0,
        pageSize: 9
    },
    curActivity: {
        title: "",
        content: "",
        signupTime: {
            type: 0,
            time: [{
                start: "",
                end: "",
            }]
        },
        time: {
            type: 0,
            time: [{
                start: "",
                end: ""
            }]
        },
        ifReview: 0,
        ifOnlyMem: 0,
        maxParticipants: '',
        materials: [],
        image: "",
        tag: {
            tag_show: false,
            tagList: [],
        }
    }
}

const mutations = {
    setActivities(state, {activities, totalSize, pageNum, pageSize}) {
        state.activities.cardList = activities;
        state.activities.totalSize = totalSize;
        state.activities.pageNum = pageNum;
        state.activities.pageSize = pageSize;
    },
    setActivity(state, newActivityInfo) {
        state.newActivity = newActivityInfo;
    }
}

const action = {
    updateCardlist: ({commit}, {select, pageSize, pageNum}) => new Promise((resolve, reject) => {
        api({
            method: "posr",
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
                commit('setActivities', {
                    activity: resdata.content,
                    totalSize: resdata.totalSize,
                    pageNum: resdata.pageNum,
                    pageSize: resdata.pageSize
                });
                resolve();
            } else {
                commit('setActivities', {activity: [], totalSize: 0, pageNum: 1, pageSize: 0});
                reject();
            }
        }).catch(error => {
            commit('setActivities', {activity: [], totalSize: 0, pageNum: 1, pageSize: 0});
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
    changeActivityState:({commit}, {stateChangeInfo, activityId}) => new Promise((resolve, reject) => {
        api({
            method: 'put',
            url: "/activities/" + activityId+"/state",
            data: stateChangeInfo
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

}