import Vue from 'vue'
import Vuex from 'vuex'
import VueCookies from 'vue-cookies'
import iView from 'iview';
import 'iview/dist/styles/iview.css';

import router from './router'
import store from './store'
import App from './App'

// bootstrap vue
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(Vuex);
Vue.use(VueCookies)
Vue.use(iView);
Vue.use(BootstrapVue);

new Vue({
    el: '#app',
    router: router,
    store: store,
    render: h => h(App)
});
