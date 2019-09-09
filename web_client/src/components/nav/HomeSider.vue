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
				<MenuItem name="UsrIndexSearchUser" @click.native="searchUserModal=true">
					<Icon type="ios-search" />
					搜索用户
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
				<Modal v-model="searchUserModal"
						title="根据用户ID搜索用户"
						:loading="true"
						@on-ok="onClickSearchUser">
					<Input v-model="searchUserId"/>
				</Modal>
			</Menu>
		</Content>
	</Layout>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import LoginModal from '@/components/auth/LoginModal'

export default {
	components:{
		LoginModal
	},
	data(){
		return {
			active:'',
			login_show:false,

			searchUserModal: false,
			searchUserId: ''
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
		...mapActions('common/currentUser',[
			'logout',
			'getUserInfoById'
		]),
		onSelect(name) {
			switch(name) {
				case 'UsrIndexLogout':
					this.logout();
			}
		},
		onClickSearchUser(){
			console.log(this.searchUserId);
			this.getUserInfoById({
				uid:this.searchUserId
			}).then(res => {
				console.log(res);
				if(res.data.data){
					this.$router.push({
						name:'UserSearchDetail',
						params: {
							uid: this.searchUserId
						}
					});
				}
				this.searchUserModal = false
			})
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
