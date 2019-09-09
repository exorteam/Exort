// pages/msgbox.js
Page({
  data: {
    msgs: [],
    jwt: '',
    pageNum: 0,
    pageSize: 4
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    const jwt = wx.getStorageSync('jwt')
    if (jwt) {
      this.setData({
        jwt: jwt
      })
      this.validateJwt()
    }
  },

  onPullDownRefresh: function() {
    console.log("pulldown")
    this.loadMsg()
  },

  onReachBottom: function() {
    console.log('bottom')
    this.loadMsg()
  },

  loadMsg: function() {
    let that = this
    wx.request({
      url: 'https://api.cirno.wang:30722/com/msg/page?pageNum='+this.data.pageNum+'&pageSize='+this.data.pageSize,
      method: 'GET',
      header: {
        authorization: 'Bearer ' + this.data.jwt
      },
      success: function(res) {
        if (res.data.data == null) {
        } else {
          var mm = []
          for(let i=0;i<res.data.data.content.length;i++){
            var msg = res.data.data.content[i];
            var tt = new Date(msg.timestamp)
            msg.tt = tt.toLocaleString()
            mm.push(msg);
          }
          console.log(mm)
          that.setData({
            msgs: that.data.msgs.concat(res.data.data.content)
          })
          if(that.data.pageNum * that.data.pageSize < res.data.data.totalSize){
            that.setData({
              pageNum: that.data.pageNum + 1
            })
          }
        }
      }
    })
  },

  deleteMsg: function(args) {
    let that = this
    let msgId = args.currentTarget.dataset.id
    let msgIndex = args.currentTarget.dataset.ii
    wx.showModal({
      title: '提示',
      content: '确认删除消息#' + msgId + '?',
      success: function(sm) {
        if (sm.confirm) {
          // TODO: Send delete request
          wx.request({
            url: 'https://api.cirno.wang:30722/com/msg/drop/' + msgId,
            method: 'POST',
            header: {
              authorization: 'Bearer ' + that.data.jwt
            },
            success: function(res) {
              if(res.data.data){
                console.log('Delete successfully')
                var mm = that.data.msgs
                mm.splice(msgIndex, 1)
                that.setData({
                  msgs: mm
                })
              }
            }
          })
        }
      }
    })
  },

  validateJwt: function() {
    let that = this
    wx.request({
      url: 'https://api.cirno.wang:30722/auth/token',
      method: 'POST',
      data: {
        rtoken: this.data.jwt
      },
      success: function(res) {
        if (res.data.data == null) {
          console.log('validation failed')
          wx.removeStorageSync('jwt')
          wx.navigateBackMiniProgram()
        } else {
          console.log('validation success')
          that.loadMsg()
        }
      },
      fail: function(res) {
        console.log(res)
      }
    })
  }
})