<template>
	<Layout>
		<Header :style="{background: '#fff'}">
			<Avatar shape="square" :src="logo" size="large" />
		</Header>
		<Content>
			<Menu width="auto" @active-name="active" @on-select="onSelect">
				<MenuItem name="UsrIndexHomePage" :to="{name:'UserHomePage'}">
					<Icon type="md-home" />
					主页
				</MenuItem>
				<MenuItem name="UsrIndexAssociationList" :to="{name:'UserAssociationList'}">
					<Icon type="md-albums" />
					社团列表
				</MenuItem>
				<MenuItem name="UsrIndexArticleList" :to="{name:'UserArticleList'}">
					<Icon type="ios-book" />
					文章列表
				</MenuItem>
				<MenuItem name="UsrIndexActivityList">
					<Icon type="md-american-football" />
					活动列表
				</MenuItem>
				<Submenu v-if="username" name="UsrIndexUserSubmenu">
					<template slot="title">
						<Icon type="ios-man" />
						{{username}}
					</template>
					<MenuItem name="UsrIndexUserInfo" :to="{name:'UserInfoDetail'}">
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
        ]),
		...mapState('common/applicationEntries',[
			'logo'
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
        },
		'admin':  function() {
			if(this.admin.isSysAdmin){
				this.$router.push({name:'SystemAdminIndex'});
			}
		}
    }

}
</script>

<style scoped>
</style>
