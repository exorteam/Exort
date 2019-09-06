import {api} from '@/http'

const state = {
    commentList: [],
}

const mutations = {
    setCommentList(state, list) {
        state.commentList = list;
    }
}

const actions = {
    createComment: ({commit, state}, data) => new Promise((resolve, reject) => {
        api({
            method: "post",
            url: "/comments",
            data: data
        }).then((res) => {
            resolve();
        }).catch(error => {
            reject(error);
        })
    }),
    deleteComment: ({commit, state}, {id, userId}) => new Promise((resolve, reject) => {
        let data = {
            id: id,
            userId: userId
        };

        api({
            method: "delete",
            url: "/comments",
            data: data
        }).then(() => {
            resolve();
        }).catch(error => {
            reject(error);
        })
    }),
    getArticleCommentList: ({commit, state}, id) => new Promise((resolve, reject) => {
        api({
            method: "get",
            url: "/articles/" + id + "/comments"
        }).then((res) => {
            commit('setCommentList', res.data.data);
            resolve();
        }).catch(error => {
            reject(error);
        })
    }),
    getActivityCommentList: ({commit, state}, id) => new Promise((resolve, reject) => {
        api({
            method: "get",
            url: "/activities/" + id + "/comments"
        }).then((res) => {
            commit('setCommentList', res.data.data);
            resolve();
        }).catch(error => {
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
