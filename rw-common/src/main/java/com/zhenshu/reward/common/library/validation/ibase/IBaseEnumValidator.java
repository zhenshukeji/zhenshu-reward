package com.zhenshu.reward.common.library.validation.ibase;


import com.zhenshu.reward.common.constant.enums.IBaseEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/8/16 13:59
 * @desc EnumValidate注解对应的校验器
 */
public class IBaseEnumValidator implements ConstraintValidator<EnumValidate, IBaseEnum<?>> {

    private Set<String> set;
    private boolean include;

    /**
     * 初始化方法
     *
     * @param constraintAnnotation 注解的详细信息都在这个对象中
     */
    @Override
    public void initialize(EnumValidate constraintAnnotation) {
        set = Arrays.stream(constraintAnnotation.value()).collect(Collectors.toSet());
        include = constraintAnnotation.include();
    }

    /**
     * 判断是否校验成功
     *
     * @param value   需要校验的值
     * @param context 校验的上下文环境信息
     * @return 校验结果
     */
    @Override
    public boolean isValid(IBaseEnum<?> value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }
        String code = String.valueOf(value.getValue());
        boolean contains = set.contains(code);
        return include == contains;
    }
}
