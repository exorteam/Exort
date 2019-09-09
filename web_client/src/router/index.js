import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter);

import user from './user'
import sysadmin from './sysadmin'
import assoadmin from './assoadmin'

const router = new VueRouter({
    mode: 'history',
    routes: [
        sysadmin,
        assoadmin,
        user
    ]
});

import store from '../store'

let first_time = true;

router.beforeEach((to, from, next) => {
    if (first_time) {
        store.dispatch('common/auth/getToken').finally(() => next());
        first_time = false;
    } else {
        next();
    }
});

export default router
