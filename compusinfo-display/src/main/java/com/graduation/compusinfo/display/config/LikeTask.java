package com.graduation.compusinfo.display.config;

import com.graduation.compusinfo.display.service.UserLikeService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 点赞从Redis回写到Mysql定时任务
 * @author zzk
 * @date 2020/3/25 11:55
 */
@Slf4j
public class LikeTask extends QuartzJobBean {
    @Autowired
    UserLikeService userLikeService;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        log.info("LikeTask START : -- {}",sdf.format(new Date()));
        //将 Redis 里的点赞信息同步到数据库里
        userLikeService.saveRedisLikeData2DB();
        userLikeService.SaveRedisLikeCount2DB();
    }
}
