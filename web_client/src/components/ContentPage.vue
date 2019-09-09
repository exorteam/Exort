<template>
<Layout class="content-page">
    <Header class="header">
        <div v-if="!noBack" class="back" @click="goBack">
            <Icon type="md-arrow-round-back"/>
        </div>
        <slot name="header"></slot>
    </Header>
    <Content class="content">
        <slot></slot>
    </Content>
</Layout>
</template>

<script>
export default {
    props:{
        noBack: Boolean
    },
    methods: {
        goBack() {
            this.$router.go(-1);
        }
    }
}
</script>

<style>
.content-page {
    height: 100%;
}
.content-page .header {
    display: flex;
    justify-content: space-between;
    background: white;
    padding: 0 8px;
}
.content-page .back {
    height: 100%;
    width: 40px;
    cursor: pointer;
}
.content-page .back i {
    font-size: 32px;
}
.content-page .content {
    background: white;
    height: 100%;
}
</style>
