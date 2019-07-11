<template>
  <div class="signIn">
      <Layout :style="{minHeight: '100vh'}">
        <Header :style="{background: '#fff', boxShadow: '0 2px 3px 2px rgba(0,0,0,.1)'}"></Header>
        <Content :style="{padding: '0 16px 16px'}">
          <sign-in-table></sign-in-table>
        </Content>
      </Layout>
  </div>
</template>

<script>
  import SignInTable from './signin_table'

  export default {
    name:"signIn",
    components:{SignInTable},
    // data () {
    // },
  }
</script>

<style scoped>
  .layout-con{
    height: 100%;
    width: 100%;
  }
  .menu-item span{
    display: inline-block;
    overflow: hidden;
    width: 69px;
    text-overflow: ellipsis;
    white-space: nowrap;
    vertical-align: bottom;
    transition: width .2s ease .2s;
  }
  .menu-item i{
    transform: translateX(0px);
    transition: font-size .2s ease, transform .2s ease;
    vertical-align: middle;
    font-size: 16px;
  }
</style>
