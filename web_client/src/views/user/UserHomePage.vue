<template>
<ContentPage no-back class="home-page">
    <template #header>
        <h2>新发表</h2>
    </template>
    <BrowserScroll @bottom="loadArticles" />
    <Card v-for="article in articles" :key="article.id" class="preview" style="cursor:pointer">
        <div class="info" slot="title" @click="$router.push({name:'UserArticleReader',params:{id:article.id}})">
            标题： {{article.title}}
            <Divider type="vertical" />
            作者： {{article.associationId}}
            <Divider type="vertical" />
            发布时间: {{new Date(article.publishTime).toLocaleString()}}
        </div>
	    <MdEditor ref="md" :value="article.content" read-mode class="content"/>
    </Card>
</ContentPage>
</template>

<script>
import { mapState } from 'vuex'
import { api } from '@/http'
import ContentPage from '@/components/ContentPage'
import BrowserScroll from '@/components/BrowserScroll'
import MdEditor from '@/components/MdEditor'

export default {
    components: {
        ContentPage,
        BrowserScroll,
        MdEditor
    },
    data() {return {
        loading: false,
        articles: [],
        nextPage: 0,
        pageSize: 10,
        totalSize: 0
    };},
    computed: {
        ...mapState('common/currentUser', ['uid'])
    },
    methods: {
        loadArticles() {
            if (!this.uid) {
                return;
            }
            if (this.loading) {
                return;
            }
            this.loading = true;
            api({
                method: 'get',
                url: '/articles/list/user',
                params: {
                    pageNum: this.nextPage,
                    pageSize: this.pageSize
                }
            }).then(res => {
                console.log(res);
                var paged = res.data.data;
                if (paged.content.length) {
                    this.nextPage = paged.pageNum + 1;
                    this.pageSize = paged.pageSize;
                    this.totalSize = paged.totalSize;
                    this.articles.push(...paged.content)
                }
            }).catch(err => {
                this.$Notice.error({
                    title: '加载失败',
                    desc: err.message || err
                });
            }).finally(() => {
                this.loading = false;
            });
        }
    },
    mounted() {
        this.loadArticles();
    }
}
</script>

<style>
</style>
