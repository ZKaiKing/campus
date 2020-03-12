package com.graduation.compusinfo.display.service.impl;

import com.graduation.compusinfo.display.entity.Articletype;
import com.graduation.compusinfo.display.mapper.ArticletypeMapper;
import com.graduation.compusinfo.display.service.ArticletypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zhangzk
 * @date 2019-12-05 16:05:11
 */
@Service("articletypeService")
public class ArticletypeServiceImpl extends ServiceImpl<ArticletypeMapper, Articletype> implements ArticletypeService {

}
