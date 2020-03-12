package com.graduation.compusinfo.display.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.compusinfo.display.entity.Tag;

import java.util.List;

/**
 * @author zzkai
 * @date 2019-12-12 09:25:48
 */
public interface TagService extends IService<Tag> {

    List<Tag> getAllTag();
}
