package com.zhenshu.reward.common.config.mybatis.handler;

/**
 * @author jing
 * @version 1.0
 * @desc 字符串列表
 * @date 2022/7/25 0025 13:43
 **/
public class LongListTypeHandler extends ListTypeHandler<Long> {
    @Override
    public Class<Long>  specificType() {
        return Long.class;
    }
}
