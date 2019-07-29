<template>
    <div id="MemManage">
        <Row>
            <Col span="8">
                <Tree :data="$store.state.association.tree" :render="renderContent"></Tree>
            </Col>
            <Col span="16">
                <Row>
                    <div>
                        <Card>
                            <p><strong>部门名称</strong></p>
                            <hr/>
                            <p>{{this.specdept.departmentName}}</p>
                            <br/>
                            <p><strong>部门描述</strong></p>
                            <hr/>
                            <p>{{this.specdept.departmentDesc}}</p>
                            <br/>
                            <Button @click="editDepartment()">编辑部门信息</Button>
                        </Card>
                    </div>
                </Row>
                <br/>
                <Row>
                    <Table border ref="selection" :columns="columns" :data="rows">
                        <template slot-scope="{ row }" slot="name">
                            <strong>{{ row.name }}</strong>
                        </template>
                        <template slot-scope="{ row, index }" slot="action">
                            <Button type="primary" size="small" style="margin-right: 5px" @click="show(index)">查看
                            </Button>
                            <Button type="error" size="small" @click="remove(index)">删除</Button>
                        </template>
                    </Table>
                    <br/>
                </Row>
                <Row>
                    <Button size="large" @click="addUser">添加</Button>
                </Row>
            </Col>
        </Row>
    </div>
</template>

<script>
    import store from "../../store";

    export default {
        name: "MemManage",
        data() {
            return {
                departmentName: '',
                description: '',
                buttonProps: {
                    type: 'default',
                    size: 'small',
                },
                columns: [
                    {
                        title: 'UserId',
                        slot: 'userid',
                        key: 'id'
                    },
                    {
                        title: 'Name',
                        slot: 'name'
                    },
                    // {
                    //   title: 'Age',
                    //   key: 'age'
                    // },
                    // {
                    //   title: 'Address',
                    //   key: 'address'
                    // },
                    {
                        title: 'Action',
                        slot: 'action',
                        width: 150,
                        align: 'center'
                    }
                ],
                rows: [],
                specdept: {
                    associationId: null,
                    departmentId: null,
                    departmentName: "",
                    departmentDesc: ""
                },
                addUserInfo: {
                    userId: null,
                }
            }
        },
        methods: {
            editDepartment() {
                let url = "";

                this.$Modal.confirm({
                    render: (h) => {
                        return h('div', [
                            h("strong", "名称:"),
                            h('Input', {
                                props: {
                                    value: this.specdept.departmentName,
                                    autofocus: true,
                                    placeholder: '请输入部门名称...'
                                },
                                on: {
                                    input: (val) => {
                                        this.specdept.departmentName = val;
                                    }
                                }
                            }),
                            h("strong", "描述:"),
                            h('Input', {
                                props: {
                                    value: this.specdept.departmentDesc,
                                    autofocus: true,
                                    placeholder: '请输入部门描述...'
                                },
                                on: {
                                    input: (val) => {
                                        this.specdept.departmentDesc = val;
                                    }
                                }
                            }),
                        ])
                    },
                    onOk: () => {
                        // console.log(this.specdept);
                        this.axios({
                            method: "post",
                            data: this.specdept,
                            url: url
                        }).then((res) => {

                        })
                    }
                });
            },
            addUser() {
                this.$Modal.confirm({
                    render: (h) => {
                        return h('div', [
                            h("strong", "UserID:"),
                            h('Input', {
                                props: {
                                    value: this.addUserInfo.userId,
                                    autofocus: true,
                                    placeholder: '请输入用户ID...'
                                },
                                on: {
                                    input: (val) => {
                                        this.addUserInfo.userId = val;
                                    }
                                }
                            }),
                        ])
                    },
                    onOk: () => {
                        // console.log(data)

                    }
                });
            },
            gettree() {
                let url = "http://localhost:8900/associations/68/departments";
                this.axios.get(url).then((res) => {
                    this.$store.commit("set_departments", res.data.data)
                }).catch((error) => {
                    console.log(error);
                    this.$Message.error("社团不存在");
                })
            },

            getDepartmentUsers(associationId, departmentId) {
                let url = "http://localhost:8900/associations/" + associationId + "/departments/" + departmentId + "/members";
                this.axios.get(url).then((res) => {
                    // console.log(res.data.data);
                    // console.log(this.rows);
                    this.rows = res.data.data;
                    // console.log(this.rows);
                }).catch((error) => {
                    console.log(error.response.status);
                })
            },

            renderContent(h, {root, node, data}) {
                // console.log(data);
                switch (data.department.departmentId) {
                    case 1:
                        return h(
                            'span',
                            {
                                style: {
                                    display: 'inline-block',
                                    width: '100%'
                                }
                            },
                            [
                                h('span', [
                                    h('Icon', {
                                        props: {
                                            type: 'ios-folder-outline'
                                        },
                                        style: {
                                            marginRight: '8px'
                                        }
                                    }),
                                    h('span', data.title)
                                ]),
                                h('span', {
                                    style: {
                                        display: 'inline-block',
                                        float: 'right',
                                        marginRight: '32px'
                                    }
                                }, [
                                    h('Button', {
                                        props: Object.assign({}, this.buttonProps, {
                                            icon: 'ios-add',
                                            type: 'primary'
                                        }),
                                        style: {
                                            width: '64px'
                                        },
                                        on: {
                                            click: () => {
                                                this.append(root, data)
                                            }
                                        }
                                    })
                                ])
                            ]
                        );
                    case 2:
                    case 0:
                        return h(
                            'span', {
                                style: {
                                    display: 'inline-block',
                                    width: '100%'
                                }
                            }, [h('span', [
                                h('Icon', {
                                    props: {
                                        type: 'ios-paper-outline'
                                    },
                                    style: {
                                        marginRight: '8px'
                                    }
                                }),
                                // 添加点击动作
                                h('a', {
                                        on: {
                                            click: () => {
                                                this.getDepartmentInfo(data.department.associationId, data.department.departmentId);
                                                this.getDepartmentUsers(data.department.associationId, data.department.departmentId);
                                            }
                                        },
                                    },
                                    data.title
                                )
                            ])]
                        );
                    default:
                        return h('span', {
                            style: {
                                display: 'inline-block',
                                width: '100%'
                            }
                        }, [
                            h('span', [
                                h('Icon', {
                                    props: {
                                        type: 'ios-paper-outline'
                                    },
                                    style: {
                                        marginRight: '8px'
                                    }
                                }),
                                // 添加点击动作
                                h('a', {
                                        on: {
                                            click: () => {
                                                // this.$router.go(0);
                                                this.getDepartmentUsers(data.department.associationId, data.department.departmentId);
                                            }
                                        },
                                    },
                                    data.title
                                )
                            ]),
                            h('span', {
                                style: {
                                    display: 'inline-block',
                                    float: 'right',
                                    marginRight: '32px'
                                }
                            }, [
                                h('Button', {
                                    props: Object.assign({}, this.buttonProps, {
                                        icon: 'ios-add'
                                    }),
                                    style: {
                                        marginRight: '8px'
                                    },
                                    on: {
                                        click: () => {
                                            this.append(root, data);
                                            // console.log(node);
                                            // console.log(root)
                                        }
                                    }
                                }),
                                h('Button', {
                                    props: Object.assign({}, this.buttonProps, {
                                        icon: 'ios-remove'
                                    }),
                                    on: {
                                        click: () => {
                                            this.removeNode(root, node, data)
                                        }
                                    }
                                })
                            ])
                        ]);
                }

            },
            append(root, data) {
                this.$Modal.confirm({
                    render: (h) => {
                        return h('div', [
                            h("strong", "Name:"),
                            h('Input', {
                                props: {
                                    value: this.departmentName,
                                    autofocus: true,
                                    placeholder: 'Please enter the department name...'
                                },
                                on: {
                                    input: (val) => {
                                        this.departmentName = val;
                                    }
                                }
                            }),
                            h('strong', "Description:"),
                            h('Input', {
                                props: {
                                    value: this.description,
                                    autofocus: true,
                                    placeholder: 'Please enter the department description...'
                                },
                                on: {
                                    input: (val) => {
                                        this.description = val;
                                    }
                                }
                            }),
                        ])
                    },
                    onOk: () => {
                        // console.log(data)

                        let url = "http://localhost:8900/associations/" + data.department.associationId + "/departments";
                        let departinfo = {
                            "name": this.departmentName,
                            "description": this.description,
                            "parentId": data.department.departmentId
                        };
                        // console.log(departinfo);
                        this.axios({
                            method: 'post',
                            url: url,
                            data: departinfo
                        }).then((res) => {
                            this.departmentName = "";
                            this.description = "";
                            this.gettree();
                        })
                    }
                });

            },
            getDepartmentInfo(associationId, departmentId) {
                let url = "http://localhost:8900/associations/" + associationId + "/departments/" + departmentId;
                this.axios.get(url).then((res) => {
                    let department = res.data.data;
                    this.specdept.associationId = associationId;
                    this.specdept.departmentId = departmentId;
                    this.specdept.departmentName = department.name;
                    this.specdept.departmentDesc = department.description;
                })
            },
            removeNode(root, node, data) {
                this.$Modal.confirm({
                    render: (h) => {
                        return h("strong", "确认删除部门吗?")
                    },
                    onOk: () => {
                        let url = "http://localhost:8900/associations/" + data.department.associationId + "/departments/" + data.department.departmentId;

                        this.axios({
                            method: 'delete',
                            url: url
                        }).then((res) => {

                            this.gettree();
                        })
                    }
                })
            },
            show(index) {
                this.$Modal.info({
                    title: 'User Info',
                    content: `Name：${this.rows[index].name}<br>Age：${this.rows[index].age}<br>Address：${this.rows[index].address}`
                })
            },
            remove(index) {
                this.rows.splice(index, 1);
            }
        },
        mounted() {
            this.gettree();
        }
    }
</script>

<style>

</style>
