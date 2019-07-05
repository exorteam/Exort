<template>
  <div id="AssoList">
    <div id=SearchAsso>
      <Input v-model="default_value" placeholder="请输入社团名称" style="width: 300px" />
      <Button type="info">搜索</Button>
      <Button type="info" @click="StartCreate" style="  position:relative ;right:-400px;">创建社团</Button>


    </div>
    <div>
  <div id="Divide">
       <Divider />
    </div>
      <b-form-checkbox-group
        v-model="selected"
        :options="options"
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
</template>
<script>
import Liquid from '../../assets/AssociationLogo/exort.png'
import Solid from '../../assets/AssociationLogo/exort.png'
import Gas from '../../assets/AssociationLogo/exort.png'
import Exort from '../../assets/AssociationLogo/exort.png'

    export default {
        data () {
            return {
                selected: ['active','blocked','applying'], // Must be an array reference!
            options: [
            { text: '已激活', value: 'active' },
            { text: '申请中', value: 'applying' },
            { text: '已锁定', value: 'blocked' },
            { text: '已取消', value: 'canceled' },
            { text: '已删除', value: 'deleted' },
            { text: '已拒绝申请', value: 'refused' },
            ],// 已激活 1
              // 已锁定 0
              // 申请中 2
              // 已取消 3
              // 已删除 4
              //
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
                      state:5,
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
              path: '/sys_create_asso',
              name: 'SysCreateAsso',
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
                case 3:return "已取消";
                case 4:return "已删除";
                case 5:return "已拒绝";

              }
          },
          EditAsso(){
            this.$router.push({
              path: '/sys_mana_asso',
              name: 'SysManaAsso',
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
