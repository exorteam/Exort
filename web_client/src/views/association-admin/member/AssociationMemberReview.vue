<template>
<Card>
    <Form inline :label-width="40">
        <FormItem label="状态">
            <Select v-model="filter.state" @on-change="changeState">
                <Option v-for="(label, value) in states" :key="value" :value="value">{{label}}</Option>
            </Select>
        </FormItem>
        <FormItem label="部门">
            <Select v-model="filter.departmentId" @on-change="changeDepartment" clearable>
                <Option v-for="dept in departments" :key="dept.departmentId"
                        :value="dept.departmentId" :label="dept.name" />
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
            <FormItem label="部门" v-if="result">
                <Select v-model="departmentId" clearable>
                    <Option v-for="dept in departments" :key="dept.departmentId"
                            :value="dept.departmentId" :label="dept.name" />
                </Select>
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
import { mapState, mapMutations, mapActions } from 'vuex'

export default {
    data() {return {
        filter: {
            departmentId: null,
            state: "all",
            pageNum: 0,
            pageSize: 5
        },
        departments: [],
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
        departmentId: null,
        message: ''
    };},
    computed: {
        ...mapState('associationAdmin/currentAssociation', ['id', 'name']),
    },
    methods: {
        associationIcon,
        refreshList() {
            if (!this.id) {
                return;
            }
            api({
                method: 'get',
                url: '/applications/list/association_member_signup',
                params: {
                    associationId: this.id,
                    ...this.filter
                }
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
        changeDepartment(departmentId) {
            this.filter.departmentId = departmentId;
            this.refreshList();
        },
        refreshDepartments() {
            if (!this.id) {
                return;
            }
			api({
				method: 'get',
				url: '/associations/' + this.id + '/departments'
			}).then(res => {
				this.departments = res.data.data;
			}).catch(err => {
				this.$Notice.error({
					title: '获取部门失败',
					desc: err.message || err
				});
			}).finally(() => {
				this.loading = false;
			});
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
                    message: this.message,
                    departmentId: this.departmentId
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
        this.refreshDepartments();
        this.refreshList();
    }
}
</script>
