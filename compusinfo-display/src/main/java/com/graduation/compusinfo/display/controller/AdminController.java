package com.graduation.compusinfo.display.controller;

import com.graduation.compusinfo.display.entity.User;
import com.graduation.compusinfo.display.service.UserService;
import com.graduation.compusinfo.display.utils.CommonResponseDto;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zzk
 * @date 2020/3/8 14:08
 */
@Api(tags = {"Admin"})
@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String toLoginPage(Model model){
        return "admin-login";
    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String toAdminPage(Model model){
        return "admin-index";
    }

    @RequestMapping(value = "/release",method = RequestMethod.GET)
    public String toReleasePage(Model model){
        return "admin-release";
    }

    @RequestMapping(value = "/commentManage",method = RequestMethod.GET)
    public String toCommentManagePage(Model model){
        return "admin-content";
    }


    @RequestMapping(value = "/subscribe",method = RequestMethod.GET)
    public String toSubscribePage(Model model){
        return "admin-subscribe";
    }

    @RequestMapping(value = "/contentData",method = RequestMethod.GET)
    public String toContentDataPage(Model model){
        return "admin-contentdata";
    }

    @RequestMapping(value = "/userInfo",method = RequestMethod.GET)
    public String toUserInfoPage(Model model){
        return "admin-userInfo";
    }


//    @ApiOperation(value = "用户登录",notes = "用户根据信息进行登录操作")
//    @RequestMapping(value = "/login",method = RequestMethod.POST)
//    public String userLogin(@ModelAttribute User user, HttpServletRequest request, Model model, HttpServletResponse response){
//        User AdminUser = userService.AdminuserLogin(user);
//        if(AdminUser==null || !AdminUser.getPhone().equals(user.getPhone())){
//            System.out.println("账户不存在");
//            model.addAttribute("error","该账户不存在");
//            return "admin-login";
//        }
//        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
//        if(!password.equals(AdminUser.getPassword())){
//            System.out.println("密码错误");
//            model.addAttribute("error","密码错误");
//            return "admin-login";
//        }
//        String rediskey = userService.putUserInfoToRedis(AdminUser);
//        Cookie cookie=new Cookie("sessionId",rediskey);
//        response.addCookie(cookie);
//        AdminUser.setPassword("");//返回前端时密码隐蔽掉
//        model.addAttribute("user",AdminUser);
//        return "admin-index";
//    }


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public  @ResponseBody CommonResponseDto
    userLogin(@ModelAttribute User user, HttpServletRequest request,HttpServletResponse response){
        User AdminUser = userService.AdminuserLogin(user);
        if(AdminUser==null || !AdminUser.getPhone().equals(user.getPhone())){
            System.out.println("账户不存在");
//            model.addAttribute("error","该账户不存在");
            return new CommonResponseDto().code(1).success(false);
        }
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        if(!password.equals(AdminUser.getPassword())){
            System.out.println("密码错误");
//            model.addAttribute("error","密码错误");
            return new CommonResponseDto().code(1).success(false);
        }
        String rediskey = userService.putUserInfoToRedis(AdminUser);
        Cookie cookie=new Cookie("sessionId",rediskey);
        response.addCookie(cookie);
        AdminUser.setPassword("");//返回前端时密码隐蔽掉
//        model.addAttribute("user",AdminUser);
        CommonResponseDto result = new CommonResponseDto();
        result.setData(AdminUser);
        return result.code(200).success(true);
    }





    @RequestMapping(value = "/article/add",method = RequestMethod.POST)
    public  @ResponseBody
    CommonResponseDto articleAdd(@RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("typeId") Long typeId,
                           @RequestParam("userId") Long userId){
        log.info("title  {} , content  {} , typeId  {} , userId  {} , ",title,content,typeId,userId);
        return new CommonResponseDto().code(0).success(true);
    }

    @RequestMapping(value = "/picture/upload",method = RequestMethod.POST)
    public  @ResponseBody
    CommonResponseDto upLoadPicture(@RequestParam("picture") MultipartFile picture){
        log.info("picture  {} ",picture);
        return new CommonResponseDto().code(0).success(true);
    }

}
