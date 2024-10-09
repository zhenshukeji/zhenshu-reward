package com.zhenshu.reward.common.constant.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * @author jing
 * @version 1.0
 * @desc 接口文档根据下面的抽象方法展示枚举取值, 实现该接口的枚举将同时集成MyBatisPlus的通用枚举功能
 * @date 2022/2/16 0016 10:54
 * @see com.baomidou.mybatisplus.core.enums.IEnum
 * 推荐泛型使用{@link String}类型, 不推荐使用数字, SpringMVC接收枚举参数时, 如果传递的数字将会根据枚举下标进行转换而不是根据value属性
 **/
public interface IBaseEnum<E extends Serializable> extends IEnum<E> {

    /**
     * 枚举对应的值
     * @return 值
     */
    @Override
    @JsonValue
    E getValue();

    /**
     * 枚举对应的注释
     * @return 注释
     */
    String getInfo();

}
