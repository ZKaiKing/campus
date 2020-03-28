//首页获取文章
function getIndexArticleList(data) {
  commonPostfun.request(serverApi.getIndexArticles,data,getHotArtSuc,getHotArtFail);
}
//首页获取文章成功
function getHotArtSuc(data) {
  // if(data.code != 0){
  //   tipObj.setErrmsg(data.msg,1);
  //   return;
  // }
    let userId=window.sessionStorage.getItem("user_id");
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
function getArticleList(pageNum){
    //参数为-1表示为跳转页面
    if(pageNum == -1){
        pageNum= $('#searchPage').val()
    }
  let data = {
    userId: window.sessionStorage.getItem("user_id"),
    pageNum: pageNum
  };
  commonPostfun.request(serverApi.getArticleList,data,renderData,renderFail);
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
    PageControl=pagePluin(data,PageControl);
      $('#articleList').html(talkStr);
      $('#pagePlugin').html(PageControl);
    }
// $('#searchPage').value
// 后台获取文章数据成功回调失败
function renderFail(err){
  tipObj.setErrmsg("请求失败，请重试",1);
}

// //后台查看文章详情
// function viewArticle(artId) {
//     let data = {
//         artId: artId
//     };
//     commonfun.request(serverApi.viewArticle,data,viewArtData,viewArtDataFail);
// }
//
// // 后台获取文章数据成功回调失败
// function viewArtData(data){
//     window.location.href="../admin-page.html";
// alert("success");
// }
//
// // 后台获取文章数据成功回调失败
// function viewArtDataFail(err){
//     console.log("请求失败");
// }


//分页插件实现调用
function pagePluin(_data,PageControl) {
    _data.data.navigatepageNums.forEach((curpage,pageindex)=>{
        //上一页按键实现
        if(pageindex==0 &&_data.data.pageNum==1){
            PageControl +='<li class="ydc-previous-item"><button class="ydc-previous-item-btn-medium ydc-disabled"><span>上一页</span></button></li>';
        }
        if(pageindex==0 &&_data.data.pageNum!=1){
            PageControl +='<li class="ydc-previous-item"><button class="ydc-previous-item-btn-medium" onclick="getArticleList('+(_data.data.pageNum-1)+')"><span>上一页</span></button></li>';
        }
        //每一页数字功能实现
        if(pageindex+1 == _data.data.pageNum){
            PageControl +='<li><button class="ydc-previous-item-btn-medium cur"  onclick="getArticleList('+(pageindex+1)+')">'+(pageindex+1)+'</button></li>'
        }else{
            PageControl +='<li><button class="ydc-previous-item-btn-medium"  onclick="getArticleList('+(pageindex+1)+')">'+(pageindex+1)+'</button></li>'
        }
        //下一页功能实现
        if(pageindex+1==_data.data.navigateLastPage && _data.data.pageNum==_data.data.navigateLastPage){
            //最后一页没有下一页功能
            PageControl +='<li class="ydc-previous-item"><button class="ydc-previous-item-btn-medium ydc-disabled"><span>上一页</span></button></li>';
        }
        if(pageindex+1==_data.data.navigateLastPage && _data.data.pageNum!=_data.data.navigateLastPage){
            PageControl +='<li class="ydc-previous-item"><button class="ydc-previous-item-btn-medium" onclick="getArticleList('+(_data.data.pageNum+1)+')"><span>下一页</span></button></li>';
        }
    });
    PageControl +=
        '<li class="ydc-item-quick">' +
        '第<div class="ydc-item-quick-kun"><input type="number" aria-invalid="false" class="" id="searchPage"></div>页'+
        '<button style="margin-left:5px;" class="ydc-previous-item-btn-medium" onclick="getArticleList(-1)"><span>跳转</span></button></li>';
    return PageControl;
}