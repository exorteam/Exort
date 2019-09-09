<template>
<ContentPage no-back>
    <template #header>
        <h2>我的申请</h2>
    </template>
    <Form inline :label-width="40">
        <FormItem label="状态">
            <Select v-model="filter.state" @on-change="changeState">
                <Option v-for="(label, value) in states" :key="value" :value="value">{{label}}</Option>
            </Select>
        </FormItem>
        <FormItem label="类型">
            <Select v-model="filter.type" @on-change="changeType">
                <Option v-for="(label, value) in types" :key="value" :value="value">{{label}}</Option>
            </Select>
        </FormItem>
    </Form>
    <Card v-for="application in applications" :key="application.id"
          style="margin-top:10px">
        <div slot="title">
            <span style="font-size:smaller;color:gray;">{{ application.id }}</span>
            <Divider type="vertical" />
            <span style="font-size:larger;margin-right:10px">
                {{ types[application.type] }}
            </span>
            <span style="font-size:smaller;color:gray">
                {{ new Date(application.createdTime).toLocaleString() }}
            </span>
        </div>
        <div slot="extra">
            <span>{{states[application.state]}}</span>
            <Divider type="vertical" />
            <Tooltip v-if="application.state=='pending'" content="取消申请" placement="top">
                <Button ghost size="small" type="warning" icon="md-undo"
                        @click="cancel(application.id)"/>
            </Tooltip>
            <span v-else style="font-size:smaller;color:gray">
                {{ new Date(application.handledTime).toLocaleString()}}
            </span>
        </div>
        <div v-if="application.type=='ActivitySignUp'">
        </div>
        <div v-else-if="application.type=='AssociationMemberSignUp'">
        </div>
        <div v-else-if="application.type=='AssociationInfo'">
            <div style="margin:0 8px">
                <Avatar :src="associationIcon(application.details.logo)" size="large"/>
                <span style="font-size:larger;margin-left:12px">{{ application.details.name }}</span>
            </div>
            <div style="margin:8px">
                {{ application.details.description}}
            </div>
        </div>
        <div v-if="application.receipt">
            <Divider />
            <span style="font-size:smaller;color:gray">
                结果: {{ application.receipt.message }}
            </span>
            <br>
            <span style="font-size:smaller;color:gray" v-if="application.receipt.departmentId">
                加入部门: {{ application.receipt.departmentId }}
            </span>
        </div>
    </Card>
    <Page v-if="totalSize > filter.pageSize" show-total :current="filter.pageNum+1"
          :total="totalSize" :page-size="filter.pageSize"
          @on-change="turnPage" @on-page-size-change="filter.pageSize=$event"/>
</ContentPage>
</template>

<script>
import {api} from '@/http'
import {associationIcon} from '@/const'
import ContentPage from '@/components/ContentPage'

export default {
    components: {
        ContentPage
    },
    data() {return {
        filter: {
            state: "all",
            type: "all",
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
        types: {
            all: '全部',
            ActivitySignUp: '加入活动',
            AssociationMemberSignUp: '加入社团',
            AssociationInfo: '创建社团'
        }
    };},
    methods: {
        associationIcon,
        refreshList() {
            api({
                method: 'get',
                url: '/applications/list/user',
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
        cancel(id) {
            api({
                method: 'post',
                url: '/applications/cancel/' + id,
                data: { message: '用户取消' }
            }).then(res => {
                this.refreshList();
            }).catch(err => {
                this.$Notice.error({
                    title: '取消失败',
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

<style>

</style>
