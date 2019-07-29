<template>
    <div class="layout">
        <Layout :style="{minHeight: '100vh'}">
            <Header style="padding: 0px">
                <adminnav></adminnav>
            </Header>
            <Layout>
                <Sider collapsible :collapsed-width="78" v-model="isCollapsed">
                    <Menu theme="dark" width="auto" :class="menuitemClasses">
                        <sysside></sysside>
                    </Menu>
                </Sider>
                <Layout>
                    <br/>
                    <Content :style="{padding: '0 16px 16px'}">
                        <Card>
                            <div style="height: 100%;">
                                <router-view/>
                            </div>
                        </Card>
                    </Content>
                </Layout>
            </Layout>
        </Layout>
    </div>
</template>

<script>
    import assoside from './sidebar/assoSidebar'
    import sysside from './sidebar/sysSidebar'
    import adminnav from './admin_nav/nav'

    export default {
        name: "app",
        components: {assoside, sysside, adminnav},
        data() {
            return {
                isCollapsed: false,
                username: 'unknown'
            };
        },
        methods: {
            auth: function () {
                const token = window.localStorage.getItem('token');
                console.log('admin-index found token: ' + token);
                if (!token) {
                    this.$router.push({name: "SignIn"});
                    return;
                }
                this.axios({
                    method: 'post',
                    url: 'http://localhost:8080/auth',
                    data: token
                }).then((res) => {
                    if (res.data.username) {
                        this.username = res.data.username;
                    }
                    else {
                        this.$router.push({name: "SignIn"});
                    }
                })
            }
        },
        computed: {
            menuitemClasses: function () {
                return [
                    'menu-item',
                    this.isCollapsed ? 'collapsed-menu' : ''
                ]
            }
        },
        mounted: function () {
            this.auth();
        }

    }
</script>

<style scoped>
    .layout {
        border: 1px solid #d7dde4;
        background: #f5f7f9;
        position: relative;
        border-radius: 4px;
        overflow: hidden;
    }

    .menu-item span {
        display: inline-block;
        overflow: hidden;
        width: 100px;
        text-overflow: ellipsis;
        white-space: nowrap;
        vertical-align: bottom;
        transition: width .2s ease .2s;
    }

    .menu-item i {
        transform: translateX(0px);
        transition: font-size .2s ease, transform .2s ease;
        vertical-align: middle;
        font-size: 16px;
    }

    .collapsed-menu span {
        width: 0px;
        transition: width .2s ease;
    }

    .collapsed-menu i {
        transform: translateX(5px);
        transition: font-size .2s ease .2s, transform .2s ease .2s;
        vertical-align: middle;
        font-size: 22px;
    }
</style>
