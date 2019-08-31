<template>
	<Card>
		<Tabs content-class="mt-3">
			<TabPane label="尚未处理" name="unsolved" active>
				<div id="AssoAppList">
					<div id=SearchAsso>
						<Input v-model="inputDefaultValue" placeholder="请输入社团名称" style="width: 300px" />
						<Button>搜索</Button>
					</div>
					<br>
					<!--<b-form-checkbox-group v-model="pendingAppTypeSelected" :options="pendingAppTypeList" switches>-->
					<!--</b-form-checkbox-group>                                                                       -->
					<CheckboxGroup v-model="pendingAppTypeSelected">
						<Checkbox label="applying">
							<span>申请创建社团</span>
						</Checkbox>
						<Checkbox label="applying_unblocked">
							<span>申请解除锁定</span>
						</Checkbox>
					</CheckboxGroup>
					<br>
					<div>
						<Table :columns="pendingAppColumn" :data="pendingAppData"></Table>
					</div>
					<div style="margin-top:15px;text-align: center">
						<Page id = "page" show-elevator show-total
							  :total="pageProp.totalSize" :page-size.sync="pageProp.pageSize" :page-size-opts="pageProp.pageSizeOpt"
							  :current.sync = "pageProp.pageNum" ></Page>
					</div>
				</div>
			</TabPane>
			<TabPane label="已处理" name="solved">
				<div id="AssoAppList">
					<div id=SearchAsso>
						<Input v-model="inputDefaultValue" placeholder="请输入社团名称" style="width: 300px" />
						<Button>搜索</Button>
					</div>
					<br>
					<CheckboxGroup v-model="handledAppTypeSelected">
						<Checkbox label="pass">
							<span>已通过申请</span>
						</Checkbox>
						<Checkbox label="canceled">
							<span>已取消申请</span>
						</Checkbox>
						<Checkbox label="refused">
							<span>已拒绝申请</span>
						</Checkbox>
					</CheckboxGroup>
						<!--<b-form-checkbox-group v-model="handledAppTypeSelected" :options="handledAppTypeList" switches>-->
						<!--</b-form-checkbox-group>                                                                       -->
					<br>
					<div>
						<Table :columns="handledAppColumns" :data="handledAppData"></Table>
					</div>
					<div style="margin-top:15px;text-align: center">
						<Page id = "page" show-elevator show-total
							  :total="pageProp.totalSize" :page-size.sync="pageProp.pageSize" :page-size-opts="pageProp.pageSizeOpt"
							  :current.sync = "pageProp.pageNum" ></Page>
					</div>
				</div>
			</TabPane>
		</Tabs>
	</Card>
</template>

<script>
import TagChoose from '@/components/TagChoose'

export default {
	name: 'ApplicationList',
    props: {
            pendingAppData: null,
            handledAppData: null,
    },
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
                // {
                //     title: '处理',
                //     key: 'operate',
                // },
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
            pageProp:{
                totalSize : 0,
                pageSize : 6,
                pageNum : 1
            },
        }
    },
    computed: {

    },
    methods: {

    },
}
</script>
