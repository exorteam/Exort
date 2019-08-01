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
    import AssoMemPerm from "./asso_mem_perm"

    export default {
        name: "MemManage",
        components: {AssoMemPerm},
        data() {
            return {
                modal1: false,
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
                rows: [],
                specdept: {
                    associationId: null,
                    departmentId: null,
                    name: null,
                    description: null,
                    parentId: null
                },
                addUserInfo: {
                    userId: null,
                }
            }
        },
        methods: {
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
                                        this.specdept.name = val;
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
                                        this.specdept.description = val;
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
                            this.axios({
                                method: "put",
                                data: upload,
                                url: url,
                            }).then((res) => {
                                if (res.data.data) {
                                    this.$Message.info("修改成功！");
                                    this.gettree();
                                } else {
                                    this.$Message.error("修改失败！" + res.data.message);
                                }
                            })
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
                            this.axios({
                                method: 'post',
                                url: "/associations/" + this.specdept.associationId + "/departments/" + this.specdept.departmentId + "/members",
                                data: this.addUserInfo
                            }).then((res) => {
                                if (res.data.data === true) {
                                    this.getDepartmentUsers(this.specdept.associationId, this.specdept.departmentId);
                                    this.$Message.info("添加成功！");
                                } else {
                                    this.$Message.error("添加失败！" + res.data.message);
                                }
                            })
                        }
                    }
                });
            },
            gettree() {
                let url = "/associations/1/departments";
                this.axios.get(url).then((res) => {
                    if (res.data.data === null) {
                        this.$Message.error("社团不存在");
                    } else {
                        this.$store.commit("set_departments", res.data.data)
                    }
                }).catch((error) => {
                    console.log(error);
                    this.$Message.error("社团不存在");
                })
            },
            getDepartmentUsers(associationId, departmentId) {
                let url = "/associations/" + associationId + "/departments/" + departmentId + "/members";
                this.axios.get(url).then((res) => {
                    let ret = [];
                    let retdata = [];
                    retdata = res.data.data;
                    for (let i = 0; i < retdata.length; i++) {
                        ret.push(retdata[i])
                    }

                    // console.log(ret);
                    this.axios({
                        method: "post",
                        url: "/users",
                        data: ret,
                    }).then((res) => {
                        // console.log(res.data);
                        this.rows=res.data;
                    })
                    // console.log(this.rows);
                    // this.rows = res.data.data;
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
                                                // console.log("off");
                                                this.getDepartmentInfo(data.department.associationId, data.department.departmentId);
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
                            }, [h('Button', {
                                props: Object.assign({}, this.buttonProps, {
                                    icon: 'ios-hand'
                                }),
                                style: {
                                    marginRight: '8px'
                                },
                                on: {
                                    click: () => {
                                        this.perm(root, data);
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
            perm(root, data) {
                this.$Modal.confirm({
                    width: "700px",
                    render: (h) => {
                        return h(AssoMemPerm, {
                            props: {
                                root: root,
                                nodedata: data
                            }
                        });
                    }
                })
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
                            let url = "/associations/" + data.department.associationId + "/departments";
                            let departinfo = {
                                "name": this.departmentName,
                                "description": this.description,
                                "parentId": data.department.departmentId
                            };
                            this.axios({
                                method: 'post',
                                url: url,
                                data: departinfo
                            }).then((res) => {
                                if (res.data.data !== null) {
                                    this.departmentName = "";
                                    this.description = "";

                                    this.$Message.info("添加成功！");
                                } else {
                                    this.$Message.error("添加失败！" + res.data.message);
                                }
                                this.gettree();
                            })
                        }
                    }
                });

            },
            getDepartmentInfo(associationId, departmentId) {
                let url = "/associations/" + associationId + "/departments/" + departmentId;
                // console.log(associationId+"+"+departmentId)
                this.axios.get(url).then((res) => {
                    let department = res.data.data;
                    // console.log(department)
                    this.specdept.associationId = associationId;
                    this.specdept.departmentId = departmentId;
                    this.specdept.name = department.name;
                    this.specdept.description = department.description;
                    this.specdept.parentId = department.parentId;
                })
            },
            removeNode(root, node, data) {
                this.$Modal.confirm({
                    render: (h) => {
                        return h("strong", "确认删除部门吗?")
                    },
                    onOk: () => {
                        let url = "/associations/" + data.department.associationId + "/departments/" + data.department.departmentId;

                        this.axios({
                            method: 'delete',
                            url: url
                        }).then((res) => {
                            this.gettree();
                            this.$Message.info("删除成功！");
                        })
                    }
                })
            },
            show(index) {
                this.$Modal.info({
                    title: 'User Info',
                    content: `id：${this.rows[index].id}
                            <br>nickname：${this.rows[index].nickname}
                            <br>description：${this.rows[index].description}
                            <br>gender：${this.rows[index].gender}
                            <br>birthday：${this.rows[index].birthday}
                            <br>name：${this.rows[index].name}
                            <br>studentId：${this.rows[index].studentId}
                            <br>phone：${this.rows[index].phone}
                            <br>qqId：${this.rows[index].qqId}
                            <br>wechatId：${this.rows[index].wechatId}
                            <br>email：${this.rows[index].email}
                            <br>enabled：${this.rows[index].enabled}`
                })
            },
            remove(index) {
                // this.rows.splice(index, 1);
                this.$Modal.confirm({
                    render: (h) => {
                        return h("strong", "确认在部门中移除此用户吗?")
                    },
                    onOk: () => {
                        let url = "/associations/" + this.specdept.associationId + "/departments/" + this.specdept.departmentId + "/members/" + this.rows[index].id;
                        this.axios({
                            method: 'delete',
                            url: url
                        }).then((res) => {
                            console.log(res);
                            if (res.data.data) {
                                this.getDepartmentUsers(this.specdept.associationId, this.specdept.departmentId);
                                this.$Message.info("移除成功！");
                            }
                            else {
                                this.$Message.error("移除失败!" + res.data.message);
                            }
                        })
                    }
                })
            }
        },
        mounted() {
            this.gettree();
        }
    }
</script>

<style>

</style>
