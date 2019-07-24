<template>
    <div id="adminnav">
        <div class="layout-logo"></div>
        <div class="layout-nav">
            <Menu mode="horizontal" theme="dark" active-name="1" style="width: 100%;child-align: right">
                <Submenu name="2">
                    <template slot="title">
                        <Icon type="ios-stats"/>
                        通知
                    </template>
                    <MenuItem v-if="admin==='1'" name="3-1" disabled>Manager</MenuItem>
                    <MenuItem v-if="admin==='0'" name="3-1" disabled>User</MenuItem>
                    <Divider style="margin: 0"/>
                    <MenuItem name="3-1" :to="{name:'ShoppingCart'}">My Shopping Cart</MenuItem>
                    <MenuItem name="3-2" :to="{name:'AllOrders'}">My Orders</MenuItem>
                    <Divider style="margin: 0"/>
                    <MenuItem name="3-3">Account Setting</MenuItem>
                    <Divider style="margin: 0"/>
                    <MenuItem name="3-4"><a @click="quit">Logout</a></MenuItem>
                </Submenu>
                <Submenu name="3">
                    <template slot="title">
                        <Icon type="ios-stats"/>
                        用户信息
                    </template>
                    <MenuItem v-if="admin==='1'" name="3-1" disabled>Manager</MenuItem>
                    <MenuItem v-if="admin==='0'" name="3-1" disabled>User</MenuItem>
                    <Divider style="margin: 0"/>
                    <MenuItem name="3-1" :to="{name:'ShoppingCart'}">My Shopping Cart</MenuItem>
                    <MenuItem name="3-2" :to="{name:'AllOrders'}">My Orders</MenuItem>
                    <Divider style="margin: 0"/>
                    <MenuItem name="3-3">Account Setting</MenuItem>
                    <Divider style="margin: 0"/>
                    <MenuItem name="3-4"><a @click="quit">Logout</a></MenuItem>
                </Submenu>
            </Menu>
        </div>
    </div>
</template>

<script>
    export default {
        name:"adminnav",
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

    .layout-logo {
        width: 100px;
        height: 30px;
        background: #5b6270;
        border-radius: 3px;
        float: left;
        position: relative;
        top: 15px;
        left: 20px;
    }

    .layout-nav {
        width: 420px;
        margin: 0 auto;
        margin-right: 20px;
    }

</style>
