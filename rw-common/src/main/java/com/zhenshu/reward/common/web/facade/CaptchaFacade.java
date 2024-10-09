package com.zhenshu.reward.common.web.facade;

import cn.hutool.core.util.RandomUtil;
import com.zhenshu.reward.common.library.cache.captcha.CaptchaCacheManages;
import com.zhenshu.reward.common.constant.Constants;
import com.zhenshu.reward.common.constant.enums.ErrorEnums;
import com.zhenshu.reward.common.constant.exception.ServiceException;
import com.zhenshu.reward.common.library.third.aliyun.sms.AliyunSmsSender;
import com.zhenshu.reward.common.library.third.aliyun.sms.SmsResult;
import com.zhenshu.reward.common.library.third.aliyun.sms.SmsTemplate;
import com.zhenshu.reward.common.web.domain.vo.CaptchaVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author hong
 * @version 1.0
 * @date 2024/6/3 14:15
 * @desc 验证码
 */
@Component
public class CaptchaFacade {
    @Resource
    private CaptchaCacheManages captchaCacheManages;
    @Resource
    private AliyunSmsSender aliyunSmsSender;

    /**
     * 发送验证码
     *
     * @param captchaVO 参数
     */
    public void captcha(CaptchaVO captchaVO) {
        // 1.判断1分钟内是否发送过
        boolean send = captchaCacheManages.checkPhone(captchaVO.getPhone(), captchaVO.getType());
        if (send) {
            throw new ServiceException(ErrorEnums.NO_THAN_ONE_MINUTE);
        }
        // 2.生成验证码
        String captcha;
        do {
            captcha = RandomUtil.randomNumbers(6);
        } while (captcha.indexOf("0") == Constants.ZERO);
        // 3.发送短信验证码
        boolean result = true;
        // TODO
//        boolean result = this.doCaptcha(captcha, captchaVO.getPhone());
        if (result) {
            captchaCacheManages.saveCaptcha(captchaVO.getPhone(), captcha, captchaVO.getType());
        } else {
            throw new ServiceException(ErrorEnums.SEND_SMS_ERROR);
        }
    }

    /**
     * 发送验证码
     *
     * @param captcha   验证码
     * @param phone 手机号
     * @return 是否成功
     */
    private boolean doCaptcha(String captcha, String phone) {
        HashMap<String, String> map = new HashMap<>(4);
        map.put("code", captcha);
        SmsResult result = aliyunSmsSender.sendTemplate(phone, map, SmsTemplate.captcha);
        return result.isSuccessful();
    }
}
