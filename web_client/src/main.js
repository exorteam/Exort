// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import VueRouter from 'vue-router';
import App from './App'
import router from './router'

// axios
import axios from 'axios'
import VueAxios from 'vue-axios'

// bootstrap vue
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

// iview
import iView from 'iview';
import 'iview/dist/styles/iview.css';

import qs from 'qs'

Vue.use(VueAxios, axios);
Vue.use(iView);
Vue.use(BootstrapVue);
Vue.use(VueRouter);
Vue.prototype.$qs = qs;

// The routing configuration
const RouterConfig = {
  routes: router
};
Vue.config.productionTip = false;

new Vue({
  el: '#app',
  router: router,
  render: h => h(App)
});
