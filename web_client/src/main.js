// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import router from './router'
import App from './App'

// bootstrap vue
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

// iview
import iView from 'iview';
import 'iview/dist/styles/iview.css';

Vue.use(iView);
Vue.use(BootstrapVue);
Vue.use(router);


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
