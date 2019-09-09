<template>
    <div id="user">
        <Submenu name="3">
            <template slot="title">
                <Icon type="ios-stats"/>
                用户信息
            </template>
            <MenuItem v-if="admin===1" name="3-1" disabled>系统管理员</MenuItem>
            <MenuItem v-if="admin===0" name="3-1" disabled>社团管理员</MenuItem>
            <Divider style="margin: 0"/>
            <MenuItem name="3-2">ID:</MenuItem>
            <MenuItem name="3-3">Name:</MenuItem>
            <Divider style="margin: 0"/>
            <MenuItem name="3-4"><a @click="quit">Log Out</a></MenuItem>
        </Submenu>
    </div>
</template>

<script>
    export default {
        name: "user",
        data() {
            return {
                admin: 0
            };
        },
        methods: {
            quit() {

            },

        },
    }
</script>

<style>


</style>
