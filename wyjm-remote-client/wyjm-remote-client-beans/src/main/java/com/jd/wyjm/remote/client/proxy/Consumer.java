package com.jd.wyjm.remote.client.proxy;

import lombok.Data;

/**
 *
 * 消费者实体类
 *
 * @author zhangqiang200
 * date 2019-01-23
 */
@Data
public class Consumer {
    /**
     * 自定义接口ID编号
     */
    private String   id;
    /**
     * 接口服务器对应IP地址
     */
    private String   ip;
    /**
     * 服务器对应端口
     */
    private Integer  port;
    /**
     * 接口类型
     */
    private String   interfaceClass;
}
