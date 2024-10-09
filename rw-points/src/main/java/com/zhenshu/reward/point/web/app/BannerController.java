package com.zhenshu.reward.point.web.app;

import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.service.domain.bo.h5.BannerBO;
import com.zhenshu.reward.point.service.facade.h5.BannerFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ruoyi
 * @version 1.0
 * @date 2024-05-06 14:35:51
 * @desc banner
 */
@RestController
@RequestMapping("/h5/banner")
@Api(tags = "Banner", produces = MediaType.APPLICATION_JSON_VALUE)
public class BannerController {
    @Resource
    private BannerFacade bannerFacade;

    @PostMapping("/list")
    @ApiOperation(value = "列表查询")
    public Result<List<BannerBO>> list() {
        List<BannerBO> list = bannerFacade.list();
        return Result.buildSuccess(list);
    }
}
