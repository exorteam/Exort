<template>
<Form v-if="asso" :label-width="80">
    <FormItem label="名称">
        <Input v-model="asso.name" />
    </FormItem>
    <FormItem label="社团简介">
        <Input type="textarea" v-model="asso.description" />
    </FormItem>
    <FormItem label="头像">
        <ImageUploader v-model="asso.logo" />
    </FormItem>
    <!--  TODO: tags  -->
    <FormItem>
        <Button ghost type="success" :loading="processing" @click="onSave" style="margin-right:20px">确定</Button>
        <Button ghost type="error" v-if="hasCancel" :loading="processing" @click="$emit('cancel')">取消</Button>
    </FormItem>
</Form>
</template>

<script>
import {api} from '@/http'

import ImageUploader from '@/components/uploader/ImageUploader'
import { mkdir } from 'fs';

export default {
    components: {
        ImageUploader
    },
    props: {
        value: Object,
        hasCancel: Boolean,
        review: Boolean
    },
    data() {return {
        asso: null,
        processing: false
    };},
    computed: {
        isUpdating() {
            return this.asso && this.asso.id
        }
    },
    methods: {
        reload() {
            this.asso = this.value && {
                logo: null,
                description: '',
                name: '',
                tags: [],
                ...this.value
            };
        },
        onSave() {
            var asso = {...this.asso};
            if (this.isUpdating) {
                // TODO: update
            } else {
                if (this.review) {
                    this.processing = true;
                    api({
                        method: 'post',
                        url: '/applications/create_association',
                        data: asso
                    }).then(res => {
                        this.$emit('input', res.data.data.details);
                        this.$Notice.success({
                            title: '发起申请成功',
                            desc: '请等待审核通过'
                        });
                    }).catch(err => {
                        this.$Notice.error({
                            title: '发起申请失败',
                            desc: err.message || err
                        })
                    }).finally(() => {
                        this.processing = false;
                    })
                } else {
                    // TODO: no review
                }
            }
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
