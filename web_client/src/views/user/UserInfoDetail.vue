<template>
	<Card>
		<!--<span>info: {{info}}</span>-->
		<h2>#{{info.id}}</h2>
		<h1>{{info.name}} ({{info.nickname}})</h1>
		<h3>@{{username}}</h3>
		<h4>"{{info.description}}"</h4>

		<br>
		<p><Icon type="ios-man" />Gender: {{info.gender}}</p>
		<p><Icon type="md-calendar" />Birthday: {{info.birthday}}</p>
		<p><Icon type="ios-briefcase" />StudentID: {{info.studentId}}</p>
		<p><Icon type="ios-call" />Phone: {{info.phone}}</p>
		<p><Icon type="ios-mail" />Email: {{info.email}}</p>
		<p><Icon type="ios-chatbubbles" /> WechatId: {{info.wechatId}}</p>

		<br>
		<Button :to="{name:'UserInfoEditor'}">Edit</Button>
	</Card>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
	name: 'UserInfoDetail',
	computed: {
		...mapState('common/currentUserInfo',['info']),
        ...mapState('common/currentUser', ['username'])
	},
	methods: {
		...mapActions('common/currentUserInfo',['refreshInfo']),
	},
	mounted() {
		this.refreshInfo();
	}
}

</script>
