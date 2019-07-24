<template>
    <div id="adminnav">
        <div class="layout-logo"></div>
        <div class="layout-nav">
            <Menu mode="horizontal" theme="dark" active-name="1" style="width: 100%;child-align: right">
                <noticebar></noticebar>
                <userbar></userbar>
            </Menu>
        </div>
    </div>
</template>

<script>
    import noticebar from './notice'
    import userbar from './user'

    export default {
        name: "adminnav",
        components: {noticebar, userbar},
        data() {
            return {};
        },
        methods: {},
    }
</script>

<style>

    .layout-logo {
        width: 100px;
        height: 30px;
        background: #5b6270;
        border-radius: 3px;
        float: left;
        position: relative;
        top: 15px;
        left: 20px;
    }

    .layout-nav {
        width: 420px;
        margin: 0 auto;
        margin-right: 20px;
        text-align: right;
    }

</style>
