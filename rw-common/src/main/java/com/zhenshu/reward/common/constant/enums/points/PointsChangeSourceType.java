package com.zhenshu.reward.common.constant.enums.points;

import com.zhenshu.reward.common.constant.enums.IBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hong
 * @version 1.0
 * @date 2023/12/11 11:03
 * @desc 积分变更来源类型
 */
@Getter
@AllArgsConstructor
public enum PointsChangeSourceType implements IBaseEnum<String> {

    /**
     *
     */
    EXCHANGE_GOODS("exchange_goods", "兑换商品"),
    ANSWER("answer", "答题"),
    LOTTERY("lottery", "抽奖"),
    VOTE("vote", "投票"),
    SIGN_IN("sign_in", "签到"),
    LINK("link", "跳外链"),
    WORK_WECHAT("work_wechat", "添加企业微信"),
    CONCERN("concern", "关注公众号"),
    SHARE("share", "分享"),
    INVITE("invite", "邀请"),
    CULTIVATE("cultivate", "养成"),
    USER_DATA_PERFECTION("user_data_perfection", "用户资料完善"),
    WECHAT_RUN("wechat_run", "微信运动"),
    ;

    private final String value;
    private final String info;
}
