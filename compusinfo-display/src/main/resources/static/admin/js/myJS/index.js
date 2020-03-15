let replySize = 3;  //回复默认显示3条  当点击更多 则为评论相关回复total总数
const replyCount = 3; //回复条数大于3  隐藏
const currentUserID = '1';
const currentUserName="张老师";
let replyUser = '';   //回复人XXX
let replyUserID = '';  //回复人ID
let replyUserImg= '';  //回复人头像
let seeAlltalkID = '';   //点击查看按钮时  储存点击的评论ID
// let user_Id=userstorage.get("user_id");
let user_Id= 32;
// let articleID = '1'    // 假设此篇文章ID 为 0 (根据文章ID 请求对应评论数据)

//首页获取文章
function getIndexArticleList(data) {
  commonfun.request(serverApi.getIndexArticles,data,getHotArtSuc,getHotArtFail);
}
//首页获取文章成功
function getHotArtSuc(data) {
  // if(data.code != 0){
  //   tipObj.setErrmsg(data.msg,1);
  //   return;
  // }
  let hotArtiles = '';  //整个信息
    let fastArticles='';
    //热门文章
    $.each(data.data.hotArticleList,function(hotArticleList,hotArticle){
      hotArtiles +='<li class="article-entry image"><div><h4><a href='+server+'/article/single?arti='+hotArticle.id+'>'+hotArticle.title+'</a></h4></div>'+
    '<span class="article-meta">'+ hotArticle.creatTime+'</span>';
     // '<span class="article-content"><a href='+'></a>'+hotArticleList.content.substring(0,300)+'</span>'+
     if(hotArticle.likeNum != 0){
         hotArtiles += '<span value='+hotArticle.likeNum+' class="like-count" onclick="unLike('+hotArticle.id+')">'+hotArticle.likeNum+'</span></li>';
     }else {
         hotArtiles += '<span value='+hotArticle.likeNum+' class="unlike-count" onclick="LikeIt('+hotArticle.id+')">'+hotArticle.likeNum+'</span></li>';
     }
    });
    //最新文章
    $.each(data.data.fastArticleList,function(fastArticleList,fastArticle){
        fastArticles +='<li class="article-entry image"><div><h4><a href='+server+'/article/single?arti='+fastArticle.id+'>'+fastArticle.title+'</a></h4></div>'
            +'<span class="article-meta">'+ fastArticle.creatTime+'</span>';
        if(fastArticle.likeNum != 0){
            fastArticles += '<span value='+fastArticle.likeNum+' class="like-count" onclick="unLike('+fastArticle.id+')">'+fastArticle.likeNum+'</span></li>';
        }else {
            fastArticles += '<span value='+fastArticle.likeNum+' class="unlike-count" onclick="LikeIt('+fastArticle.id+')">'+fastArticle.likeNum+'</span></li>';
        }
    });
  $('#hotArticleList').html(hotArtiles);
  $('#fastArticleList').html(fastArticles);
}
//首页获取文章失败
function getHotArtFail(data) {

}
//点赞功能
function unLike(artId) {
    var clas = $(this).attr("class");
    alert(clas);
    if(clas=="like-count"){
        $(this).css({color:"#395996",background:"url('../images/like.png')no-repeat 6px 8px"});
        $(this).attr("class","unlike-count");
    }else {
        $(this).css({color:"#395996",background:"url('../images/like.png')no-repeat 6px -22px"});
        $(this).attr("class","like-count");
    }
    likefunc(artId,clas);
}
//取消点赞功能
function LikeIt(artId) {
    var clas = $(this).attr("class");
    alert(clas);
    if(clas=="like-count"){
        $(this).css({color:"#395996",background:"url('../images/like.png')no-repeat 6px 8px"});
        $(this).attr("class","unlike-count");
    }else {
        $(this).css({color:"#395996",background:"url('../images/like.png')no-repeat 6px -22px"});
        $(this).attr("class","like-count");
    }
    likefunc(artId,clas);
}
//点赞响应
var likefunc= function (artId,clas) {
    // var clas = $(this).attr("class");
    if(clas=="like-count"){
        $.ajax({
            type: "POST",
            async : false,
            url: server+"/article/unlike",
            data: {"artId": artId,"userId": user_Id},
            dataType: "json",
            success: function (data) {
                if (data.code === 200){
                    console.log("取消点赞操作成功");
                }
            },
            error : function(e) {           //请求失败的回调函数
                console.log(e);
            }
        })
    }else {
        $.ajax({
            type: "POST",
            async : false,
            url: server+"/article/like",
            data: {"artId": artId,"userId": user_Id},
            dataType: "json",
            success: function (data) {
                if (data.code === 200){
                    console.log("点赞操作成功");
                }
            },
            error : function(e) {           //请求失败的回调函数
                console.log(e);
            }
        })
    }
}

//发布文章功能
function publicArticle(data) {
  commonPostfun.request(serverApi.publicArticle,data,publicSuc,publicFail);
}
//发布文章成功
function publicSuc(data) {
  alert("success");
}
//发布文章失败
function publicFail(data) {
  alert("fail");
}

//后台获取用户所有文章
function getArticleList(){
  let data = {
    userId: 32
  };
  commonPostfun.request(serverApi.getArticleList,data,renderData,renderFail);
}
//后台获取文章数据成功回调
function renderData(data){
      if(data.code != 0){
          tipObj.setErrmsg(data.msg,1);
          return;
      }
      let talkStr = '';  //整个信息
      data.data.forEach((articleList,index)=>{
        //判断此评论是否有回复
        talkStr +='<li><a href="page.html" target="_blank">'+articleList.title+'</a><span>'+
            articleList.updateTime+'</span></li>'
      });
      $('#articleList').html(talkStr);

    }
// 后台获取文章数据成功回调失败
function renderFail(err){
  tipObj.setErrmsg("请求失败，请重试",1);
}
