package com.zhenshu.reward.app.web;

import com.zhenshu.reward.common.constant.annotation.LoginToken;
import com.zhenshu.reward.common.user.app.domain.bo.LoginBO;
import com.zhenshu.reward.common.user.app.domain.bo.UserBO;
import com.zhenshu.reward.common.user.app.domain.vo.LoginVO;
import com.zhenshu.reward.common.user.app.domain.vo.UserUpdateVO;
import com.zhenshu.reward.common.user.app.facade.UserFacade;
import com.zhenshu.reward.common.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/8/5 15:16
 * @desc 登录接口
 */
@RestController
@RequestMapping("/wx/user")
@Api(tags = "用户接口", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Resource
    private UserFacade userFacade;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result<LoginBO> placeLogin(@RequestBody @Validated LoginVO loginVO) {
        LoginBO loginBO = userFacade.login(loginVO);
        return new Result<LoginBO>().success(loginBO);
    }

    @LoginToken(roles = {})
    @PostMapping("/getInfo")
    @ApiOperation(value = "获取用户信息")
    public Result<UserBO> getInfo() {
        return new Result<UserBO>().success(userFacade.getInfo());
    }

    @LoginToken(roles = {})
    @PostMapping("/logout")
    @ApiOperation(value = "用户退出登录")
    public Result<Object> logout() {
        userFacade.logout();
        return new Result<>().success();
    }

    @LoginToken(roles = {})
    @PostMapping("/update")
    @ApiOperation(value = "用户修改信息")
    public Result<Object> update(@RequestBody @Validated UserUpdateVO updateVO) {
        userFacade.update(updateVO);
        return new Result<>().success();
    }
}
