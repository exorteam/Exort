<template>
<Layout>
	<Sider :style="{background: '#fff'}">
		<UsrNavbar/>
	</Sider>
	<Content style="min-height:420px;margin:10px;">
		<Card>
			<span>Home page</span>
			<br>
			<span>{{this.subscribed}}</span>
		</Card>
	</Content>
</Layout>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import UsrNavbar from '@/components/user/UsrNavbar'

export default {
    components: {
		UsrNavbar
    },
    data() { 
		return {
        	image: null 
		}
	},
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

<style>
</style>
