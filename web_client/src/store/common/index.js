import associationSelector from './association-selector'
import associationOps from './association-ops'

import currentUser from './current-user'
import currentUserInfo from './current-user-info'
import currentUserSubscription from './current-user-subscription'

export default {
    namespaced: true,
    modules: {
        associationSelector,
		associationOps,

        currentUser,
        currentUserInfo,
        currentUserSubscription
    }
}
