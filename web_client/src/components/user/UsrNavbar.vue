<template>
	<Layout>
		<Header :style="{background: '#fff'}">
			<Avatar shape="square" :src="require('@/assets/AssociationLogo/solid.jpg')" size="large" />
		</Header>
		<Content>
			<Menu width="auto" @active-name="active">
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
				<Submenu name="UsrIndexUserSubmenu">
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
			</Menu>
		</Content>
	</Layout>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
	name: 'UsrNavbar',
	components:{
	},
	data(){
		return {
			active:''
		}
	},
    computed: {
        ...mapState('common/currentUser', [
            'uid',
            'username',
            'admin'
        ])
    },
	created(){
		this.active = this.$route.name;
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
