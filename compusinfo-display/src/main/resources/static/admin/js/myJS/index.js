//首页获取文章
function getIndexArticleList(data) {
  commonPostfun.request(serverApi.getIndexArticles,data,getHotArtSuc,getHotArtFail);
}
//首页获取文章成功
function getHotArtSuc(data) {
    let userId=window.sessionStorage.getItem("user_id");
    if(userId ==null){
        userId = -1;
    }
  let hotArtiles = '';  //整个信息
    let fastArticles='';
    //热门文章
    $.each(data.data.hotArticleList,function(hotArticleList,hotArticle){
      hotArtiles +='<li class="article-entry image"><div><h4><a href='+server+'/article/single?arti='+hotArticle.id+'&&userId='+userId+'>'+hotArticle.title+'</a></h4></div>'+
    '<span class="article-meta">'+ hotArticle.createTime+'</span>'
     +'<span class="article-content"><a href='+server+'/article/single?arti='+hotArticle.id+'&&userId='+userId+'>'+hotArticle.content.substring(0,300)+'</a></span>';
    });
    //最新文章
    $.each(data.data.fastArticleList,function(fastArticleList,fastArticle){
        fastArticles +='<li class="article-entry image"><div><h4><a href='+server+'/article/single?arti='+fastArticle.id+'&&userId='+userId+'>'+fastArticle.title+'</a></h4></div>'
            +'<span class="article-meta">'+ fastArticle.createTime+'</span>'
            +'<span class="article-content"><a href='+server+'/article/single?arti='+fastArticle.id+'&&userId='+userId+'>'+fastArticle.content.substring(0,300)+'</a></span>';
    });
  $('#hotArticleList').html(hotArtiles);
  $('#fastArticleList').html(fastArticles);
}
//首页获取文章失败
function getHotArtFail(data) {
    alert("system fail");
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
function getArticleList(pageNum,pageSize){
    //参数为-1表示为跳转页面
    if(pageNum == -1){
        pageNum= $('#searchPage').val()
    }
  let data = {
    userId: window.sessionStorage.getItem("user_id"),
    pageNum: pageNum,
    pageSize: pageSize
  };
  commonPostfun.request(serverApi.getUserArticleList,data,renderData,renderFail);
}
//后台获取文章数据成功回调
function renderData(data){
      let talkStr = '';  //整个信息
      let PageControl='';//页面跳转控件
      data.data.list.forEach((articleList,index)=>{
        talkStr +='<li><a href="'+server+'/article/view?artId='+articleList.id+'"target="_blank">'+articleList.title+'</a><span>'+
            articleList.updateTime+'</span></li>'
      });
    //  分页栏显示
    PageControl=pagePluin(data,PageControl,'getArticleList');
      $('#articleList').html(talkStr);
      $('#pagePlugin').html(PageControl);
    }


// 后台获取文章数据成功回调失败
function renderFail(err){
  tipObj.setErrmsg("请求失败，请重试",1);
}

//文章清单显示
function getAllArticleList(pageNum,pageSize) {
    //参数为-1表示为跳转页面
    if(pageNum == -1){
        pageNum= $('#searchPage').val()
    }
    let data = {
        pageNum: pageNum,
        pageSize: pageSize
    };
    commonGetfun.request(serverApi.getAllArticleList,data,allArticleListSuc,publicFail);
}

//文章清单回显
function allArticleListSuc(data) {
    let userId=window.sessionStorage.getItem("user_id");
    if(userId ==null){
        userId = -1;
    }
    let articleStr = '';  //整个信息
    let PageControl='';//页面跳转控件
    data.data.list.forEach((article,index)=>{
        articleStr +='<article class="format-standard type-post hentry clearfix"><header class="clearfix"><h3 class="post-title">'+
            '<a href='+server+'/article/single?arti='+article.id+'&&userId='+userId+'>'+article.title+'</a></h3>'+
            '<div class="post-meta clearfix"><span class="date">'+article.createTime+'</span>' +
            '<span class="category"><a href="#" title="Server &amp">Server</a></span>' +
            '<span class="comments"><a href="#">'+article.comNum+'</a></span></div></header>' +
           '<span class="article-content"><a href='+server+'/article/single?arti='+article.id+'&&userId='+userId+'>'+article.content.substring(0,300)+'</a></span></article>';
    });
    //  分页栏显示
    PageControl=pagePluin(data,PageControl,'getAllArticleList');
    $('#articleList').html(articleStr);
    $('#pagePlugin').html(PageControl);
}

//后台用户评论查询
function getUserAllTalkData(pageNum,pageSize){
    let user_id = window.sessionStorage.getItem("user_id");
    let data = {
        user_id: user_id,
        pageNum: pageNum,
        pageSize: pageSize,
    };
    commonGetfun.request(serverApi.getUserAllTalk,data,renderUserAllTalkData,renderUserAllTalkFail)
}

//后台用户评论查询成功回调
function renderUserAllTalkData(data){
    let talkStr = '';  //整个评论信息
    let PageControl='';
    // let moreStr = '';
    if(data.data.total >0 ){
        talkStr += '<div class="ydc-group-altogether">共<span>'+data.data.total+'</span>条评论</div>'
    }
    data.data.list.forEach((talkitem,index)=>{
            //判断此评论是否有回复
            let replyStr = '';  //回复文本
            // if(talkitem.child.length > 0){
            //     replyStr = '<div class="comment_reply">';
            //     talkitem.child.forEach((item,index)=>{
            //         //渲染评论数据
            //         replyStr +=
            //             '<div data-fromID='+item.replyUserId+' data-id='+item.id+' class="one_reply '+(index==talkitem.child.length-1?'one_reply_noborder':'')+'">'+
            //             '<div class="comment_people_info">'+
            //             '<img class="fl_left" src="'+item.replyUserImg+'" alt="">'+
            //             '<span class="first_reply fl_left"><span class="first_reply_aa">'+item.replyUserName+'</span>'+
            //             '<span class="first_reply_text"> 回复: </span>'+
            //
            //             '<span class="first_reply_aa">'+item.talkName+'</span></span>'+
            //             '<span class="date fl_right">'+item.createTime+'</span></div>'+
            //             '<div class="comment_people_content">'+
            //             '<span class="fl_left">'+item.content+'</span>'+
            //             '<span data-userID='+item.replyUserId+' data-userName='+item.replyUserName+' class=" reply_fn reply_btn fl_right">回复</span>'+
            //             '</div></div>'
            //     })
            // }
        talkStr += '<span class="fl_left"><H4 class="fl_left">文章：<a href="#">'+talkitem.articleName+'</a></H4></span>';
        talkStr += '<div data-id='+talkitem.id+' data-userID='+talkitem.userId+' class="comment">'+
            '<div class="comment_people">'+
            '<div class="comment_people_titleBox">'+
            '<div class="comment_people_info">'+
            '<img class="fl_left" src="'+talkitem.img+'" alt="">'+
            '<span class="first_reply fl_left">'+talkitem.userName+'</span>'+
            '<span class="date fl_left">'+talkitem.createTime+'</span>'+
            '<span data-userID='+talkitem.userId+' data-userName='+talkitem.userName+' class="reply_fn fl_right reply_btn">回复</span></div>'+
            '<div class="comment_people_content">'+
            '<span class="fl_left">'+talkitem.content+'</span>'+
            '</div></div>'+replyStr+'</div></div>';
    });
    //  分页栏显示
    PageControl=pagePluin(data,PageControl,'getUserAllTalkData');
    $('#talk_content').html(talkStr);
    $('#pagePlugin').html(PageControl);
}

// 后台用户评论查询失败
function renderUserAllTalkFail(err){
    tipObj.setErrmsg("请求失败，请重试",1);
}

//标签列表列中文章显示
function getTagArticleList(tagId,pageNum,pageSize){
    let data = {
        pageNum: pageNum,
        pageSize: pageSize,
    };
    let  getTagArticleListURL=server+'/tag/'+tagId+'/articleList';         //标签页面文章显示URL
    commonGetfun.request(getTagArticleListURL,data,TagArticleListSuc,TagArticleListFail)
}
//标签列表列中文章显示成功
function TagArticleListSuc(data) {
    console.log("根据标签id获取文章列表成功");
    let articleStr = '';  //整个信息
    let PageControl='';//页面跳转控件
    data.data.list.forEach((article,index)=>{
        articleStr +='<article class="excerpt excerpt-1"><header><a class="cat" href="#" title="" >文章</a>'+
        '<h2><a href="#" title="" target="_blank" >'+article.title+'</a></h2></header>'+
        '<p class="meta"><time class="time"><i class="glyphicon glyphicon-time"></i>'+article.createTime+'</time></p>'+
        '<span>'+article.content.substring(0,300)+'</span></article>';
    });
    //  分页栏显示
    PageControl=pagePluin(data,PageControl,'getAllArticleList');
    $('#tagArticleList').html(articleStr);
    $('#tagPagePlugin').html(PageControl);
}

//标签列表列中文章显示失败
function TagArticleListFail(data) {
    console.log("根据标签id获取文章列表失败");
}

//分页插件实现调用
function pagePluin(_data,PageControl,functionName) {
    _data.data.navigatepageNums.forEach((curpage,pageindex)=>{
        //上一页按键实现
        if(pageindex==0 &&_data.data.pageNum==1){
            PageControl +='<li class="ydc-previous-item"><button class="ydc-previous-item-btn-medium ydc-disabled"><span>上一页</span></button></li>';
        }
        if(pageindex==0 &&_data.data.pageNum!=1){
            PageControl +='<li class="ydc-previous-item"><button class="ydc-previous-item-btn-medium" onclick="'+functionName+'('+(_data.data.pageNum-1)+','+_data.data.pageSize+')"><span>上一页</span></button></li>';
        }
        //每一页数字功能实现
        if(pageindex+1 == _data.data.pageNum){
            PageControl +='<li><button class="ydc-previous-item-btn-medium cur"  onclick="'+functionName+'('+(pageindex+1)+','+_data.data.pageSize+')">'+(pageindex+1)+'</button></li>'
        }else{
            PageControl +='<li><button class="ydc-previous-item-btn-medium"  onclick="'+functionName+'('+(pageindex+1)+','+_data.data.pageSize+')">'+(pageindex+1)+'</button></li>'
        }
        //下一页功能实现
        if(pageindex+1==_data.data.navigateLastPage && _data.data.pageNum==_data.data.navigateLastPage){
            //最后一页没有下一页功能
            PageControl +='<li class="ydc-previous-item"><button class="ydc-previous-item-btn-medium ydc-disabled"><span>上一页</span></button></li>';
        }
        if(pageindex+1==_data.data.navigateLastPage && _data.data.pageNum!=_data.data.navigateLastPage){
            PageControl +='<li class="ydc-previous-item"><button class="ydc-previous-item-btn-medium" onclick="'+functionName+'('+(_data.data.pageNum+1)+','+_data.data.pageSize+')"><span>下一页</span></button></li>';
        }
    });
    PageControl +=
        '<li class="ydc-item-quick">' +
        '第<div class="ydc-item-quick-kun"><input type="number" aria-invalid="false" class="" id="searchPage"></div>页'+
        '<button style="" class="ydc-previous-item-btn-medium" onclick="'+functionName+'(-1,'+_data.data.pageSize+')"><span>跳转</span></button></li>';
    return PageControl;
}



//点击查看  获取评论并且根据talkID获取全部评论
$('#talk_content').on('click','#see_reply',function(){

    seeAlltalkID = $(this).parents('.comment').attr('data-id')
    let replyTotal = $(this).attr('data-total')
    if(replySize == replyTotal){
        replySize = replyCount;
        getTalkData(articleId);
    }else{
        replySize = replyTotal;
        //初始化查询评论数据
        getTalkData(seeAlltalkID);
    }

})

//回复取消
$('#talk_content').on('click','#cancelbtn_s',function(){
    $(this).parents('.talk_box_s').remove();
})

//回复评论框
function setchildTextarea(){
    return '<div class="talk_box talk_box_s">'+
        '<textarea name="talk" id="textareaBox_s" placeholder="写下你的评论..."></textarea>'+
        '<button id="talk_submit_s">发布</button><button id="cancelbtn_s" class="cancelbtn">取消</button></div>'
}