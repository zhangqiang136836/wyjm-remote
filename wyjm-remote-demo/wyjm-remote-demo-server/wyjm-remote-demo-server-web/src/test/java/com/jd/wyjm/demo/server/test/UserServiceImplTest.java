package com.jd.wyjm.demo.server.test;


import com.jd.wyjm.demo.server.service.IUserService;
import com.jd.wyjm.demo.server.web.app.WebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 用户服务层实现类
 *
 * @author zhangqiang200
 * @date 2019-01-23
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class UserServiceImplTest {

    @Autowired
    private IUserService userService;

    @Test
    public void getUserNameByUserId(){
      String  userName =  userService.getUserNameByUserId(1000L);
      System.out.println("用户名称是："+userName);
    }
}
