<template>
    <Tabs active-key="key1">
        <Tab-pane label="社团管理" key="key1">
<div id="AssoList">
    <div id=SearchAsso>
      <Input v-model="default_value" placeholder="请输入社团名称" style="width: 300px" />
      <Button type="info">搜索</Button>
      <Button type="info" @click="StartCreate" style="  position:relative ;right:-400px;">创建社团</Button>
      <!-- <Button type="info" style="  position:relative ;right:-400px;">创建社团</Button> -->
    </div>

    <div>
  <div id="Divide">
       <Divider />
    </div>
      <b-form-checkbox-group
        v-model="selected1"
        :options="options1"
        switches
      ></b-form-checkbox-group>
    </div>
    <div id="Divide">
       <Divider />
    </div>

    <div id="CardList"  style="display: flex;justify-content: space-around;flex-wrap: wrap">
      <Card v-for="item in AssoList" :key="item.id" :row="item" style="width:350px;height:300px" >
        <p slot="title">
            <Icon type="ios-film-outline"></Icon>
            {{item.name}}（{{StateList(item.state)}}）
        </p>

        <a href="#" slot="extra" @click.prevent="EditAsso">
            <Icon type="ios-loop-strong"></Icon>
            Edit
        </a>
        <div style="text-align: center;height:100px">
            <img :src="item.logo" style="width:80px;height:80px;"/>
        </div>
                <p>{{ item.description }}</p>
        <ul v-for="tag in item.tags" :key="tag.id" :row="tag" style="color:#5cadff">
            <li>
                <p>{{ tag }}</p>
            </li>
        </ul>

      </Card>
    </div>
  </div>
    </Tab-pane>
    <Tab-pane label="社团申请管理" key="key1">
        <b-tabs content-class="mt-3">
    <b-tab title="尚未处理" active>
    <div id="AssoAppList">
          <div id=SearchAsso>
            <Input v-model="default_value" placeholder="请输入社团名称" style="width: 300px" />
            <Button type="info">搜索</Button>
          </div>
          <div>
            <div id="Divide">
              <Divider />
            </div>
            <b-form-checkbox-group v-model="selected2" :options="options2" switches>
            </b-form-checkbox-group>
          </div>
          <div id="Divide">
            <Divider />
          </div>
          <div>
            <i-table :columns="columns1" :data="data1"></i-table>
            </div>
        </div>
    </b-tab>
    <b-tab title="已处理">

        <div id="AssoAppList">
          <div id=SearchAsso>
            <Input v-model="default_value" placeholder="请输入社团名称" style="width: 300px" />
            <Button type="info">搜索</Button>
          </div>
          <div>
            <div id="Divide">
              <Divider />
            </div>
            <b-form-checkbox-group v-model="selected3" :options="options3" switches>
            </b-form-checkbox-group>
          </div>
          <div id="Divide">
            <Divider />
          </div>
          <div>
            <i-table :columns="columns2" :data="data2"></i-table>
            </div>
        </div>

    </b-tab>

      </b-tabs>
      </Tab-pane>


    </Tabs>
</template>

<script>

import Liquid from '../../assets/AssociationLogo/solid.jpg'
import Solid from '../../assets/AssociationLogo/solid.jpg'
import Gas from '../../assets/AssociationLogo/solid.jpg'
import Exort from '../../assets/AssociationLogo/solid.jpg'

    export default {
        data () {
            return {
              columns1: [
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

            data1: [
                    {
                        applicant_Id: 'wangxiaoming',
                        aoolicant_name: '王小明',
                        asso_name: '坚强的学习小组',
                        submit_time:'2019/7/5 17:17',
                        apply_type:'请求解除封禁',
                        operate:'管理'
                    },
                    {
                        applicant_Id: 'wangxiaoming',
                        aoolicant_name: '王小明',
                        asso_name: '坚强的学习小组',
                        submit_time:'2019/7/5 17:17',
                        apply_type:'请求解除封禁',
                        operate:'管理'
                    },
                    {
                        applicant_Id: 'wangxiaoming',
                        aoolicant_name: '王小明',
                        asso_name: '坚强的学习小组',
                        submit_time:'2019/7/5 17:17',
                        apply_type:'请求解除封禁',
                        operate:'管理'
                    },
                    {
                        applicant_Id: 'wangxiaoming',
                        aoolicant_name: '王小明',
                        asso_name: '坚强的学习小组',
                        submit_time:'2019/7/5 17:17',
                        apply_type:'请求解除封禁',
                        operate:'管理'
                    },
                    {
                        applicant_Id: 'wangxiaoming',
                        aoolicant_name: '王小明',
                        asso_name: '坚强的学习小组',
                        submit_time:'2019/7/5 17:17',
                        apply_type:'请求解除封禁',
                        operate:'管理'
                    },
                ],



               columns2: [
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

            data2: [
                    {
                        applicant_Id: 'wangxiaoming',
                        aoolicant_name: '王小明',
                        asso_name: '坚强的学习小组',
                        submit_time:'2019/7/5 17:17',
                        apply_type:'请求解除封禁',
                        operate_time:'2019/7/5 17:25',
                        operate_result:'未通过'
                    },
                    {
                        applicant_Id: 'wangxiaoming',
                        aoolicant_name: '王小明',
                        asso_name: '坚强的学习小组',
                        submit_time:'2019/7/5 17:17',
                        apply_type:'请求解除封禁',
                        operate_time:'2019/7/5 17:25',
                        operate_result:'未通过'
                    },
                    {
                        applicant_Id: 'wangxiaoming',
                        aoolicant_name: '王小明',
                        asso_name: '坚强的学习小组',
                        submit_time:'2019/7/5 17:17',
                        apply_type:'请求解除封禁',
                        operate_time:'2019/7/5 17:25',
                        operate_result:'未通过'
                    },
                    {
                        applicant_Id: 'wangxiaoming',
                        aoolicant_name: '王小明',
                        asso_name: '坚强的学习小组',
                        submit_time:'2019/7/5 17:17',
                        apply_type:'请求解除封禁',
                        operate_time:'2019/7/5 17:25',
                        operate_result:'未通过'
                    },
                    {
                        applicant_Id: 'wangxiaoming',
                        aoolicant_name: '王小明',
                        asso_name: '坚强的学习小组',
                        submit_time:'2019/7/5 17:17',
                        apply_type:'请求解除封禁',
                        operate_time:'2019/7/5 17:25',
                        operate_result:'未通过'
                    },
                ],

            selected1: ['active','blocked'], // Must be an array reference!
            options1: [
            { text: '已激活', value: 'active' },
            { text: '已锁定', value: 'blocked' },
            { text: '已封禁', value: 'deleted' },

            ],
            selected2:['applying','applying_unblocked'],
            options2: [
            { text: '申请创建社团', value: 'applying' },
            { text: '申请取消锁定', value: 'applying_unblocked' }
            ],

            selected3:['pass','canceled','refused'],
            options3: [
              { text: '已通过申请', value: 'pass' },
            { text: '已取消申请', value: 'canceled' },
            { text: '已拒绝申请', value: 'refused' },
            ],
            default_value : "",


            AssoList: [
                    {
                        name: 'Team Liquid',
                        description: "欧洲老牌战队，成立于2012年12月。在2016年在2016 Shanghai Major和2016ESL ONE这两项赛事中都取得了亚军。紧接着队伍人员进行调整，引进了Miracle-等数名天梯9000高分选手。在 2017 年成為 TI 冠軍。",
                        logo: Liquid,
                        state:0,
                        tags:[
                          "Dota2",
                          "Ti冠军"
                        ]
                    },
                    {
                      name: "Team Solid",
                      description: "B神、龙神、跳刀、二冰他们和yyf一起组建的Ti9海选战队。",
                      logo: Solid,
                      state:1,
                      tags:[
                        "Dota2",
                        "2届Ti冠军("

                      ]
                    },
                                  {
                      name: "Team Solid",
                      description: "B神、龙神、跳刀、二冰他们和yyf一起组建的Ti9海选战队。",
                      logo: Solid,
                      state:2,
                      tags:[
                        "Dota2",
                        "2届Ti冠军("

                      ]
                    },
                                  {
                      name: "Team Solid",
                      description: "B神、龙神、跳刀、二冰他们和yyf一起组建的Ti9海选战队。",
                      logo: Solid,
                      state:3,
                      tags:[
                        "Dota2",
                        "2届Ti冠军("

                      ]
                    },

                    {
                      name: 'Team Gas',
                      description: "气体战队成员为：一号位：ZSMJ 二号位：水瓶座（之前担任固体战队的工具人中单） 三号位：Axx 四号位：Ch 五号位：狗哥SanSheng",
                      logo: Gas,
                      state:4,
                      tags:[
                        "Dota2"
                      ]
                    },
                    {
                      name: 'Exort',
                      description: "使用微服务架构独立开发的在线社团管理与社交工具",
                      logo: Exort,
                      state:4,
                      tags:[
                        "社团管理",
                        "实用"
                      ]
                    }
                ],
            }
        },
        methods: {
          StartCreate(){
            this.$router.push({
              path: '/create_asso',
              name: 'CreateAsso',
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
          StateList(state){
              switch(state)
              {
                case 0:return "已锁定";
                case 1:return "已激活";
                case 2:return "申请中";
                case 3:return "已取消申请";
                case 4:return "已删除";
                case 5:return "已拒绝申请";
              }
          },
          EditAsso(){
            this.$router.push({
              path: '/edit_asso',
              name: 'EditAsso',
              params: {
                  name: this.name,

              }
              /*query: {
                  key: 'key',
                  msgKey: this.msg
              }*/
            })
          }
        },

        // mounted () {
        //     this.changeLimit();
        // }
    }
</script>
<style>

</style>
