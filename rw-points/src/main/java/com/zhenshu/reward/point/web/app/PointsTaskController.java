package com.zhenshu.reward.point.web.app;

import com.zhenshu.reward.common.web.Result;
import com.zhenshu.reward.point.service.domain.bo.h5.PointsTaskBO;
import com.zhenshu.reward.point.service.facade.h5.PointsTaskFacade;
import com.zhenshu.reward.common.constant.annotation.LoginToken;
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
 * @date 2023/12/12 14:21
 * @desc 任务
 */
@RestController
@RequestMapping("/h5/pointsTask")
@Api(tags = "任务", produces = MediaType.APPLICATION_JSON_VALUE)
public class PointsTaskController {
    @Resource
    private PointsTaskFacade pointsTaskFacade;

    @LoginToken
    @PostMapping
    @ApiOperation(value = "任务")
    public Result<List<PointsTaskBO>> tasks() {
        List<PointsTaskBO> tasks = pointsTaskFacade.tasks();
        return Result.buildSuccess(tasks);
    }
}
