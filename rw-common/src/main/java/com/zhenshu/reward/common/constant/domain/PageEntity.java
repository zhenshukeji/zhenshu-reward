package com.zhenshu.reward.common.constant.domain;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhenshu.reward.common.constant.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/1/11 9:37
 * @desc 分页基础类
 */
@Data
@ApiModel(description = "分页入参")
public class PageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 页数
     */
    @Min(value = Constants.ONE)
    @NotNull
    @ApiModelProperty(required = true, value = "页数, 默认1", example = "1")
    private Integer pageNum = 1;

    /**
     * 条数
     */
    @Min(value = Constants.ONE)
    @NotNull
    @ApiModelProperty(required = true, value = "条数, 默认10", example = "10")
    private Integer pageSize = 10;

    /**
     * 返回MyBaitsPlus的分页对象
     *
     * @param <T> 泛型
     * @return 结果
     */
    @ApiModelProperty(hidden = true)
    public <T> IPage<T> page() {
        return new Page<>(pageNum, pageSize);
    }

    @ApiModelProperty(hidden = true)
    public <T> IPage<T> page(boolean searchCount) {
        return searchCount ? page() : pageNotCount();
    }

    @ApiModelProperty(hidden = true)
    public <T> IPage<T> pageNotCount() {
        return new Page<T>(pageNum, pageSize).setSearchCount(false);
    }
}
