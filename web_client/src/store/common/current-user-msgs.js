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
			const ret = res.data.data.map(e => {
				const ts = new Date(e.timestamp);
				e.timestamp = ts.toLocaleString();
				return e;
			})
			resolve(ret);
		}).catch(err => {
            if (err.response && err.response.data) {
                reject(err.response.data);
            } else {
                reject({ error: 'unknown', message: err });
            }
        });
	}),
	queryPagedMessage: ({commit} , pageArgs) => new Promise((resolve,reject) => {
		api({
			method: 'get',
			url: '/com/msg/page',
			params: pageArgs
		}).then(res => {
			console.log(res);
			res.data.data.content = res.data.data.content.map(e => {
				const ts = new Date(e.timestamp);
				e.timestamp = ts.toLocaleString();
				return e;
			})
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

