package com.graduation.compusinfo.display.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.compusinfo.display.entity.Comment;

import java.util.List;

/**
 * @author zhangzk
 * @date 2019-12-05 16:29:10
 */
public interface CommentService extends IService<Comment> {

    List<Comment> getAllCommontsById(Long articleId, int replyCount);

    boolean addComment(Long userID, String userName, Long articleId, String content);

    boolean removeComment(Long id);
}
