<template>
<div>
    <i-form :model="formItem" :label-width="80">
        <Form-item label="社团名称">
            <i-input :value.sync="formItem.input" placeholder="请输入"></i-input>
        </Form-item>
        <Form-item label="社团图片">
            <i-input :value.sync="formItem.input" placeholder="请输入"></i-input>
        </Form-item>
        <Form-item label="社团描述">
            <i-input :value.sync="formItem.textarea" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..."></i-input>
        </Form-item>
        <Form-item label="社团标签">
            <i-input :value.sync="formItem.input" placeholder="请输入"></i-input>
        </Form-item>
        <Upload
            multiple
            type="drag"
            action="//jsonplaceholder.typicode.com/posts/" style="text-align: center;">
            <div style="padding: 20px 0">
                <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
                <p>点击或将文件拖拽到这里上传</p>
            </div>
        </Upload>
        <Form-item>
            <b-button v-b-modal.modal-1 variant="success" >提交</b-button>
            <b-modal id="modal-1" title="提交信息" hide-footer>
              <p class="my-4">提交成功</p>
              <b-button class="mt-3" variant="outline-primary" block @click="ReturnList">返回列表</b-button>
            </b-modal>

            <b-button v-b-modal.modal-2 variant="outline-primary"  style="  position:relative ;right:-400px;">取消</b-button>
            <b-modal id="modal-2"  title="确认返回" hide-footer>
              <p class="my-4">您确定返回社团列表界面吗？（创建信息将不保存）</p>
              <b-button class="mt-3" variant="outline-primary" block @click="ReturnList">确定</b-button>
              <b-button class="mt-3" variant="outline-primary" block @click="$bvModal.hide('modal-2')">取消</b-button>
            </b-modal>
        </Form-item>
    </i-form>

</div>
</template>
<script>
    export default {
        data () {
            return {
                modal2: false,
                modal1: false,
                modal_loading: false,
                formItem: {
                    input: '',
                    select: '',
                    radio: 'male',
                    checkbox: [],
                    switch: true,
                    date: '',
                    time: '',
                    slider: [20, 50],
                    textarea: ''
                }
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
          ReturnThis(){
            this.$router.push({
              path: '/create_asso',
              name: 'CreateAsso',
            })
          },
          del(){
            this.modal_loading = true;

          },
          Confirm(){
            this.modal1=true;
          },
          ok () {
                this.$Message.info('点击了确定');
            },
          cancel () {
                this.$Message.info('点击了取消');
            }
        },
    }
</script>
