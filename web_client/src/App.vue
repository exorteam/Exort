<template>
<div id="app">
    <router-view v-if="routerOk"/>
</div>
</template>

<script>
import { mapActions, mapState, mapMutations } from 'vuex'

export default {
    name: 'App',
    data() { return {
        routerOk: true
    }},
    computed: {
        ...mapState('common/currentUser', ['uid'])
    },
    methods: {
        ...mapActions('common/currentUser', ['getAdmin', 'refreshInfo']),
        ...mapMutations('common/currentUser', ['setInfo']),
        reload() {
            this.routerOk = false;
            this.$nextTick(function() {
                this.routerOk = true;
            })
        }
    },
    watch: {
        'uid': function(newUid, oldUid) {
            if (newUid) {
                this.getAdmin().catch(err => this.$Notice.error({
                    title: '获取管理权限列表失败',
                    desc: err.message || err
                })).finally(() => {
                    this.reload()
                });
                this.refreshInfo().catch(err => this.$Notice.error({
                    title: '获取个人信息失败',
                    desc: err.message || err
                })).finally(() => {
                    this.reload()
                });
            } else {
                this.setInfo(null);
                this.reload();
            }
        }
    }
}
</script>

<style>
</style>
