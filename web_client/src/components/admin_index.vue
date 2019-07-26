<template>
    <div class="layout">
        <Layout :style="{minHeight: '100vh'}">
            <Header style="padding: 0px">
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

            </Header>
            <Layout>
                <Sider collapsible :collapsed-width="78" v-model="isCollapsed">
                    <Menu active-name="1-2" theme="dark" width="auto" :class="menuitemClasses">
                        <sysside></sysside>
                    </Menu>
                </Sider>
                <Layout>

                    <Content :style="{padding: '0 16px 16px'}">
                        <Breadcrumb :style="{margin: '16px 0'}">
                            <BreadcrumbItem>Home</BreadcrumbItem>
                            <BreadcrumbItem>Components</BreadcrumbItem>
                            <BreadcrumbItem>Layout</BreadcrumbItem>
                        </Breadcrumb>
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

    export default {
        name: "app",
        components: {assoside, sysside},
        data() {
            return {
                admin: 0,
                isCollapsed: false,
				username: 'unknown'
            };
        },
        methods: {
            quit() {

            },
			auth:function(){
				const token = window.localStorage.getItem('token');
				console.log('admin-index found token: ' + token);
				if(!token){
					this.$router.push({name:"SignIn"});
					return;
				}
				this.axios({
					method:'post',
					url:'http://localhost:8080/auth',
					data:token
				}).then((res)=>{
					if(res.data.username){
						this.username = res.data.username;
					}
					else{
						this.$router.push({name:"SignIn"});
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
		mounted: function(){
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

    .layout-con {
        height: 100%;
        width: 100%;
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
