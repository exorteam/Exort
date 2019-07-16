<template>
  <div id="MemManage">
    <Row>
      <Col span="8">
        <Tree :data="trees" :render="renderContent"></Tree>
      </Col>
      <Col span="16">
        <Table border ref="selection" :columns="columns" :data="rows">
          <template slot-scope="{ row }" slot="name">
            <strong>{{ row.name }}</strong>
          </template>
          <template slot-scope="{ row, index }" slot="action">
            <Button type="primary" size="small" style="margin-right: 5px" @click="show(index)">View</Button>
            <Button type="error" size="small" @click="remove(index)">Delete</Button>
          </template>
        </Table>
      </Col>
    </Row>
  </div>
</template>

<script>
  export default {
    name:"MemManage",
    data () {
      return {
        trees: [
          {
            title: 'parent 1',
            expand: true,
            render: (h, { root, node, data }) => {
              return h('span', {
                style: {
                  display: 'inline-block',
                  width: '100%'
                }
              }, [
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
                      click: () => { this.append(data) }
                    }
                  })
                ])
              ]);
            },
            children: [
              {
                title: 'child 1-1',
                expand: true,
                children: [
                  {
                    title: 'leaf 1-1-1',
                    expand: true
                  },
                  {
                    title: 'leaf 1-1-2',
                    expand: true
                  }
                ]
              },
              {
                title: 'child 1-2',
                expand: true,
                children: [
                  {
                    title: 'leaf 1-2-1',
                    expand: true
                  },
                  {
                    title: 'leaf 1-2-1',
                    expand: true
                  }
                ]
              }
            ]
          }
        ],
        buttonProps: {
          type: 'default',
          size: 'small',
        },
        columns: [
          {
            title: 'Name',
            slot: 'name'
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
            width: 150,
            align: 'center'
          }
        ],
        rows: [
          {
            name: 'John Brown',
            age: 18,
            address: 'New York No. 1 Lake Park'
          },
          {
            name: 'Jim Green',
            age: 24,
            address: 'London No. 1 Lake Park'
          },
          {
            name: 'Joe Black',
            age: 30,
            address: 'Sydney No. 1 Lake Park'
          },
          {
            name: 'Jon Snow',
            age: 26,
            address: 'Ottawa No. 2 Lake Park'
          }
        ]
      }
    },
    methods: {
      renderContent (h, { root, node, data }) {
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
                icon: 'ios-add'
              }),
              style: {
                marginRight: '8px'
              },
              on: {
                click: () => { this.append(data) }
              }
            }),
            h('Button', {
              props: Object.assign({}, this.buttonProps, {
                icon: 'ios-remove'
              }),
              on: {
                click: () => { this.removeNode(root, node, data) }
              }
            })
          ])
        ]);
      },
      append (data) {
        const children = data.children || [];
        children.push({
          title: 'appended node',
          expand: true
        });
        this.$set(data, 'children', children);
      },
      removeNode (root, node, data) {
        const parentKey = root.find(el => el === node).parent;
        const parent = root.find(el => el.nodeKey === parentKey).node;
        const index = parent.children.indexOf(data);
        parent.children.splice(index, 1);
      },
      show (index) {
        this.$Modal.info({
          title: 'User Info',
          content: `Name：${this.rows[index].name}<br>Age：${this.rows[index].age}<br>Address：${this.rows[index].address}`
        })
      },
      remove (index) {
        this.rows.splice(index, 1);
      }
    }
  }
</script>

<style>

</style>
