package com.graduation.compusinfo.display.controller;

import com.github.pagehelper.PageInfo;
import com.graduation.compusinfo.display.dto.WangEditor;
import com.graduation.compusinfo.display.entity.Article;
import com.graduation.compusinfo.display.entity.Comment;
import com.graduation.compusinfo.display.service.ArticleService;
import com.graduation.compusinfo.display.service.CommentService;
import com.graduation.compusinfo.display.service.UserService;
import com.graduation.compusinfo.display.utils.CommonResponseDto;
import com.graduation.compusinfo.display.utils.FastDFSClientUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
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
    private CommentService commentService;

    @Autowired
    private FastDFSClientUtil fastDFSClientUtil;

    @RequestMapping(value = "/userInfo",method = RequestMethod.GET)
    public String toUserInfoPage(Model model){
        return "admin-userInfo";
    }

    @PostMapping("/article/public")
    @ApiOperation("发布文章")
    public  @ResponseBody
    CommonResponseDto articleAdd(@RequestParam("title") String title,
                                 @RequestParam("content") String content,
                                 @RequestParam("typeId") Long typeId,
                                 @RequestParam("titleImgUrl") String titleImgUrl,
                                 @RequestParam("userId") Long userId){
        log.info("title  {} , content  {} , typeId  {} , userId  {} ,titleImgUrl;{} ",title,content,typeId,userId,titleImgUrl);

        articleService.addArticle(title,content,typeId,titleImgUrl,userId);
        return new CommonResponseDto().code(0).success(true);
    }

    @PostMapping("/picture/upload")
    @ApiOperation("图片上传到FastDFS")
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
            // 返回图片访问路径
            String url = fastDFSClientUtil.uploadFileStream(inputStream,size,filename);
            log.info("图片的url："+url);
            String [] str = {url};
            WangEditor we = new WangEditor(str);
            return we;
        } catch (IOException e) {
            log.error("上传文件失败", e);
        }
        return null;
    }
    @GetMapping("/getAllTalk")
    @ApiOperation("获取该用户文章所有被评论信息")
    public  @ResponseBody
    CommonResponseDto<PageInfo<Comment>> getAllTalk(@RequestParam("user_id") Long user_id,
                                                          @RequestParam("pageNum") int pageNum,
                                                          @RequestParam("pageSize") int pageSize){
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        log.info("user_id  {} , pageNum  {} , pageSize  {}  , ",user_id,pageNum,pageSize);
        PageInfo<Comment> listCommentInfo = commentService.userAllArticleComment(user_id, pageNum, pageSize);
        commonResponseDto.setData(listCommentInfo);
        return commonResponseDto.code(200).success(true);
    }

    @GetMapping("/searchAllTalk")
    @ApiOperation("获取该用户文章所有被评论信息")
    public  @ResponseBody
    CommonResponseDto<PageInfo<Comment>> searchAllTalk(@RequestParam("user_id") Long user_id,
                                                       @RequestParam("searchVal") String searchVal,
                                                       @RequestParam("pageNum") int pageNum,
                                                       @RequestParam("pageSize") int pageSize){
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        log.info("user_id  {} , pageNum  {} , pageSize  {}  searchVal:{}, ",user_id,pageNum,pageSize,searchVal);
        PageInfo<Comment> listCommentInfo = commentService.ArticleCommentBySearchVal(user_id, pageNum, pageSize,searchVal);
        commonResponseDto.setData(listCommentInfo);
        return commonResponseDto.code(200).success(true);
    }

    @GetMapping("/variousIndicators")
    @ApiOperation("后台首页获取各项指标")
    public  @ResponseBody
    CommonResponseDto<Map<String,Integer>> variousIndicators(@RequestParam("userId") Long userId){
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        log.info("userId  {}  ",userId);
        Map<String,Integer> variousIndicators = articleService.getvariousIndicators(userId);
        commonResponseDto.setData(variousIndicators);
        return commonResponseDto.code(200).success(true);
    }

    @GetMapping("/managecharTable")
    @ApiOperation("后台内容管理获取文章指标")
    public  @ResponseBody
    CommonResponseDto<Map<String,Integer>> getManagecharTable(@RequestParam("userId") Long userId,
                                                              @RequestParam("pageNum") int pageNum,
                                                              @RequestParam("pageSize") int pageSize){
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        log.info("userId  {} , ",userId);
        PageInfo<Article> articleList = articleService.selectAdminArticleList(userId,pageNum,pageSize);
        commonResponseDto.setData(articleList);
        return commonResponseDto.code(200).success(true);
    }

}
