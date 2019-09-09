<template>
<ContentPage no-back class="home-page">
    <template #header>
        <h2>新发表</h2>
    </template>
    <BrowserScroll @bottom="loadArticles" />
    <div v-for="article in articles" :key="article.id" class="preview">
        <div class="cover">

        </div>
	    <MdEditor ref="md" :value="article.content" read-mode class="content"/>
    </div>
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
.home-page .preview {
    height: 320px;
    overflow: hidden;
    position: relative;
}
.home-page .preview .content{
    position: relative;
    top:-320px;
}
.home-page .preview .cover {
    height: 320px;
    background: rgba(255,255,255, 0);
}
</style>
