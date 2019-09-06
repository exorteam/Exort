<template>
    <div id="finance">
        <Card>
            <p slot="title">社团资产流动清单</p>
            <a slot="extra" @click="modal=true">
                <Icon type="ios-loop-strong"></Icon>
                新建
            </a>
            <div>
                社团余额：<strong style="color: red">{{this.balance}}￥</strong>
            </div>
            <br/>
            <Row>
                <Col span="8">
                    时间区间：
                    <DatePicker v-model="timeRange" type="daterange" split-panels
                                placeholder="Select date"
                                style="width: 180px; margin-right: 45px"></DatePicker>
                </Col>
                <Col span="8">
                    关键字：
                    <Input v-model="select.keyword" style="width: 300px; margin-right: 40px"/>
                </Col>
                <Col style="text-align: center" span="8">
                    <Button type="info" @click="handleSelect">搜索</Button>
                </Col>
            </Row>
            <br/>
            <Table border :columns="columns" :data="this.financeList">
                <template slot-scope="{row}" slot="time">
                    <Time type="datetime" :time="row.time"/>
                </template>
                <template slot-scope="{row,index}" slot="action">
                    <Button type="info" size="small" @click="view(index)">使用详情</Button>
                    <!--<Button type="info" size="small" @click="remove(index)">删除</Button>-->
                </template>
            </Table>
            <div>
                <Page show-elevator show-total :total="page.totalSize" :current.sync="page.pageNum+1"
                      :page-size.sync="page.pageSize" simple @on-change="changePage"
                      style="text-align: center;"></Page>
            </div>
        </Card>
        <finance-create v-on:close="modal=false" :modal="modal"></finance-create>
        <Card>

        </Card>
    </div>
</template>

<script>
    import {mapActions, mapState} from 'vuex';
    import FinanceCreate from './FinanceCreate'

    export default {
        components: {FinanceCreate},
        data() {
            return {
                balance: 0,
                modal: false,
                page: {
                    totalSize: 0,
                    pageNum: 0,
                    pageSize: 6
                },
                timeRange:null,
                columns: [
                    {
                        title: "项目名称",
                        key: "projectName"
                    },
                    {
                        title: "社团名称",
                        key: "associationName"
                    },
                    {
                        title: "负责人",
                        key: "supervisor"
                    },
                    {
                        title: "录入时间",
                        slot: "time"
                    },
                    {
                        title: "金额",
                        key: "transactionAmount"
                    },
                    {
                        title: "往来方向",
                        key: "direction"
                    },
                    {
                        title: "操作者ID",
                        key: "operatorId"
                    },
                    {
                        title: "申请时余额",
                        key: "balance"
                    },
                    {
                        title: "动作",
                        slot: "action",
                        align: 'center',
                        width: 150
                    },

                ],
                select: {
                    associationId: null,
                    keyword: null,
                    timeRange: {
                        start:"",
                        end:""
                    },
                }
            }
        },
        computed: {
            ...mapState("associationAdmin/currentAssociation", {
                assoId: state => state.id
            }),
            ...mapState('associationAdmin/finance', [
                'financeList'
            ]),
        },
        methods: {
            ...mapActions('associationAdmin/finance', [
                'deleteFinance',
                'getAssociationFiances',
                'getAssociationBalance'
            ]),
            view(index) {
                this.$Modal.info({
                    title: `${this.financeList[index].projectName}`+"的金额使用情况说明",
                    content: `${this.financeList[index].content}`
                })
            },
            remove(index) {
                this.deleteFinance(this.financeList[index].id).then(() => {
                    this.init();
                }).catch(err => {
                    console.log(err);
                });
            },
            changePage(e) {
                this.page.pageNum = e - 1;
                this.init();
            },
            handleSelect(){
                if (this.timeRange != null && this.timeRange.length == 2) {
                    if (this.timeRange[0] === "") {
                        this.select.timeRange = null
                    } else {
                        let create = {
                            start: this.timeRange[0],
                            end: this.timeRange[1]
                        }
                        this.select.timeRange = create
                    }
                }

                if(this.timeRange===null||this.timeRange[0]===""||this.timeRange[1]===""){
                    this.select.timeRange=null;
                }else{
                    this.select.timeRange.start=this.timeRange[0];
                    this.select.timeRange.end=this.timeRange[1];
                }

                this.getAssociationFiances({
                    select: this.select,
                    page: this.page
                }).then(res => {
                    this.page.pageNum = res.data.pageNum;
                    this.page.pageSize = res.data.pageSize;
                    this.page.totalSize = res.data.totalSize;
                    // console.log(res);
                });
            },
            init() {
                this.handleSelect();
                this.getAssociationBalance("assoId").then((res) => {
                    this.balance = res;
                });
            }
        },
        mounted() {
            this.init();
        },
    }
</script>

