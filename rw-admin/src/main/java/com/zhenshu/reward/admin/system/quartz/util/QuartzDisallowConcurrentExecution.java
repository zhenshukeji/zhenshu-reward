package com.zhenshu.reward.admin.system.quartz.util;

import com.zhenshu.reward.admin.system.quartz.domain.SysJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（禁止并发执行）
 *
 * @author zhenshu
 *
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob
{
    @Override
    protected Object doExecute(JobExecutionContext context, SysJob sysJob) throws Exception
    {
        return JobInvokeUtil.invokeMethod(sysJob);
    }
}
