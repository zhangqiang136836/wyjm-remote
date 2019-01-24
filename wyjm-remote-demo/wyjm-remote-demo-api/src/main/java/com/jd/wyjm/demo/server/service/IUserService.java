package com.jd.wyjm.demo.server.service;

/**
 * 用户服务层接口
 *
 * @author zhangqiang200
 * @date 2019-01-23
 */
public interface IUserService {
    /**
     * 通过用户ID，查询出用户名称。
     * @param userId
     * @return
     */
    String getUserNameByUserId(Long userId);
}
