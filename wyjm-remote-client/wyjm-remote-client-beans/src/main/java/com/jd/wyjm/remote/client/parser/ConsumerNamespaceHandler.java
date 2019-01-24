package com.jd.wyjm.remote.client.parser;
import com.jd.wyjm.remote.client.proxy.Consumer;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 注册消费者节点数据
 *
 * @author zhangqiang200
 * @date 2019-01-23
 */
public class ConsumerNamespaceHandler extends NamespaceHandlerSupport {
    /**
     * 消费者xml配置节点
     */
    private final String CONSUMER="consumer";

    /**
     * 初始化消费者信息到ConfigurableListableBeanFactory列表
     */
    @Override
    public void init() {
        registerBeanDefinitionParser(CONSUMER,new ConsumerBeanDefinitionParser(Consumer.class));
    }
}
