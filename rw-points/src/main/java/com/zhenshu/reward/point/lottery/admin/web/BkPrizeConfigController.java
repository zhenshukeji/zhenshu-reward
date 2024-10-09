package com.zhenshu.reward.point.lottery.admin.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.constant.annotation.LockFunction;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.lottery.service.admin.domain.bo.BkPrizeConfigBO;
import com.zhenshu.reward.point.lottery.service.admin.domain.vo.BkPrizeConfigAddVO;
import com.zhenshu.reward.point.lottery.service.admin.domain.vo.BkPrizeConfigEditVO;
import com.zhenshu.reward.point.lottery.service.admin.domain.vo.BkPrizeConfigQueryVO;
import com.zhenshu.reward.point.lottery.service.admin.facade.BkPrizeConfigFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台-奖品配置
 *
 * @Author xuzq
 * @Date 2022/10/12 10:19
 * @Version 1.0
 */
@PreAuthorize("@ss.hasPermi('lottery:pond')")
@RestController
@RequestMapping("/bk/lottery/prize")
@Api(tags = "后台-奖品配置", value = "WEB - PrizeConfigController", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkPrizeConfigController {
    @Resource
    private BkPrizeConfigFacade prizeConfigFacade;

    @PostMapping("/listPage")
    @ApiOperation(value = "查询奖品配置")
    public Result<IPage<BkPrizeConfigBO>> listPage(@RequestBody @Validated BkPrizeConfigQueryVO queryVO) {
        IPage<BkPrizeConfigBO> page = prizeConfigFacade.listPage(queryVO);
        return Result.buildSuccess(page);
    }

    @Log(title = "新增奖品配置", businessType = BusinessType.INSERT)
    @LockFunction(methodName = "prize-handler", keyName = "pondId")
    @PostMapping
    @ApiOperation(value = "新增奖品配置")
    public Result<Object> add(@RequestBody @Validated BkPrizeConfigAddVO addVO) {
        prizeConfigFacade.add(addVO);
        return new Result<>().success();
    }

    @Log(title = "编辑奖品配置", businessType = BusinessType.UPDATE)
    @LockFunction(methodName = "prize-handler", keyName = "pondId")
    @PutMapping
    @ApiOperation(value = "编辑奖品配置")
    public Result<Object> edit(@RequestBody @Validated BkPrizeConfigEditVO editVO) {
        prizeConfigFacade.edit(editVO);
        return new Result<>().success();
    }

    @Log(title = "删除奖品配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除奖品配置")
    public Result<Object> remove(@PathVariable("id") @ApiParam("奖品id") Long id) {
        prizeConfigFacade.remove(id);
        return new Result<>().success();
    }
}
