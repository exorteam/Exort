<template>
<Split v-model="split_width_left" min="240px">
    <template #left>
        <Button icon="md-refresh" style="margin:0 5px 10px 0;" @click="refresh" :loading="processing">
            刷新列表
        </Button>
        <Button icon="md-add" style="margin:0 5px 10px 0;" @click="beginCreatingPerm" :loading="processing">
            新建权限
        </Button>

        <Menu :active-name="active_cat" @on-select="active_cat=$event" width="auto">
            <MenuGroup title="分类">
                <MenuItem v-for="(perms, cat) in permCatList" :key="cat" :name="cat">
                    <span v-if="cat == ''" style="font-style:italic;">(默认)</span>
                    <span v-else>{{ cat }}</span>
                </MenuItem>
            </MenuGroup>
        </Menu>
    </template>

    <template #right>
        <Row type="flex">
            <Card style="margin: 0 0 6px 6px; width: 360px;"
                  v-for="perm in permCatList[active_cat]" :key="perm.name">

                <template #title>
                    <p>{{ perm.name }}</p>
                </template>

                <template #extra>
                    <Tooltip content="编辑权限">
                        <Button ghost icon="md-create" type="success" size="small"
                                :loading="processing" @click="beginEditingPerm(perm)"/>
                    </Tooltip>
                    <Tooltip content="删除权限">
                        <Button ghost icon="md-close" type="error" size="small"
                                :loading="processing" @click="applyDeleting(perm)"/>
                    </Tooltip>
                </template>

                {{ perm.description }}

            </Card>
        </Row>

        <Modal :title="perm_editing.title"
               :closable="!processing" :mask-closable="!processing"
               :value="perm_editing.mode!=null" @on-visible-change="onEditModalChange">
            <Form v-if="perm_editing.perm" :label-width="66">
                <FormItem label="名字">
                    <Input v-model="perm_editing.perm.name" :disabled="perm_editing.mode=='edit'"/>
                </FormItem>
                <FormItem label="分类">
                    <Input v-model="perm_editing.perm.category" placeholder="(default)"/>
                </FormItem>
                <FormItem label="描述">
                    <Input v-model="perm_editing.perm.description" type="textarea"/>
                </FormItem>
            </Form>

            <Alert type="error" v-if="perm_editing.error">
                {{perm_editing.error.title}}
                <template #desc>{{perm_editing.error.message}}</template>
            </Alert>

            <template #footer>
                <Button type="success" @click="applyEditing" :loading="processing">
                    确定
                </Button>
                <Button @click="onEditModalChange(false)" :loading="processing">
                    返回
                </Button>
            </template>
        </Modal>
    </template>
</Split>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
    data() { return {
        split_width_left: '240px',
        active_cat: '',
        processing: false,
        perm_editing: {
            mode: null,
            title: '',
            perm: null,
            oldPerm: null,
            error: null
        }
    };},
    computed: {
        ...mapGetters('systemAdmin/perm', [
            'permCatList'
        ])
    },
    methods: {
        beginEditingPerm(perm) {
            this.perm_editing.oldPerm = perm;
            this.perm_editing.perm = {...perm};
            this.perm_editing.mode = 'edit';
            this.perm_editing.title= '编辑权限';
        },
        beginCreatingPerm() {
            this.perm_editing.perm = {
                name: '',
                category: '',
                description: ''
            }
            this.perm_editing.mode = 'create';
            this.perm_editing.title= '新建权限';
        },
        onEditModalChange(visible) {
            if (!visible) {
                this.perm_editing.mode = null;
                this.perm_editing.perm = null;
                this.perm_editing.error = null;
            }
        },
        applyEditing() {
            this.processing = true;
            this.perm_editing.error = null;
            let that = this;
            (this.perm_editing.mode == 'create' ?
                this.createPerm(
                    this.perm_editing.perm
                ) :
                this.updatePerm({
                    oldPerm: this.perm_editing.oldPerm,
                    newPerm: this.perm_editing.perm
                })
            ).then(() => {
                that.refreshActive(that.perm_editing.perm.category);
                that.onEditModalChange(false);
            }).catch(err => {
                that.perm_editing.error = {
                    title: '错误',
                    message: err
                }
            }).finally(() => {
                that.processing = false;
            });
        },
        applyDeleting(perm) {
            this.processing = true;
            let that = this;
            this.deletePerm(perm).then(() => {
                that.refreshActive();
            }).catch(err => {
                console.log(err);
            }).finally(() => {
                that.processing = false;
            });
        },
        refresh() {
            this.processing = true;
            let that = this;
            this.updatePermList().then(() => {
                that.refreshActive('');
            }).catch(err => {
                console.log(err);
            }).finally(() => {
                that.processing = false;
            })
        },
        refreshActive(cat) {
            if (cat) {
                this.active_cat = cat;
            }
            if (!this.permCatList[this.active_cat]) {
                this.active_cat = '';
                for (let cat in this.permCatList) {
                    this.active_cat = cat;
                    break;
                }
            }
        },
        ...mapActions('systemAdmin/perm', [
            'createPerm',
            'updatePerm',
            'deletePerm',
            'updatePermList'
        ])
    },
    created() {
        this.refresh();
    },
    watch: {
        '$route': function() {
            this.refresh();
        }
    }
}
</script>

<style>

</style>
