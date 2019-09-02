<template>
<Layout>
	<Sider :style="{background: '#fff'}">
		<UsrNavbar/>
	</Sider>
	<Content style="min-height:420px;margin:10px;">
		<router-view/>
	</Content>
</Layout>
</template>

<script>
import UsrNavbar from '@/components/user/UsrNavbar'

export default {
    components: {
		UsrNavbar
    },
}
</script>

<style>
</style>
