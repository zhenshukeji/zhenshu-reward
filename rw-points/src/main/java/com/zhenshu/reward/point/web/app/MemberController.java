package com.zhenshu.reward.point.web.app;

import com.zhenshu.reward.common.constant.annotation.LoginToken;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.service.domain.bo.h5.MemberStandardBO;
import com.zhenshu.reward.point.service.domain.bo.h5.UserMemberBO;
import com.zhenshu.reward.point.service.facade.h5.MemberFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hong
 * @version 1.0
 * @date 2024/6/3 19:21
 * @desc 会员标准
 */
@RestController
@RequestMapping("/h5/member")
@Api(tags = "会员", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {
    @Resource
    private MemberFacade memberFacade;

    @PostMapping("/standard")
    @ApiOperation(value = "会员标准")
    public Result<List<MemberStandardBO>> standard() {
        List<MemberStandardBO> list = memberFacade.standard();
        return Result.buildSuccess(list);
    }

    @LoginToken
    @PostMapping("/user")
    @ApiOperation(value = "用户会员信息")
    public Result<UserMemberBO> userMember() {
        UserMemberBO bo = memberFacade.userMember();
        return Result.buildSuccess(bo);
    }
}
