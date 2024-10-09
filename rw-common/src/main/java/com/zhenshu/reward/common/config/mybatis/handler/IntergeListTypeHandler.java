package com.zhenshu.reward.common.config.mybatis.handler;

/**
 * @author jing
 * @version 1.0
 * @desc 字符串列表
 * @date 2022/7/25 0025 13:43
 **/
public class IntergeListTypeHandler extends ListTypeHandler<Integer> {
    @Override
    public Class<Integer> specificType() {
        return Integer.class;
    }
}
