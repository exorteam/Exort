<template>
<Layout>
    <NavHeader/>
    <Layout>
        <Sider>
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
            </Menu>
        </Sider>
        <Content style="min-height:420px;margin:10px;">
            <router-view/>
        </Content>
    </Layout>
</Layout>
</template>

<script>
import NavHeader from '@/components/nav/Header'

export default {
    components: {
        NavHeader
    },
    data() { return {
        active: ''
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
