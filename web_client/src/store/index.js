import Vue from 'vue'
import Vuex from 'vuex'

import common from './common'
import sysadmin from './sysadmin'
import assoadmin from './assoadmin'

Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        common,
        sysadmin,
        assoadmin
    },
    strict: process.env.NODE_ENV !== 'production'
})
