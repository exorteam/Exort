<template>
<span>
    <Button v-if="users.list[index].enabled" :loading="processing"
            ghost size="small" type="error" @click="disableUser(true)">
        禁用
    </Button>
    <Button v-else :loading="processing"
            ghost size="small" type="success" @click="disableUser(false)">
        恢复
    </Button>
    <Button ghost size="small" type="warning" @click="beginEditingRoles">
        设置角色
    </Button>
</span>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
    props: [ 'index' ],
    data() { return {
        processing: false
    }},
    computed: {
        ...mapState('systemAdmin/user', ['users', 'curUser'])
    },
    methods: {
        beginEditingRoles() {
            this.processing = true;
            let that = this;
            this.updateCurUser({
                user: this.users.list[this.index]
            }).catch(err => {
                console.log(err);
            }).finally(() => {
                that.processing = false;
            })
        },
        disableUser(disabled) {
            this.processing = true;
            let that = this;
            this.changeUserState({index: this.index, disabled}).catch(err => {
                console.log(err);
            }).finally(() => {
                that.processing = false;
            });
        },
        ...mapActions('systemAdmin/user', [
            'changeUserState',
            'updateCurUser',
            'grantCurUser',
            'revokeCurUser'
        ])
    }
}
</script>

<style>

</style>
