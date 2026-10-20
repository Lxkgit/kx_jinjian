package com.kx.job.util;

import org.quartz.JobExecutionContext;

import com.kx.job.domain.SysJob;

/**
 * 定时任务处理（允许并发执行）
 *
 * @author kangxu
 *
 */
public class QuartzJobExecution extends AbstractQuartzJob
{
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception
    {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
