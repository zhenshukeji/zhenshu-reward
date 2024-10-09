package com.zhenshu.reward.admin.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.constant.annotation.LockFunction;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.common.constant.enums.KeyDataSource;
import com.zhenshu.reward.common.user.admin.domain.bo.BkUserTagBO;
import com.zhenshu.reward.common.user.admin.domain.bo.BkUserTagBindBO;
import com.zhenshu.reward.common.user.admin.domain.bo.BkUserTagDetailsBO;
import com.zhenshu.reward.common.user.admin.domain.vo.*;
import com.zhenshu.reward.common.user.admin.facade.BkUserTagFacade;
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
 * @date 2024-06-05 19:03:51
 * @desc 用户标签
 */
@RestController
@PreAuthorize("@ss.hasPermi('user:tag')")
@RequestMapping("/bk/userTag")
@Api(tags = "用户标签", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkUserTagController {
    @Resource
    private BkUserTagFacade bkUserTagFacade;

    @PostMapping("/list")
    @ApiOperation(value = "列表查询")
    public Result<IPage<BkUserTagBO>> list(@RequestBody @Validated BkUserTagQueryVO queryVO) {
        IPage<BkUserTagBO> page = bkUserTagFacade.list(queryVO);
        return Result.buildSuccess(page);
    }

    @PostMapping("/details/{id}")
    @ApiOperation(value = "详情")
    public Result<BkUserTagDetailsBO> details(@PathVariable("id") @ApiParam("标签id") Long id) {
        BkUserTagDetailsBO details = bkUserTagFacade.details(id);
        return Result.buildSuccess(details);
    }

    @PreAuthorize("@ss.hasAnyPermi('user:member,user:tag')")
    @PostMapping("/all")
    @ApiOperation(value = "查询全部")
    public Result<List<BkUserTagBO>> listAll(@RequestBody @Validated BkUserTagQueryVO queryVO) {
        List<BkUserTagBO> list = bkUserTagFacade.listAll(queryVO);
        return Result.buildSuccess(list);
    }

    @PreAuthorize("@ss.hasPermi('user:member')")
    @PostMapping("/userBind/{userId}")
    @ApiOperation(value = "用户标签")
    public Result<List<BkUserTagBindBO>> userBind(@PathVariable("userId") @ApiParam("用户id") Long userId) {
        List<BkUserTagBindBO> list = bkUserTagFacade.userBind(userId);
        return Result.buildSuccess(list);
    }

    @LockFunction(methodName = "addUserTag", form = KeyDataSource.NONE)
    @Log(title = "添加用户标签", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "添加")
    public Result<Void> add(@RequestBody @Validated BkUserTagAddVO addVO) {
        for (BkUserTagRuleVO rule : addVO.getRules()) {
            rule.verify();
        }
        bkUserTagFacade.add(addVO);
        return Result.buildSuccess();
    }

    @Log(title = "修改用户标签", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改")
    public Result<Void> update(@RequestBody @Validated BkUserTagEditVO editVO) {
        for (BkUserTagRuleVO rule : editVO.getRules()) {
            rule.verify();
        }
        bkUserTagFacade.update(editVO);
        return Result.buildSuccess();
    }

    @Log(title = "删除用户标签", businessType = BusinessType.DELETE)
    @DeleteMapping
    @ApiOperation(value = "删除")
    public Result<Void> delete(@RequestBody @Validated BkUserTagRemoveVO removeVO) {
        bkUserTagFacade.delete(removeVO.getId());
        return Result.buildSuccess();
    }

    @PreAuthorize("@ss.hasPermi('user:member')")
    @Log(title = "绑定用户标签", businessType = BusinessType.INSERT)
    @PostMapping("/bind")
    @ApiOperation(value = "绑定")
    public Result<Void> bind(@RequestBody @Validated BkUserTagBindVO bindVO) {
        bkUserTagFacade.bind(bindVO);
        return Result.buildSuccess();
    }

    @Log(title = "绑定用户标签-手动更新", businessType = BusinessType.UPDATE)
    @PostMapping("/bind/execute/{id}")
    @ApiOperation(value = "绑定用户标签-手动更新")
    public Result<Void> bindExecute(@PathVariable("id") @ApiParam("标签id") Long id) {
        bkUserTagFacade.manualRefreshTag(id);
        return Result.buildSuccess();
    }
}
