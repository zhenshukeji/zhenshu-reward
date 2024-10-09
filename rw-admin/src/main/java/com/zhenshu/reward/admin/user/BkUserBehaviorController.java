package com.zhenshu.reward.admin.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhenshu.reward.common.user.admin.domain.bo.BkUserBehaviorBO;
import com.zhenshu.reward.common.user.admin.domain.vo.BkUserBehaviorQueryVO;
import com.zhenshu.reward.common.user.admin.facade.BkUserBehaviorFacade;
import com.zhenshu.reward.common.web.Result;
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

/**
 * @author hong
 * @version 1.0
 * @date 2024/6/5 14:57
 * @desc 用户行为
 */
@RestController
@RequestMapping("/bk/userBehavior")
@PreAuthorize("@ss.hasPermi('user:member')")
@Api(tags = "用户行为", produces = MediaType.APPLICATION_JSON_VALUE)
public class BkUserBehaviorController {
    @Resource
    private BkUserBehaviorFacade bkUserBehaviorFacade;

    @PostMapping("/list")
    @ApiOperation(value = "列表查询")
    public Result<IPage<BkUserBehaviorBO>> list(@RequestBody @Validated BkUserBehaviorQueryVO queryVO) {
        IPage<BkUserBehaviorBO> page = bkUserBehaviorFacade.list(queryVO);
        return Result.buildSuccess(page);
    }
}
