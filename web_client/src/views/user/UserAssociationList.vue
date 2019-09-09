<template>
<ContentPage no-back>
	<template #header>
		<div>
			<Input search @on-search="getAssociationList" placeholder="请输入关键词" style="width: 300px" />
		</div>
		<div>
			<Button @click="creating=true">创建社团</Button>
		</div>
		<Modal v-model="creating" footer-hide title="申请创建社团">
			<AssociationInfoEditor :value="{}" @cancel="creating=false" @input="creating=false" has-cancel review/>
		</Modal>
	</template>

	<br>
	<div id="CardList"  style="display: flex; flex-wrap: wrap">
		<Card v-for="item in AssoList"
				:key="item.id"
				:row="item"
				style="width:100%;margin-left:5px;margin-top:5px" >
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

			<div style="display:flex;width:100%;cursor:pointer;" @click="onClickCard(item.id)">
				<div style="text-align: center;height:100px">
					<img :src="associationIcon(item.logo)" style="width:80px;height:80px;"/>
				</div>
				<div style="margin-left:16px">
					<div>
						<!--<Tag v-for="tag in item.tags" :key="tag.id" :row="tag"  color="geekblue">-->
						<!--    {{ tag }}                                                            -->
						<!--</Tag>                                                                   -->
					</div>
					<div>
						<p>{{ item.description }}</p>
					</div>
				</div>
			</div>
		</Card>
	</div>
	<div style="margin-top:15px;text-align: center">
		<Page id = "page" show-total
		:total="pageProp.totalSize" :page-size.sync="pageProp.pageSize" :page-size-opts="pageProp.pageSizeOpt"
		:current.sync = "pageProp.pageNum"  @on-change="onPageChange"></Page>
	</div>
</ContentPage>
</template>

<script>
import TagChoose from '@/components/TagChoose'
import AssociationInfoEditor from '@/components/editor/AssociationInfoEditor'
import ContentPage from '@/components/ContentPage'
import { mapState, mapActions, mapMutations } from 'vuex'
import { associationIcon } from '@/const'

export default {
    name:'UserAssociationList',
    components:{ TagChoose,ContentPage,AssociationInfoEditor },
    data () {
        return {
			creating: false,
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
			searchText: '',
        }
    },
	computed: {
		...mapState('common/currentUserSubscription',[
			'subscribed'
		])
	},
    methods: {
		associationIcon,
		...mapActions('common/associationOps',[
			'queryPagedAssociationsWithFilter',
		]),
		...mapActions('common/currentUserSubscription',[
			'refreshSubscription',
			'commitSubscription',
			'removeSubscription'
		]),
        getAssociationList(search,newPageNum) {
			this.searchText = search;
            const tags = this.tag.tagList.join();

			this.queryPagedAssociationsWithFilter({
				filter: {
					keyword: search == ''?null:search,
					tags: tags?tags:[],
					state: 1
				},
				pageArgs: {
					pageNum: newPageNum?newPageNum:this.pageProp.pageNum,
					pageSize: this.pageProp.pageSize
				}
			}).then(response => {
				this.AssoList = response.data.data.content
				this.pageProp.totalSize = response.data.data.totalSize
			}).catch(err => {
				this.$Notice.error({
					title: '获取社团列表失败',
					desc: err.message || err
				})
			});
        },
		onPageChange(newPageNum) {
			this.getAssociationList(this.searchText,newPageNum);
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
