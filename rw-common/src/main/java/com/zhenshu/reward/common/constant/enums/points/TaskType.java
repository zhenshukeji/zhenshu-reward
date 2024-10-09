package com.zhenshu.reward.common.constant.enums.points;

import com.zhenshu.reward.common.constant.enums.IBaseEnum;
import com.zhenshu.reward.common.constant.game.rule.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hong
 * @version 1.0
 * @date 2023/12/8 18:16
 * @desc 任务类型
 */
@Getter
@AllArgsConstructor
public enum TaskType implements IBaseEnum<String> {
    /**
     * 任务类型 答题 抽奖 投票 签到 跳外链
     */
    ANSWER("answer", "答题", PointsChangeSourceType.ANSWER, AnswerRule.class, true),
    VOTE("vote", "投票", PointsChangeSourceType.VOTE, StandardRule.class, true),
    SIGN_IN("sign_in", "签到", PointsChangeSourceType.SIGN_IN, SignInRule.class, true),
    INVITE("invite", "邀请", PointsChangeSourceType.INVITE, StandardRule.class, true),
    CULTIVATE("cultivate", "养成", PointsChangeSourceType.CULTIVATE, PointsRule.class, true),
    USER_DATA_PERFECTION("user_data_perfection", "用户资料完善", PointsChangeSourceType.USER_DATA_PERFECTION, StandardRule.class, true),
    WECHAT_RUN("wechat_run", "微信运动", PointsChangeSourceType.WECHAT_RUN, WechatRunRule.class, false),

    LINK("link", "跳外链", PointsChangeSourceType.LINK, StandardRule.class, true),
    WORK_WECHAT("work_wechat", "添加企业微信", PointsChangeSourceType.WORK_WECHAT, StandardRule.class, true),
    CONCERN("concern", "关注公众号", PointsChangeSourceType.CONCERN, StandardRule.class, true),
    SHARE("share", "分享", PointsChangeSourceType.SHARE, StandardRule.class, true),
    ;

    private final String value;
    private final String info;
    private final PointsChangeSourceType sourceType;
    private final Class<? extends PointsTaskRule> rule;
    /**
     * 是否允许同时生效多个同类型任务
     */
    private final Boolean multiple;
}

