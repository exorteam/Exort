<template>
    <Transfer
        :data="data3"
        :target-keys="targetKeys3"
        :list-style="listStyle"
        :render-format="render3"
        filterable
        @on-change="handleChange3">
    </Transfer>
</template>

<script>
    export default {
        props: ['root', 'nodedata'],
        data() {
            return {
                // modal1: false
                data3: this.getMockData(),
                targetKeys3: this.getTargetKeys(),
                listStyle: {
                    width: '250px',
                    height: '300px'
                }
            }
        },
        methods: {
            getParentPermList() {
                let ret = [];
                this.axios.get('/associations/' + this.nodedata.department.associationId + "/departments/" + this.nodedata.department.parentId + "/permissions").then((res) => {
                    ret = res.data.data;
                });
                return ret;
            },
            getChildPermList() {
                let ret = [];
                this.axios.get('/associations/' + this.nodedata.department.associationId + "/departments/" + this.nodedata.department.departmentId + "/permissions").then((res) => {
                    ret = res.data.data;
                });
                return ret;
            },
            getMockData() {
                let mockData = [];
                let parent = this.getParentPermList();
                for (let i = 0; i < parent.length + 20; i++) {
                    mockData.push({
                        key: i.toString(),
                        label: i.toString(),
                        description: i.toString(),
                    });
                }
                return mockData;
            },
            getTargetKeys() {
                let target = this.getChildPermList();

                return ["0", "2"];
            },
            handleChange3(newTargetKeys) {
                console.log(newTargetKeys);
                this.targetKeys3 = newTargetKeys;
                this.axios({
                    method: "post",
                    data: newTargetKeys,
                    url: "/associations/" + this.nodedata.department.associationId + "/departments/" + this.nodedata.department.departmentId + "/permissions"
                }).then((res) => {

                });
            },
            render3(item) {
                return item.label + ' - ' + item.description;
            }
        },
        mounted() {
        }
    }
</script>
