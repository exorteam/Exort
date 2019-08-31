<template>
<Menu mode="horizontal" theme="dark" style="display:inline-block;" @on-select="onSelect">
    <Submenu name="user" v-if="uid">
        <template #title>
            <Avatar size="large">{{username}}</Avatar>
        </template>
        <MenuItem name="uid" disabled>
            UID {{uid}}
        </MenuItem>
        <MenuItem name="info">
            个人信息
        </MenuItem>
        <MenuItem name="SystemAdminIndex" v-if="admin.isSysAdmin" :to="{name:'SystemAdminIndex'}">
            系统管理后台
        </MenuItem>
        <MenuItem name="AssociationAdminIndex" v-if="admin.assoAdmins.length" :to="{name:'AssociationAdminIndex'}">
            社团管理后台
        </MenuItem>
        <MenuItem name="logout">
            注销
        </MenuItem>
    </Submenu>
    <MenuItem name="login" v-else>
        <Icon type="ios-contact"/>
        登录
    </MenuItem>
    <LoginModal v-model="login_show"/>
</Menu>
</template>

<script>
import LoginModal from '@/components/auth/LoginModal'
import { mapState, mapActions } from 'vuex'

export default {
    components: {
        LoginModal
    },
    data() { return {
        login_show: false
    }},
    computed: {
        ...mapState('common/currentUser', [
            'uid',
            'username',
            'admin'
        ])
    },
    methods: {
        ...mapActions('common/currentUser', [
            'logout'
        ]),
        onSelect(name) {
            switch (name) {
                case "logout":
                    this.logout();
                    break;
                case "login":
                    this.login_show = true;
                    break;
                case "info":
                    this.$Notice.warning({
                        title: '页面施工中',
                        desc: '个人信息页面开发中'
                    })
            }
        }
    }
}
</script>

<style>
</style>
