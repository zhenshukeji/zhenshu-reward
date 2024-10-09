package com.zhenshu.reward.common.constant.annotation;

import com.zhenshu.reward.common.constant.enums.KeyDataSource;

import java.lang.annotation.*;

/**
 * @author hualong
 * @version 1.0
 * @desc 用于做防重点的锁
 * @date 2021/2/19 0019 10:59
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LockFunction {

    /**
     * 方法名称，用于区分不同的请求
     * {@link this#methodName()}将于与{@link this#keyName()}、{@link this#isObj()}、{@link this#form()}三个参数指定的key共同组成一个LockKey
     */
    String methodName();

    /**
     * 数据源取值属性名名称
     * 当{@link this#isObj()}为true时将从指定数据源中取出{@link this#keyName()}属性对应的值作为key
     * 当{@link this#isObj()}为false时将会使用{@link String#valueOf(Object)}将整个数据源转换成字符串作为key
     */
    String keyName() default "";

    /**
     * 指定key的数据来源
     * 当{@link this#form()}为{@link KeyDataSource#NONE}时, {@link this#keyName()}与{@link this#isObj()}参数将会被忽略
     */
    KeyDataSource form() default KeyDataSource.PARAM;

    /**
     * 入参是object 还是一个基本类型包装类，比如long
     */
    boolean isObj() default true;

}
