package com.graduation.compusinfo.display.controller;

import com.graduation.compusinfo.display.dto.WangEditor;
import com.graduation.compusinfo.display.service.ArticleService;
import com.graduation.compusinfo.display.service.UserService;
import com.graduation.compusinfo.display.utils.CommonResponseDto;
import com.graduation.compusinfo.display.utils.FastDFSClientUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

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


//    @RequestMapping(value = "/login",method = RequestMethod.GET)
//    public String toLoginPage(Model model){
//        return "admin-login";
//    }

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
    WangEditor upLoadPicture(@RequestParam("myPicture") MultipartFile myPicture,
                             HttpServletRequest request){
        log.info("picture  {} ",myPicture);
        String separator = System.getProperty("file.separator");
        separator=separator.replaceAll("\\\\","/");
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +           request.getContextPath()+ separator; //获取项目路径+端口号 比如：http://localhost:8080/
        try {
            // 获取项目路径
            String realPath = request.getSession().getServletContext()
                    .getRealPath("");
            InputStream inputStream = myPicture.getInputStream();
            String filename = myPicture.getOriginalFilename();
            // 将文件上传的服务器根目录下的upload文件夹
            long size = myPicture.getSize();
            String url = fastDFSClientUtil.uploadFileStream(inputStream,size,filename);
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


}
