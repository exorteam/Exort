import {api} from '@/http'

const actions = {
	postMessage: ({commit},{receiverId,msg}) => new Promise((resolve,reject) => {
		api({
			method: 'post',
			url: '/com/msg/' + receiverId,
			data: {
				content: msg
			}
		}).then(res => {
			resolve(res);
		}).catch(err => {
            if (err.response && err.response.data) {
                reject(err.response.data);
            } else {
                reject({ error: 'unknown', message: err });
            }
        });
	}),
	queryMessage: ({commit}) => new Promise((resolve,reject) => {
		api({
			method: 'get',
			url: '/com/msg'
		}).then(res => {
			resolve(res);
		}).catch(err => {
            if (err.response && err.response.data) {
                reject(err.response.data);
            } else {
                reject({ error: 'unknown', message: err });
            }
        });
	}),
	dropMessageById: ({commit},{msgId}) => new Promise((resolve,reject) => {
		api({
			method: 'post',
			url: '/com/msg/drop/' + msgId
		}).then(res => {
			resolve(res);
		}).catch(err => {
            if (err.response && err.response.data) {
                reject(err.response.data);
            } else {
                reject({ error: 'unknown', message: err });
            }
        });
	})
}

export default {
	namespaced: true,
	actions
}

