import {api} from '@/http'

const actions = {
	queryPagedAssociationsWithFilter: ({commit},{filter,pageArgs}) => new Promise((resolve,reject) => {
		console.log(filter);
		console.log(pageArgs);

		api({
			method:'post',
			url:'/associations/list',
			params: {
				pageNum: pageArgs.pageNum - 1,
				pageSize: pageArgs.pageSize
			},
			data: filter
		})
		.then(res => {
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
    actions,
}


