package com.zhenshu.reward.admin.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.user.admin.domain.bo.BkUserBO;
import com.zhenshu.reward.common.user.admin.domain.bo.BkUserDetailsBO;
import com.zhenshu.reward.common.user.admin.domain.vo.BkUserQueryVO;
import com.zhenshu.reward.common.user.admin.domain.vo.BkUserStatusVO;
import com.zhenshu.reward.common.user.admin.facade.BkUserFacade;
import com.zhenshu.reward.common.utils.poi.ExcelUtil;
import com.zhenshu.reward.common.web.Result;
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
 * @author xyh
 * @version 1.0
 * @date 2022/8/16 19:13
 * @desc 会员管理
 */
@RestController
@PreAuthorize("@ss.hasPermi('user:member')")
@RequestMapping("/admin/user")
@Api(tags = "会员管理", value = "WEB - BkUserController", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkUserController {
    @Resource
    private BkUserFacade bkUserFacade;

    @PostMapping("/list")
    @ApiOperation(value = "分页查询用户信息")
    public Result<IPage<BkUserBO>> userListPage(@RequestBody @Validated BkUserQueryVO queryVO) {
        IPage<BkUserBO> page = bkUserFacade.listPage(queryVO);
        return new Result<IPage<BkUserBO>>().success(page);
    }

    @PostMapping("/details/{id}")
    @ApiOperation(value = "用户详情")
    public Result<BkUserDetailsBO> details(@PathVariable("id") @ApiParam("用户id") Long id) {
        BkUserDetailsBO details = bkUserFacade.details(id);
        return Result.buildSuccess(details);
    }

    @Log(title = "导出用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出用户信息", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void export(@RequestBody @Validated BkUserQueryVO queryVO) {
        List<BkUserBO> list = bkUserFacade.listAll(queryVO);
        ExcelUtil.exportIOFlow(list, "用户信息", "用户信息");
    }

    @Log(title = "账号状态变更", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    @ApiOperation(value = "账号状态变更")
    public Result<Void> status(@RequestBody @Validated BkUserStatusVO statusVO) {
        bkUserFacade.status(statusVO);
        return Result.buildSuccess();
    }
}
