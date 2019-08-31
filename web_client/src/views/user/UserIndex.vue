<template>
<Layout>
    <NavHeader/>
    <Layout>
        <Form :label-width="80">
            <FormItem label="社团名称">
                <Input style="width: 420px"/>
            </FormItem>
            <FormItem label="社团标志">
                <ImageUploader v-model="image" inline :max-size="512"/>
            </FormItem>
            <FormItem label="balabalba">
                <Input style="width: 420px"/>
            </FormItem>
        </Form>
    </Layout>
</Layout>
</template>

<script>
import NavHeader from '@/components/nav/Header'

import ImageUploader from '@/components/uploader/ImageUploader'

export default {
    components: {
        NavHeader,
        ImageUploader
    },
    data() { return {
        image: null
    }}
}
</script>

<style>
</style>
