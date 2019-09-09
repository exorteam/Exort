//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
  },
  onLoad: function() {
    const jwt = wx.getStorageSync('jwt')
    this.validateJwt(jwt)
  },
  login: function(args) {
    const acc = args.detail.value
    console.log(acc)
    let that = this

    if(acc.username == '' || acc.password == ''){
      wx.showToast({
        title: '用户名和密码不能为空',
        icon: 'none',
        duration: 2000
      })
      return
    }

    wx.request({
      url: 'https://api.cirno.wang:30722/auth/login',
      method: 'POST',
      data: {
        username: acc.username,
        password: acc.password
      },
      success: function(res) {
        if (res.data.data == null) {
          //login failed or error
          wx.removeStorageSync('jwt')
          wx.showToast({
            title: '登陆失败',
            icon: 'none',
            duration: 2000
          })
          console.log(res)
        } else {
          const token = res.data.data.token;
          that.validateJwt(token)
        }
      },
      fail: function(res) {
        console.log(res)
      }
    })
  },
  validateJwt: function(jwt) {
    wx.request({
      url: 'https://api.cirno.wang:30722/auth/token',
      method: 'POST',
      data: {
        rtoken: jwt
      },
      success: function(res) {
        if (res.data.data == null) {
          console.log('validation failed')
          wx.removeStorageSync('jwt')
        } else {
          console.log('validation success')
          wx.setStorageSync('jwt', jwt)
          wx.navigateTo({
            url: '../msgbox/msgbox',
          })
        }
      },
      fail: function(res) {
        console.log(res)
      }
    })
  }
})