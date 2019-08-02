<template>
<Split v-model="split_width_left" min="240px">
    <template #left>
        <Card :bordered="false" dis-hover :shadow="false" :padding="8">

            <template #title>
                <p>分类</p>
            </template>

            <template #extra>
                <span v-if="roles.curCategory == ''" style="font-style:italic;">(默认)</span>
                <span v-else>{{ roles.curCategory }}</span>
            </template>

            <Input v-model="category" placeholder="(默认)" @on-enter="changeCategory"/>

            <Button icon="md-search" style="margin:5px 5px 10px 0;" @click="changeCategory" :loading="processing">
                查询角色
            </Button>
            <Button icon="md-add" style="margin:5px 5px 10px 0;" @click="beginCreatingRole" :loading="processing">
                新建角色
            </Button>

        </Card>
    </template>
    <template #right>
        <Row type="flex">
            <Card style="margin: 0 0 6px 6px; width: 360px;"
                  v-for="role in roles.roleList" :key="role.name">

                <template #title>
                    <p>{{ role.name }}</p>
                </template>

                <template #extra>
                    <Tooltip content="设置权限">
                        <Button ghost icon="md-flag" type="warning" size="small"
                                :loading="processing" @click="editRolePerms(role)"/>
                    </Tooltip>

                    <Tooltip content="编辑角色">
                        <Button ghost icon="md-create" type="success" size="small"
                                :loading="processing" @click="beginEditingRole(role)"/>
                    </Tooltip>

                    <Tooltip content="删除角色">
                        <Button ghost icon="md-close" type="error" size="small"
                                :loading="processing" @click="applyDeleting(role)"/>
                    </Tooltip>
                </template>

                {{ role.description }}
            </Card>
        </Row>

        <Modal :title="role_editing.title"
               :closable="!processing" :mask-closable="!processing"
               :value="role_editing.mode!=null" @on-visible-change="onEditModalChange">
            <Form v-if="role_editing.role" :label-width="66">
                <FormItem label="名字">
                    <Input v-model="role_editing.role.name" :disabled="role_editing.mode=='edit'"/>
                </FormItem>
                <FormItem label="分类">
                    <Input v-model="role_editing.role.category" placeholder="(default)" :disabled="role_editing.mode=='create'"/>
                </FormItem>
                <FormItem label="描述">
                    <Input v-model="role_editing.role.description" type="textarea"/>
                </FormItem>
            </Form>

            <Alert type="error" v-if="role_editing.error">
                {{role_editing.error.title}}
                <template #desc>{{role_editing.error.message}}</template>
            </Alert>

            <template #footer>
                <Button type="success" @click="applyEditing" :loading="processing">
                    确定
                </Button>
                <Button :loading="processing" @click="onEditModalChange(false)">
                    返回
                </Button>
            </template>
        </Modal>

        <Modal title="设置权限" :footer-hide="true" :width="640"
               :closable="!processing" :mask-closable="!processing"
               :value="curRole.role!=null" @on-visible-change="$event?null:editRolePerms(null)">

            <Card v-if="curRole.role!=null" :bordered="false" dis-hover :shadow="false">
                <template #title>
                    <p>{{ curRole.role.name }}</p>
                </template>

                <template #extra>
                    <span v-if="curRole.role.category == ''" style="font-style:italic;">(默认)</span>
                    <span v-else>{{ curRole.role.category }}</span>

                    <Divider type="vertical"/>

                    <Tooltip content="更新列表">
                        <Button ghost icon="md-refresh" size="small" type="success"
                                :loading="processing" @click="editRolePerms(curRole.role)"/>
                    </Tooltip>
                </template>

                {{ curRole.role.description }}

            </Card>

            <Divider />

            <Transfer :titles="['所有权限','当前权限']"
                      :operations="['移除','添加']" @on-change="movePerms"
                      :list-style="{height:'360px',width:'260px'}"
                      :data="totalEntities" :target-keys="targetKeys" />

        </Modal>
    </template>
</Split>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
    data() { return {
        split_width_left: '240px',
        processing: false,
        category: '',
        role_editing: {
            mode: null,
            title: '',
            role: null,
            oldRole: null,
            error: null
        },
    }},
    computed: {
        totalEntities() {
            let entities = [];
            for (let cat in this.permCategories) {
                for (let perm of this.permCategories[cat]) {
                    entities.push({
                        key: perm.name,
                        label: perm.name + ' - ' + perm.category,
                        perm: perm
                    });
                }
            }
            entities.sort((a, b) => (
                a.perm.category.localeCompare(b.perm.category) ||
                a.perm.name.localeCompare(b.perm.name)
            ));
            return entities;
        },
        targetKeys() {
            let keys = [];
            for (let perm of this.curRole.perms) {
                keys.push(perm.name);
            }
            return keys;
        },
        ...mapState('sysadmin/perm', [
            'permCategories',
            'roles',
            'curRole'
        ])
    },
    methods: {
        beginEditingRole(role) {
            this.role_editing.oldRole = role;
            this.role_editing.role = {...role};
            this.role_editing.mode = 'edit';
            this.role_editing.title= '编辑角色';
        },
        beginCreatingRole() {
            this.role_editing.role = {
                name: '',
                category: this.roles.curCategory,
                description: ''
            }
            this.role_editing.mode = 'create';
            this.role_editing.title= '新建角色';
        },
        onEditModalChange(visible) {
            if (!visible) {
                this.role_editing.mode = null;
                this.role_editing.role = null;
                this.role_editing.error = null;
            }
        },
        applyEditing() {
            this.processing = true;
            this.role_editing.error = null;
            let that = this;
            (this.role_editing.mode == 'create' ?
                this.createRole(
                    this.role_editing.role
                ) :
                this.updateRole({
                    oldRole: this.role_editing.oldRole,
                    newRole: this.role_editing.role
                })
            ).then(() => {
                that.onEditModalChange(false);
            }).catch(err => {
                that.role_editing.error = {
                    title: '错误',
                    message: err
                }
            }).finally(() => {
                that.processing = false;
            });
        },
        applyDeleting(role) {
            this.processing = true;
            let that = this;
            this.deleteRole(role).catch(err => {
                console.log(err);
            }).finally(() => {
                that.processing = false;
            });
        },
        changeCategory() {
            this.processing = true;
            let that = this;
            this.updateRoleList(this.category).catch(err => {
                console.log(err);
            }).finally(() => {
                that.processing = false;
            });
        },
        editRolePerms(role) {
            this.processing = true;
            let that = this;
            this.updateCurRole(role).then(() => {
                that.updatePermList().catch(err => {
                    console.log(err);
                });
            }).catch(err => {
                console.log(err);
            }).finally(() => {
                that.processing = false;
            });
        },
        movePerms(targetKeys, direction, moveKeys) {
            this.processing = true;
            let that = this;
            (direction == 'left' ?
                this.revokePerms({
                    role: this.curRole.role,
                    permNames: moveKeys
                }) :
                this.grantPerms({
                    role: this.curRole.role,
                    permNames: moveKeys
                })
            ).catch(err => {
                console.log(err);
            }).finally(() => {
                that.processing = false;
            });
        },
        ...mapActions('sysadmin/perm', [
            'updateRoleList',
            'createRole',
            'updateRole',
            'deleteRole',
            'updateCurRole',
            'grantPerms',
            'revokePerms',
            'updatePermList'
        ])
    },
    created() {
        this.changeCategory();
    },
    watch: {
        '$route': function() {
            this.changeCategory();
        }
    }
}
</script>

<style>

</style>
