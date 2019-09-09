<template>
<Header class="nav-header">
    <Row type="flex" justify="space-between" align="middle" class="nav-header-row">
        <Col class="nav-header-logo">
			<router-link to="/">
				<Avatar shape="square" :src="logo" size="large" style="margin-bottom:25px"/>
			</router-link>
        </Col>
        <Col class="nav-header-menu">
            <UserDropDown/>
        </Col>
    </Row>
</Header>
</template>

<script>
import { mapState } from 'vuex'
import UserDropDown from './UserDropDown'

export default {
    components: {
        UserDropDown
    },
	computed: {
		...mapState('common/applicationEntries',['logo'])
	}
}
</script>

<style>
.nav-header {
    padding: 0 10px;
}

.nav-header-row {
    height: 100%;
}
</style>
