package com.graduation.compusinfo.display.config;

/**
 * @author zzk
 * @date 2020/3/14 2:16
 */

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

@Configuration
@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class FastClientImporter {
//    #导入FastDFS-Client组件
//    #解决jmx重复注册bean的问
}
