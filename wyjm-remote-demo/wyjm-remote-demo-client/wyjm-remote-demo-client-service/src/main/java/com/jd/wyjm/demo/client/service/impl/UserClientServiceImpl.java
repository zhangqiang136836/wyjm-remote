package com.jd.wyjm.demo.client.service.impl;

import com.jd.wyjm.demo.client.service.IUserClientService;
import com.jd.wyjm.demo.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户客户端服务层实现类
 */
public class UserClientServiceImpl implements IUserClientService {
    /**
     * RPC用户服务层接口注入
     */
    @Autowired
    private IUserService userService;

    @Override
    public String queryUserNameByUserId(Long userId) {
        return userService.getUserNameByUserId(userId);
    }
}
