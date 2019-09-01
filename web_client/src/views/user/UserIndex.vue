<template>
<Layout>
	<Sider :style="{background: '#fff'}">
		<UsrNavbar/>
	</Sider>
	<Content style="min-height:420px;margin:10px;">
		<Card>
			<span>Home page</span>
		</Card>
	</Content>
</Layout>
</template>

<script>
import UsrNavbar from '@/components/user/UsrNavbar'

export default {
    components: {
		UsrNavbar
    },
    data() { 
		return {
        	image: null 
		}
	}
}
</script>

<style>
</style>
