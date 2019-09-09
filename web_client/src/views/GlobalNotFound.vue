<template>
<div class="no-found-wrapper">
    <h>404</h>
    <p>Not Found</p>
    <span>{{filename}}</span>
    <Button @click="upload" >上传</Button>
    <MdEditor ref="md" v-model="filename"/>
</div>
</template>

<script>
import MdEditor from '@/components/MdEditor'

export default {
    components: {
        MdEditor
    },
    data() { return {
        filename: 'test.txt'
    };},
    methods: {
        upload() {
            this.$refs.md.$save().finally(()=>{console.log('finish')});
        }
    }
}
</script>

<style>
.no-found-wrapper .md-editor{
    width: 50%;
    height: 560px;
}
</style>
