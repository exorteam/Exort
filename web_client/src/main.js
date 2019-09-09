import Vue from 'vue'
import Vuex from 'vuex'
import VueGoogleCharts from 'vue-google-charts'
import iView from 'iview';
import 'iview/dist/styles/iview.css';
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

import router from './router'
import store from './store'
import App from './App'

Vue.use(Vuex);
Vue.use(VueGoogleCharts)
Vue.use(iView);
Vue.use(mavonEditor);

new Vue({
    el: '#app',
    router: router,
    store: store,
    render: h => h(App)
});
