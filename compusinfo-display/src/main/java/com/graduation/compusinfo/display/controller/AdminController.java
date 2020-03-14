package com.graduation.compusinfo.display.controller;

import com.alibaba.druid.util.StringUtils;
import com.graduation.compusinfo.display.dto.WangEditor;
import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.entity.User;
import com.graduation.compusinfo.display.service.ArticleService;
import com.graduation.compusinfo.display.service.UserService;
import com.graduation.compusinfo.display.utils.CommonResponseDto;
import com.graduation.compusinfo.display.utils.FastDFSClientUtil;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private ArticleService articleService;

    @Autowired
    private FastDFSClientUtil fastDFSClientUtil;


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
        articleService.addArticle(title,content,typeId,userId);
        return new CommonResponseDto().code(0).success(true);
    }

    @RequestMapping(value = "/picture/upload",method = RequestMethod.POST)
    public  @ResponseBody
    WangEditor upLoadPicture(@RequestParam("myPicture") MultipartFile multipartFile,
                             HttpServletRequest request){
        log.info("picture  {} ",multipartFile);
        String separator = System.getProperty("file.separator");
        separator=separator.replaceAll("\\\\","/");
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +           request.getContextPath()+ separator; //获取项目路径+端口号 比如：http://localhost:8080/
        try {
            // 获取项目路径
            String realPath = request.getSession().getServletContext()
                    .getRealPath("");
            InputStream inputStream = multipartFile.getInputStream();
//            String contextPath = request.getContextPath();
            // 服务器根目录的路径
//            String path = realPath.replace(contextPath.substring(1), "");
            // 根目录下新建文件夹upload，存放上传图片
//            String uploadPath = "path" + "upload";
            // 获取文件名称
            String filename = multipartFile.getOriginalFilename();
            // 将文件上传的服务器根目录下的upload文件夹
            long size = multipartFile.getSize();
//            InputStream is = null;
//            is = multipartFile.getFile(param).getInputStream();

            String url = fastDFSClientUtil.uploadFileStream(inputStream,size,filename);
//            File file = new File(uploadPath, filename);
//            FileUtils.copyInputStreamToFile(inputStream, file);
            // 返回图片访问路径
//            String url = request.getScheme() + "://" + request.getServerName()
//                    + ":" + request.getServerPort() + "/upload/" + filename;
            log.info("图片的url："+url);
            String [] str = {url};
            WangEditor we = new WangEditor(str);
            return we;
        } catch (IOException e) {
            log.error("上传文件失败", e);
        }
        return null;
    }

    /**
     *
     * @param multipartFile
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/picture/up",method = RequestMethod.POST)
    public Map<String,String> fileUp(MultipartHttpServletRequest multipartFile, HttpServletRequest request){

        Map<String,String> result = new HashMap<String,String>();
        String param = request.getParameter("myPicture");//参数名称
        if(StringUtils.isEmpty(param)){
            result.put("result","false");
            result.put("msg","请添加参数");
        }
        InputStream is = null;

        String fileName = multipartFile.getFile(param).getOriginalFilename();
        try {
            long size = multipartFile.getFile(param).getSize();
            is = multipartFile.getFile(param).getInputStream();
            String path = fastDFSClientUtil.uploadFileStream(is,size,fileName);
            result.put("result","true");
            //图片地址
            result.put("srckey",path);
        }catch (IOException e){
            result.put("result","false");
            log.error("file:"+fileName,e.fillInStackTrace());
        }finally {
            if (is !=null){
                try {
                    is.close();
                }catch (IOException io){
                    log.error(io.getMessage());
                }
            }
        }
        return result;
    }




}
