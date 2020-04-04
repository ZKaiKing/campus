package com.graduation.compusinfo.display.controller;


import com.graduation.compusinfo.display.entity.User;
import com.graduation.compusinfo.display.service.UserService;
import com.graduation.compusinfo.display.utils.CommonResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zzkai
 * @date 2019-12-12 15:21:16
 */
@Api(tags = {"User"})
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册",notes = "用户根据信息进行注册操作")
    public String userRegister(@ModelAttribute User user, HttpServletRequest request, Model model){
//        User user = userdto.userdtoConverUser(new User());
        user.setStatus(1);
//        System.out.println(userdto);
        Boolean hasregister = userService.userRegister(user);
        if (!hasregister) {
            model.addAttribute("该用户名已存在");
            return "regist";
        } else {
            model.addAttribute("注册成功");
            log.info("注册成功");
            return "regist";
        }
    }



    @GetMapping("/loginOut")
    @ApiOperation(value = "用户登出",notes = "用户进行登出操作")
    public String loginOut(HttpServletRequest request, Model model){
//       删除cookie中的用户密钥
        Cookie[] cookies = request.getCookies();
        String struuid = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("USER_TOKEN")) {
                struuid = cookie.getValue();
                cookie.setMaxAge(0);
            }
        }
        //去redis删除该用户密钥
        userService.deleteUserInfoFromRedis(struuid);
        return "redirect:/admin/login";
    }

    @PostMapping("/ToLogin")
    @ApiOperation(value = "用户登录",notes = "用户根据信息进行登录操作")
    public  @ResponseBody
    CommonResponseDto
    userLogin(@ModelAttribute User user, HttpServletRequest request, HttpServletResponse response){
        User AdminUser = userService.AdminuserLogin(user);
        if(AdminUser==null || !AdminUser.getStudentNo().equals(user.getStudentNo())){
            log.info("账户不存在");
            return new CommonResponseDto().code(10001).success(false);
        }
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        if(!password.equals(AdminUser.getPassword())){
            log.info("密码错误");
            return new CommonResponseDto().code(10002).success(false);
        }
        String rediskey = userService.putUserInfoToRedis(AdminUser);
        Cookie cookie=new Cookie("USER_TOKEN",rediskey);
        cookie.setPath("/");
        response.addCookie(cookie);//将用户密钥写入cookie中
        AdminUser.setPassword(rediskey);//返回前端时密码隐蔽掉
        CommonResponseDto result = new CommonResponseDto();
        result.setData(AdminUser);
        return result.code(200).success(true);
    }


}
