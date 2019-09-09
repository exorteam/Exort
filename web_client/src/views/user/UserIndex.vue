<template>
<Layout class="user-index" style="background:white">
	<Sider class="sider" :width="240" style="background:white">
		<div class="nav">
			<div class="logo">
				<Avatar shape="square" :src="logo" size="large" />
			</div>
			<Menu v-if="uid" :active-name="active" class="menu">
				<MenuItem name="UserHomePage" :to="{name:'UserHomePage'}">
					<Icon type="md-home" />
					主页
				</MenuItem>
				<MenuItem name="UserAssociationList" :to="{name:'UserAssociationList'}">
					<Icon type="md-albums" />
					社团
				</MenuItem>
				<MenuItem name="UserActivityList" :to="{name:'UserActivityList'}">
					<Icon type="md-american-football" />
					活动
				</MenuItem>
				<MenuItem name="UserApplicationList" :to="{name:'UserApplicationList'}">
					<Icon type="md-paper-plane" />
					申请
				</MenuItem>
				<MenuItem name="UserMessagePage" :to="{name:'UserMessagePage'}">
					<Icon type="md-notifications-outline" />
					通知
				</MenuItem>
				<MenuItem name="user" :to="{name:'UserInfoDetail', params:{id: uid}}">
					<Avatar size="small"></Avatar>
					{{ info && info.nickname }}
				</MenuItem>
			</Menu>
			<Login v-else class="login" />
		</div>
	</Sider>
	<Content style="min-height:420px;margin-left:10px">
		<router-view/>
	</Content>
</Layout>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import Login from '@/components/auth/Login'

export default {
	components:{
		Login
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
            'info',
            'admin'
        ]),
		...mapState('common/applicationEntries',[
			'logo'
		])
    },
	methods: {
		...mapActions('common/currentUser',['logout'])
	},
	created(){
		this.active = this.$route.name;
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

<style>
.user-index {
	width: 960px;
	height: 100%;
	margin: 0 auto;
}

.user-index .sider {
	height: 100%;
	width: 240px;
}

.user-index .nav {
	position: fixed;
	width: 240px;
	height: 100%;
	z-index: 3;
}
.user-index .nav:after {
	content: '';
    display: block;
    width: 1px;
    height: 100%;
    background: #dcdee2;
    position: absolute;
    top: 0;
    bottom: 0;
    right: 0;
    z-index: 1;
}

.user-index .logo {
	padding: 16px 24px;
}

.user-index .menu .ivu-menu-item {
	font-size: 19px;
	padding: 16px 24px;
}

.user-index .login {
	padding: 16px 24px;
}


</style>
