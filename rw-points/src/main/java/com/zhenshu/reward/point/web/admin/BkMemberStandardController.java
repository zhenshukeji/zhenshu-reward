package com.zhenshu.reward.point.web.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.service.domain.bo.bk.BkMemberStandardBO;
import com.zhenshu.reward.point.service.domain.vo.bk.BkMemberStandardEditVO;
import com.zhenshu.reward.point.service.domain.vo.bk.BkMemberStandardQueryVO;
import com.zhenshu.reward.point.service.facade.bk.BkMemberStandardFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xyh
 * @version 1.0
 * @date 2024-06-03 16:41:05
 * @desc 会员标准
 */
@RestController
@PreAuthorize("@ss.hasPermi('user:memberStandard')")
@RequestMapping("/bk/memberStandard")
@Api(tags = "会员标准", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkMemberStandardController {
    @Resource
    private BkMemberStandardFacade bkMemberStandardFacade;

    @PostMapping("/list")
    @ApiOperation(value = "列表查询")
    public Result<IPage<BkMemberStandardBO>> list(@RequestBody @Validated BkMemberStandardQueryVO queryVO) {
        IPage<BkMemberStandardBO> page = bkMemberStandardFacade.list(queryVO);
        return Result.buildSuccess(page);
    }

    @Log(title = "修改会员标准", businessType = BusinessType.INSERT)
    @PutMapping
    @ApiOperation(value = "修改")
    public Result<Void> update(@RequestBody @Validated BkMemberStandardEditVO editVO) {
        bkMemberStandardFacade.update(editVO);
        return Result.buildSuccess();
    }
}
