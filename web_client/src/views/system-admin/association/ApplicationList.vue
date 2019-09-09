<template>
<Card>
    <Form inline :label-width="40">
        <FormItem label="状态">
            <Select v-model="filter.state" @on-change="changeState">
                <Option v-for="(label, value) in states" :key="value" :value="value">{{label}}</Option>
            </Select>
        </FormItem>
    </Form>
    <Card v-for="application in applications" :key="application.id"
          style="margin-top:10px">
        <div slot="title">
            <span style="font-size:smaller;color:gray;">{{ application.id }}</span>
            <Divider type="vertical" />
            <span style="font-size:smaller;color:gray">
                提交于 {{ new Date(application.createdTime).toLocaleString() }}
            </span>
        </div>
        <div slot="extra">
            <span>{{states[application.state]}}</span>
            <span v-if="application.state!='pending'">
                <Divider type="vertical" />
                <span style="font-size:smaller;color:gray">
                    {{ new Date(application.handledTime).toLocaleString()}}
                </span>
            </span>
        </div>
        <div @click="application.state=='pending' && beginReview(application.id)"
             :style="application.state=='pending' ? {cursor:'pointer'} : {}">
            <div style="margin:0 8px">
                <Avatar :src="associationIcon(application.details.logo)" size="large"/>
                <span style="font-size:larger;margin-left:12px">{{ application.details.name }}</span>
            </div>
            <div style="margin:8px">
                {{ application.details.description}}
            </div>
            <div v-if="application.receipt">
                <Divider />
                <span style="font-size:smaller;color:gray">
                    结果: {{ application.receipt.message }}
                </span>
            </div>
        </div>
    </Card>
    <Page v-if="totalSize > filter.pageSize" show-total :current="filter.pageNum+1"
          :total="totalSize" :page-size="filter.pageSize"
          @on-change="turnPage" @on-page-size-change="filter.pageSize=$event"/>
    <Modal :title="'审核: '+applicationId" v-model="reviewing"
           @on-ok="review()">
        <Form :label-width="40">
            <FormItem label="结果">
                <i-switch v-model="result" size="large">
                    <span slot="open">通过</span>
                    <span slot="close">拒绝</span>
                </i-switch>
            </FormItem>
            <FormItem label="理由">
                <Input type="textarea" v-model="message" />
            </FormItem>
        </Form>
    </Modal>
</Card>
</template>

<script>
import {api} from '@/http'
import {associationIcon} from '@/const'

export default {
    data() {return {
        filter: {
            state: "all",
            pageNum: 0,
            pageSize: 5
        },
        applications: [],
        totalSize: 0,
        states: {
            all: '全部',
            pending: '等待审核',
            accepted: '已通过',
            refused: '未通过',
            canceled: '已取消'
        },
        applicationId: null,
        reviewing: false,
        result: false,
        message: ''
    };},
    methods: {
        associationIcon,
        refreshList() {
            api({
                method: 'get',
                url: '/applications/list/association_creation',
                params: this.filter
            }).then(res => {
                this.applications = res.data.data.content;
                this.filter.pageNum = res.data.data.pageNum;
                this.filter.pageSize = res.data.data.pageSize;
                this.totalSize = res.data.data.totalSize;
            }).catch(err => {
                this.$Notice.error({
                    title: '获取申请列表失败',
                    desc: err.message || err
                });
            })
        },
        turnPage(page) {
            this.filter.pageNum = page - 1;
            this.refreshList();
        },
        changeState(state) {
            this.filter.state = state;
            this.refreshList();
        },
        changeType(type) {
            this.filter.type = type;
            this.refreshList();
        },
        beginReview(id) {
            this.reviewing = true;
            this.result = false;
            this.message = '';
            this.applicationId = id;
        },
        review() {
            api({
                method: 'post',
                url: '/applications/'+ (this.result ? 'accept': 'refuse') +'/' + this.applicationId,
                data: {
                    message: this.message
                }
            }).then(res => {
                this.$Notice.success({
                    title: this.result ? '通过申请成功' : '拒绝申请成功'
                });
                this.refreshList();
            }).catch(err => {
                console.log(err)
                this.$Notice.error({
                    title: '审核失败',
                    desc: err.message || err
                });
            })
        }
    },
    mounted() {
        this.refreshList();
    }
}
</script>
