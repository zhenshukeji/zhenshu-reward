package com.zhenshu.reward.game.cultivate.admin.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.game.cultivate.admin.domain.bo.BkCultivateActivityBO;
import com.zhenshu.reward.game.cultivate.admin.domain.vo.BkCultivateActivityAddVO;
import com.zhenshu.reward.game.cultivate.admin.domain.vo.BkCultivateActivityEditVO;
import com.zhenshu.reward.game.cultivate.admin.domain.vo.BkCultivateActivityQueryVO;
import com.zhenshu.reward.game.cultivate.admin.domain.vo.BkCultivateActivityRemoveVO;
import com.zhenshu.reward.game.cultivate.admin.facade.BkCultivateActivityFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xyh
 * @version 1.0
 * @date 2024-05-27 10:21:42
 * @desc 养成活动
 */
@RestController
@PreAuthorize("@ss.hasPermi('integral:pointsTask')")
@RequestMapping("/bk/cultivateActivity")
@Api(tags = "养成活动", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkCultivateActivityController {
    @Resource
    private BkCultivateActivityFacade bkCultivateActivityFacade;

    @PostMapping("/list")
    @ApiOperation(value = "列表查询")
    public Result<IPage<BkCultivateActivityBO>> list(@RequestBody @Validated BkCultivateActivityQueryVO queryVO) {
        IPage<BkCultivateActivityBO> page = bkCultivateActivityFacade.list(queryVO);
        return Result.buildSuccess(page);
    }

    @PostMapping("/info/{id}")
    @ApiOperation(value = "活动信息")
    public Result<BkCultivateActivityBO> info(@PathVariable("id") @ApiParam("id") Long id) {
        BkCultivateActivityBO info = bkCultivateActivityFacade.info(id);
        return Result.buildSuccess(info);
    }

    @Log(title = "添加养成活动", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加")
    public Result<Long> add(@RequestBody @Validated BkCultivateActivityAddVO addVO) {
        Long id = bkCultivateActivityFacade.add(addVO);
        return Result.buildSuccess(id);
    }

    @Log(title = "修改养成活动", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改")
    public Result<Void> update(@RequestBody @Validated BkCultivateActivityEditVO editVO) {
        bkCultivateActivityFacade.update(editVO);
        return Result.buildSuccess();
    }

    @Log(title = "删除养成活动", businessType = BusinessType.DELETE)
    @DeleteMapping
    @ApiOperation(value = "删除")
    public Result<Void> delete(@RequestBody @Validated BkCultivateActivityRemoveVO removeVO) {
        bkCultivateActivityFacade.delete(removeVO.getId());
        return Result.buildSuccess();
    }
}
