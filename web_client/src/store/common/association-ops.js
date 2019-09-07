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
		}).catch(err => reject(err));
	}),
	queryAssociationById: ({commit},{assoId}) => new Promise((resolve,reject) => {
		api({
			method: 'get',
			url: '/associations/' + assoId,
		}).then(res => {
			console.log(res);
			resolve(res);
		}).catch(err => reject(err));
	}),
	createAssociation: ({commit}, asso) => new Promise((resolve,reject) => {
		api({
			method: 'post',
			url: '/associations',
			data: asso
		}).then(res => {
			resolve(res);
		}).catch(err => reject(err));
	}),
	deleteAssociationById: ({commit},{assoId}) => new Promise((resolve,reject) => {
		api({
			method:'delete',
			url:'/associations/'+assoId
		})
		.then(res => {
			resolve(res);
		}).catch(err => reject(err));
	})
}

export default {
    namespaced: true,
    actions,
}
