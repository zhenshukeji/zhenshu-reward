package com.zhenshu.reward.point.web.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.utils.poi.ExcelUtil;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.service.domain.bo.bk.BkTaskCompleteBO;
import com.zhenshu.reward.point.service.domain.vo.bk.BkTaskCompleteQueryVO;
import com.zhenshu.reward.point.service.facade.bk.BkTaskCompleteFacade;
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
 * @date 2023/12/18 14:15
 * @desc 任务完成记录
 */
@RestController
@PreAuthorize("@ss.hasPermi('integral:taskComplete')")
@RequestMapping("/bk/taskComplete")
@Api(tags = "后台-任务完成记录", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkTaskCompleteController {
    @Resource
    private BkTaskCompleteFacade bkTaskCompleteFacade;

    @PostMapping("/list")
    @ApiOperation(value = "列表查询")
    public Result<IPage<BkTaskCompleteBO>> list(@RequestBody @Validated BkTaskCompleteQueryVO queryVO) {
        IPage<BkTaskCompleteBO> page = bkTaskCompleteFacade.list(queryVO);
        return Result.buildSuccess(page);
    }

    @Log(title = "导出任务完成记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出任务完成记录", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void export(@RequestBody @Validated BkTaskCompleteQueryVO queryVO) {
        List<BkTaskCompleteBO> list = bkTaskCompleteFacade.listAll(queryVO);
        ExcelUtil.exportIOFlow(list, "任务完成记录", "任务完成记录");
    }
}
