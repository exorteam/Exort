<template>
    <Transfer
            :data="this.nodePerm.data"
            :target-keys="this.nodePerm.targetKeys"
            :list-style="this.nodePerm.lifeStyle"
            :render-format="render3"
            filterable
            @on-change="handleChange3">
    </Transfer>
</template>

<script>
    import {mapState, mapActions} from 'vuex'

    export default {
        props: ['root', 'nodedata'],
        data() {
            return {}
        },
        computed: {
            ...mapState('associationAdmin/assoMem', [
                'nodePerm'
            ])
        },
        methods: {
            handleChange3(newTargetKeys, direction, moveKeys) {
                if (direction === "left") {
                    this.deleteTargetkeys({
                        nodedata: this.nodedata,
                        data: moveKeys
                    });
                } else {
                    this.addTargetkeys({
                        nodedata: this.nodedata,
                        data: moveKeys
                    });
                }
            },
            render3(item) {
                return item.label;
            },
            ...mapActions('associationAdmin/assoMem', [
                'getParentPermList',
                'getChildPermList',
                'deleteTargetkeys',
                'addTargetkeys',
            ])
        },
        mounted() {
            this.getParentPermList();
            this.getChildPermList();
        }
    }
</script>
