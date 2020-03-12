package com.graduation.compusinfo.display.service.impl;

import com.graduation.compusinfo.display.entity.Attention;
import com.graduation.compusinfo.display.mapper.AttentionMapper;
import com.graduation.compusinfo.display.service.AttentionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zhangzk
 * @date 2019-12-05 16:29:10
 */
@Service("attentionService")
public class AttentionServiceImpl extends ServiceImpl<AttentionMapper, Attention> implements AttentionService {

}
