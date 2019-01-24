package com.jd.wyjm.remote.client.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * 初始化实体时，解析消费者配置文件
 *
 * @author zhangqiang200
 * @date 2019-01-23
 */
public class ConsumerBeanDefinitionParser implements BeanDefinitionParser {
    /**
     * 实体Bean的类型
     */
    private final Class<?> beanClass;
    /**
     * ID
     */
    private final String ID="id";
    /**
     * IP
     */
    private final String IP="ip";
    /**
     * PORT
     */
    private final String PORT="port";
    /**
     * INTERFACE_CLASS
     */
    private final String INTERFACE_CLASS="interfaceClass";
    /**
     * 设置实体Bean的类型
     * @param beanClass
     */
    public ConsumerBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * 通过xml节点，实例化Bean，并设置属性。
     * @param element
     * @param parserContext
     * @return
     */
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        //设置实体类型
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        //IP地址
        beanDefinition.getPropertyValues().add(IP, element.getAttribute(IP));
        //端口号
        beanDefinition.getPropertyValues().add(PORT, element.getAttribute(PORT));
        //接口类型名称
        beanDefinition.getPropertyValues().add(INTERFACE_CLASS, element.getAttribute(INTERFACE_CLASS));
        BeanDefinitionRegistry beanDefinitionRegistry = parserContext.getRegistry();
        //注册bean到BeanDefinitionRegistry中
        beanDefinitionRegistry.registerBeanDefinition(element.getAttribute(ID), beanDefinition);
        return beanDefinition;
    }
}
