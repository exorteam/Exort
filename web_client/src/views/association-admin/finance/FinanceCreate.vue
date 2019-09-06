<template>
    <div>
        <Drawer
                title="新建资产流动"
                v-model="this.modal"
                width="720"
                :closable="false"
                placement="left"
                :styles="styles"
        >
            <Form :model="formData">
                <Row :gutter="32">
                    <Col span="12">
                        <FormItem label="项目名称" label-position="top">
                            <Input v-model="formData.projectName" placeholder=""/>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem label="负责人" label-position="top">
                            <Input v-model="formData.supervisor" placeholder=""/>
                        </FormItem>
                    </Col>
                </Row>
                <Row :gutter="32">
                    <Col span="12">
                        <FormItem label="资金流动方向" label-position="top">
                            <Select v-model="formData.direction" placeholder="">
                                <Option value="汇入">汇入</Option>
                                <Option value="汇出">汇出</Option>
                            </Select>
                        </FormItem>
                    </Col>
                    <Col span="12">
                        <FormItem>
                            <div>
                                金额（￥）
                            </div>
                            <div>
                                <InputNumber style="width: 100%" v-model="formData.transactionAmount" :min="0"/>
                            </div>
                        </FormItem>
                    </Col>
                </Row>
                <FormItem label="金额使用情况说明" label-position="top">
                    <Input type="textarea" v-model="formData.content" :autosize="{minRows: 4,maxRows: 4}"
                           placeholder=""/>
                </FormItem>
            </Form>
            <div class="demo-drawer-footer">
                <Button style="margin-right: 8px" @click="modal = false">取消</Button>
                <Button type="primary" @click="submit">创建</Button>
            </div>
        </Drawer>
    </div>
</template>
<script>
    import {mapActions, mapState} from 'vuex';

    export default {
        props: ['modal'],
        data() {
            return {
                styles: {
                    height: 'calc(100% - 55px)',
                    overflow: 'auto',
                    paddingBottom: '53px',
                    position: 'static'
                },
                formData: {
                    projectName: null,
                    associationId: null,
                    associationName: null,
                    content: null,
                    supervisor: null,
                    transactionAmount: null,
                    direction: null,
                    operatorId: null
                },
            }
        },
        computed: {
            ...mapState("associationAdmin/currentAssociation", {
                assoId: state => state.id,
                assoName: state => state.name
            }),
            ...mapState("common/currentUser", [
                'uid'
            ]),
        },
        methods: {
            submit() {
                // this.formData.associationId = this.assoId;
                // this.formData.associationName = this.assoName;
                this.formData.associationId = "assoId";
                this.formData.associationName = "aaa";
                this.formData.operatorId = this.uid;

                this.createFinance(this.formData).then(() => {
                    this.$emit('close');
                    this.$router.go(0);
                });
            },
            ...mapActions('associationAdmin/finance', [
                'createFinance',
                'getAssociationFiances'
            ]),
        }
    }
</script>
<style>
    .demo-drawer-footer {
        width: 100%;
        position: absolute;
        bottom: 0;
        left: 0;
        border-top: 1px solid #e8e8e8;
        padding: 10px 16px;
        text-align: right;
        background: #fff;
    }
</style>
