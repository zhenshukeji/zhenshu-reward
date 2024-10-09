package com.zhenshu.reward.point.web.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.service.domain.bo.bk.BkBannerBO;
import com.zhenshu.reward.point.service.domain.vo.bk.BkBannerAddVO;
import com.zhenshu.reward.point.service.domain.vo.bk.BkBannerEditVO;
import com.zhenshu.reward.point.service.domain.vo.bk.BkBannerQueryVO;
import com.zhenshu.reward.point.service.domain.vo.bk.BkBannerRemoveVO;
import com.zhenshu.reward.point.service.facade.bk.BkBannerFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ruoyi
 * @version 1.0
 * @date 2024-05-06 14:35:51
 * @desc banner
 */
@RestController
@PreAuthorize("@ss.hasPermi('integral:banner')")
@RequestMapping("/bk/banner")
@Api(tags = "banner", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkBannerController {
    @Resource
    private BkBannerFacade bkBannerFacade;

    @PostMapping("/list")
    @ApiOperation(value = "列表查询")
    public Result<IPage<BkBannerBO>> list(@RequestBody @Validated BkBannerQueryVO queryVO) {
        IPage<BkBannerBO> page = bkBannerFacade.list(queryVO);
        return Result.buildSuccess(page);
    }

    @Log(title = "添加Banner", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加")
    public Result<Void> add(@RequestBody @Validated BkBannerAddVO addVO) {
        bkBannerFacade.add(addVO);
        return Result.buildSuccess();
    }

    @Log(title = "修改Banner", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改")
    public Result<Void> update(@RequestBody @Validated BkBannerEditVO editVO) {
        bkBannerFacade.update(editVO);
        return Result.buildSuccess();
    }

    @Log(title = "删除Banner", businessType = BusinessType.DELETE)
    @DeleteMapping
    @ApiOperation(value = "删除")
    public Result<Void> delete(@RequestBody @Validated BkBannerRemoveVO removeVO) {
        bkBannerFacade.delete(removeVO.getId());
        return Result.buildSuccess();
    }
}
