<template>
    <Modal :title="signup?'注册':'登录'" :value="value" @on-visible-change="display"
           :closable="!processing" :mask-closable="!processing">

        <Form :label-width="60">
            <FormItem label="用户名">
                <Input v-model="username" :autofocus="true"/>
            </FormItem>
            <FormItem label="密码">
                <Input v-model="password" type="password" @on-enter="signup?clickSignup():clickLogin()"/>
            </FormItem>
        </Form>

        <Alert type="error" v-if="error">
            {{error.title}}
            <template #desc>{{error.message}}</template>
        </Alert>

        <template #footer>
            <div style="text-align: center;" v-if="signup">
                <Button type="success" @click="clickSignup" :loading="processing">注册</Button>
                <Button :loading="processing" @click="setSignupMode(false)">返回</Button>
            </div>
            <div style="text-align: center;" v-else>
                <Button type="primary" @click="clickLogin" :loading="processing">登录</Button>
                <Button :loading="processing" @click="setSignupMode(true)">注册</Button>
            </div>
        </template>

    </Modal>
</template>

<script>
import { mapActions } from 'vuex'

export default {
    props: [ 'value' ],
    data() { return {
        username: "",
        password: "",
        error: null,
        processing: false,
        signup: false
    }},
    methods: {
        clickLogin() {
            if(this.processing) {
                return;
            }
            if (/^[a-zA-Z][a-zA-Z0-9_]{2,23}$/.test(this.username)) {
                this.processing = true;
                this.error = null;

                let that = this;
                this.login({
                    username: this.username,
                    password: this.password
                }).then(() => {
                    that.display(false);
                }).catch(err => {
                    that.error = {
                        title: '登录失败',
                        message: err
                    }
                }).finally(() => {
                    that.processing = false;
                });
            } else {
                this.error = {
                    title: '用户名格式不正确',
                    message: '只能由字母(a-z,A-Z)开头，由字母、数字(0-9)、下划线(_)组成，且长度在 3 至 24 之间'
                }
            }
        },
        clickSignup() {
            // TODO: signup
        },
        setSignupMode(signup) {
            this.signup = signup;
            this.error = null;
        },
        display(visible) {
            this.password = '';
            this.$emit('input', visible);
        },
        ...mapActions('common/currentUser', ['login'])
    }
}
</script>

<style>

</style>
