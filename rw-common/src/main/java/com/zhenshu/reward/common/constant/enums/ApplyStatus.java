package com.zhenshu.reward.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 意见反馈处理状态
 *
 * @author zhenshu
 */
@Getter
@AllArgsConstructor
public enum ApplyStatus implements IBaseEnum<String> {
    /**
     * 0 未处理 1 已标记
     */
    UNHANDLED("unhandled", "未处理"),
    MARKED("marked", "已标记"),
    ;

    private final String value;

    private final String info;

}
