<template>
<div id="app">
    <router-view v-if="routerOk"/>
</div>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
    name: 'App',
    data() { return {
        routerOk: true
    }},
    computed: {
        ...mapState('common/currentUser', ['uid'])
    },
    methods: {
        ...mapActions('common/currentUser', ['getAdmin']),
        reload() {
            this.routerOk = false;
            this.$nextTick(function() {
                this.routerOk = true;
            })
        }
    },
    watch: {
        'uid': function(newUid, oldUid) {
            let that = this;
            if (newUid) {
                this.getAdmin().catch(err => that.$Notice.error({
                    title: '获取管理权限列表失败',
                    desc: err.message || err,
                })).finally(() => {
                    that.reload()
                });
            } else {
                this.reload();
            }
        }
    }
}
</script>

<style>
</style>
