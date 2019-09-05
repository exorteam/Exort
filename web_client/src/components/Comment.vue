<template>
    <div id="comment">
        <br/>
        <Card>
            <p slot="title">
                评论
            </p>
            <div>
                <div>
                    <Input v-model="comment" type="textarea" :autosize="{minRows: 2,maxRows: 5}"
                           placeholder=""></Input>
                </div>
                <br/>
                <div style="text-align: right">
                    <Button @click="createNewComment" type="info">发表评论</Button>
                </div>
            </div>
            <div id="comment_show">
                <Divider/>
                <div v-for="c in this.commentList" :key="c.id">
                    <Row>
                        <Col span="2" style="text-align: center">
                            <Avatar :src="user_pic" size="large"/>
                        </Col>
                        <Col span="22">
                            <div>
                                <strong>{{c.userName}}</strong>
                                <Time style="float: right" type="datetime"
                                      :time="c.time"/>
                            </div>
                            <div>
                                {{c.content}}
                            </div>
                            <div v-if="c.userId==uid" style="text-align: right">
                                <a @click="deleteCom(c.id)">删除</a>
                            </div>
                        </Col>
                    </Row>
                    <Divider/>
                </div>
            </div>
        </Card>
    </div>
</template>

<script>
    import {api} from '@/http';
    import user_pic from '@/assets/comment-user/user.jpg';
    import {mapActions, mapState} from 'vuex';

    export default {
        props: ['type'],
        data() {
            return {
                comment: "",
                user_pic:user_pic,
            }
        },
        computed: {
            ...mapState('common/comment', [
                'commentList'
            ]),
            ...mapState('common/currentUser', [
                'uid',
                'username'
            ]),
        },
        methods: {
            ...mapActions('common/comment', [
                'getActivityCommentList',
                'getArticleCommentList',
                'createComment',
                'deleteComment'
            ]),
            createNewComment() {
                let commentData = {
                    userId: this.uid,
                    userName: this.username,
                    type: this.type,
                    commentedId: this.$route.params.id,
                    content: this.comment
                };

                this.createComment(commentData).then(() => {
                    this.init();
                    this.comment = "";
                }).catch(err => {
                    console.log(err);
                });
            },
            deleteCom(id) {
                let data = {
                    id: id,
                    userId: this.uid
                };
                this.deleteComment(data).then(() => {
                    this.init();
                }).catch(err => {
                    console.log(err);
                });
            },
            init() {
                if (this.type == "activity") {
                    this.getActivityCommentList(this.$route.params.id);
                } else {
                    this.getArticleCommentList(this.$route.params.id);
                }
            }
        },
        mounted() {
            this.init();
        },
    }
</script>

