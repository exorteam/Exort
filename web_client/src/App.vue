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
        ...mapState('common/auth', ['uid'])
    },
    methods: {
        ...mapActions('common/auth', ['getAdmin']),
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
                this.getAdmin().catch(err => {
                    console.log(err);
                })
            }
            this.reload();
        }
    }
}
</script>

<style>
</style>
