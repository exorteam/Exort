import Vue from 'vue'
import Vuex from 'vuex'

import common from './common'
import systemAdmin from './system-admin'
import associationAdmin from './association-admin'
import user from './user'

Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        common,
        systemAdmin,
        associationAdmin,
        user
    },
    strict: process.env.NODE_ENV !== 'production'
})
