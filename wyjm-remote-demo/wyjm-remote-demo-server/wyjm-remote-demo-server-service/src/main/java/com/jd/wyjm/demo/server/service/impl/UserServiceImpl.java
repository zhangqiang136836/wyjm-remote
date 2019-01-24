package com.jd.wyjm.demo.server.service.impl;

import com.jd.wyjm.demo.server.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * 用户服务层实现类
 *
 * @author zhangqiang200
 * @date 2019-01-23
 */
@Service
public class UserServiceImpl implements IUserService {
    /**
     * 通过用户ID，查询到用户名称。
     * @param userId
     * @return
     */
    @Override
    public String getUserNameByUserId(Long userId) {
        String userName="张强";
        System.out.println("==============用户ID："+userId+"===========");
        return userName;
    }
}
