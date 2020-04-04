var storage=window.sessionStorage;
let replySize = 5;  //回复默认显示3条  当点击更多 则为评论相关回复total总数
const replyCount = 5; //回复条数大于3  隐藏
const currentUserID = storage.getItem("user_id");
const currentUserName= storage.getItem("user_name");
let articleId=$("#articleId").val();

let replyUser = '';   //回复人XXX
let replyUserID = '';  //回复人ID
let replyUserImg= '';  //回复人头像
let seeAlltalkID = '';   //点击查看按钮时  储存点击的评论ID

//初始化渲染评论数据
function getTalkData(articel_id){
  let data = {
    replyCount:replyCount,
    articleId:articel_id
  };
  commonGetfun.request(serverApi.getTalk,data,renderData,renderFail)
}

//获取数据成功回调
function renderData(data){
      let talkStr = '';  //整个评论信息
      let moreStr = '';
      if(data.data.length >0 ){
        talkStr += '<h3 id="comments-title">'+'('+data.data.length+')条评论'+'</h3>'
      }
        data.data.forEach((talkitem,index)=>{
        //判断此评论是否有回复
        let replyStr = '';  //回复文本
        if(talkitem.child.length > 0){
          replyStr = '<div class="comment_reply">';
          talkitem.child.forEach((item,index)=>{
                //渲染评论数据
                replyStr += 
      '<div data-fromID='+item.replyUserId+' data-id='+item.id+' class="one_reply '+(index==talkitem.child.length-1?'one_reply_noborder':'')+'">'+
          '<div class="comment_people_info">'+
              '<img class="fl_left" src="'+item.replyUserImg+'" alt="">'+
              '<span class="first_reply fl_left"><span class="first_reply_aa">'+item.replyUserName+'</span>'+
                '<span class="first_reply_text"> 回复: </span>'+
                
              '<span class="first_reply_aa">'+item.talkName+'</span></span>'+
              '<span class="date fl_right">'+item.createTime+'</span></div>'+
          '<div class="comment_people_content">'+
              '<span class="fl_left">'+item.content+'</span>'+
              
              '<span data-userID='+item.replyUserId+' data-userName='+item.replyUserName+' class=" reply_fn reply_btn fl_right">回复</span>'+
              '<span id="reply_remove" class="reply_remove reply_btn fl_right reply_btn_cont none">删除</span>'+
              '</div></div>'
          })
          //子类评论结束
          moreStr = '<div class="comment_more '+(talkitem.commentList>replyCount?'':'none')+'">'+
      '<span>剩余'+(seeAlltalkID== talkitem.id? talkitem.commentList-replySize : talkitem.commentList-replyCount)+'条评论</span>  <span id="see_reply" data-total='+talkitem.replytotal+' class="green see">'+(talkitem.replytotal-replySize == 0?'收起':"点击查看")+'</span></div>';
          replyStr += moreStr+'</div>'
        }
          talkStr += '<div data-id='+talkitem.id+' data-userID='+talkitem.userId+' class="comment">'+
      '<div class="comment_people">'+
        '<div class="comment_people_titleBox">'+
          '<div class="comment_people_info">'+
              '<img class="fl_left" src="#" alt="">'+
              '<span class="first_reply fl_left">'+talkitem.userName+'</span>'+
              '<span class="date fl_left">'+talkitem.createTime+'</span>'+
              '<span data-userID='+talkitem.userId+' data-userName='+talkitem.userName+' class="reply_fn fl_right reply_btn">回复</span></div>'+
          '<div class="comment_people_content">'+
          '<span class="fl_left">'+talkitem.content+'</span>'+
              '<span id="talk_remove" class="reply_btn fl_right none reply_btn_cont">删除</span>'+
              '</div></div>'+replyStr+'</div></div>';
      })
      $('#talk_content').html(talkStr);
    }

// 渲染数据失败
function renderFail(err){
  tipObj.setErrmsg("请求失败，请重试",1);
}

//对文章评论
$('#textareaBox').on('focus',function () {
   $('#cancelbtn').removeClass('none')
})

//发布评论
$('#talk_submit').on('click',function(){
  //提交请求
  let content = $.trim($('#textareaBox').val());
  var articleId=$("#articleId").val();
  if(content.length > 0){
    let data = {
      userID:currentUserID,
      userName:currentUserName,
      articleId:articleId,
      content,
    };
    commonfun.request(serverApi.addTalk,data,addTalkfun,addTalkFail)
  }else{
    //提示层
    tipObj.setErrmsg('请输入评论内容',1);
  }
})

//添加评论成功
function addTalkfun(data){
  $('#textareaBox').val('')
  $('#cancelbtn').addClass('none')
  getTalkData(articleId);
}

//添加评论失败
function addTalkFail(err){
  tipObj.setErrmsg("请求失败，请重试",1);
}



//发布评论回车事件处理
$('#textareaBox').keypress(function(e){ 
  if(e.ctrlKey && e.which == 13 || e.which == 10 || e.which == 13) { 
    $('#talk_submit').trigger('click')
    e.preventDefault(); //屏蔽enter对系统作用。按后增加\r\n等换行
  } 
});

//取消评论
$('#cancelbtn').on('click',function(){
  $('#textareaBox').val('')
  $(this).addClass('none')
})

//滑过评论控件  显示删除按钮
$('#talk_content').on('mouseover','.comment_people_titleBox ',function(){
  if(currentUserID == $(this).parents('.comment').attr('data-userID')){
    $(this).find('#talk_remove').removeClass('none')
  }
});

//移出评论控件  隐藏删除按钮
$('#talk_content').on('mouseout','.comment_people_titleBox',function(){
  $(this).find('#talk_remove').addClass('none')
})

//删除评论
$('#talk_content').on('click','#talk_remove',function(){
  let id = $(this).parents('.comment').attr('data-id')

  let data = {
    id,
  }
  commonfun.request(serverApi.removeTalk,data,removeTalkfun,removeTalkFail)
})

//删除评论成功回调
function removeTalkfun(){
    //初始化查询评论数据
    getTalkData(articleId);
}
//删除评论失败回调
function removeTalkFail(){
  tipObj.setErrmsg("请求失败，请重试",1);
}

//滑过回复控件  显示删除
$('#talk_content').on('mouseover','.one_reply ',function(){
    if(currentUserID == $(this).attr('data-fromid')){
      $(this).find('#reply_remove').removeClass('none')
    }
});
//移出回复控件  隐藏删除
$('#talk_content').on('mouseout','.one_reply',function(){
    $(this).find('#reply_remove').addClass('none')
})

//点击回复 
// 判断是否对自己回复  对自己回复则 return
// 判断是否存在评论框  不存在创建 同时设置文本域值 @xxxx ++++++++++++++++++++++
$('#talk_content').on('click','.reply_fn',function(){

  if($(this).attr('data-userid') == currentUserID){
    return;
  }

  let areachild = $('#talk_content').find('.talk_box_s')

  if($(areachild).length == 0){
    let textBox = setchildTextarea();
    $(this).parents('.comment').append(textBox)

  }else{

    $('#talk_content').find('.talk_box_s').remove();
    let textBox = setchildTextarea();
    $(this).parents('.comment').append(textBox)
  }

  let val = $(this).attr('data-username');
  $('#talk_content').find('#textareaBox_s').val('@'+val+' ')

  replyUser = val;
  replyUserID = $(this).attr('data-userid');
  replyUserImg=$(this).attr('')

})

//回复发布   
$('#talk_content').on('click','#talk_submit_s',function(){

  // 替换 @ xxx 为空
  let content = ($.trim($('#textareaBox_s').val())).replace((replyUser),'')
  content = content.replace('@','')

  let talkID = $(this).parents('.comment').attr('data-id')

  if(content.length > 0){

    let data = {
        commentId:talkID,
        content,
        replyUserId:currentUserID,
        replyUserName:currentUserName,
        talkId:replyUserID,
        // replyUserImg:replyUserImg,
        talkName:replyUser,
    }
    commonfun.request(serverApi.addreply,data,replyfun,replyfail)

  }else{
    tipObj.setErrmsg('请输入评论内容',1);
  }

})
//回复发布成功
function replyfun(){
  getTalkData(articleId);
  $(this).parents('.talk_box_s').remove();
}
//回复发布失败
function replyfail(){
  tipObj.setErrmsg('请求失败，请重试',1)
}

//回复发布  回车事件处理
$('#talk_content').keypress('#textareaBox_s',function(e){ 
  if(e.ctrlKey && e.which == 13 || e.which == 10 || e.which == 13) { 
    $('#talk_content').find('#talk_submit_s').trigger('click')
    e.preventDefault();     //屏蔽enter对系统作用。按后增加\r\n等换行
  } 
});

//删除回复
$('#talk_content').on('click','#reply_remove',function(){

  let id = $(this).parents('.one_reply').attr('data-id')
  let data = {
    id:id
  }
  commonfun.request(serverApi.removereply,data,removeReplyfun,removeReplyfail)
})

//删除回复成功
function removeReplyfun(){
  //初始化查询评论数据
  getTalkData(articleId);
}
//删除回复失败
function removeReplyfail(){
  tipObj.setErrmsg('请求失败，请重试',1)
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
