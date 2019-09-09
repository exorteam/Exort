<template>
<ContentPage no-back>
	<template #header>
		<h2>通知</h2>
		<Button @click="postMsgModal=true">
			<Icon type="ios-mail-outline" size="22"/>
		</Button>
	</template>

	<div id="MsgCardList">
		<h5 v-if="msgs.isEmpty">Nothing new here.</h5>
		<Card v-for="(item,index) in msgs" :key="item.id" :row="item" style="margin-top:10px">
		<Row type="flex" justify="space-between">
			<Col span="10"><p style="font-weight:bold">{{item.timestamp}} 来自 {{item.senderName?item.senderName:"用户 #"+item.senderId}}</p></Col>
			<Col span="1">
				<Tooltip content="删除信息" placement="top">
					<Icon @click.native="onClickDropMsg(item.id,index)" type="ios-trash" />
				</Tooltip>
			</Col>
		</Row>
			<p style="word-break: break-all;white-space: normal;">{{item.content}}</p>
		</Card>
		<Row type="flex" justify="center" style="margin-top:50px">
			<Button @click="loadMoreMsgs">加载更多</Button>
		</Row>
	</div>

	<Modal v-model="postMsgModal"
			title="New Message"
			:loading="true"
			@on-ok="onClickSendMsg">
		<Form :model="newMsgForm" :label-width="20">
			<FormItem label="To">
				<Input v-model="newMsgForm.receiverId" type="text" style="width:80px"/>
			</FormItem>
			<FormItem>
				<Input v-model="newMsgForm.content" type="textarea" :rows="5" />
			</FormItem>
		</Form>
	</Modal>
</ContentPage>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import ContentPage from '@/components/ContentPage'
import BrowserScroll from '@/components/BrowserScroll'

export default {
	name: 'UserHomePage',
	components: {ContentPage,BrowserScroll},
	data() {
		return {
			msgs: [],
			msgPageNum: 0,
			msgPageSize: 10,

			postMsgModal: false,
			newMsgForm: {}
		}
	},
	computed: {
        ...mapState('common/currentUser', ['uid'])
	},
	methods: {
		...mapActions('common/currentUserMsgs',[
			'queryPagedMessage',
			'postMessage',
			'dropMessageById'
		]),

		onClickSendMsg(){
			const id = this.newMsgForm.receiverId;
			const content = this.newMsgForm.content;
			if(!id
				|| id.isEmpty
				|| !content
				|| content.isEmpty){

				this.$Message.warning('接受者ID和消息内容不能为空');
				this.postMsgModal = false;

				return;
			}
			this.postMessage({
				receiverId: id,
				msg: content
			}).then(res => {
				this.postMsgModal = false;
				this.newMsgForm = {};
				this.$Notice.success({
					desc: '已发送消息至用户#' + id,
				})
			}).catch(err => {
				this.$Notice.error({
					title: '发送消息至用户#' + id + '时出现错误',
					desc: err
				})
			})

		},
		loadMoreMsgs(){
			this.queryPagedMessage({
				pageNum: ++this.msgPageNum,
				pageSize: this.msgPageSize
			}).then(res => {
				this.msgs.push(...res.data.data.content);
				if(res.data.data.pageNum * res.data.data.pageSize > res.data.data.totalSize){
					--this.msgPageNum;
				}
			})
		},


		onClickDropMsg(msgId,index){
			this.$Modal.confirm({
				title: '提示',
				content: '<p>确认删除消息？(ID:'+msgId+')</p>',
				onOk: () => {
					this.dropMessageById({msgId}).then(() => {
						this.msgs.splice(index,1);
						this.$Notice.success({
							desc: '已删除消息ID: ' + msgId
						})
					}).catch(err => {
						this.$Notice.error({
							title: '删除消息时出现错误ID: ' + msgId ,
							desc: err
						})
					})

				},
				onCancel: () => {

				}
			});
		}
	},
	mounted(){
		if (this.uid) {
			this.queryPagedMessage({
				pageNum: this.msgPageNum,
				pageSize: this.msgPageSize
			}).then(res => {
				this.msgs = res.data.data.content;
			})
		}
	},
    watch: {
        'uid': function(newUid, oldUid) {
            if (newUid) {
				this.queryPagedMessage({
					pageNum: this.msgPageNum,
					pageSize: this.msgPageSize
				}).then(res => {
					this.msgs = res.data.data.content;
				})
            } else {
                this.msgs = [];
            }
        }
    }
}
</script>
