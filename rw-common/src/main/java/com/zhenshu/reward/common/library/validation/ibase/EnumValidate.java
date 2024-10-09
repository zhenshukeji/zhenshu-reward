package com.zhenshu.reward.common.library.validation.ibase;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/8/16 13:58
 * @desc 枚举校验注解, 仅支持实现了IBaseEnum接口的枚举校验
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IBaseEnumValidator.class})
@Documented
public @interface EnumValidate {

    String message() default "数据不在指定集合中";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?>[] target() default {};

    /**
     * 枚举getCode()获取的值, 会将getCode()方法返回的值转成String类型进行比较
     */
    String[] value();

    /**
     * 是否包含value中列举的所有值, 反之不包含
     */
    boolean include() default true;
}
