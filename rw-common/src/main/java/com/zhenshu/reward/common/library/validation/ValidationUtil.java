package com.zhenshu.reward.common.library.validation;

import cn.hutool.extra.validation.BeanValidationResult;
import com.zhenshu.reward.common.library.validation.exception.BeanValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author hong
 * @version 1.0
 * @date 2024/5/30 14:37
 * @desc JSR303校验工具类
 */
public class ValidationUtil {
    /**
     * 验证器
     */
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 获取验证器
     *
     * @return 验证器
     */
    public static Validator getValidator() {
        return VALIDATOR;
    }

    /**
     * bean校验
     *
     * @param bean   bean
     * @param groups 分组
     * @return 结果
     */
    public static <T> Set<ConstraintViolation<T>> validate(T bean, Class<?>... groups) {
        return VALIDATOR.validate(bean, groups);
    }

    /**
     * bean校验并转换成BeanValidationResult
     *
     * @param bean   bean
     * @param groups 分组
     * @return 结果
     */
    public static <T> BeanValidationResult warpValidate(T bean, Class<?>... groups) {
        return warpBeanValidationResult(validate(bean, groups));
    }

    /**
     * bean校验, 校验不通过则抛出 BeanValidationException 异常
     *
     * @param bean   bean
     * @param groups 分组
     * @throws BeanValidationException bean校验异常
     */
    public static <T> void exceptionValidate(T bean, Class<?>... groups) throws BeanValidationException {
        warpBeanValidationException(validate(bean, groups));
    }

    /**
     * Set<ConstraintViolation<T>> 转 BeanValidationResult
     *
     * @param constraintViolations 约束违法集合
     * @return BeanValidationResult
     */
    private static <T> BeanValidationResult warpBeanValidationResult(Set<ConstraintViolation<T>> constraintViolations) {
        BeanValidationResult result = new BeanValidationResult(constraintViolations.isEmpty());
        for (ConstraintViolation<T> violation : constraintViolations) {
            BeanValidationResult.ErrorMessage errorMessage = new BeanValidationResult.ErrorMessage();
            errorMessage.setPropertyName(violation.getPropertyPath().toString());
            errorMessage.setMessage(violation.getMessage());
            errorMessage.setValue(violation.getInvalidValue());
            result.addErrorMessage(errorMessage);
        }
        return result;
    }

    /**
     * Set<ConstraintViolation<T>> 不为空时抛出 BeanValidationException
     *
     * @param constraintViolations 约束违法集合
     * @throws BeanValidationException bean校验异常
     */
    private static <T> void warpBeanValidationException(Set<ConstraintViolation<T>> constraintViolations) throws BeanValidationException {
        BeanValidationResult result = warpBeanValidationResult(constraintViolations);
        if (result.isSuccess()) {
            return;
        }
        throw new BeanValidationException(result);
    }
}
