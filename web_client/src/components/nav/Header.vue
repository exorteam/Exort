<template>
<Header class="nav-header">
    <Row type="flex" justify="space-between" align="middle" class="nav-header-row">
        <Col class="nav-header-logo">
            <!--<img src="@/assets/AssociationLogo/solid.jpg" alt="LOGO" style="height:50px;">-->
        </Col>
        <Col class="nav-header-menu">
            <UserDropDown/>
        </Col>
    </Row>
</Header>
</template>

<script>
import UserDropDown from './UserDropDown'

export default {
    components: {
        UserDropDown
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
