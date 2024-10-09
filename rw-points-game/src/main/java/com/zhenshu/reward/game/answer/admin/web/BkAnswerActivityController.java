package com.zhenshu.reward.game.answer.admin.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.utils.ServletUtils;
import com.zhenshu.reward.common.utils.poi.ExcelUtil;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.game.answer.service.admin.domain.bo.BkAnswerActivityBO;
import com.zhenshu.reward.game.answer.service.admin.domain.bo.BkAnswerActivityDetailsBO;
import com.zhenshu.reward.game.answer.service.admin.domain.bo.BkAnswerRecordBO;
import com.zhenshu.reward.game.answer.service.admin.domain.vo.BkAnswerActivityAddVO;
import com.zhenshu.reward.game.answer.service.admin.domain.vo.BkAnswerActivityEditVO;
import com.zhenshu.reward.game.answer.service.admin.domain.vo.BkAnswerActivityQueryVO;
import com.zhenshu.reward.game.answer.service.admin.domain.vo.BkAnswerRecordQueryVO;
import com.zhenshu.reward.game.answer.service.admin.facade.BkAnswerActivityFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2023-10-18
 * @desc 活动
 */
@RestController
@PreAuthorize("@ss.hasPermi('answer:activity')")
@RequestMapping("/bk/activity/answer")
@Api(tags = "答题活动", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkAnswerActivityController {
    @Resource
    private BkAnswerActivityFacade bkAnswerActivityFacade;

    @PreAuthorize("@ss.hasAnyPermi('answer:activity,integral:pointsTask')")
    @PostMapping("/list")
    @ApiOperation(value = "列表查询")
    public Result<IPage<BkAnswerActivityBO>> list(@RequestBody @Validated BkAnswerActivityQueryVO queryVO) {
        IPage<BkAnswerActivityBO> page = bkAnswerActivityFacade.list(queryVO);
        return Result.buildSuccess(page);
    }

    @PreAuthorize("@ss.hasAnyPermi('integral:pointsTask,answer:activity')")
    @PostMapping("/list/all")
    @ApiOperation(value = "查询全部")
    public Result<List<BkAnswerActivityBO>> listAll() {
        List<BkAnswerActivityBO> list = bkAnswerActivityFacade.listAll();
        return Result.buildSuccess(list);
    }

    @PostMapping("/details/{id}")
    @ApiOperation(value = "详情")
    public Result<BkAnswerActivityDetailsBO> details(@PathVariable("id") @ApiParam("id") Long id) {
        BkAnswerActivityDetailsBO details = bkAnswerActivityFacade.details(id);
        return Result.buildSuccess(details);
    }

    @Log(title = "添加答题活动", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加")
    public Result<Void> add(@RequestBody @Validated BkAnswerActivityAddVO addVO) {
        bkAnswerActivityFacade.add(addVO);
        return Result.buildSuccess();
    }

    @Log(title = "修改答题活动", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改")
    public Result<Void> update(@RequestBody @Validated BkAnswerActivityEditVO editVO) {
        bkAnswerActivityFacade.update(editVO);
        return Result.buildSuccess();
    }

    @Log(title = "删除答题活动", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    public Result<Void> delete(@PathVariable("id") @ApiParam("id") Long id) {
        bkAnswerActivityFacade.delete(id);
        return Result.buildSuccess();
    }

    @PostMapping("/data/statistics/answer")
    @ApiOperation(value = "答题数据统计")
    public Result<IPage<BkAnswerRecordBO>> dataStatisticsAnswer(@RequestBody @Validated BkAnswerRecordQueryVO queryVO) {
        IPage<BkAnswerRecordBO> page = bkAnswerActivityFacade.dataStatisticsAnswer(queryVO);
        return Result.buildSuccess(page);
    }

    @Log(title = "答题数据统计导出", businessType = BusinessType.EXPORT)
    @PostMapping("/data/statistics/answer/export")
    @ApiOperation(value = "答题数据统计导出")
    public void dataStatisticsAnswerExport(@RequestBody @Validated BkAnswerRecordQueryVO queryVO) {
        List<BkAnswerRecordBO> list = bkAnswerActivityFacade.dataStatisticsAnswerAll(queryVO);
        ExcelUtil<BkAnswerRecordBO> excelUtil = new ExcelUtil<>(BkAnswerRecordBO.class);
        HttpServletResponse response = ServletUtils.getResponse();
        excelUtil.exportExcel(response, list, "活动数据");
    }
}
