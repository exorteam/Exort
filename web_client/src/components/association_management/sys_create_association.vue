<template>
    <div>
        <i-form :model="formItem" :label-width="80">
            <Form-item label="社团名称">
                <i-input :value.sync="formItem.name" placeholder="请输入"></i-input>
            </Form-item>
            <!-- <Form-item label="社团图片">
                <i-input :value.sync="formItem.input" placeholder="请输入"></i-input>
            </Form-item> -->
            <Form-item label="社团描述" :autosize="{minRows: 2,maxRows: 5}">
                <i-input :value.sync="formItem.description" type="textarea" :autosize="{minRows: 2,maxRows: 5}"
                         placeholder="请输入..."></i-input>
            </Form-item>
            <Form-item label="社团标签">
                <Tag v-for="item in formItem.tags" :key="item.key" type="border" closable color="blue">{{ item }}</Tag>
                <i-input v-model="newtag" placeholder="请输入"></i-input>
                <i-button icon="ios-plus-empty" type="primary" size="small" @click="AddNewTag">添加标签</i-button>
            </Form-item>

            <Form-item label="状态管理">
                <i-select :model.sync="formItem.state" style="width:200px">
                    <i-option v-for="item in cityList" :key="item.Id" :value="item.value">{{ item.label }}</i-option>
                </i-select>
            </Form-item>

            <Upload
                multiple
                type="drag"
                action="//jsonplaceholder.typicode.com/posts/" style="text-align: center;">
                <div style="padding: 20px 0">
                    <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
                    <p>点击或拖拽将社团logo放到这里上传</p>
                </div>
            </Upload>
            <Form-item>
                <b-button v-b-modal.modal-1 variant="success">提交</b-button>
                <b-modal id="modal-1" title="提交信息" hide-footer>
                    <p class="my-4">提交成功</p>
                    <b-button class="mt-3" variant="outline-primary" block @click="ReturnList">返回列表</b-button>
                </b-modal>

                <b-button v-b-modal.modal-2 variant="outline-primary" style="margin-left: 800px">取消</b-button>
                <b-modal id="modal-2" title="确认返回" hide-footer>
                    <p class="my-4">您确定返回社团列表界面吗？（创建信息将不保存）</p>
                    <b-button class="mt-3" variant="outline-primary" block @click="ReturnList">确定</b-button>
                    <b-button class="mt-3" variant="outline-primary" block @click="$bvModal.hide('modal-2')">取消
                    </b-button>
                </b-modal>
            </Form-item>
        </i-form>

    </div>
</template>
<script>
    export default {
        data() {
            return {
                modal2: false,
                modal1: false,
                modal_loading: false,
                newtag: '',
                formItem: {
                    name: this.$route.params.name,
                    description: '',
                    state: '',
                    tags: [],

                },
                cityList: [
                    {
                        value: '0',
                        label: '已锁定'
                    },
                    {
                        value: '1',
                        label: '已激活'
                    }
                ],

            }
        },
        methods: {
            ReturnList() {
                this.$router.push({
                    path: '/sys_asso_list',
                    name: 'SysAssoList',
                })
            },
            ReturnThis() {
                this.$router.push({
                    path: '/sys_create_asso',
                    name: 'SysCreateAsso',
                })
            },
            AddNewTag() {
                this.formItem.tags.push(this.newtag);
            },
            del() {
                this.modal_loading = true;
            },
            Confirm() {
                this.modal1 = true;
            },
        },
    }
</script>
