<template>
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

        <Form-item label="禁用">
              <b-form-checkbox v-model="checked" name="check-button" switch>
               <!-- <b>( {{ checked }})</b> -->
            </b-form-checkbox>
        </Form-item>
        <Form-item>
            <b-button v-b-modal.modal-1 variant="success" >提交</b-button>
            <b-modal id="modal-1" title="提交信息" hide-footer>
              <p class="my-4">提交成功</p>
              <b-button class="mt-3" variant="outline-primary" block @click="ReturnList">返回社团列表</b-button>
            </b-modal>


            <b-button variant="outline-primary" @click="ReturnList">取消</b-button>

            <b-button variant="danger" @click="$bvModal.show('bv-modal1')">删除</b-button>

              <b-modal id="bv-modal1" hide-footer>
            <template slot="modal-title">
              确认删除
            </template>
            <div class="d-block text-center">
              <h3>你确定删除该社团吗？</h3>
            </div>
            <b-button class="mt-3" variant="danger" block @click="ReturnList">确定</b-button>
            <b-button class="mt-3" block @click="$bvModal.hide('bv-modal1')">取消</b-button>
            </b-modal>
        </Form-item>
    </i-form>
</template>
<script>
    export default {
        data () {
            return {
              checked: false,
              ok:"已激活",
              no:"未激活",
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
        }
    }
</script>
