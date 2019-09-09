<template>
	<Card>
		<!--<span>info: {{info}}</span>-->
		<h2>#{{content.id}}</h2>
		<h1>{{content.name}} ({{content.nickname}})</h1>
		<h4>"{{content.description}}"</h4>

		<br>
		<p><Icon type="ios-man" />Gender: {{content.gender}}</p>
		<p><Icon type="md-calendar" />Birthday: {{content.birthday}}</p>
		<p><Icon type="ios-briefcase" />StudentID: {{content.studentId}}</p>
		<p><Icon type="ios-call" />Phone: {{content.phone}}</p>
		<p><Icon type="ios-mail" />Email: {{content.email}}</p>
		<p><Icon type="ios-chatbubbles" /> WechatId: {{content.wechatId}}</p>

	</Card>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
	name: 'UserSearchDetail',
	props:['uid'],
	data() {
		return {
			content: {}
		}
	},
	methods: {
		...mapActions('common/currentUser',[
			'getUserInfoById'
		]),
	},
	mounted() {
		this.getUserInfoById({
			uid:this.uid
		}).then((res) => {
			this.content = res.data.data
		})
	},
	watch: {
		uid: function() {
			this.getUserInfoById({
				uid:this.uid
			}).then((res) => {
				this.content = res.data.data
			})
		}
	}

}

</script>
