<template>
<Menu mode="horizontal" theme="dark" style="display:inline-block;" @on-select="onSelect">
    <Submenu name="user" v-if="uid">
        <template #title>
            <Avatar size="large">{{username}}</Avatar>
        </template>
        <MenuItem name="uid">UID {{uid}}</MenuItem>
        <MenuItem name="sysAdmin" v-if="admin.isSysAdmin" :to="{name: 'SysAdminIndex'}">
            系统管理后台
        </MenuItem>
        <MenuItem name="assoAdmin" v-if="admin.assoAdmins.length" :to="{name: 'AssoAdminIndex'}">
            社团管理后台
        </MenuItem>
        <MenuItem name="logout">注销</MenuItem>
    </Submenu>
    <MenuItem name="login" v-else>
        <Icon type="ios-contact"/>
        登录
    </MenuItem>
    <LoginModal v-model="login_show"/>
</Menu>
</template>

<script>
import LoginModal from '../auth/LoginModal'
import { mapState, mapActions } from 'vuex'

export default {
    name: "NavUser",
    components: {
        LoginModal
    },
    data() { return {
        login_show: false
    }},
    computed: {
        ...mapState('common/auth', [
            'uid',
            'username',
            'admin'
        ])
    },
    methods: {
        ...mapActions('common/auth', [
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
                case "uid":
                    console.log("goto user info");
            }
        }
    }
}
</script>

<style>
</style>
