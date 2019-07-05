<template>
    <i-form :model="formItem" :label-width="80">
        <Form-item label="社团名称">
            <i-input :value.sync="formItem.input" placeholder="请输入"></i-input>
        </Form-item>
        <Form-item label="社团图片">
        <Upload
            multiple
            type="drag"
            action="//jsonplaceholder.typicode.com/posts/" style="text-align: center;">
            <div style="padding: 20px 0">
                <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
                <p>点击或将文件拖拽到这里上传</p>
            </div>
        </Upload>
        </Form-item>
        <Form-item label="社团描述">
            <i-input :value.sync="formItem.textarea" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..."></i-input>
        </Form-item>
        <Form-item label="社团标签">
            <i-input :value.sync="formItem.input" placeholder="请输入"></i-input>
        </Form-item>
        <Form-item>
            <b-button v-b-modal.modal-1 variant="success" >提交</b-button>
            <b-modal id="modal-1" title="提交信息" hide-footer>
              <p class="my-4">提交成功</p>
              <b-button class="mt-3" variant="outline-primary" block @click="ReturnIndex">返回列表</b-button>
            </b-modal>
            <i-button type="default" style="margin-left: 8px">取消</i-button>
        </Form-item>
    </i-form>
</template>
<script>
    export default {
        data () {
            return {
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
          ReturnIndex(){
            this.$router.push({
              path: '/',
              name: 'Index',
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
    }
    }
</script>
