<template>
<Header class="nav-header">
    <Row type="flex" justify="space-between" align="middle" class="nav-header-row">
        <Col class="nav-header-logo">
            <slot name="logo"></slot>
        </Col>
        <Col class="nav-header-menu">
            <slot></slot>
        </Col>
    </Row>
</Header>
</template>

<script>
export default {
    name: "NavHeader"
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
