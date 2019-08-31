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
                data3: [],
                targetKeys3: [],
                listStyle: {
                    width: '250px',
                    height: '300px'
                }
            }
        },
        methods: {
            getParentPermList() {
                let url = '/associations/' + this.nodedata.department.associationId + "/departments/" + this.nodedata.department.parentId + '/permissions';
                this.axios.get(url).then((res) => {
                    this.getMockData(res.data.data);
                });
            },
            getChildPermList() {
                this.axios.get('/associations/' + this.nodedata.department.associationId + "/departments/" + this.nodedata.department.departmentId + "/permissions").then((res) => {
                    this.getTargetKeys(res.data.data);
                });
            },
            getMockData(data) {
                let mockData = [];
                let parent = data;
                for (let i = 0; i < parent.length; i++) {
                    mockData.push({
                        key: parent[i].name,
                        label: parent[i].name,
                        description: parent[i].description,
                    });
                }
                this.data3 = mockData;
            },
            getTargetKeys(data) {
                let target = data;
                // console.log(target);
                let ret = [];
                for (let i = 0; i < target.length; i++) {
                    ret.push(target[i].name);
                }
                this.targetKeys3 = ret;
            },
            handleChange3(newTargetKeys, direction, moveKeys) {
                if(direction==="left"){
                    this.axios({
                        method: "delete",
                        data: moveKeys,
                        url: "/associations/" + this.nodedata.department.associationId + "/departments/" + this.nodedata.department.departmentId + "/permissions"
                    }).then((res) => {
                        if (res.data.data !== null) {
                            this.targetKeys3 = newTargetKeys;
                        }else{
                            this.$Message.error(res.data.message);
                        }
                    });
                }else{
                    this.axios({
                        method: "post",
                        data: moveKeys,
                        url: "/associations/" + this.nodedata.department.associationId + "/departments/" + this.nodedata.department.departmentId + "/permissions"
                    }).then((res) => {
                        if (res.data.data !== null) {
                            this.targetKeys3 = newTargetKeys;
                        }else{
                            this.$Message.error(res.data.message);
                        }
                    });
                }
            },
            render3(item) {
                return item.label;
            }
        },
        mounted() {
            this.getParentPermList();
            this.getChildPermList();
        }
    }
</script>
