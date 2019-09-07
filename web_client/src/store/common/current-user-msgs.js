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
		}).catch(err => reject(err));
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
		}).catch(err => reject(err));
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
		}).catch(err => reject(err));
	}),
	dropMessageById: ({commit},{msgId}) => new Promise((resolve,reject) => {
		api({
			method: 'post',
			url: '/com/msg/drop/' + msgId
		}).then(res => {
			resolve(res);
		}).catch(err => reject(err));
	})
}

export default {
	namespaced: true,
	actions
}
