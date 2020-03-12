package com.graduation.compusinfo.display.service;

import com.graduation.compusinfo.display.bean.Student;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zzhengkai
 * @date 2019/11/21 11:12
 */
@Service
public class apiServiceImpl extends apiService implements InitializingBean {


    @Autowired
    private Student Student;

    @Override
    public void afterPropertiesSet() throws Exception {
        Student.setAddress("广东汕头");
        Student.setAge(18);
        System.out.println(Student.getAddress()+":"+Student.getAge());
        //HttpClient httpClient = HttpClientBuilder.create().build();
        //HttpGet httpGet = new HttpGet("http://10.194.186.230:8788/api/rest_j/v1/publicservice/getDirFileTrees?path=file:///tmp/linkis/hadoop");
        //httpGet.addHeader("Cookie","bdp-user-ticket-id=M7UZXQP9Ld1QEfW0EL0kBi8ib+uxWhId2HLiVea4FcQ=");
        //String entityStr;
        //try {
        //    HttpResponse httpResponse = httpClient.execute(httpGet);
        //    HttpEntity entity = httpResponse.getEntity();
        //    StatusLine statusLine = httpResponse.getStatusLine();
        //    int statusCode = statusLine.getStatusCode();
        //    System.out.println("statusCode:"+statusCode);
        //    entityStr = EntityUtils.toString(entity);
        //    System.out.println("响应返回内容:"+entityStr);
        //    //JSONObject j = new JSONObject(entityStr);
        //    //Object data = j.get("data");
        //    //System.out.println(data.toString());
        //}catch (Exception e) {
        //    // TODO Auto-generated catch block
        //    e.printStackTrace();
        //}
        //System.out.println("---------------------");
        //HttpPost httpPost = new HttpPost("http://10.194.186.230:8788/api/rest_j/v1/publicservice/createNewDir");
        //httpPost.addHeader("Cookie","bdp-user-ticket-id=M7UZXQP9Ld1Wr0gzmORixF2z2ZFQASsc2HLiVea4FcQ=");
        //httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        //String content = "{\"path\": \""  + "file:///tmp/linkis/hadoop/wzm2\"}";
        ////组织数据
        //StringEntity se = new StringEntity(content);
        ////设置编码格式
        //se.setContentEncoding("UTF-8");
        ////设置数据类型
        //se.setContentType("application/json");
        ////对于POST请求,把请求体填充进HttpPost实体.
        //httpPost.setEntity(se);
        //HttpResponse response = httpClient.execute(httpPost);
        //int code = response.getStatusLine().getStatusCode();
        //System.out.print(code);
        //HttpEntity entity = response.getEntity();
        //String result = EntityUtils.toString(entity,"utf-8");
        //System.out.print("响应返回内容:"+result);
    }


}
