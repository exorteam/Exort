<template>
	<Card>
		<span>Your subscription list: {{subscribed}}</span>
	</Card>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
	name: 'UserHomePage',
	computed: {
		...mapState('common/currentUserSubscription',['subscribed'])
	},
	methods: {
		...mapActions('common/currentUserSubscription',['refreshSubscription'])
	},
	mounted(){
		this.refreshSubscription();
	}
}
</script>
