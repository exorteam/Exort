<template>
	<Card>
		<div id="AssoList">
			<div id=SearchAsso>
				<Input search @on-search="getAssociationList" placeholder="请输入关键词" style="width: 300px" />
				<div style="display:inline">
					<Button @click="tag.tag_show=true" style="width: 80px">选择标签</Button>
					<TagChoose :tag="tag"/>
				</div>
				<div>
					<div v-if="tag.tagList.length" style="display:inline">
						<Tag v-for="tag in tag.tagList" :key="tag.id" :row="tag" color="blue">{{ tag }}</Tag>
					</div>
				</div>
			</div>

			<br>
			<div id="CardList"  style="display: flex; flex-wrap: wrap">
				<Card v-for="item in AssoList" 
						:key="item.id" 
						:row="item" 
						style="width:350px;height:350px;margin-left:5px;margin-top:5px" >
					<p slot="title">
						<Icon type="ios-people" ></Icon>
						{{item.name}}
						<Tooltip content="已订阅" placement="top"
								style="position:absolute;right:10px;" 
								v-if="subscribed.includes(item.id)">
							<Icon type="ios-notifications" size="20" color="green" 
								@click.native="onClickUnsubscribe(item.id,item.name)" />
						</Tooltip>
						<Tooltip content="订阅" placement="top"
								style="position:absolute;right:10px;" 
								v-else>
							<Icon type="ios-notifications-off" size="20" 
							    @click.native="onClickSubscribe(item.id,item.name)" />
						</Tooltip>
					</p>


					<div style="text-align: center;height:100px">
						<img :src="item.logo" style="width:80px;height:80px;"/>
					</div>
					<div style= "min-height: 100%; ">
						<p>{{ item.description }}</p>
					</div>
					<div style="margin-top:80px">
						<Tag v-for="tag in item.tags" :key="tag.id" :row="tag"  color="geekblue">
							{{ tag }}
						</Tag>
					</div>
					<div style="text-align:center">
						<Button @click="onClickCard(item.id)" long>查看社团详情</Button>
					</div>
				</Card>
			</div>
			<div style="margin-top:15px;text-align: center">
				<Page id = "page" show-elevator show-total
				:total="pageProp.totalSize" :page-size.sync="pageProp.pageSize" :page-size-opts="pageProp.pageSizeOpt"
				:current.sync = "pageProp.pageNum"  @on-change="getAssociationList"></Page>
			</div>
		</div>
	</Card>
</template>

<script>
import TagChoose from '@/components/TagChoose'
import { mapState, mapActions, mapMutations } from 'vuex'

export default {
    name:'UserAssociationList',
    components:{ TagChoose },
    data () {
        return {
            pageProp:{
                totalSize : 0,
                pageSize : 6,
                pageNum : 1
            },
            tag:{
                tag_show: false,
                tagList:[],
                tagrepo : ["运动", "饮食", "音乐", "舞蹈", "历史", "游戏", "户外", "天文","航模","动漫"]
            },
            AssoList: [],
        }
    },
	computed: {
		...mapState('common/currentUserSubscription',[
			'subscribed'
		])
	},
    methods: {
		...mapActions('common/associationOps',[
			'queryPagedAssociationsWithFilter',
		]),
		...mapActions('common/currentUserSubscription',[
			'refreshSubscription',
			'commitSubscription',
			'removeSubscription'
		]),
        getAssociationList(search) {
            const tags = this.tag.tagList.join();

			this.queryPagedAssociationsWithFilter({
				filter: {
					keyword: search?search:'',
					tags: tags?tags:[],
					state: 1
				},
				pageArgs: {
					pageNum: this.pageProp.pageNum,
					pageSize: this.pageProp.pageSize
				}
			}).then(response => {
				this.AssoList = response.data.data.content
				this.pageProp.totalSize = response.data.data.totalSize
			})
			.catch(e => {
				console.log(e)
			});
        },
		onClickCard(id){
			this.$router.push({
				name:'UserAssociationDetail',
				params: {
					assoId: id
				}
			});
		},
		onClickSubscribe(id,name) {
			this.commitSubscription({
				ids: [id]
			}).then(() => {
				this.refreshSubscription();
				this.$Notice.success({
					desc: '成功订阅社团' + name
				})
			}).catch(err => {
				this.$Notice.error({
					title: '订阅时发生错误',
					desc: err
				})
			})

		},
		onClickUnsubscribe(id,name) {
			this.removeSubscription({
				ids: [id]
			}).then(() => {
				this.refreshSubscription();
				this.$Notice.success({
					desc: '已取消订阅社团' + name
				})
			}).catch(err => {
				this.$Notice.error({
					title: '取消订阅时发生错误',
					desc: err
				})
			})
		}
	},
    mounted() {
		this.refreshSubscription().then(() => {
			this.getAssociationList();
		})
    }
}
</script>
