<template>
<div/>
</template>

<script>
var instances = [];

window.onscroll = function() {
    var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
    var windowHeight = document.documentElement.clientHeight || document.body.clientHeight;
    var scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight;
    if(scrollTop + windowHeight == scrollHeight){
        for (var inst of instances) {
            inst.onScroll();
        }
    }
};

export default {
    methods: {
        onScroll() {
            this.$emit('bottom', true);
        }
    },
    created() {
        instances[instances.length] = this;
    },
    beforeDestroy() {
        instances.splice(instances.findIndex(inst => inst === this), 1);
    }
}
</script>

<style>

</style>
