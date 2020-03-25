package com.graduation.compusinfo.display.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.graduation.compusinfo.display.entity.Articletype;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author zhangzk
 * @date 2019-12-05 16:05:11
 */
@Component
public interface ArticletypeMapper extends BaseMapper<Articletype> {

    ArrayList<Articletype> randomAricleType();
}
