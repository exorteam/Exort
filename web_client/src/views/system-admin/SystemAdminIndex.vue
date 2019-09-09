<template>
<Layout>
    <NavHeader/>
    <Layout>
		<Sider :style="{background: '#fff'}">
            <Menu width="auto" v-bind:active-name="active" :open-names="['perm-mgr']">
                <MenuItem name="SystemAdminIndex" :to="{name:'SystemAdminIndex'}">
                    <Icon type="md-home"/>
                    概览
                </MenuItem>
                <MenuGroup title="社团管理">
                    <MenuItem name="SystemAdminAssociationList" :to="{name:'SystemAdminAssociationList'}">
                        <Icon type="md-albums"/>
                        社团列表
                    </MenuItem>
					<MenuItem name="SystemAdminApplicationList" :to="{name:'SystemAdminApplicationList'}">
                        <Icon type="ios-hand"/>
                        社团申请
                    </MenuItem>
                </MenuGroup>
                <MenuGroup title="用户管理">
                    <MenuItem name="SystemAdminUserList" :to="{name:'SystemAdminUserList'}">
                        <Icon type="md-people"/>
                        用户列表
                    </MenuItem>
                </MenuGroup>
                <MenuGroup title="权限管理">
                    <MenuItem name="SystemAdminPermList" :to="{name:'SystemAdminPermList'}">
                        <Icon type="md-flag"/>
                        权限列表
                    </MenuItem>
                    <MenuItem name="SystemAdminRoleList" :to="{name:'SystemAdminRoleList'}">
                        <Icon type="md-contacts"/>
                        角色列表
                    </MenuItem>
                </MenuGroup>
				<MenuGroup title="其他操作">
					<MenuItem @click.native="postNotificationModal = true">
						<Icon type="ios-chatboxes" />
						发布公告
					</MenuItem>
				</MenuGroup>
            </Menu>
        </Sider>
        <Content style="min-height:420px;margin:10px;">
            <router-view/>
        </Content>

		<Modal v-model="postNotificationModal"
				title="New Notification"
				:loading="true"
				@on-ok="onClickPostNotification">
			<Form :model="newNotification" :label-width="20">
				<p>向订阅用户推送通知:</p>
				<br>
				<FormItem>
					<Input v-model="newNotification.content" type="textarea" :rows="5" />
				</FormItem>
			</Form>
		</Modal>
    </Layout>
</Layout>
</template>

<script>
import NavHeader from '@/components/nav/Header'
import { mapActions } from 'vuex'

export default {
    components: {
        NavHeader
    },
    data() { 
		return {
			active: '',
			postNotificationModal : false,
			newNotification: {
				content: ''
			}
		};
	},
	methods: {
		...mapActions('common/currentUserMsgs',['postSysNotification']),
		onClickPostNotification() {
			this.postSysNotification({
				content: this.newNotification.content
			}).then(res => {
				console.log(res)
				this.postNotificationModal = false
			})
		}
	},
    created() {
        this.active = this.$route.name;
    },
    watch: {
        '$route': function(newValue, oldValue) {
            this.active = newValue.name;
        }
    }
}
</script>

<style>

</style>
