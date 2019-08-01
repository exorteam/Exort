<template>
    <div class="sysside">
        <MenuItem name="1-1" :to="{name:'AssociaMemManage'}">
            <Icon type="ios-navigate"></Icon>
            <span>社团成员管理</span>
        </MenuItem>
        <MenuItem name="1-2" :to="{name:'AppliManagement'}" @click="">
            <Icon type="ios-navigate"></Icon>
            <span>成员申请管理</span>
        </MenuItem>
        <MenuItem name="1-3" :to="{name:'activity'}">
            <Icon type="ios-navigate"></Icon>
            <span>活动管理</span>
        </MenuItem>
        <MenuItem name="1-4">
			<router-link :to="{name:'Articles'}">
            <Icon type="ios-navigate"></Icon>
            <span>文章管理</span>
			</router-link>
        </MenuItem>
            <MenuItem name="1-5" :to="{name:'SysAssoList'}">
            <Icon type="ios-navigate"></Icon>
            <span>社团管理</span>
        </MenuItem>
        </MenuItem>
            <MenuItem name="1-6"  :to="{name:'AssoIndex'}">
            <Icon type="ios-navigate"></Icon>
            <span>社团信息管理</span>
        </MenuItem>
    </div>
</template>

<script>
    export default {
        name: "sysside",
        computed: {}
    }
</script>

<style scoped>

    .menu-item span {
        display: inline-block;
        overflow: hidden;
        width: 100px;
        text-overflow: ellipsis;
        white-space: nowrap;
        vertical-align: bottom;
        transition: width .2s ease .2s;
    }

    .menu-item i {
        transform: translateX(0px);
        transition: font-size .2s ease, transform .2s ease;
        vertical-align: middle;
        font-size: 16px;
    }

    .collapsed-menu span {
        width: 0px;
        transition: width .2s ease;
    }

    .collapsed-menu i {
        transform: translateX(5px);
        transition: font-size .2s ease .2s, transform .2s ease .2s;
        vertical-align: middle;
        font-size: 22px;
    }
</style>
