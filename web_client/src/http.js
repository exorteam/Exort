import axios from 'axios'
import store from './store'

const api = axios.create({
    baseURL: process.env.VUE_APP_API_BASE,
    withCredentials: true
});
api.defaults.headers.common['Content-Type'] = 'application/json';
api.interceptors.response.use(undefined, async err => {
    let config = err.config;
    let response = err.response;

    if (response && response.status == 401 && !config.__retried) {
        console.log('[INFO] not login or token expired, refresh token and retry request.');
        config.__retried = true;
        try {
            await store.dispatch('common/currentUser/getToken');
            return api(config);
        } catch (err) { }
    }

    if(response && response.data) {
        return Promise.reject(response.data);
    } else {
        console.log('[ERROR] Failed to get response:');
        console.log(err);
        return Promise.reject({ error: 'unknown', message: err });
    }
});

const fe = axios.create({withCredentials: true})

export {
    axios as default,
    api,
    fe
}
