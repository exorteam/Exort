<template>
<ContentPage>
	<template #header>
		<h2>用户信息</h2>
		<div/>
	</template>
	<Card v-if="user">
		<div class="display" v-if="!editing">
			<Avatar :src="userIcon(user.studentId)"
					shape="circle" size="large"
					style="margin-right:20px"/>

			<Button v-if="uid == user.id" ghost type="success"
					@click="editing=true">
				编辑
			</Button>

			<Row>
				<Col span="4">UID</Col>
				<Col>{{user.id}}</Col>
			</Row>
			<Row>
				<Col span="4">昵称</Col>
				<Col>{{user.nickname}}</Col>
			</Row>
			<Row>
				<Col span="4">个人描述</Col>
				<Col>{{user.description}}</Col>
			</Row>
			<Row>
				<Col span="4"><Icon type="ios-man" />性别</Col>
				<Col>{{['保密', '男', '女'][user.gender]}}</Col>
			</Row>
			<Row>
				<Col span="4"><Icon type="md-calendar" />生日</Col>
				<Col>{{user.birthday}}</Col>
			</Row>
			<Row>
				<Col span="4"><Icon type="ios-briefcase" />姓名</Col>
				<Col>{{user.name}}</Col>
			</Row>
			<Row>
				<Col span="4"><Icon type="ios-call" />电话</Col>
				<Col>{{user.phone}}</Col>
			</Row>
			<Row>
				<Col span="4"><Icon type="ios-mail" />邮箱</Col>
				<Col>{{user.email}}</Col>
			</Row>
			<Row>
				<Col span="4"><Icon type="ios-chatbubbles" />>微信</Col>
				<Col>{{user.wechatId}}</Col>
			</Row>
			<br>
			<Button type="warning" v-if="uid == user.id"
					@click="logout()">
				登出
			</Button>
		</div>
		<div v-else class="edit">
			<UserInfoEditor has-cancel v-model="user" @saved="onSaved" @cancel="editing=false" />
		</div>
	</Card>
</ContentPage>
</template>

<script>
import {api} from '@/http'
import { mapState, mapActions, mapMutations } from 'vuex'
import { userIcon } from '@/const'

import UserInfoEditor from '@/components/editor/UserInfoEditor'
import ContentPage from '@/components/ContentPage'


export default {
	name: 'UserInfoDetail',
	components: {
		UserInfoEditor,
		ContentPage
	},
	props: ['id'],
	data() {return {
		user: null,
		editing: false
	};},
	computed: {
        ...mapState('common/currentUser', ['uid', 'username', 'info'])
	},
	methods: {
		...mapActions('common/currentUser', ['logout']),
		...mapMutations('common/currentUser', ['setInfo']),
		userIcon,
		getUserInfo() {
			var uid = parseInt(this.id);
			if (uid == this.uid && this.info) {
				this.user = {...this.info}
			} else if (uid >= 0) {
				api({
					method: 'get',
					url: '/users/' + uid
				}).then(res => {
					console.log(res);
					this.user = {...res.data.data};
				}).catch(err => {
					this.$Notice.error({
						title: '获取用户信息失败',
						desc: err.message || err
					})
				});
			}
		},
		onSaved() {
			this.editing=false;
			if (this.user.id == this.uid) {
				this.setInfo(this.user);
			}
		}
	},
	mounted() {
		this.getUserInfo();
	},
	watch: {
		'id': function() { this.getUserInfo(); }
	}
}

</script>
