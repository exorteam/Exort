import Vue from 'vue'
import Vuex from 'vuex'
import VueCookies from 'vue-cookies'
import iView from 'iview';
import 'iview/dist/styles/iview.css';

import router from './router'
import store from './store'
import App from './App'


Vue.use(Vuex);
Vue.use(VueCookies)
Vue.use(iView);

new Vue({
    el: '#app',
    router: router,
    store: store,
    render: h => h(App)
});
