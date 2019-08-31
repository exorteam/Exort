import {api} from '@/http'

const state = {
    id: null,
    name: null,
    // ...
}

const mutations = {
    setAssociation(state, {id, name}) {
        state.id = id;
        state.name = name;
    }
}

const actions = {

}

export default {
    namespaced: true,
    state,
    actions,
    mutations
}
