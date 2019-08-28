<template>
<Layout>
    <NavHeader>
        <template #logo>
            <!--<img src="@/assets/AssociationLogo/solid.jpg" alt="LOGO" style="height:50px;">-->
        </template>
        <NavUser></NavUser>
    </NavHeader>

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
import NavHeader from '../common/nav/Header'
import NavUser from '../common/nav/User'
import ImageUploader from '../common/uploader/ImageUploader'

export default {
    components: {
        NavHeader,
        NavUser,
        ImageUploader
    },
    data() { return {
        image: null
    }}
}
</script>

<style>
</style>
