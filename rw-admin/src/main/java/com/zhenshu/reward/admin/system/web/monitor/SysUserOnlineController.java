package com.zhenshu.reward.admin.system.web.monitor;

import com.zhenshu.reward.admin.system.system.service.ISysUserOnlineService;
import com.zhenshu.reward.common.library.core.domain.AjaxResult;
import com.zhenshu.reward.common.constant.enums.LoginIdentity;
import com.zhenshu.reward.common.utils.StringUtils;
import com.zhenshu.reward.common.web.login.LoginCacheManages;
import com.zhenshu.reward.common.constant.annotation.Log;
import com.zhenshu.reward.common.library.core.controller.BaseController;
import com.zhenshu.reward.common.library.core.domain.model.LoginUser;
import com.zhenshu.reward.common.library.core.page.TableDataInfo;
import com.zhenshu.reward.common.constant.enums.BusinessType;
import com.zhenshu.reward.admin.system.system.domain.SysUserOnline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 在线用户监控
 *
 * @author zhenshu
 */
@RestController
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController {
    @Autowired
    private ISysUserOnlineService userOnlineService;
    @Resource
    private LoginCacheManages loginCacheManages;

    @PreAuthorize("@ss.hasPermi('monitor:online:list')")
    @GetMapping("/list")
    public TableDataInfo list(String ipaddr, String userName) {
        List<SysUserOnline> userOnlineList = new ArrayList<>();
        loginCacheManages.foreachLoginUser(LoginIdentity.admin, key -> {
            LoginUser user = loginCacheManages.getLogin(StringUtils.toEncodedString(key, StandardCharsets.UTF_8), LoginUser.class);
            if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
                userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
            } else if (StringUtils.isNotEmpty(ipaddr)) {
                userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
            } else if (StringUtils.isNotEmpty(userName) && StringUtils.isNotNull(user.getUser())) {
                userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
            } else {
                // 格式转换
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        });
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return getDataTable(userOnlineList);
    }

    /**
     * 强退用户
     */
    @PreAuthorize("@ss.hasPermi('monitor:online:forceLogout')")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @DeleteMapping("/{tokenId}")
    public AjaxResult forceLogout(@PathVariable String tokenId) {
        loginCacheManages.deleteLogin(null, tokenId, LoginIdentity.admin);
        return AjaxResult.success();
    }
}
