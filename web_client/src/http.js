import axios from 'axios'
import store from './store'

const api = axios.create({
    baseURL: process.env.VUE_APP_API_BASE
});
api.defaults.withCredentials = true;
api.defaults.headers.post['Content-Type'] = 'application/json';
api.interceptors.response.use(undefined, async err => {
    let config = err.config;
    let response = err.response;
    if (response) {
        console.log(responses);
        if (response.status == 401 && !config.__retried) {
            config.__retried = true;
            try {
                await store.dispatch('common/auth/getToken');
                return api(config);
            } catch (err) { }
        }
    }
    return Promise.reject(err);
});

const fe = axios.create({withCredentials: true})

export {
    axios as default,
    api,
    fe
}
