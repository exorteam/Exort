<template>
<Layout>
	<Sider :style="{background: '#fff'}">
		<HomeSider/>
	</Sider>
	<Content style="min-height:420px;margin:10px;">
		<router-view/>
	</Content>
</Layout>
</template>

<script>
import HomeSider from '@/components/nav/HomeSider'

export default {
    components: {
		HomeSider
    },
}
</script>

<style>
</style>
