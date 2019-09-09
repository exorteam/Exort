<template>
    <div id="MemManage">
        <Card>
            <Row>
                <Col span="8">
                    <Tree :data="this.tree" :render="renderContent"></Tree>
                </Col>
                <Col span="16">
                    <Row>
                        <div>
                            <Card>
                                <p><strong>部门名称</strong></p>
                                <hr/>
                                <p>{{this.specdept.name}}</p>
                                <br/>
                                <p><strong>部门描述</strong></p>
                                <hr/>
                                <p>{{this.specdept.description}}</p>
                                <br/>
                                <Button @click="editDepartment()">编辑部门信息</Button>
                            </Card>
                        </div>
                    </Row>
                    <br/>
                    <Row>
                        <Table border ref="selection" :columns="columns" :data="this.deptUserList">
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
        </Card>
        <Modal v-model="modal1" style="width:700px">
            <Transfer
                    :data="this.nodePerm.data"
                    :target-keys="this.nodePerm.targetKeys"
                    :list-style="this.nodePerm.lifeStyle"
                    :render-format="render3"
                    filterable
                    @on-change="handleChange3">
            </Transfer>
        </Modal>
    </div>
</template>

<script>
    import {mapActions, mapState, mapMutations} from "vuex"

    export default {
        name: "MemManage",
        data() {
            return {
                modal1: false,
                nodedata:null,
                departmentName: '',
                description: '',
                buttonProps: {
                    type: 'default',
                    size: 'small',
                },
                columns: [
                    {
                        title: 'UserId',
                        key: 'id',
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
                        title: "StudentID",
                        key: "studentId"
                    },
                    {
                        title: "Gender",
                        key: "gender"
                    },
                    {
                        title: "Enabled",
                        key: "enabled"
                    },
                    {
                        title: 'Action',
                        slot: 'action',
                        width: 150,
                        align: 'center'
                    }
                ],
                addUserInfo: {
                    userId: null,
                }
            }
        },
        computed: {
            ...mapState("associationAdmin/assoMem", [
                'deptUserList',
                'departments',
                'tree',
                'specdept',
                'nodePerm'
            ])
        },
        methods: {
            ...mapMutations("associationAdmin/assoMem", [
                'setSpecDept'
            ]),
            ...mapActions("associationAdmin/assoMem", [
                'getDepartmentInfo',
                'editDeptInfo',
                'removeDept',
                'createDept',
                'gettree',
                'getDepartmentUsers',
                'addDeptUser',
                'deleteDeptUser',
                'getParentPermList',
                'getChildPermList',
                'deleteTargetkeys',
                'addTargetkeys'
            ]),
            editDepartment() {
                let url = "/associations/" + this.specdept.associationId + "/departments/" + this.specdept.departmentId;

                this.$Modal.confirm({
                    render: (h) => {
                        return h('div', [
                            h("strong", "名称:"),
                            h('Input', {
                                props: {
                                    value: this.specdept.name,
                                    autofocus: true,
                                    placeholder: '请输入部门名称...'
                                },
                                on: {
                                    input: (val) => {
                                        this.setSpecDept({
                                            associationId: null,
                                            departmentId: null,
                                            name: val,
                                            description: null,
                                            parentId: null
                                        });
                                    }
                                }
                            }),
                            h("strong", "描述:"),
                            h('Input', {
                                props: {
                                    value: this.specdept.description,
                                    autofocus: true,
                                    placeholder: '请输入部门描述...'
                                },
                                on: {
                                    input: (val) => {
                                        this.setSpecDept({
                                            associationId: null,
                                            departmentId: null,
                                            name: null,
                                            description: val,
                                            parentId: null
                                        });
                                    }
                                }
                            }),
                        ])
                    },
                    onOk: () => {
                        let upload = this.specdept;
                        if (upload.name === null || upload.name === undefined || upload.name === "") {
                            this.$Message.error("部门名称不能为空");
                        } else {
                            this.editDeptInfo({url: url, data: upload}).then(() => {
                                this.$Message.info("修改成功!");
                            }).catch(error => {
                                this.$Message.error(error);
                            });
                        }
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
                        if (this.addUserInfo.userId === null || this.addUserInfo.userId === undefined || this.addUserInfo.userId === "") {
                            this.$Message.error("输入不能为空!");
                        } else {
                            this.addDeptUser(this.addUserInfo.userId).then(()=>{
                                this.$Message.info("添加成功！");
                            }).catch(error=>{
                                this.$Message.error(error);
                            });
                        }
                    }
                });
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
                                        },
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
                                                // console.log("off");
                                                this.getDepartmentInfo({
                                                    associationId: data.department.associationId,
                                                    departmentId: data.department.departmentId
                                                });
                                                this.getDepartmentUsers({
                                                    associationId: data.department.associationId,
                                                    departmentId: data.department.departmentId
                                                });
                                                console.log(this.deptUserList);
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
                                                // console.log("off");
                                                this.getDepartmentInfo({
                                                    associationId: data.department.associationId,
                                                    departmentId: data.department.departmentId
                                                });
                                                this.getDepartmentUsers({
                                                    associationId: data.department.associationId,
                                                    departmentId: data.department.departmentId
                                                });
                                                console.log(this.deptUserList);
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
                            }, [h('Button', {
                                props: Object.assign({}, this.buttonProps, {
                                    icon: 'ios-hand'
                                }),
                                style: {
                                    marginRight: '8px'
                                },
                                on: {
                                    click: () => {
                                        this.modal1=true;
                                        this.nodedata=data;
                                        this.getParentPermList(data);
                                        this.getChildPermList(data);
                                    }
                                }
                            }),
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
            handleChange3(newTargetKeys, direction, moveKeys) {
                if (direction === "left") {
                    this.deleteTargetkeys({
                        nodedata: this.nodedata,
                        data: moveKeys
                    }).then().catch(error => {
                        this.$Message.error(error);
                    });
                } else {
                    this.addTargetkeys({
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
                        if (this.departmentName === null || this.departmentName === undefined || this.departmentName === "") {
                            this.$Message.error("部门名称不能为空");
                        } else {
                            let thisAssociationId = data.department.associationId;

                            this.createDept({
                                associationId: thisAssociationId,
                                name: this.departmentName,
                                description: this.description,
                                parentId: data.department.departmentId
                            }).then((res) => {
                                // console.log(res);
                                this.$Message.info(res);
                                this.departmentName = "";
                                this.description = "";
                            }).catch(error => {
                                this.$Message.error(error);
                            });

                        }
                    }
                });

            },
            removeNode(root, node, data) {
                this.$Modal.confirm({
                    render: (h) => {
                        return h("strong", "确认删除部门吗?")
                    },
                    onOk: () => {
                        this.removeDept(data).then(() => {
                            this.$Message.info("删除成功！");
                        }).catch(error => {
                            this.$Message.error(error);
                        });
                    }
                })
            },
            show(index) {
                this.$Modal.info({
                    title: 'User Info',
                    content: `id：${this.deptUserList[index].id}
                            <br>nickname：${this.deptUserList[index].nickname}
                            <br>description：${this.deptUserList[index].description}
                            <br>gender：${this.deptUserList[index].gender}
                            <br>birthday：${this.deptUserList[index].birthday}
                            <br>name：${this.deptUserList[index].name}
                            <br>studentId：${this.deptUserList[index].studentId}
                            <br>phone：${this.deptUserList[index].phone}
                            <br>qqId：${this.deptUserList[index].qqId}
                            <br>wechatId：${this.deptUserList[index].wechatId}
                            <br>email：${this.deptUserList[index].email}
                            <br>enabled：${this.deptUserList[index].enabled}`
                })
            },
            remove(index) {
                this.$Modal.confirm({
                    render: (h) => {
                        return h("strong", "确认在部门中移除此用户吗?")
                    },
                    onOk: () => {
                        this.deleteDeptUser(index).then(()=>{
                            this.$Message.info("移除成功！");
                        }).catch(error=>{
                            this.$Message.error(error);
                        });
                    }
                })
            }
        },
        mounted() {
            this.gettree(this.$route.params.assoId).then().catch(error => {
                this.$Message.error(error);
                // console.log(error);
            });
        }
    }
</script>

<style>

</style>
