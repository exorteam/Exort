<template>
<NavHeader>
    <template #logo>
        <!--<img src="@/assets/AssociationLogo/solid.jpg" alt="LOGO" style="height:50px;">-->
    </template>
    <NavUser></NavUser>
</NavHeader>
</template>

<script>
import NavHeader from '../common/nav/Header'
import NavUser from '../common/nav/User'

export default {
    components: {
        NavHeader,
        NavUser
    }
}
</script>

<style>

</style>
