import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    user: {
      activityid: 0,
      associationid: 0
    },
    association: {
      departments: [],
      tree: [{title: "Loading", department: {departmentId: 0}}]
    }
  },
  getters: {
    get_activityid: (state) => {
      return state.user.activityid;
    },
    get_associationid: (state) => {
      return state.user.associationid;
    }
  },
  mutations: {
    set_activityid: (state, data) => {
      if (data) {
        state.user.activityid = data;
      } else {
        state.user.activityid = 0;
        sessionStorage.setItem('id', state.user.activityid);
      }
    },
    set_user_firstname: (state, data) => {
      if (data) {
        state.user.associationid = data;
      } else {
        state.user.associationid = 0;
        sessionStorage.setItem('associationid', state.user.associationid);
      }
    },
    set_departments: (state, data) => {
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

      state.association.tree = [treenodes[1], treenodes[2]];
      state.association.departments = departments;
    }
  },
  actions: {
    setStatus({commit}) {
      commit('set_activityid', sessionStorage.getItem('activityid'))
      commit('set_associationid', sessionStorage.getItem('associationid'))
    }
  }
})

export default store
