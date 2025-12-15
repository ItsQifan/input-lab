package com.zhouchuanxiang.inputlab.wheels.job.standalone.springtask._01simple;


import com.zhouchuanxiang.inputlab.common.tool.SnowGeneratorTool;
import com.zhouchuanxiang.inputlab.redis.tool.RedisCacheTool;
import com.zhouchuanxiang.inputlab.utils.util.GetIPUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * spring task 举例
 * @Author zhouchuanxiang
 * @Description
 * 注意事项：
 * 1-执行任务锁
 * 2-设计可重跑
 * 3-记录日志【执行时间 / 执行状态 / 】
 * 4-finally 块，记录日志，删除锁
 *

 *
 * @Date 14:03 2025/5/21
 * @Param
 * @return
 **/
@Component
@Slf4j
public class SpringTaskSimpleExampleJob {

    @Autowired
    private RedisCacheTool redisCacheTool;

    //执行日期，格式yyyyMMdd，例如“20190410”
    private String jobDate;
    private final String lockKey = "MON_JOB_REDIS_LOCK_KEY";

    /**
     * @return
     * @Author zhouchuanxiang
     * @Description 定时任务执行的方法
     * @Date 16:59 2025/5/22
     * @Param []
     **/
    @Scheduled(cron = "${task.cron}")
    public void execute() {
        //获取锁
        if (!getLock()) {
            log.info("企业监控任务MonCompanyJob未获取到锁，return");
            return;
        }

        log.info("企业监控任务MonCompanyJob开始,ip={}", GetIPUtil.getLocalIPAddresses());
        long startTime = System.currentTimeMillis();


        //本次的跑批batchNo
        String jobLogId ="BATCH_NO_"+ SnowGeneratorTool.nextId();
        //重跑标志


        //当日

        try {
            log.info("企业监控任务MonCompanyJob，jobDate={}", jobDate);

            String status="";
            //校验状态：已执行中的不允许重跑，成功和失败都可以重跑

            if (status.equals("1")) {
                log.warn("企业监控任务MonCompanyJob，{}正在执行中，无法重跑", jobLogId);
                return;
            }

            //重跑
            //删除的表：t_p_mon_result_base的MONITOR_TYPE=1的数据
            //deleteDateForReExecute(jobLogId, MonitorTypeEnum.ENT_MON);

            //设置数据库状态为 执行中
            //monJobLogDTO.setSuccess(JobEnum.ING.getCode());
            writeJobLog();

            //执行
            doCallSk(jobLogId);

            //执行成功
//            monJobLogDTO.setSuccess(MonJobSuccessEnum.YES.getCode());
//            monJobLogDTO.setMessage(MonitorConstant.EMPTY_STR);
        } catch (Exception e) {
            //执行失败
//            monJobLogDTO.setSuccess(MonJobSuccessEnum.NO.getCode());
            if (StringUtils.isNotBlank(e.getMessage())) {
                //错误日志入库
//                monJobLogDTO.setMessage(e.getMessage().length() > 200 ? e.getMessage().substring(0, 200) : e.getMessage());
            }
            log.error("企业监控任务MonCompanyJob失败！jobLogId:{}", jobLogId, e);
        } finally {
            //耗时
//            monJobLogDTO.setTime((int) (System.currentTimeMillis() - startTime) / 1000);
//            writeJobLog(monJobLogDTO);
            //删除锁
//            redisManager.deleteByKey(CMKey.MON_JOB_REDIS_LOCK_KEY.getKey(MonitorTypeEnum.ENT_MON.getCode()));
        }

    }


    /**
     * @return
     * @Author zhouchuanxiang
     * @Description 掉数科 和处理返回值
     * @Date 16:59 2025/5/22
     * @Param []
     **/
    public void doCallSk(String jobLogId) throws Exception {
        long startTime = System.currentTimeMillis();

        log.info("企业监控任务MonCompanyJob jobDate={}", jobDate);


        log.info("企业监控任务MonCompanyJob结束，耗时秒：{}", (System.currentTimeMillis() - startTime) / 1000);
    }



    /**
     * @return
     * @Author zhouchuanxiang
     * @Description 获取锁
     * @Date 17:49 2025/5/28
     * @Param [monitorTypeEnum]
     **/
    protected boolean getLock() {

        log.info("监控任务获取锁：{}", lockKey);
        return redisCacheTool.lock(lockKey, "1" ,10, TimeUnit.MINUTES);
    }

    /**
     * @return
     * @Author zhouchuanxiang
     * @Description 管理日志状态
     * @Date 16:37 2025/6/1
     * @Param [typeEnum, jobLogId, success]
     **/
    protected void writeJobLog() {
        if (true) {
            //写入执行中

        } else {
           //更新为成功或者失败
        }
    }

}
