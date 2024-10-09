package com.zhenshu.reward.point.web.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.utils.poi.ExcelUtil;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.service.domain.bo.bk.BkUserPrizeBO;
import com.zhenshu.reward.point.service.domain.vo.bk.BkUserPrizeQueryVO;
import com.zhenshu.reward.point.service.domain.vo.bk.BkUserPrizeSendOutVO;
import com.zhenshu.reward.point.service.facade.bk.BkPointsRewardsFacade;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hong
 * @version 1.0
 * @date 2023/12/18 15:13
 * @desc 兑换记录
 */
@RestController
@PreAuthorize("@ss.hasPermi('integral:exchangeRecord')")
@RequestMapping("/bk/exchangeRecord")
@Api(tags = "后台-兑换记录", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkExchangeRewardsController {
    @Resource
    private BkPointsRewardsFacade bkPointsRewardsFacade;

    @PostMapping
    @ApiOperation(value = "兑换记录")
    public Result<IPage<BkUserPrizeBO>> exchangeRecord(@RequestBody @Validated BkUserPrizeQueryVO queryVO) {
        IPage<BkUserPrizeBO> boPage = bkPointsRewardsFacade.exchangeRecord(queryVO);
        return Result.buildSuccess(boPage);
    }

    @Log(title = "积分商品发货", businessType = BusinessType.UPDATE)
    @PostMapping("/sendOut")
    @ApiOperation(value = "发货")
    public Result<Void> sendOut(@RequestBody @Validated BkUserPrizeSendOutVO sendOutVO) {
        bkPointsRewardsFacade.sendOut(sendOutVO);
        return Result.buildSuccess();
    }

    @Log(title = "积分商品兑换记录导出", businessType = BusinessType.EXPORT)
    @PostMapping("/exchangeRecordExport")
    @ApiOperation(value = "兑换记录导出", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exchangeRecordExport(@RequestBody @Validated BkUserPrizeQueryVO exchangeVO) {
        List<BkUserPrizeBO> boList = bkPointsRewardsFacade.exchangeRecordExport(exchangeVO);
        ExcelUtil.exportIOFlow(boList, "兑换记录", "兑换记录");
    }

}
