package com.graduation.compusinfo.display.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.compusinfo.display.entity.ReplyComment;

import java.util.List;

/**
 * @author zzk
 * @date 2020/3/5 14:04
 */
public interface ReplyCommentService extends IService<ReplyComment> {

    List<ReplyComment> getReplyCommentByCommentId(Long id);

    boolean addReplyComment(Long commentId, String content, Long replyUserId, String replyUserName, Long talkId, String talkName);


    boolean removeReplyComment(Long id);
}
