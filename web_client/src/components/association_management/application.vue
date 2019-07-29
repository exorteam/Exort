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
                  <i-table :columns="handledAppColumns" :data="pendingAppData"></i-table>
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
import TagChoose from '../activity/tag_choose.vue'
import axios from 'axios'
export default {
    props: {
            pendingAppData: [
            ],
            handledAppData: [
            ],
    },
    data(){
        return {
            pendingAppColumn: [
                {
                    title: '申请人Id',
                    key: 'applicant_Id'
                },
                {
                    title: '申请人',
                    key: 'applicant_name'
                },
                {
                    title: '社团名称',
                    key: 'asso_name'
                },
                {
                    title: '提交时间',
                    key: 'submit_time'
                },
                {
                    title: '申请类型',
                    key: 'apply_type'
                },
                {
                    title: '处理',
                    key: 'operate',
                }
            ],
            handledAppColumns: [
                {
                    title: '申请人Id',
                    key: 'applicant_Id'
                },
                {
                    title: '申请人',
                    key: 'applicant_name'
                },
                {
                    title: '社团名称',
                    key: 'asso_name'
                },
                {
                    title: '提交时间',
                    key: 'submit_time'
                },
                {
                    title: '申请类型',
                    key: 'apply_type'
                },
                {
                    title: '处理时间',
                    key: 'operate_time',
                },
                {
                    title: '处理结果',
                    key: 'operate_result',
                }
            ],
        }
    },
    computed: {

    },
    methods: {

    },
}
</script>
