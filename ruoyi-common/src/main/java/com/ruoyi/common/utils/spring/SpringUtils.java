package com.ruoyi.common.utils.spring;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 *
 * 该工具类通过实现BeanFactoryPostProcessor和ApplicationContextAware接口，
 * 在Spring容器初始化时保存ApplicationContext和BeanFactory的引用，以便在非Spring管理的环境中获取bean
 * @author ruoyi
 */
@Component
public final class SpringUtils implements BeanFactoryPostProcessor, ApplicationContextAware
{
    /** Spring应用上下文环境*/

    /**
     * 用于存储ConfigurableListableBeanFactory的AtomicReference
     */
    private static final AtomicReference<Optional<ConfigurableListableBeanFactory>> beanFactoryRef = new AtomicReference<>();

    /**
     * 用于存储ApplicationContext的AtomicReference
     */
    private static final AtomicReference<Optional<ApplicationContext>> applicationContextRef = new AtomicReference<>();

    private static final String UNINITIALIZED_EXCEPTION = " is not initialized yet.";

    private SpringUtils(){}

    /**
     * 在BeanFactory后处理器中设置beanFactory引用
     *
     * @param beanFactory ConfigurableListableBeanFactory实例
     * @throws BeansException 如果beanFactory设置过程中出现异常
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException
    {
        beanFactoryRef.set(Optional.of(beanFactory));
    }

    /**
     * 设置ApplicationContext引用
     *
     * @param applicationContext ApplicationContext实例
     * @throws BeansException 如果applicationContext设置过程中出现异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        applicationContextRef.set(Optional.of(applicationContext));
    }

    /**
     * 该方法接受一个参数，并返回一个异常 Supplier
     *
     * @param clz    异常所属类
     * @param suffix 异常信息后缀
     * @return 返回RuntimeException的Supplier
     */
    private static Supplier<RuntimeException> exceptionSupplier(Class<?> clz, String suffix) {
        return () -> new IllegalStateException(generateErrorMessage(clz, suffix));
    }

    /**
     * 该方法接受一个参数，并返回一个异常 Supplier
     *
     * @param clz    异常所属类
     * @param suffix 异常信息后缀
     * @return 返回拼接好的异常信息 类名+异常信息后缀
     */
    private static String generateErrorMessage(Class<?> clz,String suffix) {
        Function<String, String> errorMessageFunction = errorMessageGenerator(suffix);
        return errorMessageFunction.apply(clz.getSimpleName());
    }

    /**
     * 该方法接受一个参数，返回拼接功能的Function
     *
     * @param suffix 异常信息后缀
     * @return 返回拼接功能的Function
     */
    private static Function<String, String> errorMessageGenerator(String suffix) {
        return parameter -> parameter + suffix;
    }

    /**
     * 获取对象
     *
     * @param name
     * @return Object 一个以所给名字注册的bean的实例
     * @throws org.springframework.beans.BeansException
     *
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException
    {
        return (T) beanFactoryRef.get()
                   .orElseThrow(exceptionSupplier(ConfigurableListableBeanFactory.class, UNINITIALIZED_EXCEPTION))
                   .getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     *
     * @param clz
     * @return
     * @throws org.springframework.beans.BeansException
     *
     */
    public static <T> T getBean(Class<T> clz) throws BeansException
    {
        return beanFactoryRef.get()
                .orElseThrow(exceptionSupplier(ConfigurableListableBeanFactory.class, UNINITIALIZED_EXCEPTION))
                .getBean(clz);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name
     * @return boolean
     */
    public static boolean containsBean(String name)
    {
        return beanFactoryRef.get()
                .orElseThrow(exceptionSupplier(ConfigurableListableBeanFactory.class,UNINITIALIZED_EXCEPTION))
                .containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param name
     * @return boolean
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     *
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException
    {
        return beanFactoryRef.get()
                .orElseThrow(exceptionSupplier(ConfigurableListableBeanFactory.class,UNINITIALIZED_EXCEPTION))
                .isSingleton(name);
    }

    /**
     * @param name
     * @return Class 注册对象的类型
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     *
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException
    {
        return beanFactoryRef.get()
                .orElseThrow(exceptionSupplier(ConfigurableListableBeanFactory.class,UNINITIALIZED_EXCEPTION))
                .getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     *
     * @param name
     * @return
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     *
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException
    {
        return beanFactoryRef.get()
                .orElseThrow(exceptionSupplier(ConfigurableListableBeanFactory.class,UNINITIALIZED_EXCEPTION))
                .getAliases(name);
    }

    /**
     * 获取aop代理对象
     *
     * @param invoker
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy(T invoker)
    {
        return (T) AopContext.currentProxy();
    }

    /**
     * 获取当前的环境配置，无配置返回null
     *
     * @return 当前的环境配置
     */
    public static String[] getActiveProfiles()
    {
        return applicationContextRef
                .get()
                .orElseThrow(exceptionSupplier(ApplicationContext.class, UNINITIALIZED_EXCEPTION))
                .getEnvironment()
                .getActiveProfiles();
    }

    /**
     * 获取当前的环境配置，当有多个环境配置时，只获取第一个
     *
     * @return 当前的环境配置
     */
    public static String getActiveProfile()
    {
        final String[] activeProfiles = getActiveProfiles();
        return StringUtils.isNotEmpty(activeProfiles) ? activeProfiles[0] : null;
    }

    /**
     * 获取配置文件中的值
     *
     * @param key 配置文件的key
     * @return 当前的配置文件的值
     *
     */
    public static String getRequiredProperty(String key)
    {
        return  applicationContextRef
                .get()
                .orElseThrow(exceptionSupplier(ApplicationContext.class, UNINITIALIZED_EXCEPTION))
                .getEnvironment()
                .getRequiredProperty(key);
    }

}
