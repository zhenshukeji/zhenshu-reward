package com.zhenshu.reward.admin.system.web.system;

import com.zhenshu.reward.admin.system.global.service.SysRegisterService;
import com.zhenshu.reward.admin.system.system.service.ISysConfigService;
import com.zhenshu.reward.common.library.core.domain.AjaxResult;
import com.zhenshu.reward.common.utils.StringUtils;
import com.zhenshu.reward.common.library.core.controller.BaseController;
import com.zhenshu.reward.common.library.core.domain.model.RegisterBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册验证
 *
 * @author zhenshu
 */
@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
