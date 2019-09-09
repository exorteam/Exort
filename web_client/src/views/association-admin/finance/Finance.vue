<template>
    <div id="finance">
        <Card>
            <p slot="title">社团资产流动清单</p>
            <a slot="extra" @click="modal=true">
                <Icon type="ios-loop-strong"></Icon>
                新建
            </a>
            <div>
                社团经费余额：<strong style="color: red">{{this.balance}}￥</strong>
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
            <Table v-if="this.assoId" border :columns="columns" :data="this.financeList">
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
        <br/>
        <Card v-if="this.assoId">
            <div v-if="this.financeList.length!==0">
                <GChart
                        type="ComboChart"
                        :data="chartData"
                        :options="chartOption"
                />
            </div>
            <div>
                区间收支总额：{{this.totalCost}}元
            </div>
        </Card>
    </div>
</template>

<script>
    import {mapActions, mapState} from 'vuex';
    import FinanceCreate from './FinanceCreate'
    import {GChart} from 'vue-google-charts'
    import {api} from '@/http'

    export default {
        components: {FinanceCreate, GChart},
        data() {
            return {
                balance: 0,
                modal: false,
                page: {
                    totalSize: 0,
                    pageNum: 0,
                    pageSize: 6
                },
                totalCost: 0,
                timeRange: null,
                assoId:this.$route.params.assoId,
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
                    associationId: this.assoId,
                    keyword: null,
                    timeRange: {
                        start: "",
                        end: ""
                    },
                },

                chartData: [],
                chartOption: {
                    title: '社团经费变化情况',
                    vAxis: {title: '金额（￥)'},
                    hAxis: {title: '日期'},
                    seriesType: 'bars',
                    series: {1: {type: 'line'}}
                }
            }
        },
        computed: {
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
                    title: `${this.financeList[index].projectName}` + "的金额使用情况说明",
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
            getTotalCost() {
                let item;
                let total = 0;
                for (item in this.financeList) {
                    if (this.financeList[item].direction === "汇入") {
                        total += this.financeList[item].transactionAmount;
                    } else {
                        total -= this.financeList[item].transactionAmount;
                    }
                }
                this.totalCost = total;
            },
            createChart() {
                let item;
                let input;
                let head = ['日期', '余额', '使用情况'];

                let m = [];


                for (item in this.financeList) {

                    let t = Date.parse(this.financeList[item].time);

                    if (this.financeList[item].direction === "汇入") {
                        input = [this.formatDate(new Date(t)), this.financeList[item].balance + this.financeList[item].transactionAmount, this.financeList[item].transactionAmount]
                    } else {
                        input = [this.formatDate(new Date(t)), this.financeList[item].balance - this.financeList[item].transactionAmount, -this.financeList[item].transactionAmount]
                    }

                    m.unshift(input);
                }
                m.unshift(head);
                this.chartData = m;
            },
            formatDate(now) {
                var year = now.getFullYear();
                var month = now.getMonth() + 1;
                var date = now.getDate();
                var hour = now.getHours();
                var minute = now.getMinutes();
                return year + "-" + month + "-" + date + " " + hour + ":" + minute;
            },
            handleSelect() {
                this.select.associationId=this.assoId;

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

                if (this.timeRange === null || this.timeRange[0] === "" || this.timeRange[1] === "") {
                    this.select.timeRange = null;
                } else {
                    this.select.timeRange.start = this.timeRange[0];
                    this.select.timeRange.end = this.timeRange[1];
                }

                this.getAssociationFiances({
                    select: this.select,
                    page: this.page
                }).then(res => {
                    this.page.pageNum = res.data.pageNum;
                    this.page.pageSize = res.data.pageSize;
                    this.page.totalSize = res.data.totalSize;

                    if (this.financeList.length !== 0) {
                        this.createChart();
                        this.getTotalCost();
                    } else {
                        this.totalCost = 0;
                    }
                    // console.log(res);
                });
            },
            init() {
                this.handleSelect();
                // this.getAssociationBalance(this.assoId).then((res) => {
                //     console.log(res);
                //     this.balance = res;
                // });
                api({
                    method: "get",
                    url: "/associations/"+this.assoId+"/balance"
                }).then((res) => {
                    this.balance = res.data.data;
                }).catch(err => {
                    console.log(err)
                })
            }
        },
        mounted() {
            this.init();
        }
        ,
    }
</script>

