package com.zhenshu.reward.point.lottery.admin.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.utils.poi.ExcelUtil;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.lottery.service.admin.domain.bo.BkLotteryRecordBO;
import com.zhenshu.reward.point.lottery.service.admin.domain.vo.BkLotteryRecordQueryVO;
import com.zhenshu.reward.point.lottery.service.admin.domain.vo.BkLotteryRecordSendOutVO;
import com.zhenshu.reward.point.lottery.service.admin.facade.BkLotteryRecordFacade;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hong
 * @version 1.0
 * @date 2023/12/28 19:20
 * @desc 抽奖记录
 */
@PreAuthorize("@ss.hasPermi('lottery:record')")
@RestController
@RequestMapping("/bk/lottery/record")
@Api(tags = "后台-抽奖记录", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkLotteryRecordController {
    @Resource
    private BkLotteryRecordFacade lotteryRecordFacade;

    @PostMapping("/list")
    @ApiOperation(value = "列表查询")
    public Result<IPage<BkLotteryRecordBO>> list(@RequestBody @Validated BkLotteryRecordQueryVO queryVO) {
        IPage<BkLotteryRecordBO> page = lotteryRecordFacade.listPage(queryVO);
        return Result.buildSuccess(page);
    }

    @Log(title = "导出抽奖记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void export(@RequestBody @Validated BkLotteryRecordQueryVO queryVO) {
        List<BkLotteryRecordBO> list = lotteryRecordFacade.listAll(queryVO);
        ExcelUtil.exportIOFlow(list, "抽奖记录", "抽奖记录");
    }

    @Log(title = "发货", businessType = BusinessType.UPDATE)
    @PostMapping("/sendOut")
    @ApiOperation(value = "发货")
    public Result<Void> sendOut(@RequestBody @Validated BkLotteryRecordSendOutVO sendOutVO) {
        lotteryRecordFacade.sendOut(sendOutVO);
        return Result.buildSuccess();
    }
}
