package com.zhenshu.reward.common.constant.domain;

import com.zhenshu.reward.common.constant.exception.ServiceException;

import java.time.LocalDateTime;

/**
 * @author xyh
 * @version 1.0
 * @date 2023/3/17 15:50
 * @desc 检查时间
 */
public interface TimeCheck {
    /**
     * 获取开始时间
     */
    LocalDateTime getStartTime();

    /**
     * 获取结束时间
     */
    LocalDateTime getEndTime();

    /**
     * 设置开始时间
     *
     * @param startTime 开始时间
     */
    void setStartTime(LocalDateTime startTime);

    /**
     * 设置结束时间
     *
     * @param endTime 结束时间
     */
    void setEndTime(LocalDateTime endTime);

    /**
     * 检查时间
     *
     * 统一:有效时间包头不包尾
     * 2023-04-03 00:00 TO 2023-04-04 00:00
     * 表示开始时间从2023-04-03 00:00:00到2023-04-04 23:59:59有效, 2023-04-04 00:00失效;
     *
     * @return 结果
     */
    default boolean checkTime() {
        LocalDateTime startTime = this.getStartTime();
        LocalDateTime endTime = this.getEndTime();
        this.setStartTime(startTime.minusSeconds(startTime.getSecond()));
        this.setEndTime(endTime.minusSeconds(endTime.getSecond()));
        startTime = this.getStartTime();
        endTime = this.getEndTime();
        return endTime.isAfter(startTime);
    }

    /**
     * 检查时间
     */
    default void verifyTime() {
        boolean allNull = this.getStartTime() == null && this.getEndTime() == null;
        boolean allNotNull = this.getStartTime() != null && this.getEndTime() != null;
        if (!allNull && !allNotNull) {
            throw new ServiceException("开始时间和结束时间必须同时传递或都为null", 500);
        }
        if (allNull) {
            return;
        }
        boolean result = this.checkTime();
        if (!result) {
            throw new ServiceException("结束时间必须大于开始时间", 500);
        }
    }
}
