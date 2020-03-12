package com.graduation.compusinfo.display.service.impl;

import com.graduation.compusinfo.display.entity.Collect;
import com.graduation.compusinfo.display.mapper.CollectMapper;
import com.graduation.compusinfo.display.service.CollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zhangzk
 * @date 2019-12-05 16:29:10
 */
@Service("collectService")
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

}
