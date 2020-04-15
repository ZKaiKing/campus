package com.graduation.compusinfo.display.mapper;

import com.graduation.compusinfo.display.entity.UserLike;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author zzk
 * @date 2020-03-25 10:46:08
 */
@Component
public interface UserLikeMapper extends BaseMapper<UserLike> {

    int selectWeekLikeNumIndicator(@Param("id") Long id);


    int selectLikeNumSumIndicator(@Param("id") Long id);
}
