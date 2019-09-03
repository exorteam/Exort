<template>
	<Card>
		<!--<span>info: {{info}}</span>-->
		<h2>#{{content.id}}</h2>
		<h1>{{content.name}} ({{content.nickname}})</h1>
		<h3>@{{username}}</h3>
		<h4>"{{content.description}}"</h4>

		<br>
		<p><Icon type="ios-man" />Gender: {{content.gender}}</p>
		<p><Icon type="md-calendar" />Birthday: {{content.birthday}}</p>
		<p><Icon type="ios-briefcase" />StudentID: {{content.studentId}}</p>
		<p><Icon type="ios-call" />Phone: {{content.phone}}</p>
		<p><Icon type="ios-mail" />Email: {{content.email}}</p>
		<p><Icon type="ios-chatbubbles" /> WechatId: {{content.wechatId}}</p>

		<br>
		<Button :to="{name:'UserInfoEditor'}">Edit</Button>
	</Card>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
	name: 'UserInfoDetail',
	data() {
		return {
			content: {}
		}
	},
	computed: {
		...mapState('common/currentUserInfo',['info']),
        ...mapState('common/currentUser', ['username'])
	},
	methods: {
		...mapActions('common/currentUserInfo',['refreshInfo']),
	},
	mounted() {
		this.refreshInfo().then(() => {
			this.content = {...this.info}
		})
	}
}

</script>
