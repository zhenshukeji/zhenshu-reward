package com.zhenshu.reward.admin.polymerization.points.facade;

import com.zhenshu.reward.admin.polymerization.points.domain.bo.BkTaskDetailsBO;
import com.zhenshu.reward.admin.polymerization.points.domain.vo.BkTaskDetailsAddVO;
import com.zhenshu.reward.admin.polymerization.points.domain.vo.BkTaskDetailsEditVO;
import com.zhenshu.reward.admin.polymerization.points.ganme.GameHandlerManager;
import com.zhenshu.reward.point.service.domain.bo.bk.BkPointsTaskBO;
import com.zhenshu.reward.point.service.domain.vo.bk.BkPointsTaskAddVO;
import com.zhenshu.reward.point.service.domain.vo.bk.BkPointsTaskUpdateVO;
import com.zhenshu.reward.point.web.admin.BkPointsTaskController;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * @author hong
 * @version 1.0
 * @date 2024/5/16 17:30
 * @desc 任务
 */
@Service
public class BkTaskDetailsFacade {
    @Resource
    private BkPointsTaskController bkPointsTaskController;

    @Resource
    private GameHandlerManager gameHandlerManager;

    @Resource
    private TransactionTemplate transactionTemplate;

    /**
     * 新增任务
     */
    public void add(BkTaskDetailsAddVO addVO) {
        BkPointsTaskAddVO taskAddVo = addVO.getTask();
        transactionTemplate.execute(status -> {
            // 新增玩法
            gameHandlerManager.add(addVO);
            // 新增任务
            bkPointsTaskController.add(taskAddVo);
            return true;
        });
    }

    /**
     * 修改任务
     */
    public void update(BkTaskDetailsEditVO editVO) {
        BkPointsTaskUpdateVO taskUpdateVo = editVO.getTask();
        transactionTemplate.execute(status -> {
            // 修改玩法
            gameHandlerManager.edit(editVO);
            // 修改任务
            bkPointsTaskController.update(taskUpdateVo);
            return true;
        });
    }

    /**
     * 任务详情
     *
     * @param id id
     * @return 结果
     */
    public BkTaskDetailsBO details(Long id) {
        BkPointsTaskBO task = bkPointsTaskController.get(id).getData();
        if (task == null) {
            return null;
        }
        Object activity = gameHandlerManager.activity(task.getActivityId(), task.getTaskType());
        BkTaskDetailsBO details = new BkTaskDetailsBO();
        details.setTask(task);
        details.setActivity(activity);
        return details;
    }
}
