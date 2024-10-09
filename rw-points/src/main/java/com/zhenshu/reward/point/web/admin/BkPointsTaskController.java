package com.zhenshu.reward.point.web.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.constant.enums.ErrorEnums;
import com.zhenshu.reward.common.constant.exception.ServiceException;
import com.zhenshu.reward.common.utils.poi.ExcelUtil;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.service.domain.bo.bk.BkPointsTaskBO;
import com.zhenshu.reward.point.service.domain.vo.bk.*;
import com.zhenshu.reward.point.service.facade.bk.BkPointsTaskFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jing
 * @version 1.0
 * @desc 积分任务
 * @date 2023/10/24 15:41
 **/
@RestController
@PreAuthorize("@ss.hasPermi('integral:pointsTask')")
@RequestMapping("/bk/pointsTask")
@Api(tags = "后台-积分任务", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkPointsTaskController {

    @Resource
    private BkPointsTaskFacade bkPointsTaskFacade;

    @PostMapping("/list")
    @ApiOperation(value = "列表查询")
    public Result<IPage<BkPointsTaskBO>> list(@RequestBody @Validated BkPointsTaskQueryVO queryVO) {
        IPage<BkPointsTaskBO> page = bkPointsTaskFacade.list(queryVO);
        return Result.buildSuccess(page);
    }

    @GetMapping
    @ApiOperation(value = "查询")
    public Result<BkPointsTaskBO> get(@RequestParam("id") Long id) {
        BkPointsTaskBO bo = bkPointsTaskFacade.get(id);
        return Result.buildSuccess(bo);
    }

    @Log(title = "新增积分任务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation(value = "新增", notes = "保存并发布(status = on), 保存(status = off)")
    public Result<Object> add(@RequestBody @Validated BkPointsTaskAddVO addVO) {
        checkParam(addVO);
        bkPointsTaskFacade.add(addVO);
        return Result.buildSuccess();
    }

    @Log(title = "修改积分任务", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    @ApiOperation(value = "修改")
    public Result<Object> update(@RequestBody @Validated BkPointsTaskUpdateVO updateVO) {
        checkParam(updateVO);
        bkPointsTaskFacade.update(updateVO);
        return Result.buildSuccess();
    }

    /**
     * 参数检查
     *
     * @param handlerVO 参数
     */
    private void checkParam(BkPointsTaskHandlerVO handlerVO) {
        boolean allNull = handlerVO.getStartTime() == null && handlerVO.getEndTime() == null;
        boolean allNotNull = handlerVO.getStartTime() != null && handlerVO.getEndTime() != null;
        if (!allNull && !allNotNull) {
            throw new ServiceException(ErrorEnums.BAD_REQUEST);
        }
    }

    @Log(title = "删除积分任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除")
    public Result<Object> delete(@RequestBody @Validated BkPointsTaskDeleteVO deleteVO) {
        bkPointsTaskFacade.delete(deleteVO);
        return Result.buildSuccess();
    }

//    @Log(title = "积分任务状态变更", businessType = BusinessType.UPDATE)
//    @PutMapping("/status")
//    @ApiOperation(value = "状态变更")
    public Result<Object> status(@RequestBody @Validated BkPointsTaskStatusVO statusVO) {
        bkPointsTaskFacade.status(statusVO);
        return Result.buildSuccess();
    }

    @Log(title = "移动积分任务", businessType = BusinessType.UPDATE)
    @PutMapping("/move")
    @ApiOperation(value = "移动")
    public Result<Object> move(@RequestBody @Validated BkPointsTaskMoveVO moveVO) {
        bkPointsTaskFacade.move(moveVO);
        return Result.buildSuccess();
    }

    @Log(title = "导出积分任务", businessType = BusinessType.EXPORT)
    @PutMapping("/export")
    @ApiOperation(value = "导出积分任务", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void export(@RequestBody @Validated BkPointsTaskQueryVO queryVO) {
        List<BkPointsTaskBO> list = bkPointsTaskFacade.listAll(queryVO);
        ExcelUtil.exportIOFlow(list, "积分任务", "积分任务");
    }
}
