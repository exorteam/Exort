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
    import { mapActions} from 'vuex'

    export default {
        props: ['root', 'nodedata','nodePerm'],
        data() {
            return {}
        },
        methods: {
            handleChange3(newTargetKeys, direction, moveKeys) {
                if (direction === "left") {
                    this.$parent.deleteTargetkeys({
                        nodedata: this.nodedata,
                        data: moveKeys
                    }).then().catch(error => {
                        this.$Message.error(error);
                    });
                } else {
                    this.$parent.addTargetkeys({
                        nodedata: this.nodedata,
                        data: moveKeys
                    }).then().catch(error => {
                        this.$Message.error(error);
                    });
                }
            },
            render3(item) {
                return item.label;
            },
            // ...mapActions('associationAdmin/assoMem', [
            //     'getParentPermList',
            //     'getChildPermList',
            //     'deleteTargetkeys',
            //     'addTargetkeys',
            // ])
        },
        mounted() {

        }
    }
</script>
