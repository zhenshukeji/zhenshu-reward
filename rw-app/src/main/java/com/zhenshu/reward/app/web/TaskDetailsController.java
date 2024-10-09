package com.zhenshu.reward.app.web;

import com.zhenshu.reward.app.domain.bo.TaskDetailsBO;
import com.zhenshu.reward.app.facade.TaskDetailsFacade;
import com.zhenshu.reward.common.constant.annotation.LoginToken;
import com.zhenshu.reward.common.web.Result;
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
 * @date 2024/5/15 15:22
 * @desc 任务
 */
@RestController
@RequestMapping("/h5/task/details")
@Api(tags = "任务详情", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskDetailsController {
    @Resource
    private TaskDetailsFacade taskDetailsFacade;

    @LoginToken
    @PostMapping
    @ApiOperation(value = "任务详情列表")
    public Result<List<TaskDetailsBO>> tasks() {
        List<TaskDetailsBO> tasks = taskDetailsFacade.tasks();
        return Result.buildSuccess(tasks);
    }
}
