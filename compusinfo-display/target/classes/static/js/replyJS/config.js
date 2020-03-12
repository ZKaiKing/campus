
let server = 'http://localhost:8080';       //服务器地址  对应 comment_server/app
let serverApi={
    // 请求接口设置
    addTalk : server+'/comment/reply/add',   //添加评论API
    getTalk : server+'/comment/reply/get',  //获取评论API及相关回复
    removeTalk : server+'/comment/reply/remove',   //删除评论API

    addreply : server+'/replyComment/addreply',   //添加回复API
    removereply : server+'/replyComment/removereply',   //删除回复API
}