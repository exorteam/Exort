<template>
	<Card>
		<h2>#{{info.id}}</h2>

		<Form :model="formContent" :label-width="80">
			<FormItem label="Nickname">
				<Input type="text" v-model="formContent.nickname"></Input>
			</FormItem>
			<FormItem label="Gender">
				<RadioGroup v-model="formContent.gender">
					<Radio label="Secret">Secret</Radio>
					<Radio label="Male">Male</Radio>
					<Radio label="Female">Female</Radio>
				</RadioGroup>
			</FormItem>
			<FormItem label="Truename">
				<Input type="text" v-model="formContent.name"></Input>
			</FormItem>
			<FormItem label="StudentID">
				<Input type="text" v-model="formContent.studentId"></Input>
			</FormItem>
			<FormItem label="Phone">
				<Input type="tel" v-model="formContent.phone"></Input>
			</FormItem>
			<FormItem label="Email">
				<Input type="email" v-model="formContent.email"></Input>
			</FormItem>
			<FormItem label="Birthday">
				<Input type="date" v-model="formContent.birthday"></Input>
			</FormItem>
			<FormItem label="Wechat">
				<Input type="text" v-model="formContent.wechatId"></Input>
			</FormItem>
		</Form>
		<Button @click="onClickSubmit">Submit</Button>
	</Card>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
	name: 'UserInfoEditor',
	data() {
		return {
			formContent: {}
		}
	},
	computed: {
		...mapState('common/currentUserInfo',['info']),
        ...mapState('common/currentUser', ['username'])
	},
	methods: {
		...mapActions('common/currentUserInfo',[
			'refreshInfo',
			'updateInfo'
		]),
		onClickSubmit() {
			var updateBody = this.formContent;
			switch(updateBody.gender){
				case "Secret": updateBody.gender=0;break;
				case "Male"  : updateBody.gender=1;break;
				case "Female": updateBody.gender=2;
			}
			this.updateInfo({uinfo:updateBody}).then(()=>{
				this.$router.push({name:'UserInfoDetail'});
				this.$Notice.success({
					desc: '成功修改个人信息'
				})
			}).catch(err=>{
				this.$Notice.error({
					title: '修改个人信息时出现错误',
					desc: err
				})
			})
		}
	},
	mounted() {
		this.refreshInfo().then(() => {
			this.formContent = {...this.info}
		});
	}
}

</script>

