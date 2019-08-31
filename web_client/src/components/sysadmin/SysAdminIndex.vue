<template>
<Layout>
    <NavHeader>
        <template #logo>
            <!--<img src="@/assets/AssociationLogo/solid.jpg" alt="LOGO" style="height:50px;">-->
        </template>
        <NavUser></NavUser>
    </NavHeader>
    <Layout>
        <Sider>
            <Menu width="auto" v-bind:active-name="active" :open-names="['perm-mgr']">
                <MenuItem name="SysAdminIndex" :to="{name: 'SysAdminIndex'}">
                    <Icon type="md-home"/>
                    概览
                </MenuItem>
                <MenuGroup title="社团管理">
                    <MenuItem name="associationList" :to="{name: 'associationList'}">
                        <Icon type="md-albums"/>
                        社团列表
                    </MenuItem>
					<MenuItem name="asso-application" :to="{name: 'ApplicationList'}">
                        <Icon type="ios-hand"/>
                        社团申请
                    </MenuItem>
                    <MenuItem name="ArticleList" :to="{name:'ArticleList'}">
                        <Icon type="ios-book"/>
                        文章列表
                    </MenuItem>
                    <MenuItem name="ActivityList" :to="{name:'ActivityList'}">
                        <Icon type="ios-cafe"/>
                        活动列表
                    </MenuItem>
                </MenuGroup>
                <MenuGroup title="用户管理">
                    <MenuItem name="SysAdminUserList" :to="{name:'SysAdminUserList'}">
                        <Icon type="md-people"/>
                        用户列表
                    </MenuItem>
                </MenuGroup>
                <MenuGroup title="权限管理">
                    <MenuItem name="SysAdminPermList" :to="{name:'SysAdminPermList'}">
                        <Icon type="md-flag"/>
                        权限列表
                    </MenuItem>
                    <MenuItem name="role-list" :to="{name:'SysAdminRoleList'}">
                        <Icon type="md-contacts"/>
                        角色列表
                    </MenuItem>
                </MenuGroup>
				<MenuGroup>
					<span>v0.1</span>
				</MenuGroup>
            </Menu>
        </Sider>
        <Content style="min-height:420px;margin:10px;">
            <router-view/>
        </Content>
    </Layout>
</Layout>
</template>

<script>
import NavHeader from '../common/nav/Header'
import NavUser from '../common/nav/User'

export default {
    components: {
        NavHeader,
        NavUser
    },
    data() { return {
        active: ""
    };},
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
