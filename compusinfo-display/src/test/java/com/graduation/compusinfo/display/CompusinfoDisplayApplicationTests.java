package com.graduation.compusinfo.display;

import com.graduation.compusinfo.display.bean.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ComponentScan
@Slf4j
class CompusinfoDisplayApplicationTests {

    @Test
    void contextLoads() {
        String password="mysadfk";
        System.out.println(DigestUtils.md5DigestAsHex(password.getBytes()));
        Student s1 = new Student(1L, "肖战", 15, "浙江");
        Student s2 = new Student(2L, "王一博", 15, "湖北");
        Student s3 = new Student(3L, "杨紫", 17, "北京");
        Student s4 = new Student(4L, "李现", 17, "浙江");
        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.stream().filter(x->"肖战".equals(x.getName())).forEach(System.out::println);
        students.stream().skip(2).forEach(System.out::println);
        List<Student> streamStudents = testFilter(students);
        streamStudents.forEach(System.out::println);
        testMap(students);
    }

    /**
     * 集合的筛选
     * @param students
     * @return
     */
    @Test
    private  List<Student> testFilter(List<Student> students) {
        //筛选年龄大于15岁的学生
//        return students.stream().filter(s -> s.getAge()>15).collect(Collectors.toList());
        //筛选住在浙江省的学生

        return students.stream().filter(s ->"浙江".equals(s.getAddress())).collect(Collectors.toList());

    }
    /**
     * 集合转换
     * @param students
     * @return
     */
    @Test
    private  void testMap(List<Student> students) {
        //在地址前面加上部分信息，只获取地址输出
        log.info("success");
        String password="mysadfk";
        System.out.println(DigestUtils.md5DigestAsHex(password.getBytes()));
        List<String> addresses = students.stream().map(s ->"住址:"+s.getAddress()).collect(Collectors.toList());
        addresses.forEach(a ->System.out.println(a));
    }

    @Autowired
    ApplicationContext context;

    @Autowired
    Student student;

    @Test
    public void testStudent(){
        log.info("开始表演");
        Object apiServiceImpl = context.getBean("apiServiceImpl");
        System.out.println("apiServiceImpl:"+apiServiceImpl);
        System.out.println("student:"+student);
        Object Student = context.getBean("student");
        System.out.println("Student:"+Student);
        System.out.println(student==Student);
        System.out.println(DigestUtils.md5DigestAsHex(Student.toString().getBytes()));
        log.info("结束表演");

    }



}
