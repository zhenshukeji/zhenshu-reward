package com.zhenshu.reward.common.constant.game.rule;

import com.zhenshu.reward.common.constant.enums.ErrorEnums;
import com.zhenshu.reward.common.constant.enums.points.TaskType;
import com.zhenshu.reward.common.constant.exception.ServiceException;
import com.zhenshu.reward.common.utils.json.JsonUtil;
import com.zhenshu.reward.common.library.validation.ValidationUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author hong
 * @version 1.0
 * @date 2024/5/16 16:19
 * @desc 任务规则
 */
public interface PointsTaskRule {
    /**
     * 校验参数
     *
     * @return 结果
     */
    default boolean verify() {
        return true;
    }

    /**
     * JSR303参数检查
     */
    default void jsr303() {
        ValidationUtil.exceptionValidate(this);
        if (!this.verify()) {
            throw new ServiceException(ErrorEnums.BAD_REQUEST);
        }
    }

    /**
     * 限制规则集合
     * <p>
     * 获取限制规则, 限制检查能力由 TaskRestrictionProvider 提供
     * </p>
     *
     * @return 结果
     */
    default List<Object> restrictions() {
        return Collections.emptyList();
    }

    /**
     * 获取规则并进行参数检查
     *
     * @param ruleJson 规则json
     * @param taskType 任务类型
     * @return 规则
     */
    static PointsTaskRule rule(String ruleJson, TaskType taskType) {
        return PointsTaskRule.rule(aClass -> JsonUtil.toObject(ruleJson, aClass), taskType);
    }

    /**
     * 获取规则并进行参数检查
     *
     * @param ruleObject 规则
     * @param taskType   任务类型
     * @return 规则
     */
    static PointsTaskRule rule(Map<String, Object> ruleObject, TaskType taskType) {
        return PointsTaskRule.rule(aClass -> JsonUtil.toObject(ruleObject, aClass), taskType);
    }

    /**
     * 获取规则并进行参数检查
     *
     * @param ruleFunction 规则
     * @param taskType     任务类型
     * @return 规则
     */
    static PointsTaskRule rule(Function<Class<? extends PointsTaskRule>, PointsTaskRule> ruleFunction, TaskType taskType) {
        PointsTaskRule rule;
        try {
            rule = ruleFunction.apply(taskType.getRule());
        } catch (Exception e) {
            throw new ServiceException(ErrorEnums.BAD_REQUEST, e);
        }
        rule.jsr303();
        return rule;
    }
}
