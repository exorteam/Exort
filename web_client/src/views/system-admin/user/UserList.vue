<template>
<Split v-model="split_width_left" min="240px">
    <template #left>
        <Card :bordered="false" dis-hover :shadow="false" :padding="8">

            <template #title><p>域</p></template>
            <template #extra>{{ scope }}</template>

            <Input v-model="scopeInputing" placeholder="sys" @on-enter="searchUsers"/>

            <Button icon="md-search" style="margin:5px 5px 10px 0;"
                    :loading="processing" @click="searchUsers">
                搜索用户
            </Button>

            <Button v-if="selectedRole==null"
                    icon="md-contacts" style="margin:5px 5px 10px 0;"
                    :loading="processing" @click="beginSelectRole">
                选择一个角色
            </Button>
            <Card v-else dis-hover :shadow="false">
                <template #title>
                    <p>{{ selectedRole.name }}</p>
                    <span v-if="selectedRole.category == ''" style="font-style:italic;">(默认)</span>
                    <span v-else>{{ selectedRole.category }}</span>
                    <Divider type="vertical"/>
                    <Tooltip content="重新选择">
                        <Button ghost size="small" icon="ios-undo" type="success"
                                :loading="processing" @click="beginSelectRole"/>
                    </Tooltip>
                    <Tooltip content="取消选择">
                        <Button ghost size="small" icon="md-close" type="error"
                                :loading="processing" @click="selectRole(null)"/>
                    </Tooltip>
                </template>
                {{ selectedRole.description }}
            </Card>

        </Card>
    </template>

    <template #right>
        <Table :loading="processing" :columns="columns" :data="tableData" stripe />

        <Modal title="选择角色" v-model="selecting" :footer-hide="true"
               :closable="!processing" :mask-closable="!processing">
            <Form :label-width="36" @submit.native.prevent>
                <FormItem label="分类">
                    <Input placeholder="(默认)" icon='ios-search'
                           v-model="categoryInputing" :disabled="processing"
                           @on-enter="searchRoles" @on-click="searchRoles"/>
                </FormItem>
            </Form>
            <Scroll :height="480">
                <Collapse simple accordion>
                <Panel v-for="role in roles.roleList" :key="role.name" hide-arrow>
                    <Row type="flex" justify="space-between" align="middle">
                        <Col>{{ role.name }}</Col>
                        <Col style="padding:0 30px">
                            <Tooltip content="选择角色">
                                <Button ghost icon="md-checkmark" type="success" size="small"
                                        :loading="processing" @click="selectRole(role)"/>
                            </Tooltip>
                        </Col>
                    </Row>
                    <template #content>
                        {{ role.description }}
                    </template>
                </Panel>
                </Collapse>
            </Scroll>
        </Modal>
        <Modal title="设置角色" :value="curUser.user!=null" footer-hide
               :closable="!processing" :mask-closable="!processing"
               @on-visible-change="$event?null:finishEditUserRoles()">
            <Input v-model="scopeInputing2" placeholder="sys" @on-enter="searchUsers"/>
        </Modal>
    </template>
</Split>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import UserTableCommandColumn from './UserTableCommandColumn'

export default {
    data() { return {
        split_width_left: '240px',
        scope: '',
        scopeInputing: '',
        processing: false,
        selectedRole: null,
        selecting: false,
        categoryInputing: '',
        scopeInputing2: '',
        columns: [
            {
                title: 'ID',
                type: 'index',
                indexMethod: row => row.id,
                minWidth: 50,
                fixed: 'left'
            },
            {
                title: '操作',
                render: (h, {row}) => h(UserTableCommandColumn, {
                    props: {index: row.index}
                }),
                minWidth: 80,
                fixed: 'left'
            },
            //{ title: '用户名', key: 'username' },
            { title: '昵称', key: 'nickname' },
            { title: '性别', key: 'gender' },
            { title: '生日', key: 'birthday'},
            { title: '姓名', key: 'name' },
            { title: '学号', key: 'studentId' },
            { title: '电话', key: 'phone' },
            { title: '邮箱', key: 'email' },
            { title: 'QQ', key: 'qqId'},
            { title: '微信', key: 'wechatId' },
            { title: '状态', key: 'state'}
        ]
    }},
    computed: {
        tableData() {
            return this.users.list.map((a, i) => ({
                id: a.id,
                nickname: a.nickname || '',
                gender: ({0:'女',1:'男','-1':'未知'})[a.gender] || '',
                birthday: (a.birthday && a.birthday.toLocaleDateString()) || '',
                name: a.name || '',
                studentId: a.studentId || '',
                phone: a.phone || '',
                email: a.email || '',
                qqId: a.qqId || '',
                wechatId: a.wechatId || '',
                state: a.enabled ? '正常' : '封禁',
                index: i
            }));
        },
        ...mapState('systemAdmin/perm', ['roles']),
        ...mapState('systemAdmin/user', ['users', 'curUser'])
    },
    methods: {
        searchRoles() {
            this.processing = true;
            let that = this;
            this.updateRoleList(this.categoryInputing).catch(err => {
                console.log(err);
            }).finally(() => {
                that.processing = false;
            });
        },
        beginSelectRole() {
            this.selecting = true;
            this.categoryInputing = '';
            this.searchRoles();
        },
        selectRole(role) {
            this.selectedRole = role;
            this.selecting = false;
            this.searchUsers();
        },
        searchUsers() {
            this.processing = true;
            let that = this;
            this.updateUserList({
                scope: this.scopeInputing,
                role: this.selectedRole
            }).then(() => {
                that.scope = that.scopeInputing || 'sys';
            }).catch(err => {
                console.log(err);
            }).finally(() => {
                that.processing = false;
            });
        },
        updateCurUser(scope) {
            this.processing = true;
            let that = this;
            this.updateCurUser({
                user: null,
                scope
            }).catch(err => {
                console.log(err);
            }).finally(() => {
                that.processing = false;
            })
        },
        ...mapActions('systemAdmin/perm', ['updateRoleList']),
        ...mapActions('systemAdmin/user', ['updateUserList'])
    }
}
</script>

<style>

</style>
