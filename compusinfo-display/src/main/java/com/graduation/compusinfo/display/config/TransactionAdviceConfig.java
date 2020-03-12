package com.graduation.compusinfo.display.config;

import org.springframework.context.annotation.Configuration;

/**
 * 全局事务管理
 * @author zzhengkai
 * @date 2019/12/9 19:08
 */
@Configuration
public class TransactionAdviceConfig {

    //private static final String TRANSACTION_POINTCUT_EXPRESSION = "execution(* com.graduation.compusinfo.display..*Service.*(..))";
    //
    //@Autowired
    //private PlatformTransactionManager transactionManager;
    //
    //@Bean
    //public TransactionInterceptor txAdvice() {
    //    Properties properties = new Properties();
    //
    //    properties.setProperty("get*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,readOnly");   // 单个对象
    //    properties.setProperty("list*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,readOnly");  // 多个对象
    //    properties.setProperty("count*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,readOnly"); // 统计值
    //    properties.setProperty("add*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
    //    properties.setProperty("insert*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
    //    properties.setProperty("save*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
    //    properties.setProperty("batch*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
    //    properties.setProperty("update*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
    //    properties.setProperty("delete*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
    //    properties.setProperty("*", "PROPAGATION_REQUIRED,ISOLATION_DEFAULT");
    //
    //    return new TransactionInterceptor(transactionManager, properties);
    //}
    //
    //@Bean
    //public Advisor txAdviceAdvisor() {
    //    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    //    pointcut.setExpression(TRANSACTION_POINTCUT_EXPRESSION);
    //    return new DefaultPointcutAdvisor(pointcut, txAdvice());
    //}



}
