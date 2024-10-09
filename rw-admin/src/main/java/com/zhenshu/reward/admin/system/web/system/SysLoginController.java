package com.zhenshu.reward.admin.system.web.system;

import com.zhenshu.reward.admin.system.global.service.SysLoginService;
import com.zhenshu.reward.admin.system.system.domain.vo.BkForgetPasswordVO;
import com.zhenshu.reward.admin.system.system.service.ISysMenuService;
import com.zhenshu.reward.admin.system.system.service.ISysUserService;
import com.zhenshu.reward.common.constant.annotation.Anonymous;
import com.zhenshu.reward.common.constant.Constants;
import com.zhenshu.reward.common.library.core.domain.AjaxResult;
import com.zhenshu.reward.common.library.core.domain.entity.SysMenu;
import com.zhenshu.reward.common.library.core.domain.entity.SysUser;
import com.zhenshu.reward.common.library.core.domain.model.LoginBody;
import com.zhenshu.reward.common.utils.SecurityUtils;
import com.zhenshu.reward.admin.system.global.service.SysPermissionService;
import com.zhenshu.reward.common.web.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping({"/login", "/surveys/login/account"}) // /surveys/login/account接口地址为tduck开源前端接口适配
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        // tduck开源前端接口适配
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", token);
        ajax.put("data", map);
        return ajax;
    }

    @Anonymous
    @PostMapping("/forgetPassword")
    @ApiOperation(value = "忘记密码")
    public Result<Void> forgetPassword(@RequestBody @Validated BkForgetPasswordVO forgetPasswordVO)
    {
        loginService.forgetPassword(forgetPasswordVO);
        return Result.buildSuccess();
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = sysUserService.selectUserById(SecurityUtils.getUserId());
        user.setPassword(null);
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
