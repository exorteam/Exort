<template>
<Form v-if="user && user.id" :label-width="40">
    <FormItem label="头像">
        <ImageUploader v-model="user.studentId" />
    </FormItem>
    <FormItem label="昵称">
        <Input v-model="user.nickname" />
    </FormItem>
    <FormItem label="个人描述">
        <Input type="textarea" v-model="user.description" />
    </FormItem>
    <FormItem label="性别">
        <Select v-model="user.gender">
            <Option :value="0">保密</Option>
            <Option :value="1">男</Option>
            <Option :value="2">女</Option>
        </Select>
    </FormItem>
    <FormItem label="生日">
        <DatePicker v-model="user.birthday" />
    </FormItem>
    <FormItem label="姓名">
        <Input v-model="user.name" />
    </FormItem>
    <FormItem label="电话">
        <Input v-model="user.phone" />
    </FormItem>
    <FormItem label="邮箱">
        <Input v-model="user.email" />
    </FormItem>
    <FormItem label="QQ">
        <Input v-model="user.qqId" />
    </FormItem>
    <FormItem label="微信">
        <Input v-model="user.wechatId" />
    </FormItem>
    <FormItem>
        <Button ghost type="success" :loading="processing" @click="onSave" style="margin-right:20px">保存</Button>
        <Button ghost type="error" v-if="hasCancel" :loading="processing" @click="$emit('cancel')">取消</Button>
    </FormItem>
</Form>
</template>

<script>
import {api} from '@/http'

import ImageUploader from '@/components/uploader/ImageUploader'

export default {
    components: {
        ImageUploader
    },
    props: {
        value: Object,
        hasCancel: Boolean
    },
    data() {return {
        user: null,
        processing: false
    };},
    methods: {
        reload() {
            this.user = this.value && {
                studentId: null,
                nickname: '',
                description: '',
                gender: 0,
                birthday: null,
                name: '',
                phone: '',
                email: '',
                qqId: '',
                wechatId: '',
                ...this.value
            };
        },
        onSave() {
            if (!this.user || !this.user.id) {
                return;
            }
            this.processing = true;
            var update = { ...this.user };
            api({
                method: 'post',
                url: '/users/' + update.id,
                data: update
            }).then(res => {
                this.$emit('input', res.data.data);
                this.$emit('saved');
            }).catch(err => {
                this.$Notice.error({
                    title: '保存失败',
                    desc: err.message || err
                });
            }).finally(() => {
                this.processing = false;
            });
        }
    },
    mounted() {
        this.reload();
    },
    watch: {
        'value': function() { this.reload(); }
    }
}
</script>

<style>

</style>
