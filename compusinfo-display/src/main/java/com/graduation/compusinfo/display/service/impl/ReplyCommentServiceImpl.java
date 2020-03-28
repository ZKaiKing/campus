package com.graduation.compusinfo.display.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.graduation.compusinfo.display.entity.ReplyComment;
import com.graduation.compusinfo.display.mapper.ReplyCommentMapper;
import com.graduation.compusinfo.display.service.ReplyCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zzk
 * @date 2020/3/5 14:05
 */
@Service("replyCommentService")
public class ReplyCommentServiceImpl  extends ServiceImpl<ReplyCommentMapper, ReplyComment> implements ReplyCommentService {


    @Autowired
    private ReplyCommentMapper replyCommentMapper;

    @Override
    public List<ReplyComment> getReplyCommentByCommentId(Long commentId) {
        List<ReplyComment> replyCommentList = replyCommentMapper.selectList(
                Wrappers.<ReplyComment>lambdaQuery().eq(ReplyComment::getCommentId, commentId)
                        .orderByDesc(ReplyComment::getCreateTime));
        return replyCommentList;
    }

    @Override
    public boolean addReplyComment(Long commentId, String content, Long replyUserId, String replyUserName, Long talkId, String talkName) {
//            public ReplyComment(Long commentId, Long talkId, String talkName, String content, Long replyUserId, String replyUserImg, String replyUserName, Date createTime) {
        Date date=new Date();
        ReplyComment replyComment=new ReplyComment(commentId,talkId,talkName,content,replyUserId,"",replyUserName,date);
        return replyCommentMapper.insert(replyComment) == 1;
    }

    @Override
    public boolean removeReplyComment(Long id) {
        return replyCommentMapper.deleteById(id) == 1;
    }

}