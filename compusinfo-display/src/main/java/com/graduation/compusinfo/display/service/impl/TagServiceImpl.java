package com.graduation.compusinfo.display.service.impl;

import com.graduation.compusinfo.display.entity.Tag;
import com.graduation.compusinfo.display.mapper.TagMapper;
import com.graduation.compusinfo.display.service.ArticleService;
import com.graduation.compusinfo.display.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zzkai
 * @date 2019-12-12 09:25:48
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleService articleService;

    @Override
    public List<Tag> getAllTag() {
        List<Tag> tags = tagMapper.selectTopTag();
        tags.stream().forEach(tag -> {
            int count = articleService.getCountFromTagId(tag.getId());
            tag.setSubArticleCount(count);
        });
        return tags;
    }
}
