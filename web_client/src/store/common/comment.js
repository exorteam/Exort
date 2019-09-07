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
        }).catch(err => {
            reject(err);
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
        }).catch(err => {
            reject(err);
        })
    }),
    getArticleCommentList: ({commit, state}, id) => new Promise((resolve, reject) => {
        api({
            method: "get",
            url: "/articles/" + id + "/comments"
        }).then((res) => {
            commit('setCommentList', res.data.data);
            resolve();
        }).catch(err => {
            reject(err);
        })
    }),
    getActivityCommentList: ({commit, state}, id) => new Promise((resolve, reject) => {
        api({
            method: "get",
            url: "/activities/" + id + "/comments"
        }).then((res) => {
            commit('setCommentList', res.data.data);
            resolve();
        }).catch(err => {
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
