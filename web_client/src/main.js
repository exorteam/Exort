// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import router from './router'
import store from './store'
import App from './App'
import Vuex from 'vuex'

// bootstrap vue
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

// iview
import iView from 'iview';
import 'iview/dist/styles/iview.css';

//引入qs
import qs from 'qs'

// 引入axios
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(iView);
Vue.use(BootstrapVue);
Vue.use(router);
Vue.use(Vuex);
Vue.use(VueAxios, axios)
Vue.prototype.$qs = qs;


// The routing configuration
const RouterConfig = {
  routes: router
};
Vue.config.productionTip = false;

store.dispatch('setStatus');

new Vue({
	el: '#app',
	router: router,
	store,
	render: h => h(App),
		axios.defaults.baseURL = 'http://localhost:8080';
		axios.defaults.withCredentials = true;
		const token = window.localStorage.getItem('token');
		if(token)axios.defaults.headers.common.Authorization = 'Bearer ' + token;
	}

});
