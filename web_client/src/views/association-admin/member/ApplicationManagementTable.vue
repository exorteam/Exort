<template>
    <div id="AppliManage">
        <Row>
            <Col offset="16" span="8">
                <div class="input-group">
                    <input v-model="searchInput" type="text" class="form-control" placeholder="Search...">
                    <b-button-group class="input-group-btn">
                        <b-button>
                            <Icon @click="search()" type="ios-search"/>
                        </b-button>
                    </b-button-group>
                </div>
            </Col>
        </Row>
        <br/>
        <Row>
            <Table border ref="selection" :columns="columns" :data="rows">
                <template slot-scope="{ row }" slot="name">
                    <strong>{{ row.name }}</strong>
                </template>
                <template slot-scope="{ row, index }" slot="action">
                    <Button type="primary" size="small" style="margin-right: 5px" @click="show(index)">View</Button>
                    <Button type="error" size="small" @click="remove(index)">Delete</Button>
                </template>
            </Table>
        </Row>
        <br/>
        <Row>
            <Page style="float: right" :total="100" show-total/>
        </Row>
    </div>
</template>

<script>
    export default {
        name: 'AppliManage',
        data() {
            return {
                searchInput: "",
                columns: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center'
                    },
                    {
                        title: 'Name',
                        key: 'name'
                    },
                    {
                        title: 'Age',
                        key: 'age'
                    },
                    {
                        title: 'Address',
                        key: 'address'
                    },
                    {
                        title: 'Action',
                        slot: 'action',
                        align: 'center'
                    }
                ],
                rows: [
                    {
                        name: 'John Brown',
                        age: 18,
                        address: 'New York No. 1 Lake Park',
                        date: '2016-10-03'
                    },
                    {
                        name: 'Jim Green',
                        age: 24,
                        address: 'London No. 1 Lake Park',
                        date: '2016-10-01'
                    },
                    {
                        name: 'Joe Black',
                        age: 30,
                        address: 'Sydney No. 1 Lake Park',
                        date: '2016-10-02'
                    },
                    {
                        name: 'Jon Snow',
                        age: 26,
                        address: 'Ottawa No. 2 Lake Park',
                        date: '2016-10-04'
                    }
                ]
            }
        },
        methods: {
            search(){

            },
            getApplicationInfos() {
                this.axios.get().then((res) => {
                    this.rows = res.data;
                })
            },
            deleteAppli(index) {
                this.rows[index].id;
                let deletedata = {};

                this.axios({
                    method: 'post',
                    url: '',
                    data: deletedata,
                }).then((res) => {

                }).catch((error) => {

                });


            },

            show(index) {
                this.$Modal.info({
                    title: 'User Info',
                    content: `Name：${this.rows[index].name}<br>Age：${this.rows[index].age}<br>Address：${this.rows[index].address}`
                });
                this.$Modal.confirm({
                    title: 'Title',
                    content: `Name：${this.rows[index].name}<br>Age：${this.rows[index].age}<br>Address：${this.rows[index].address}`,
                    okText: 'Accept',
                    cancelText: 'Cancel',
                    onOk: () => {
                        this.$Message.info('Accept');
                    },
                    onCancel: () => {
                        this.$Message.info('Clicked cancel');
                    }
                });
            },
            remove(index) {
                this.rows.splice(index, 1);
                // this.deleteAppli(index);
            }
        },
        computed: {
            filterData: {
                get: function () {
                    let searchInput = this.searchInput && this.searchInput.toLowerCase();
                    let rows = this.rows;
                    let items1;
                    if (searchInput) {
                        items1 = rows.filter(function (item) {
                            return Object.keys(item).some(function (key) {
                                return String(item[key]).toLowerCase().match(searchInput)
                            })
                        })
                    } else {
                        items1 = this.rows;
                    }
                    return items1;
                }
            }
        }

    }
</script>

<style>

</style>
