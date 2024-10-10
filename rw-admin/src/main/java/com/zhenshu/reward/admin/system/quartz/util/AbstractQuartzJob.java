package com.zhenshu.reward.admin.system.quartz.util;

import com.zhenshu.reward.admin.system.quartz.constants.ScheduleConstants;
import com.zhenshu.reward.admin.system.quartz.domain.SysJob;
import com.zhenshu.reward.admin.system.quartz.domain.SysJobLog;
import com.zhenshu.reward.admin.system.quartz.service.ISysJobLogService;
import com.zhenshu.reward.common.constant.Constants;
import com.zhenshu.reward.common.utils.ExceptionUtil;
import com.zhenshu.reward.common.utils.bean.BeanUtils;
import com.zhenshu.reward.common.utils.json.JsonUtil;
import com.zhenshu.reward.common.utils.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Objects;

/**
 * 抽象quartz调用
 *
 * @author zhenshu
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {

    /**
     * 线程本地变量
     */
    private static final ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    public static final String SEPARATOR = " | ";

    /**
     * 主机名称
     */
    private final String hostName;

    {
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.hostName = hostName;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SysJob sysJob = new SysJob();
        BeanUtils.copyBeanProp(sysJob, context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES));
        try {
            before(context, sysJob);
            Object result = doExecute(context, sysJob);
            after(context, sysJob, result, null);
        } catch (Exception e) {
            log.error("任务执行异常  - ：", e);
            after(context, sysJob, null, e);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     */
    protected void before(JobExecutionContext context, SysJob sysJob) {
        threadLocal.set(new Date());
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文对象
     * @param result  执行结果
     * @param sysJob  系统计划任务
     */
    protected void after(JobExecutionContext context, SysJob sysJob, Object result, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();

        final SysJobLog sysJobLog = new SysJobLog();
        sysJobLog.setJobName(sysJob.getJobName());
        sysJobLog.setJobGroup(sysJob.getJobGroup());
        sysJobLog.setInvokeTarget(sysJob.getInvokeTarget());
        sysJobLog.setStartTime(startTime);
        sysJobLog.setStopTime(new Date());
        sysJobLog.setCreateTime(startTime);
        long runMs = sysJobLog.getStopTime().getTime() - sysJobLog.getStartTime().getTime();
        sysJobLog.setExecuteTime(runMs);
        sysJobLog.setJobMessage("总共耗时：" + runMs + "毫秒;");
        try {
            String instanceId = context.getScheduler().getSchedulerInstanceId();
            sysJobLog.setJobMessage("运行实例:" + instanceId + "; " + sysJobLog.getJobMessage());
            sysJobLog.setHostName(hostName);
        } catch (SchedulerException ignored) {}
        if (Objects.nonNull(result)) {
            sysJobLog.setResult(JsonUtil.toJson(result));
        }
        if (e != null) {
            sysJobLog.setStatus(Constants.FAIL);
            String errorMsg = ExceptionUtil.getExceptionMessage(e);
            sysJobLog.setExceptionInfo(errorMsg);
        } else {
            sysJobLog.setStatus(Constants.SUCCESS);
        }

        // 写入数据库当中
        SpringUtils.getBean(ISysJobLogService.class).addJobLog(sysJobLog);
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param sysJob  系统计划任务
     * @return 执行结果
     * @throws Exception 执行过程中的异常
     */
    protected abstract Object doExecute(JobExecutionContext context, SysJob sysJob) throws Exception;
}
