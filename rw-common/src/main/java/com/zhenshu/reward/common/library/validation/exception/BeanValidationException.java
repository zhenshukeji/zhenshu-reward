package com.zhenshu.reward.common.library.validation.exception;

import cn.hutool.extra.validation.BeanValidationResult;

import java.util.stream.Collectors;

/**
 * @author hong
 * @version 1.0
 * @date 2024/5/30 15:02
 * @desc 校验异常
 */
public class BeanValidationException extends RuntimeException {
    private final BeanValidationResult result;

    public BeanValidationException(BeanValidationResult result) {
        this.result = result;
    }

    @Override
    public String getMessage() {
        return result.getErrorMessages()
                .stream()
                .map(BeanValidationResult.ErrorMessage::toString)
                .collect(Collectors.joining());
    }
}
