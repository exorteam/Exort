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
                <MenuItem name="AssoAdminIndex">
                    <Icon type="md-home"/>
                    概览
                </MenuItem>
                <MenuGroup title="社团相关">
                    <MenuItem name="assolist">
                        <Icon type="md-albums"/>
                        社团列表
                    </MenuItem>
					<MenuItem name="asso-application" :to="{name:'UsrArticleList'}">
						<Icon type="ios-book" />
                        社团文章
                    </MenuItem>
					<MenuItem name="asso-activity" :to="{name:'UsrActivityList'}">
						<Icon type="ios-cafe" />
                        社团活动
                    </MenuItem>
                </MenuGroup>
                <MenuGroup title="用户相关">
                    <MenuItem name="ArticleList">
                        <Icon type="md-chatboxes"/>
						消息通知
                    </MenuItem>
                    <MenuItem name="SysAdminUserList">
                        <Icon type="md-filing" />
                        申请列表
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
