<template>
  <div id="AssoList">
    <div id=SearchAsso>
      <Input v-model="value" placeholder="请输入社团名称" style="width: 300px" />
      <Button type="info">搜索</Button>

        <Radio-group :model.sync="type">
            <Radio value="apple" style="position:relative ;right:-40px;">
                <span>全部</span>
            </Radio>
            <Radio value="android" style="position:relative ;right:-40px;">
                <span>已激活</span>
            </Radio>
            <Radio value="windows" style="position:relative ;right:-40px;">
                <span>未激活</span>
            </Radio>
        </Radio-group>

      <Button type="info" style="  position:relative ;right:-400px;">创建社团</Button>
    </div>
    <div id="Divide">
       <Divider />
    </div>


    <div id="CardList"  style="display: flex;justify-content: space-around;flex-wrap: wrap">
      <Card v-for="item in AssoList" :key="item.id" :row="item" style="width:350px;height:300px" >
        <p slot="title">
            <Icon type="ios-film-outline"></Icon>
            {{item.name}}
        </p>
        <a href="#" slot="extra" @click.prevent="changeLimit">
            <Icon type="ios-loop-strong"></Icon>
            Edit
        </a>
        <div style="text-align: center;">
            <img :src="item.logo" style="width:80px;height:80px;"/>
        </div>
        
        <ul>
       
            <li>
                <p>{{ item.description }}</p>
            </li>
        </ul>
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
import Liquid from '../../assets/Liquid.png'
import Solid from '../../assets/solid.jpg'
import Gas from '../../assets/Gas.jpg'
import Exort from '../../assets/1.png'
    export default {
        data () {
            return {
                type: 'apple',
                
                AssoList: [
                    {
                        name: 'Team Liquid',
                        description: "欧洲老牌战队，成立于2012年12月。在2016年在2016 Shanghai Major和2016ESL ONE这两项赛事中都取得了亚军。紧接着队伍人员进行调整，引进了Miracle-等数名天梯9000高分选手。在 2017 年成為 TI 冠軍。",
                        logo: Liquid,
                        tags:[
                          "Dota2",
                          "Ti冠军"
                        ]
                    },
                    {
                      name: "Team Solid",
                      description: "B神、龙神、跳刀、二冰他们和yyf一起组建的Ti9海选战队。",
                      logo: Solid,
                      tags:[
                        "Dota2",
                        "2届Ti冠军("

                      ]
                    },

                    {
                      name: 'Team Gas',
                      description: "气体战队成员为：一号位：ZSMJ 二号位：水瓶座（之前担任固体战队的工具人中单） 三号位：Axx 四号位：Ch 五号位：狗哥SanSheng",
                      logo: Gas,
                      tags:[
                        "Dota2"
                      ]
                    },
                    {
                      name: 'Exort',
                      description: "使用微服务架构独立开发的在线社团管理与社交工具",
                      logo: Exort,
                      tags:[
                        "社团管理",
                        "实用"
                      ]
                    }
                ],
            }
        },

        // mounted () {
        //     this.changeLimit();
        // }
    }
</script>
<style>

</style>
