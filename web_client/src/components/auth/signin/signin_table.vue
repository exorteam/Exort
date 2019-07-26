<template>
  <div class="signintable">
    <Row>
      <Col offset="4" span="16">
        <Card style="padding-top: 20px">
          <h3 class="box-title m-b-0">Welcome to Exort</h3>
          <Divider></Divider>
          <Form ref="formCustom" :model="formCustom" :rules="ruleCustom" :label-width="80">
            <FormItem label="Name" prop="name">
              <Input type="text" placeholder="Name" v-model="formCustom.name"></Input>
            </FormItem>
            <FormItem label="Password" prop="passwd">
              <Input type="password" placeholder="Password" v-model="formCustom.passwd"></Input>
            </FormItem>
            <FormItem style="text-align: center;">
              <Button type="primary" @click="handleSubmit('formCustom')" style="margin-left: -60px">Sign In</Button>
              <Button @click="handleReset('formCustom')" style="margin-left: 8px">Reset</Button>
            </FormItem>
          </Form>
          <div class="form-group m-b-0">
            <div class="col-sm-12 text-center">
              <p>Does not have an account?
                <router-link class="text-danger m-l-5" :to="{name:'SignUp'}"><b>Sign Up</b></router-link>
              </p>
            </div>
          </div>
        </Card>
      </Col>
    </Row>
  </div>
</template>

<script>

export default {
	name: "SignInTable",
	data() {
		const validatePass = (rule, value, callback) => {
			if (value === '') {
				callback(new Error('Please enter your password'));
			} else {
				callback();
			}
		};
		return {
			formCustom: {
				passwd: '',
				name: '',
			},
			ruleCustom: {
				passwd: [
					{required: true, validator: validatePass, trigger: 'blur'}
				],
				name: [
					{required: true, message: 'The name cannot be empty', trigger: 'blur'}
				],
			}
		}
	},
	methods: {
		validateToken:function(){
			const token = window.localStorage.getItem('token');
			this.axios({
				method:'post',
				url:'/auth',
				data:token
			}).then((res)=>{
				console.log(res.data);
				if(res.data.id){
					this.axios({
						method:'get',
						url:'/perm/admin',
					}).then((res)=>{
						console.log(res.data);
					})
				}
			})
		},
		findUser(){
			const that = this;
			this.axios({
				method:'post',
				url:'/login',
				data:{
					'username':this.formCustom.name,
					'password':this.formCustom.passwd,
				}
			}).then((res)=>{
				console.log(res);
				if(res.data.token){
					window.localStorage.setItem('token',res.data.token);
					this.axios.defaults.headers.common.Authorization = 'Bearer ' + res.data.token;
					that.axios({
						method: 'post',
						url: '/auth',
						data: res.data.token
					}).then((res)=>{
						that.$Message.success("Login success, " + res.data.username);
						this.$router.push('admin');
					})
				}

			}).catch(function (error) {
				that.$Message.error('Fail!');
				console.log(error);
			});

		},
		handleSubmit(name) {
			this.$refs[name].validate((valid) => {
				if (valid) {
					this.findUser();
				} else {
					this.$Message.error('Fail!');
				}
			})
		},
		handleReset(name) {
			this.$refs[name].resetFields();
		}
	},
	mounted:function(){
		this.validateToken();
	}
}
</script>

<style scoped>
  .signintable {
    margin-top: 50px;
  }
</style>
