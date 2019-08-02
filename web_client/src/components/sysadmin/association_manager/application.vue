<template>
    <Tab-pane label="社团申请管理" key="tabpane_key2">
            <b-tabs content-class="mt-3">
            <b-tab title="尚未处理" active>
            <div id="AssoAppList">
                <div id=SearchAsso>
                  <Input v-model="inputDefaultValue" placeholder="请输入社团名称" style="width: 300px" />
                  <Button type="info">搜索</Button>
                </div>
                <div>
                  <div id="Divide">
                    <Divider />
                  </div>
                  <b-form-checkbox-group v-model="pendingAppTypeSelected" :options="pendingAppTypeList" switches>
                  </b-form-checkbox-group>
                </div>
                <div id="Divide">
                  <Divider />
                </div>
                <div>
                  <i-table :columns="pendingAppColumn" :data="pendingAppData"></i-table>
                </div>
                <div id="Divide">
                  <Divider />
                </div>
                <div style="margin-top:15px;text-align: center">
                <Page id = "page" show-elevator show-total
                :total="pageProp.totalSize" :page-size.sync="pageProp.pageSize" :page-size-opts="pageProp.pageSizeOpt"
                :current.sync = "pageProp.pageNum" ></Page>
                </div>
            </div>
            </b-tab>
            <b-tab title="已处理">

              <div id="AssoAppList">
                <div id=SearchAsso>
                  <Input v-model="inputDefaultValue" placeholder="请输入社团名称" style="width: 300px" />
                  <Button type="info">搜索</Button>
                </div>
                <div>
                  <div id="Divide">
                    <Divider />
                  </div>
                  <b-form-checkbox-group v-model="handledAppTypeSelected" :options="handledAppTypeList" switches>
                  </b-form-checkbox-group>
                </div>
                <div id="Divide">
                  <Divider />
                </div>
                <div>
                  <i-table :columns="handledAppColumns" :data="handledAppData"></i-table>
                </div>
                <div id="Divide">
                  <Divider />
                </div>
                <div style="margin-top:15px;text-align: center">
                <Page id = "page" show-elevator show-total
                :total="pageProp.totalSize" :page-size.sync="pageProp.pageSize" :page-size-opts="pageProp.pageSizeOpt"
                :current.sync = "pageProp.pageNum" ></Page>
                </div>
              </div>

            </b-tab>
            </b-tabs>
        </Tab-pane>
</template>

<script>
// <p class="mt-2">Selected file: <b>{{ file ? file.name : '' }}</b></p>
import TagChoose from '../../common/tag_choose.vue'
import axios from 'axios'
export default {
    // props: {
    //         pendingAppData: null,
    //         handledAppData: null,
    // },
    data(){
        return {
            pendingAppTypeSelected:['applying','applying_unblocked'],
            pendingAppTypeList: [
                { text: '申请创建社团', value: 'applying' },
                { text: '申请取消锁定', value: 'applying_unblocked' }
            ],
            handledAppTypeSelected:['pass','canceled','refused'],
            handledAppTypeList: [
                { text: '已通过申请', value: 'pass' },
                { text: '已取消申请', value: 'canceled' },
                { text: '已拒绝申请', value: 'refused' },
            ],
            inputDefaultValue : "",
            pendingAppColumn: [
                {
                    title: '申请人Id',
                    key: 'applicant_Id',
                    sortable: true
                },
                {
                    title: '申请人',
                    key: 'applicant_name',
                    sortable: true
                },
                {
                    title: '社团名称',
                    key: 'asso_name',
                    sortable: true
                },
                {
                    title: '提交时间',
                    key: 'submit_time',
                    sortable: true
                },
                {
                    title: '申请类型',
                    key: 'apply_type'
                },
                {
                    title: '处理',
                    key: 'operate',
                    width: 120,
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'success',
                                    size: 'small'
                                }
                            }, '接受'),
                            h('Button', {
                                props: {
                                    type: 'warning',
                                    size: 'small'
                                }
                            }, '拒绝')
                        ]);
                    }
                }
            ],
            handledAppColumns: [
                {
                    title: '申请人Id',
                    key: 'applicant_Id',
                    sortable: true
                },
                {
                    title: '申请人',
                    key: 'applicant_name',
                    sortable: true
                },
                {
                    title: '社团名称',
                    key: 'asso_name',
                    sortable: true
                },
                {
                    title: '提交时间',
                    key: 'submit_time',
                    sortable: true
                },
                {
                    title: '处理时间',
                    key: 'operate_time',
                    sortable: true
                },
                {
                    title: '申请类型',
                    key: 'apply_type'
                },
                {
                    title: '处理结果',
                    key: 'operate_result',
                }
            ],
            pendingAppData: [
                {
                    applicant_Id: 'testid1',
                    applicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate:'管理'
                },
                {
                    applicant_Id: 'testid2',
                    applicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate:'管理'
                },
                {
                    applicant_Id: 'testid3',
                    applicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate:'管理'
                },
                {
                    applicant_Id: 'testid4',
                    applicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate:'管理'
                },
                {
                    applicant_Id: 'testid5',
                    applicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate:'管理'
                },
            ],
            handledAppData: [
                {
                    applicant_Id: 'testid1',
                    applicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate_time:'2019/7/5 17:25',
                    operate_result:'未通过'
                },
                {
                    applicant_Id: 'testid2',
                    applicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate_time:'2019/7/5 17:25',
                    operate_result:'未通过'
                },
                {
                    applicant_Id: 'testid3',
                    applicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate_time:'2019/7/5 17:25',
                    operate_result:'未通过'
                },
                {
                    applicant_Id: 'testid4',
                    applicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate_time:'2019/7/5 17:25',
                    operate_result:'未通过'
                },
                {
                    applicant_Id: 'testid5',
                    applicant_name: '王小明',
                    asso_name: '坚强的学习小组',
                    submit_time:'2019/7/5 17:17',
                    apply_type:'请求解除封禁',
                    operate_time:'2019/7/5 17:25',
                    operate_result:'未通过'
                },
            ],
            pageProp:{
                totalSize : 50,
                pageSize : 6,
                pageNum : 1
            },
            selectData:{
                pageNum:0,
                pageSize:10,
                sortBy:"_createdtime_",

                keyword:"",
                applicantId:1,
                type:"",
                createdAfter:"",
                createdBefore:"",
                state:"",

            }
// - Query Parameters

//    |Parameter|Description|
//    |--|--|
//    |`pageNum`|页码, 默认为0|
//    |`pageSize`|每页数量, 默认为20, 大于100|
//    |`sortBy`|排序方式, 可以为 _createdTime_, _handledTime_, _object.userDefinedField_, 默认为 _createdTime_|

// - Body Parameters

//    |Parameter|Description|
//    |--|--|
//    |`applicantId` _int_|申请者ID, 默认为null|
//    |`type` _int_|申请类型, 默认为null(全部)|
//    |`createdAfter` _string_|申请创建于此时间之后,默认为最早|
//    |`createdBefore` _string_|申请创建于此时间之前,默认为当前时间|
//    |`state` _string_|申请状态|
//    |`object.userDefinedField` _any_|用户自定义字段|
        }
    },
    computed: {

    },
    methods: {

    },
    mounted() {

    },
}
</script>
