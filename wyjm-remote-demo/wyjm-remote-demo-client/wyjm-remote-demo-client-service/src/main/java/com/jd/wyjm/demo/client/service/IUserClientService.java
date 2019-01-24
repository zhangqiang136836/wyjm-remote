package com.jd.wyjm.demo.client.service;

/**
 * 用户客户端服务层接口
 *
 * @author zhangqiang200
 * @date 2019-01-23
 */
public interface IUserClientService {
    /**
     * 通过用户ID，查询用户名称。
     * @param userId
     * @return
     */
    String  queryUserNameByUserId(Long userId);
}
