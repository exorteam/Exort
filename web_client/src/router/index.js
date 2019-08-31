import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter);

import systemAdmin from './system-admin'
import associationAdmin from './association-admin'
import user from './user'
import GlobalNotFound from '@/views/GlobalNotFound'

const router = new VueRouter({
    mode: 'history',
    routes: [
        systemAdmin,
        associationAdmin,
        user,
        {
            path: '*',
            name: 'GlobalNotFound',
            component: GlobalNotFound
        }
    ]
});

import store from '../store'

let first_time = true;

router.beforeEach((to, from, next) => {
    if (first_time) {
        store.dispatch('common/currentUser/getToken').finally(() => next());
        first_time = false;
    } else {
        next();
    }
});

export default router
