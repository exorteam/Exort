<template>
    <Table border :columns="columns7" :data="data6"></Table>
</template>
<script>
    export default {
        name:"UserManagement",
        data () {
            return {
                columns7: [
                    {
                        title: 'Id',
                        key: 'id'
                    },
                    {
                        title: 'Username',
                        key: 'username'
                    },
                    {
                        title: 'Type',
                        key: 'type'
                    },
                    {
                        title: 'Action',
                        key: 'action',
                        width: 150,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.show(params.index)
                                        }
                                    }
                                }, 'change'),
                                h('Button', {
                                    props: {
                                        type: 'error',
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            this.remove(params.index)
                                        }
                                    }
                                }, 'Delete')
                            ]);
                        }
                    }
                ],
                data6: [
                    
                ]
            }
        },
        methods: {
            getall(){
                this.axios.get("http://localhost:8080/user/all").then((res)=>{
                    // console.log(res.data);
                    this.data6=res.data
                })
            },
            show (index) {
                
                // this.axios.pos
                // console.log(this.data6[index]);
                var a=0;
                if(this.data6[index].type==1){
                    a=0;
                }else{
                    a=1;
                }
                
                let info = {'id':this.data6[index].id,'username': this.data6[index].username, 'password': this.data6[index].password,'type':a};
                
                // var that=this;

                this.axios({
                method:'post',
                url:'http://localhost:8080/user/update',
                data:info
                }).then((res)=>{
                // console.log(res);
                this.getall();

                }).catch(function (error) {
                // this.$Message.error('Fail!');
                console.log(error);
                });



            },
            remove (index) {
                let info =  this.$qs.stringify({'id':this.data6[index].id});
                
                // var that=this;

                this.axios({
                method:'post',
                url:'http://localhost:8080/user/delete',
                data:info
                }).then((res)=>{
                // console.log(res);
                this.getall();

                }).catch(function (error) {
                // this.$Message.error('Fail!');
                console.log(error);
                });
            }
        },
        mounted() {
            this.getall();
        },
    }
</script>
