package com.graduation.compusinfo.display.service.impl;

import com.graduation.compusinfo.display.entity.Role;
import com.graduation.compusinfo.display.mapper.RoleMapper;
import com.graduation.compusinfo.display.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zhangzk
 * @date 2019-12-05 16:29:10
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
