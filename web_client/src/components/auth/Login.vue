<template>
<Form>
    <FormItem label="用户名">
        <Input v-model="username" :autofocus="true"/>
    </FormItem>
    <FormItem label="密码">
        <Input v-model="password" type="password" @on-enter="signup?clickSignup():clickLogin()"/>
    </FormItem>

    <FormItem style="text-align: center;" v-if="signup">
        <Button type="success" @click="clickSignup" :loading="processing">注册</Button>
        <Button style="margin-left:30px" :loading="processing" @click="signup=false">返回</Button>
    </FormItem>
    <FormItem style="text-align: center;" v-else>
        <Button type="primary" @click="clickLogin" :loading="processing">登录</Button>
        <Button style="margin-left:30px" :loading="processing" @click="signup=true">注册</Button>
    </FormItem>
</Form>
</template>

<script>
import { api } from '@/http'
import { mapActions } from 'vuex'

export default {
    data() { return {
        username: "",
        password: "",
        processing: false,
        signup: false
    }},
    methods: {
        clickLogin() {
            if (/^[a-zA-Z][a-zA-Z0-9_]{2,23}$/.test(this.username)) {
                this.processing = true;

                this.login({
                    username: this.username,
                    password: this.password
                }).then(() => {
                    this.$emit('login');
                }).catch(err => {
                    console.log(err);
                    this.$Notice.error({
                        title: '登录失败',
                        desc: err.message || err
                    });
                }).finally(() => {
                    this.processing = false;
                });
            } else {
                this.$Notice.error({
                    title: '用户名格式不正确',
                    desc: '只能由字母(a-z,A-Z)开头，由字母、数字(0-9)、下划线(_)组成，且长度在 3 至 24 之间'
                });
            }
        },
        clickSignup() {
            if(this.processing) {
                return;
            }
            if (/^[a-zA-Z][a-zA-Z0-9_]{2,23}$/.test(this.username)) {
                if (this.password.length > 5) {
                    this.processing = true;

                    api({
                        method: 'post',
                        url: '/auth/register',
                        data: {
                            username: this.username,
                            password: this.password
                        }
                    }).then(res => {
                        this.$Notice.success({
                            title: '注册成功'
                        });
                        this.signup = false;
                    }).catch(err => {
                        this.$Notice.error({
                            title: '注册失败',
                            desc: err.message || err
                        });
                    }).finally(() => {
                        this.processing = false;
                    });
                } else {
                    this.$Notice.error({
                        title: '密码不符合要求',
                        desc: '密码长度至少需要6位'
                    });
                }
            } else {
                this.$Notice.error({
                    title: '用户名格式不正确',
                    desc: '只能由字母(a-z,A-Z)开头，由字母、数字(0-9)、下划线(_)组成，且长度在 3 至 24 之间'
                });
            }
        },
        setSignupMode(signup) {
            this.signup = signup;
        },
        ...mapActions('common/currentUser', ['login'])
    }
}
</script>

<style>

</style>
