package com.zhenshu.reward.admin.polymerization.points.web;

import com.zhenshu.reward.admin.polymerization.points.domain.bo.BkTaskDetailsBO;
import com.zhenshu.reward.admin.polymerization.points.domain.vo.BkTaskDetailsAddVO;
import com.zhenshu.reward.admin.polymerization.points.domain.vo.BkTaskDetailsEditVO;
import com.zhenshu.reward.admin.polymerization.points.facade.BkTaskDetailsFacade;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author jing
 * @version 1.0
 * @desc 积分任务
 * @date 2023/10/24 15:41
 **/
@RestController
@PreAuthorize("@ss.hasPermi('integral:pointsTask')")
@RequestMapping("/bk/task/details")
@Api(tags = "后台-积分任务", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkTaskDetailsController {

    @Resource
    private BkTaskDetailsFacade bkTaskDetailsFacade;

    @PostMapping("/{id}")
    @ApiOperation(value = "任务详情")
    public Result<BkTaskDetailsBO> details(@PathVariable("id") @ApiParam("id") Long id) {
        BkTaskDetailsBO details = bkTaskDetailsFacade.details(id);
        return Result.buildSuccess(details);
    }

    @Log(title = "新增积分任务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation(value = "新增")
    public Result<Object> add(@RequestBody @Validated BkTaskDetailsAddVO addVO) {
        bkTaskDetailsFacade.add(addVO);
        return Result.buildSuccess();
    }

    @Log(title = "修改积分任务", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    @ApiOperation(value = "修改")
    public Result<Object> update(@RequestBody @Validated BkTaskDetailsEditVO updateVO) {
        bkTaskDetailsFacade.update(updateVO);
        return Result.buildSuccess();
    }
}
