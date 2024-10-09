package com.zhenshu.reward.admin.polymerization.points.domain.vo;

import com.zhenshu.reward.common.constant.enums.ErrorEnums;
import com.zhenshu.reward.common.constant.exception.ServiceException;
import com.zhenshu.reward.common.utils.json.JsonUtil;
import com.zhenshu.reward.common.library.validation.ValidationUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author hong
 * @version 1.0
 * @date 2024/5/17 15:38
 * @desc 任务
 */
@Data
@ApiModel(description = "任务")
public class BkTaskDetailsHandlerVO {
    /**
     * 活动信息
     */
    @ApiModelProperty(required = false, value = "任务信息")
    private Map<String, Object> activity;

    /**
     * 获取活动信息并进行JSR303
     */
    public <T> T activity(Class<T> tClass) {
        T t;
        try {
            t = JsonUtil.toObject(activity, tClass);
        } catch (Exception e) {
            throw new ServiceException(ErrorEnums.BAD_REQUEST, e);
        }
        ValidationUtil.exceptionValidate(t);
        return t;
    }
}
