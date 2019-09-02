<template>
	<Layout>
		<Header :style="{background: '#fff'}">
			<Avatar shape="square" :src="require('@/assets/AssociationLogo/solid.jpg')" size="large" />
		</Header>
		<Content>
			<Menu width="auto" @active-name="active" @on-select="onSelect">
				<MenuItem name="UsrIndexHomePage">
					<Icon type="md-home" />
					主页
				</MenuItem>
				<MenuItem name="UsrIndexAssociationList">
					<Icon type="md-albums" />
					社团列表
				</MenuItem>
				<MenuItem name="UsrIndexArticleList">
					<Icon type="ios-book" />
					文章列表
				</MenuItem>
				<MenuItem name="UsrIndexActivityList">
					<Icon type="md-american-football" />
					活动列表
				</MenuItem>
				<!--<MenuItem v-if="!username" name="UsrIndexLogin">-->
				<!--    <Icon type="ios-man" />                     -->
				<!--    登陆                                      -->
				<!--</MenuItem>                                     -->
				<Submenu v-if="username" name="UsrIndexUserSubmenu">
					<template slot="title">
						<Icon type="ios-man" />
						{{username}}
					</template>
					<MenuItem name="UsrIndexUserInfo">
						个人信息
					</MenuItem>
					<MenuItem name="UsrIndexLogout">
						注销
					</MenuItem>
				</Submenu>

				<LoginModal v-model="login_show"/>
			</Menu>
		</Content>
	</Layout>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import LoginModal from '@/components/auth/LoginModal'

export default {
	name: 'UsrNavbar',
	components:{
		LoginModal
	},
	data(){
		return {
			active:'',
			login_show:false
		}
	},
    computed: {
        ...mapState('common/currentUser', [
            'uid',
            'username',
            'admin'
        ])
    },
	methods: {
		...mapActions('common/currentUser',['logout']),
		onSelect(name) {
			switch(name) {
				case 'UsrIndexLogout':
					this.logout();
			}
		}
	},
	created(){
		this.active = this.$route.name;
		if(!this.username){this.login_show=true}
	},
	watch: {
        '$route': function(newValue, oldValue) {
            this.active = newValue.name;
        }
    }

}
</script>

<style scoped>
</style>
