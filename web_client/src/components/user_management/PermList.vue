<template>
    <div>
        <Button icon="md-add" @click="perm_creating = true; perm_editing_show = true;" style="margin-bottom: 8px">New
        </Button>
        <Row>
            <Col span="4">
                <Menu :active-name="active_name" @on-select="active_name=$event" style="width: 100%">
                    <MenuItem v-for="(pl, cat) in cat_permissions" :key="cat" :name="cat">{{ cat }}</MenuItem>
                </Menu>
            </Col>
            <Col>
                <Row type="flex">
                    <Card style="margin: 0 0 8px 8px; width: 360px;" v-for="perm in cat_permissions[active_name]"
                          :key="perm.id">
                        <Row slot="title" type="flex" align="middle">
                            <Col span="16">
                                <span> {{ perm.id }} </span>
                                <Divider type="vertical"/>
                                <span>{{ perm.name }} </span>
                            </Col>
                            <Col>
                                <Button ghost icon="md-create" type="success" @click="perm_edit(perm)"/>
                                <Button ghost icon="md-close" type="error" @click="perm_delete(perm)"/>
                            </Col>
                        </Row>
                        {{ perm.description }}
                    </Card>
                </Row>
            </Col>
        </Row>
        <Modal v-model="perm_editing_show" @on-ok="perm_ok" @on-cancel="perm_cancel">
            <Form v-model="perm_editing" :label-width="66">
                <FormItem label="ID">
                    <Input v-model="perm_editing.id" disabled/>
                </FormItem>
                <FormItem label="名字">
                    <Input v-model="perm_editing.name"/>
                </FormItem>
                <FormItem label="分类">
                    <Input v-model="perm_editing.category"/>
                </FormItem>
                <FormItem label="描述">
                    <Input type="textarea" v-model="perm_editing.description"/>
                </FormItem>
            </Form>
        </Modal>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                active_name: 'cat aaa',
                permissions: [
                    {id: 1, name: 'aaaread', category: "cat aaa", description: 'aaa 读取权限'},
                    {id: 2, name: 'aaawrite', category: "cat aaa", description: 'aaa 写入权限'},
                    {id: 5, name: 'aaawrite', category: "cat aaa", description: 'aaa 写入权限'},
                    {id: 6, name: 'aaawrite', category: "cat aaa", description: 'aaa 写入权限'},
                    {id: 7, name: 'aaawrite', category: "cat aaa", description: 'aaa 写入权限'},
                    {id: 8, name: 'aaawrite', category: "cat aaa", description: 'aaa 写入权限'},
                    {id: 9, name: 'aaawrite', category: "cat aaa", description: 'aaa 写入权限'},
                    {id: 10, name: 'aaawrite', category: "cat aaa", description: 'aaa 写入权限'},
                    {id: 3, name: 'bbboperate', category: "cat bbb", description: 'bbb 操作权限'}
                ],
                perm_editing: {},
                perm_editing_show: false,
                perm_creating: false
            };
        },
        methods: {
            perm_ok() {
                if (this.perm_creating) {
                    this.perm_editing.id = 'created';
                    this.permissions.push(this.perm_editing);
                } else {
                    for (var i in this.permissions) {
                        if (this.permissions[i].id == this.perm_editing.id) {
                            for (var e in this.permissions[i]) {
                                this.permissions[i][e] = this.perm_editing[e];
                            }
                        }
                    }
                }
                this.perm_editing = {};
                this.perm_creating = false;
            },
            perm_cancel() {
                this.perm_editing = {};
                this.perm_creating = false;
            },
            perm_edit(perm) {
                this.perm_editing = {}
                for (var e in perm) {
                    this.perm_editing[e] = perm[e];
                }
                this.perm_editing_show = true;
            },
            perm_delete(perm) {
                for (var i in this.permissions) {
                    if (this.permissions[i].id == perm.id) {
                        this.permissions.splice(i, 1);
                        break;
                    }
                }
            }
        },
        computed: {
            cat_permissions() {
                var r = {};
                for (var i in this.permissions) {
                    var cat = this.permissions[i].category;
                    if (!r[cat]) {
                        r[cat] = [];
                    }
                    r[cat].push(this.permissions[i]);
                }
                if (!r[this.active_name]) {
                    for (var i in r) {
                        this.active_name = i;
                        break;
                    }
                }
                return r;
            }
        }
    }
</script>

<style>

</style>
