import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        user: {
            activityid: 0,
            associationid:0
        }
    },
    getters: {
        get_activityid: (state) => {
            return state.user.activityid
        },
        get_associationid: (state) => {
            return state.user.associationid
        }
    },
    mutations: {
        set_activityid: (state, data) => {
            if (data) {
                state.user.activityid = data
            } else {
                state.user.activityid = 0
                sessionStorage.setItem('id', state.user.activityid)
            }
        },
        set_user_firstname: (state, data) => {
            if (data) {
                state.user.associationid = data
            } else {
                state.user.associationid = 0
                sessionStorage.setItem('associationid', state.user.associationid)
            }
        }
    },
    actions: {
        setStatus({ commit }) {
            commit('set_activityid', sessionStorage.getItem('activityid'))
            commit('set_associationid', sessionStorage.getItem('associationid'))
        }
    }
})

export default store