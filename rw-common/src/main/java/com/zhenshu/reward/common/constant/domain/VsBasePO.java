package com.zhenshu.reward.common.constant.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hong
 * @version 1.0
 * @date 2023/12/7 11:19
 * @desc
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VsBasePO extends BasePO {
    /**
     * 版本号
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;
}
