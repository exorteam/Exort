<template>
  <div class="signuptable">
    <Row>
      <Col offset="4" span="16">
        <Card style="padding-top: 20px">
          <h3 class="box-title m-b-0">Sign Up to Exort</h3>
          <Divider></Divider>
          <Form ref="formCustom" :model="formCustom" :rules="ruleCustom" :label-width="80">
            <FormItem label="Name" prop="name">
              <Input type="text" placeholder="Name" v-model="formCustom.name"></Input>
            </FormItem>
            <FormItem label="Password" prop="passwd">
              <Input type="password" placeholder="Password" v-model="formCustom.passwd"></Input>
            </FormItem>
            <FormItem label="Confirm" prop="passwdCheck">
              <Input type="password" placeholder="Conform Password" v-model="formCustom.passwdCheck"></Input>
            </FormItem>
            <FormItem style="text-align: center;">
              <Button type="primary" @click="handleSubmit('formCustom')" style="margin-left: -60px">Submit</Button>
              <Button @click="handleReset('formCustom')" style="margin-left: 8px">Reset</Button>
            </FormItem>
          </Form>
          <div class="form-group m-b-0">
            <div class="col-sm-12 text-center">
              <p>Already have an account?
                <router-link class="text-danger m-l-5" :to="{name:'SignIn'}"><b>Sign In</b></router-link>
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
    name: "SignUpTable",
    data() {
      const validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('Please enter your password'));
        } else {
          if (this.formCustom.passwdCheck !== '') {
            this.$refs.formCustom.validateField('passwdCheck');
          }
          callback();
        }
      };
      const validatePassCheck = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('Please enter your password again'));
        } else if (value !== this.formCustom.passwd) {
          callback(new Error('The two input passwords do not match!'));
        } else {
          callback();
        }
      };

      return {
        formCustom: {
          passwd: '',
          passwdCheck: '',
          name: '',
          mail: '',
        },
        ruleCustom: {
          passwd: [
            {required: true, validator: validatePass, trigger: 'blur'}
          ],
          passwdCheck: [
            {required: true, validator: validatePassCheck, trigger: 'blur'}
          ],
          name: [
            {required: true, message: 'The name cannot be empty', trigger: 'blur'}
          ],
          mail: [
            {required: true, message: 'Mailbox cannot be empty', trigger: 'blur'},
            {type: 'email', message: 'Incorrect email format', trigger: 'blur'}
          ],
        }
      }
    },
    methods: {
      createUser: function () {
        var that=this;

        this.axios({
          method:'post',
          url:'http://localhost:8080/register',
          data:{
			  'username':this.formCustom.name,
			  'password':this.formCustom.passwd
		  }
        }).then( (res) => {

		  this.$Message.info(res.data);
          // this.$Message.success('Success!');
          setTimeout(function () {

            that.$router.push({name: "SignIn"});

          }.bind(this), 1000)


        }).catch( (error) =>{
		  this.$Message.error('Fail!');
          console.log(error);
        });
      },
      handleSubmit(name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            this.createUser();
          } else {
            // this.$Message.error('Fail!');
          }
        })
      },
      handleReset(name) {
        this.$refs[name].resetFields();
      }
    }
  }
</script>

<style scoped>
  .signuptable {
    margin-top: 50px;
  }
</style>
