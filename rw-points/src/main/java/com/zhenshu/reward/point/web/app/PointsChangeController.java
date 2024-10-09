package com.zhenshu.reward.point.web.app;

import com.zhenshu.reward.common.constant.annotation.LoginToken;
import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.service.domain.bo.h5.PointsChangeBO;
import com.zhenshu.reward.point.service.domain.vo.h5.PointsChangeQueryVO;
import com.zhenshu.reward.point.service.facade.h5.PointChangesFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hong
 * @version 1.0
 * @date 2024/5/22 10:12
 * @desc 积分变动
 */
@RestController
@RequestMapping("/h5/pointsChange")
@Api(tags = "积分变动", produces = MediaType.APPLICATION_JSON_VALUE)
public class PointsChangeController {
    @Resource
    private PointChangesFacade pointChangesFacade;

    @LoginToken
    @PostMapping("/list")
    @ApiOperation(value = "列表查询")
    public Result<List<PointsChangeBO>> list(@RequestBody @Validated PointsChangeQueryVO queryVO) {
        List<PointsChangeBO> list = pointChangesFacade.list(queryVO);
        return Result.buildSuccess(list);
    }
}
