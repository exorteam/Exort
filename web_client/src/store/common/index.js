import associationSelector from './association-selector'
import currentUser from './current-user'
import currentUserSubscription from './current-user-subscription'

export default {
    namespaced: true,
    modules: {
        associationSelector,
        currentUser,
        currentUserSubscription
    }
}
