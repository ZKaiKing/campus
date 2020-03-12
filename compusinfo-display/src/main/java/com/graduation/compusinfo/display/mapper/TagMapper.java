package com.graduation.compusinfo.display.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.graduation.compusinfo.display.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zzkai
 * @date 2019-12-12 09:25:48
 */
@Component
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> selectTopTag();
}
