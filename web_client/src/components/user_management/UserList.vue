<template>
<div>
    <Row type="flex" justify="start" :gutter="16" style="padding-bottom: 8px">
        <Col><Button icon="md-add" @click="info_creating=true; info_editing_show=true;">New</Button></Col>
        <Col><Input search enter-button placeholder="搜索用户" @on-search="updateUsers"/></Col>
    </Row>

    <Table :columns="columns" :data="users"/>

    <Modal v-model="info_editing_show" @on-ok="info_ok" @on-cancel="info_cancel" loading :closable="false">
        <Form v-model="info_editing" :label-width="66">
            <FormItem label="id">
                <Input v-model="info_editing.id" disabled/>
            </FormItem>
            <FormItem label="用户名">
                <Input v-model="info_editing.username" disabled/>
            </FormItem>
            <FormItem label="昵称">
                <Input v-model="info_editing.nickname"/>
            </FormItem>
            <FormItem label="自我介绍">
                <Input v-model="info_editing.description" type="textarea"/>
            </FormItem>
            <FormItem label="性别">
                <Select v-model="info_editing.gender">
                    <Option value="secret">未知</Option>
                    <Option value="man">男</Option>
                    <Option value="woman">女</Option>
                    <Option value="other">其他</Option>
                </Select>
            </FormItem>
            <FormItem label="生日">
                <DatePicker type="date" placeholder="yyyy-mm-dd" v-model="info_editing.birthday"></DatePicker>
            </FormItem>
            <FormItem label="真实姓名">
                <Input v-model="info_editing.name"/>
            </FormItem>
            <FormItem label="学号">
                <Input v-model="info_editing.student_number"/>
            </FormItem>
            <FormItem label="手机">
                <Input v-model="info_editing.phone"/>
            </FormItem>
            <FormItem label="邮箱">
                <Input v-model="info_editing.email"/>
            </FormItem>
            <FormItem label="QQ">
                <Input v-model="info_editing.qq"/>
            </FormItem>
            <FormItem label="微信">
                <Input v-model="info_editing.wechat"/>
            </FormItem>
        </Form>
    </Modal>

    <Modal v-model="role_editing_show" @on-ok="role_finish" @on-cancel="role_finish">
        <Form v-model="info_editing" inline>
            <FormItem>
                <Input v-model="info_editing.id" disabled>
                    <span slot="prepend">ID</span>
                </Input>
            </FormItem>
            <FormItem>
                <Input prefix="ios-contact" v-model="info_editing.username" disabled/>
            </FormItem>
        </Form>
        <Row type="flex" justify="start" :gutter="16" style="margin-bottom: 8px">
            <Col><Button icon="md-add">添加角色</Button></Col>
            <Col><Input search placeholder="all">
                <span slot="prepend">Scope</span>
            </Input></Col>
        </Row>
        <Row type="flex">
            <Card style="margin: 8px 8px 0 0; width: 400px;" v-for="role in role_editing" :key="role.id">
                <Row slot="title" type="flex" align="middle">
                    <Col span="20">
                        <span> {{ role.id }} </span>
                        <Divider type="vertical" />
                        <span>{{ role.name }} </span>
                    </Col>
                    <Col>
                        <Button ghost icon="md-close" type="error"/>
                    </Col>
                </Row>
                {{ role.description }}
            </Card>
        </Row>
        <div slot="footer">
        </div>
    </Modal>
</div>
</template>

<script>
import UserListExpand from '@/components/user_management/UserListExpand'

export default {
    components: {UserListExpand},
    data() {
        return {
            users: [],
            columns: [
                {
                    type: 'expand',
                    width: 50,
                    render: (h, params) => {
                        return h(UserListExpand, {
                            props: {
                                user: params.row,
                                parentListeners: {
                                    'edit-info': user => {
                                        this.info_editing = {}
                                        for (var e in user) {
                                            this.info_editing[e] = user[e];
                                        }
                                        this.info_editing_show = true
                                    },
                                    'edit-role': user => {
                                        this.info_editing = {}
                                        for (var e in user) {
                                            this.info_editing[e] = user[e];
                                        }
                                        this.fetchRoles("all")
                                        this.role_editing_show = true
                                    }
                                }
                            }
                        })
                    }
                },
                {
                    title: 'id',
                    key: 'id'
                },
                {
                    title: '用户名',
                    key: 'username'
                },
                {
                    title: '昵称',
                    key: 'nickname'
                },
                {
                    title: '状态',
                    key: 'state'
                }
            ],
            info_editing: {},
            role_editing: {},
            info_creating: false,
            info_editing_show: false,
            role_editing_show: false
        }
    },
    methods: {
        updateUser(user) {
            // replace with one fetch user from server
            if (this.info_creating) {
                this.users = [this.info_editing].concat(this.users);
            } else {
                for (var i in this.users) {
                    if (this.users[i].id == user.id) {
                        for (var e in user) {
                            this.users[i][e] = user[e];
                        }
                    }
                }
            }
        },
        updateUsers(keyword) {
            this.users = [
                {id: 1, username: "hello1", nickname: "world1", description: "hello world!", gender: "secret", birthday: new Date(1999, 1, 2), name: "XXX", student_number: "123456", phone: "654321", email: "sb@sb.sb", qq: "09876", wechat: "03214", state: "normal"},
                {id: 2, username: "hello2", nickname: "world2", description: "hello world!", gender: "secret", birthday: new Date(1999, 1, 2), name: "XXX", student_number: "123456", phone: "654321", email: "sb@sb.sb", qq: "09876", wechat: "03214", state: "blocked"},
                {id: 3, username: "hello3", nickname: "world3", description: "hello world!", gender: "secret", birthday: new Date(1999, 1, 2), name: "XXX", student_number: "123456", phone: "654321", email: "sb@sb.sb", qq: "09876", wechat: "03214", state: "inactive"},
                {id: 4, username: "hello4", nickname: "world4", description: "hello world!", gender: "secret", birthday: new Date(1999, 1, 2), name: "XXX", student_number: "123456", phone: "654321", email: "sb@sb.sb", qq: "09876", wechat: "03214", state: "normal"},
                {id: 5, username: "hello5", nickname: "world5", description: "hello world!", gender: "secret", birthday: new Date(1999, 1, 2), name: "XXX", student_number: "123456", phone: "654321", email: "sb@sb.sb", qq: "09876", wechat: "03214", state: "normal"},
                {id: 6, username: "hello6", nickname: "world6", description: "hello world!", gender: "secret", birthday: new Date(1999, 1, 2), name: "XXX", student_number: "123456", phone: "654321", email: "sb@sb.sb", qq: "09876", wechat: "03214", state: "normal"},
            ]
        },
        fetchRoles(id, scope) {
            this.role_editing = [
                {id: 1, name: "manager", description: "管理员"},
                {id: 2, name: "normal", description: "普通成员"}
            ];
        },
        info_ok() {
            // replace with a request
            setTimeout(() => {
                if (this.info_creating) {
                    this.info_editing.id = "test-created_local";
                    this.info_editing.state = "inactive";
                }
                this.updateUser(this.info_editing);
                this.info_editing_show = false;
                this.info_creating = false;
                this.info_editing = {};
            }, 1000);
        },
        info_cancel() {console.log('cancel');
            this.info_creating = false;
            this.info_editing = {};
        },
        role_finish() {
            this.role_editing = {};
            this.info_editing = {};
        }
    }
}
</script>

<style>

</style>
