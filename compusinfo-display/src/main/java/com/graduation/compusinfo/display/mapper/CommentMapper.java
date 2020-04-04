package com.graduation.compusinfo.display.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.graduation.compusinfo.display.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangzk
 * @date 2019-12-05 16:29:10
 */
@Component
public interface CommentMapper extends BaseMapper<Comment> {

//    通过用户id获取该用户的所有赞
    List<Comment> selectCommentByUserId(@Param("user_id") Long user_id);
}
