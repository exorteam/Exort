import applicationEntries from './application-entries'

import associationSelector from './association-selector'
import associationOps from './association-ops'

import articleOps from './article-ops'

import currentUser from './current-user'
import currentUserSubscription from './current-user-subscription'
import currentUserMsgs from './current-user-msgs'

import comment from './comment'

export default {
    namespaced: true,
    modules: {
		applicationEntries,

        associationSelector,
		associationOps,

		articleOps,

        currentUser,
        currentUserSubscription,
		currentUserMsgs,

        comment
    }
}
