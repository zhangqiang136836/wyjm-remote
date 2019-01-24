package com.jd.wyjm.remote.client.register;

import com.jd.wyjm.remote.client.proxy.Consumer;
import com.jd.wyjm.remote.client.proxy.DynamicProxyBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * 动态代理注入到Spring的beanFactory
 *
 * @author zhangqiang200
 * @date 2019-01-23
 */
@Component
public class DynamicProxyRegister implements BeanFactoryPostProcessor {
    /**
     * beanFactory列表
     */
    private DefaultListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        //初始化beanFactory
        this.beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        //获取到接口配置名称列表
        String[] beanNames = configurableListableBeanFactory.getBeanNamesForType(Consumer.class);
        for (String beanName : beanNames) {
            //获取到消费者数据信息
            Consumer consumer = (Consumer) configurableListableBeanFactory.getBean(beanName);
            //实例化一个动态代理Bean
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DynamicProxyBean.class);
            //设置接口类
            beanDefinitionBuilder.addPropertyValue("interfaceClass", consumer.getInterfaceClass());
            //设置IP地址
            beanDefinitionBuilder.addPropertyValue("ip", consumer.getIp());
            //设置端口
            beanDefinitionBuilder.addPropertyValue("port", consumer.getPort());
            //注入远程调用客户端
            beanDefinitionBuilder.addPropertyReference("clientRemoteCall", "clientRemoteCall");
            //接口注入到beanFactory的列表
            this.beanFactory.registerBeanDefinition(consumer.getInterfaceClass(), beanDefinitionBuilder.getRawBeanDefinition());
        }
    }
}
