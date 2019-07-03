<template>
  <div id="AssoList">
    <div id=SearchAsso>
      <Input v-model="value" placeholder="请输入社团名称" style="width: 300px" />
      <Button type="info">搜索</Button>
      <Button type="info" style="  position:relative ;right:-600px;">创建社团</Button>
    </div>
    <div id="Divide">
       <Divider />
    </div>
    <div id="CardList" v-for="item in AssoList">
      <Card style="width:350px;">
        <p slot="title">
            <Icon type="ios-film-outline"></Icon>
            {{item.name}}
        </p>
        <a href="#" slot="extra" @click.prevent="changeLimit">
            <Icon type="ios-loop-strong"></Icon>
            Edit
        </a>
        <ul>
            <li>
              <img src="item.logo">
            </li>
            <li>
                <p>{{ item.description }}</p>
            </li>
        </ul>
        <ul v-for="tag in item.tags" style="color:#5cadff">

            <li>
                <p>{{ tag }}</p>
            </li>
        </ul>
      </Card>
    </div>

  </div>
</template>
<script>
    export default {
        data () {
            return {
                AssoList: [
                    {
                        name: 'Team Liquid',
                        description: "欧洲老牌战队，成立于2012年12月。直到2015年开始随着Kuroky的加入，Liquid慢慢走进世界舞台中，成为欧洲一支强劲的战队。在2016年在2016 Shanghai Major和2016ESL ONE这两项赛事中都取得了亚军。紧接着队伍人员进行调整，引进了Miracle-等数名天梯9000高分选手。在 2017 年成為 TI 冠軍。",
                        logo: "",
                        tags:[
                          "Dota2",
                          "Ti冠军"
                        ]
                    },
                    {
                      name: "Team Solid",
                      description: "B神、龙神、跳刀、二冰他们和yyf一起组建的Ti9海选战队。",
                      logo: "",
                      tags:[
                        "Dota2",
                        "2届Ti冠军("

                      ]
                    },

                    {
                      name: 'Team Gas',
                      description: "气体战队成员为：一号位：ZSMJ 二号位：水瓶座（之前担任固体战队的工具人中单） 三号位：Axx 四号位：Ch 五号位：狗哥SanSheng",
                      logo: "",
                      tags:[
                        "Dota2"
                      ]
                    }
                ],
                randomMovieList: []
            }
        },

        mounted () {
            this.changeLimit();
        }
    }
</script>
<style>

</style>
