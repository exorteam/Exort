import applicationEntries from './application-entries'

import associationSelector from './association-selector'
import associationOps from './association-ops'

import currentUser from './current-user'
import currentUserInfo from './current-user-info'
import currentUserSubscription from './current-user-subscription'
import currentUserMsgs from './current-user-msgs'

export default {
    namespaced: true,
    modules: {
		applicationEntries,

        associationSelector,
		associationOps,

        currentUser,
        currentUserInfo,
        currentUserSubscription,
		currentUserMsgs
    }
}
