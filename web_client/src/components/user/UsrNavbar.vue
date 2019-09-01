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
				<MenuItem name="UsrIndexUserInfo">
					<Icon type="ios-man" />
					个人信息
				</MenuItem>
			</Menu>
		</Content>
	</Layout>
</template>

<script>

export default {
	name: 'UsrNavbar',
	components:{
	},
	data(){
		return {
			active:''
		}
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
