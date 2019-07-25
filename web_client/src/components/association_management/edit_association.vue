<template>
    <i-form :model="formItem" :label-width="80" >
        <Form-item label="社团名称">
            <i-input :value.sync="formItem.name" placeholder="请输入"></i-input>
        </Form-item>
        <!-- <Form-item label="社团图片">
            <i-input :value.sync="formItem.input" placeholder="请输入"></i-input>
        </Form-item> -->
        <Form-item label="社团描述" :autosize="{minRows: 2,maxRows: 5}">
            <i-input :value.sync="formItem.description" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..."></i-input>
        </Form-item>
        <Form-item label="社团标签">
            <Tag v-for="item in formItem.tags" :key="item.key" type="border" closable color="blue">{{ item  }}</Tag>
            <i-input v-model="newtag" placeholder="请输入"></i-input>
            <i-button icon="ios-plus-empty" type="primary" size="small" @click="AddNewTag">添加标签</i-button>
        </Form-item>

        <Form-item label="状态">
            <p>{{formItem.state}}</p>
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
            <b-button v-b-modal.modal-1 variant="success"  >提交</b-button>
            <b-modal id="modal-1" title="提交信息" hide-footer>
              <p class="my-4">提交成功</p>
              <b-button class="mt-3" variant="outline-primary" block @click="ReturnList">返回社团列表</b-button>
            </b-modal>


            <b-button variant="outline-primary" @click="ReturnList" style="margin-left: 20px">取消</b-button>


        </Form-item>
    </i-form>
</template>
<script>
    export default {
        data () {
            return {
              newtag:'',
              formItem: {
                  name:this.$route.params.name,
                  description:'',
                  state:'已被锁定',
                  tags:['T1','T2'],

              },

            cityList: [
                    {
                        value: '0',
                        label: '已锁定'
                    },
                    {
                        value: '1',
                        label: '已激活'
                    },
                    {
                        value: '2',
                        label: '申请中'
                    },
                    {
                        value: '3',
                        label: '已取消'
                    },
                    {
                        value: '4',
                        label: '已删除'
                    },
                    {
                        value: '5',
                        label: '已拒绝'
                    }
                ],

            }
        },

        methods: {
          ReturnList(){
            this.$router.push({
              path: '/asso_list',
              name: 'AssoList',
              // params: {
              //     key: 'key',
              //     msgKey: this.msg
              // }
              /*query: {
                  key: 'key',
                  msgKey: this.msg
              }*/
            })
          },
          AddNewTag(){
            this.formItem.tags.push(this.newtag);
          }
        }
    }
</script>
