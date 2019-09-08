import {api} from '@/http'

const actions = {
	queryPagedAssociationsWithFilter: ({commit},{filter,pageArgs}) => new Promise((resolve,reject) => {
		console.log(filter,pageArgs);
		api({
			method:'post',
			url:'/associations/list',
			params: {
				pageNum: pageArgs.pageNum - 1,
				pageSize: pageArgs.pageSize
			},
			data: filter
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
	queryAssociationById: ({commit},{assoId}) => new Promise((resolve,reject) => {
		api({
			method: 'get',
			url: '/associations/' + assoId,
		}).then(res => {
			console.log(res);
			resolve(res);
		}).catch(err => {
            if (err.response && err.response.data) {
                reject(err.response.data);
            } else {
                reject({ error: 'unknown', message: err });
            }
        });
	}),
	createAssociation: ({commit}, asso) => new Promise((resolve,reject) => {
		api({
			method: 'post',
			url: '/associations',
			data: asso
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
	deleteAssociationById: ({commit},{assoId}) => new Promise((resolve,reject) => {
		api({
			method:'delete',
			url:'/associations/'+assoId
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
	postNotification: ({commit},{assoId,content}) => new Promise((resolve,reject) => {
		api({
			method: 'post',
			url: '/com/notify/'+assoId,
			data: {
				content
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
}

export default {
    namespaced: true,
    actions,
}


