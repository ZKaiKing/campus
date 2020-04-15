let server = 'http://localhost:8080';       //服务器地址  对应 comment_server/app
let serverApi={
    // 请求接口设置
    getIndexArticles: server+'/article/indexArticles',  //首页热度文章回显
    LastCommentList:  server+'/comment/LastCommentList',  //首页热度文章回显
    publicArticle : server+'/admin/article/public',   //发布文章API
    getVariousIndicators: server+'/admin/variousIndicators',   //后台首页获取各类指标
    getManagecharTable: server+'/admin/managecharTable',   //后台内容管理文章数据显示
    getUserArticleList : server+'/article/admin/articleList',  //回显用户所有文章
    getAllArticleList : server+'/article/all/articleList',  //回显所有用户所有文章清单
    viewArticle: server+'/article/view',
    // 评论请求接口设置
    addTalk : server+'/comment/reply/add',   //添加评论API
    getTalk : server+'/comment/reply/get',  //获取评论API及相关回复
    removeTalk : server+'/comment/reply/remove',   //删除评论API
    addreply : server+'/replyComment/addreply',   //添加回复API
    removereply : server+'/replyComment/removereply',   //删除回复API
    getUserAllTalk: server+'/admin/getAllTalk',          //用户后台评论显示searchUserAllTalkData
    searchUserAllTalkData: server+'/admin/searchAllTalk',          //用户后台搜索评论显示
}