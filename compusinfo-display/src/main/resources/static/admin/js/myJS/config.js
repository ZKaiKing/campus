
let server = 'http://localhost:8080';       //服务器地址  对应 comment_server/app
let serverApi={
    // 请求接口设置
    getIndexArticles: server+'/article/indexArticles',  //首页热度文章回显
    publicArticle : server+'/admin/article/add',   //发布文章AP
    getArticleList : server+'/article/admin/articleList',  //回显用户所有文章
    viewArticle: server+'/article/view',
}