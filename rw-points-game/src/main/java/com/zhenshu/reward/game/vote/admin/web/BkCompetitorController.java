package com.zhenshu.reward.game.vote.admin.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.utils.poi.ExcelUtil;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.game.vote.admin.domain.bo.BkCompetitorBO;
import com.zhenshu.reward.game.vote.admin.domain.bo.BkCompetitorImportBO;
import com.zhenshu.reward.game.vote.admin.domain.vo.BkCompetitorAddVO;
import com.zhenshu.reward.game.vote.admin.domain.vo.BkCompetitorEditVO;
import com.zhenshu.reward.game.vote.admin.domain.vo.BkCompetitorQueryVO;
import com.zhenshu.reward.game.vote.admin.domain.vo.BkCompetitorStatusVO;
import com.zhenshu.reward.game.vote.admin.facade.BkCompetitorFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2023-10-13
 * @desc 选手
 */
@RestController
@PreAuthorize("@ss.hasPermi('vote:activity')")
@RequestMapping("/bk/competitor")
@Api(tags = "选手", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkCompetitorController {
    @Resource
    private BkCompetitorFacade bkCompetitorFacade;

    @PostMapping("/list")
    @ApiOperation(value = "列表查询")
    public Result<IPage<BkCompetitorBO>> list(@RequestBody @Validated BkCompetitorQueryVO queryVO) {
        IPage<BkCompetitorBO> page = bkCompetitorFacade.list(queryVO);
        return Result.buildSuccess(page);
    }

    @PostMapping("/listAll")
    @ApiOperation(value = "查询全部")
    public Result<List<BkCompetitorBO>> listAll(@RequestBody @Validated BkCompetitorQueryVO queryVO) {
        List<BkCompetitorBO> list = bkCompetitorFacade.listAll(queryVO);
        return Result.buildSuccess(list);
    }

    @Log(title = "添加投票选手", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加")
    public Result<Void> add(@RequestBody @Validated BkCompetitorAddVO addVO) {
        bkCompetitorFacade.add(addVO);
        return Result.buildSuccess();
    }

    @Log(title = "批量添加投票选手", businessType = BusinessType.INSERT)
    @PostMapping("/batch")
    @ApiOperation(value = "批量添加")
    public Result<List<BkCompetitorImportBO>> batchAdd(@RequestBody @Validated List<BkCompetitorAddVO> addVos) {
        List<BkCompetitorImportBO> list = bkCompetitorFacade.batchAdd(addVos);
        return Result.buildSuccess(list);
    }

    @Log(title = "修改投票选手", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改")
    public Result<Void> update(@RequestBody @Validated BkCompetitorEditVO editVO) {
        bkCompetitorFacade.update(editVO);
        return Result.buildSuccess();
    }

    @Log(title = "删除投票选手", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    public Result<Void> delete(@PathVariable("id") @ApiParam("id") Long id) {
        bkCompetitorFacade.delete(id);
        return Result.buildSuccess();
    }

    @PostMapping("/import/excel/template")
    @ApiOperation(value = "导入模板下载")
    public void importExcelTemplate() {
        ExcelUtil<BkCompetitorAddVO> util = new ExcelUtil<>(BkCompetitorAddVO.class);
        util.importTemplateExcel("选手导入模板");
    }

    @Log(title = "投票选手状态变更", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    @ApiOperation(value = "状态变更")
    public Result<Object> status(@RequestBody @Validated BkCompetitorStatusVO statusVO) {
        bkCompetitorFacade.status(statusVO);
        return Result.buildSuccess();
    }

    @Log(title = "批量导入投票选手", businessType = BusinessType.IMPORT)
    @PostMapping("/import/excel/{activityId}")
    @ApiOperation(value = "批量导入")
    public Result<List<BkCompetitorImportBO>> importExcel(@PathVariable("activityId") @ApiParam("活动id") Long activityId,
                                                          @RequestParam("file") MultipartFile file) {
        List<BkCompetitorAddVO> list = ExcelUtil.importConvert(file, BkCompetitorAddVO.class, item -> item.setActivityId(activityId));
        List<BkCompetitorImportBO> bos = bkCompetitorFacade.importExcel(list, activityId);
        return Result.buildSuccess(bos);
    }
}
