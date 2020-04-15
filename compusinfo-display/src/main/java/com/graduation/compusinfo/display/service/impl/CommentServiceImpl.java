package com.graduation.compusinfo.display.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.entity.Comment;
import com.graduation.compusinfo.display.entity.ReplyComment;
import com.graduation.compusinfo.display.mapper.CommentMapper;
import com.graduation.compusinfo.display.service.ArticleService;
import com.graduation.compusinfo.display.service.CommentService;
import com.graduation.compusinfo.display.service.ReplyCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangzk
 * @date 2019-12-05 16:29:10
 */
@Service("commentService")
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private ReplyCommentService replyCommentService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleService articleService;

    //用户文章评论总指标
    private Integer  commentSumIndicator=0;

    //文章昨日点赞指标
    private int WeekCommentIndicator=0;

    @Override
    public List<Comment> getAllCommontsById(Long articleId, int replyCount) {
        log.info("service----->articleId:  {} ,replyCount:{} ",articleId,replyCount);

        List<Comment> commentList = commentMapper.selectList(Wrappers.<Comment>lambdaQuery()
                .eq(Comment::getArticleId, articleId).orderByDesc(Comment::getCreateTime));
        //获取子评论
        commentList.stream().forEach(comment -> {
            List<ReplyComment> replyCommentList = replyCommentService.getReplyCommentByCommentId(comment.getId());
            comment.setChild(replyCommentList);
        });
        return commentList;

    }

    @Override
    public boolean addComment(Long userID, String userName, Long articleId, String content) {
        Date date = new Date();
        Comment comment=new Comment(content,articleId,userID,userName,null,date);
        return commentMapper.insert(comment)==1;
    }

    @Override
    public boolean removeComment(Long id) {
        return commentMapper.deleteById(id)==1;
    }

    @Override
    public PageInfo<Comment> userAllArticleComment(Long user_id, int pageNum, int pageSize) {
        log.info("service----->user_id:  {} ,pageNum:{} ,pageSize:{}",user_id,pageNum,pageSize);
//        List<Comment> commentList = commentMapper.selectList(Wrappers.<Comment>lambdaQuery()
//                .eq(Comment::getUserId, user_id).orderByDesc(Comment::getCreateTime));
        //获取子评论
//        commentList.stream().forEach(comment -> {
//            List<ReplyComment> replyCommentList = replyCommentService.getReplyCommentByCommentId(comment.getId());
//            comment.setChild(replyCommentList);
//            Article article = articleService.getById(comment.getArticleId());
//            comment.setArticleName(article.getTitle());
//        });
        PageInfo<Comment> CommentListPage = PageHelper.startPage(pageNum,pageSize,"")
                .doSelectPageInfo(()->commentMapper.selectCommentByUserId(user_id));
        List<Comment> commentList = CommentListPage.getList();
        commentList.stream().forEach(comment -> {
                List<ReplyComment> replyCommentList = replyCommentService.getReplyCommentByCommentId(comment.getId());
                comment.setChild(replyCommentList);
                Article article = articleService.getById(comment.getArticleId());
                comment.setArticleName(article.getTitle());
        });
        CommentListPage.setList(commentList);
        return CommentListPage;
    }

    @Override
    public PageInfo<Comment> ArticleCommentBySearchVal(Long user_id, int pageNum, int pageSize, String searchVal) {
        log.info("service----->user_id:  {} ,pageNum:{} ,pageSize:{}",user_id,pageNum,pageSize);
        List<Article> searchArticle = articleService.getArticalBysearchval(searchVal);
        PageInfo<Comment> CommentListPage = PageHelper.startPage(pageNum,pageSize,"")
                .doSelectPageInfo(()->commentMapper.selectCommentBysearchVal(user_id,searchVal));
        List<Comment> commentList = CommentListPage.getList();
        commentList.stream().forEach(comment -> {
            List<ReplyComment> replyCommentList = replyCommentService.getReplyCommentByCommentId(comment.getId());
            comment.setChild(replyCommentList);
            Article article = articleService.getById(comment.getArticleId());
            comment.setArticleName(article.getTitle());
        });
        CommentListPage.setList(commentList);
        return CommentListPage;
    }
    @Override
    public List<Article> getLastComment(int num) {
        List<Long> comments = commentMapper.selectLastNumComment(num);
        List<Article> articleList=new ArrayList<>();
        comments.stream().forEach(articleId->{
            Article article = articleService.getById(articleId);
            articleList.add(article);
        });
        return articleList;
    }

    @Override
    public int selectCommentSumIndicator(List<Article> articleList) {
        commentSumIndicator=0;
        articleList.stream().forEach(article -> {
            commentSumIndicator += commentMapper.selectCount(Wrappers.<Comment>lambdaQuery()
                    .eq(Comment::getArticleId,article.getId()));
        });
        return commentSumIndicator;
    }

    @Override
    public int selectWeekCommentIndicator(List<Article> articleList) {
        WeekCommentIndicator=0;
        articleList.stream().forEach(article -> {
            WeekCommentIndicator +=commentMapper.selectWeekCommentIndicator(article.getId());
        });
        return WeekCommentIndicator;
    }
//    select * from article ,comment where article.id=comment.article_id and
    //1查询出该用户的所有文章id
//    select user.id from user，article where user.id=article.user_id --

}
