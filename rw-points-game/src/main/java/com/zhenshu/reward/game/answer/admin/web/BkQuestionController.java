package com.zhenshu.reward.game.answer.admin.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.utils.ServletUtils;
import com.zhenshu.reward.common.utils.poi.ExcelUtil;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.game.answer.service.admin.domain.bo.BkQuestionBO;
import com.zhenshu.reward.game.answer.service.admin.domain.vo.BkQuestionAddVO;
import com.zhenshu.reward.game.answer.service.admin.domain.vo.BkQuestionEditVO;
import com.zhenshu.reward.game.answer.service.admin.domain.vo.BkQuestionQueryVO;
import com.zhenshu.reward.game.answer.service.admin.facade.BkQuestionFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author xyh
 * @version 1.0
 * @date 2023-10-18
 * @desc 题目
 */
@RestController
@PreAuthorize("@ss.hasPermi('answer:activity')")
@RequestMapping("/bk/question")
@Api(tags = "题目", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkQuestionController {
    @Resource
    private BkQuestionFacade bkQuestionFacade;

    @PostMapping("/list")
    @ApiOperation(value = "列表查询")
    public Result<IPage<BkQuestionBO>> list(@RequestBody @Validated BkQuestionQueryVO queryVO) {
        IPage<BkQuestionBO> page = bkQuestionFacade.list(queryVO);
        return Result.buildSuccess(page);
    }

    @Log(title = "添加答题题目", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加")
    public Result<Void> add(@RequestBody @Validated BkQuestionAddVO addVO) {
        bkQuestionFacade.add(addVO);
        return Result.buildSuccess();
    }

    @Log(title = "修改答题题目", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改")
    public Result<Void> update(@RequestBody @Validated BkQuestionEditVO editVO) {
        bkQuestionFacade.update(editVO);
        return Result.buildSuccess();
    }

    @Log(title = "删除答题题目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    public Result<Void> delete(@PathVariable("id") @ApiParam("id") Long id) {
        bkQuestionFacade.delete(id);
        return Result.buildSuccess();
    }

    @PostMapping("/importTemplate")
    @ApiOperation(value = "导入模板")
    public void importTemplate() {
        HttpServletResponse response = ServletUtils.getResponse();
        ExcelUtil<BkQuestionAddVO> excelUtil = new ExcelUtil<>(BkQuestionAddVO.class);
        excelUtil.importTemplateExcel(response, "题目导入模板");
    }

    @Log(title = "批量导入答题题目", businessType = BusinessType.IMPORT)
    @PostMapping("/import/excel/{activityId}")
    @ApiOperation(value = "批量导入")
    public Result<Void> importExcel(@PathVariable("activityId") @ApiParam("活动id") Long activityId,
                                    @RequestParam("file") MultipartFile file) {
        List<BkQuestionAddVO> list = ExcelUtil.importConvert(file, BkQuestionAddVO.class, item -> item.setActivityId(activityId));
        bkQuestionFacade.importExcel(list);
        return Result.buildSuccess();
    }
}
