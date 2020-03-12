package com.graduation.compusinfo.display.controller;


import com.graduation.compusinfo.display.dto.UserDTO;
import com.graduation.compusinfo.display.entity.User;
import com.graduation.compusinfo.display.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 *
 * @author zzkai
 * @date 2019-12-12 15:21:16
 */
@Api(tags = {"User"})
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册",notes = "用户根据信息进行注册操作")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String userRegister(@ModelAttribute UserDTO userdto, HttpServletRequest request, Model model){
        User user = userdto.userdtoConverUser(new User());
        String token= UUID.randomUUID().toString();
        user.setToken(token);
        user.setStatus(1);
        System.out.println(userdto);
        Boolean hasregister = userService.userRegister(user);
        if (!hasregister) {
            model.addAttribute("该用户名已存在");
            return "regist";
        } else {
            model.addAttribute("注册成功");
            request.getSession().setAttribute("user-token",token);
            return "index";
        }
    }



    @ApiOperation(value = "用户登录",notes = "用户根据信息进行登录操作")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String userLogin(@ModelAttribute User user, HttpServletRequest request, Model model){
        User DBuser = userService.userLogin(user);
        if(DBuser ==null || !DBuser.getUsername().equals(user.getUsername())){
            System.out.println("账户不存在");
            model.addAttribute("error","该账户不存在");
            return "login";
        }
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        if(!password.equals(DBuser.getPassword())){
            System.out.println("密码错误");
            model.addAttribute("error","密码错误");
            return "login";
        }
        model.addAttribute("user",user);
        return "redirect:/campus/index";
    }

}
