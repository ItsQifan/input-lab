package com.zhouchuanxiang.inputlab.utils.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName StopWatchUtil
 * @Description 计时器工具类，支持单任务计时和多任务计时，使用方式详见 StopWatchTest
 * @Author
 * @Date 2025/11/17 14:11
 * @Version 1.0
 */
@Slf4j
public class StopWatchUtil {

    // 时间单位常量
    private static final long NANO_TO_MILLIS = 1000000;
    private static final long MILLIS_TO_SECONDS = 1000;
    private static final long SECONDS_TO_MINUTES = 60;
    private static final long MINUTES_TO_HOURS = 60;

    // 主计时器（单任务模式）
    private long startTimeNanos;
    private long pauseTimeNanos;
    private boolean isRunning;
    private long totalPausedNanos;

    // 多任务计时存储
    private final Map<String, Long> taskMap = new HashMap<>();
    private String currentTaskName;
    private long currentTaskStartTimeNanos;

    /**
     * 初始化计时器（默认未启动）
     */
    public StopWatchUtil() {
        reset();
    }

    /**
     * 重置主计时器
     */
    public void reset() {
        startTimeNanos = 0;
        pauseTimeNanos = 0;
        isRunning = false;
        totalPausedNanos = 0;
        taskMap.clear();
        currentTaskName = null;
        currentTaskStartTimeNanos = 0;
    }

    /**
     * 启动主计时器
     */
    public void start() {
        if (isRunning) {
            throw new IllegalStateException("计时器已在运行中");
        }
        startTimeNanos = System.nanoTime() - totalPausedNanos;
        isRunning = true;
    }

    /**
     * 暂停主计时器
     */
    public void pause() {
        if (!isRunning) {
            throw new IllegalStateException("计时器未运行，无法暂停");
        }
        pauseTimeNanos = System.nanoTime();
        totalPausedNanos = pauseTimeNanos - startTimeNanos;
        isRunning = false;
    }

    /**
     * 继续主计时器（从暂停状态恢复）
     */
    public void resume() {
        if (isRunning) {
            throw new IllegalStateException("计时器已在运行中，无需继续");
        }
        startTimeNanos = System.nanoTime() - totalPausedNanos;
        isRunning = true;
    }

    /**
     * 停止主计时器并返回总耗时（毫秒）
     */
    public long stop() {
        if (!isRunning) {
            throw new IllegalStateException("计时器未运行，无法停止");
        }
        long elapsedNanos = System.nanoTime() - startTimeNanos;
        isRunning = false;
        return elapsedNanos / NANO_TO_MILLIS;
    }

    /**
     * 获取当前主计时器已运行时间（毫秒）
     */
    public long getTimeMillis() {
//        if (isRunning) {
            return (System.nanoTime() - startTimeNanos) / NANO_TO_MILLIS;
//        } else {
//            return totalPausedNanos / NANO_TO_MILLIS;
//        }
    }

    /**
     * 获取当前主计时器已运行时间（秒）
     */
    public double getTimeSeconds() {
        return getTimeMillis() / (double) MILLIS_TO_SECONDS;
    }

    /**
     * 开始一个新的任务计时（多任务模式）
     * @param taskName 任务名称
     */
    public void startTask(String taskName) {
        if (currentTaskName != null) {
            throw new IllegalStateException("当前有任务正在运行：" + currentTaskName);
        }
        currentTaskName = taskName;
        currentTaskStartTimeNanos = System.nanoTime();
    }

    /**
     * 结束当前任务计时（多任务模式）
     * @return 任务耗时（毫秒）
     */
    public long stopTask() {
        if (currentTaskName == null) {
            throw new IllegalStateException("没有正在运行的任务");
        }
        long elapsedNanos = System.nanoTime() - currentTaskStartTimeNanos;
        long elapsedMillis = elapsedNanos / NANO_TO_MILLIS;
        taskMap.put(currentTaskName, elapsedMillis);
        currentTaskName = null;
        return elapsedMillis;
    }

    /**
     * 获取指定任务的耗时（毫秒）
     * @param taskName 任务名称
     * @return 耗时（毫秒），任务不存在返回-1
     */
    public long getTaskTimeMillis(String taskName) {
        return taskMap.getOrDefault(taskName, -1L);
    }

    /**
     * 格式化时间（将毫秒转换为 HH:mm:ss.SSS 格式）
     * @param millis 毫秒数
     * @return 格式化后的时间字符串
     */
    public static String formatTime(long millis) {
        if (millis < 0) {
            return "00:00:00.000";
        }

        long hours = millis / (MILLIS_TO_SECONDS * SECONDS_TO_MINUTES * MINUTES_TO_HOURS);
        millis %= (MILLIS_TO_SECONDS * SECONDS_TO_MINUTES * MINUTES_TO_HOURS);

        long minutes = millis / (MILLIS_TO_SECONDS * SECONDS_TO_MINUTES);
        millis %= (MILLIS_TO_SECONDS * SECONDS_TO_MINUTES);

        long seconds = millis / MILLIS_TO_SECONDS;
        long ms = millis % MILLIS_TO_SECONDS;

        DecimalFormat df = new DecimalFormat("00");
        DecimalFormat msDf = new DecimalFormat("000");
        return String.format("%s:%s:%s.%s",
                df.format(hours),
                df.format(minutes),
                df.format(seconds),
                msDf.format(ms));
    }

    /**
     * 打印所有任务的计时结果
     */
    public void printTaskStats() {
        log.info("===== 任务计时统计 =====");

        taskMap.forEach((name, time) ->
                log.info("{}: {} ({} ms)", name, formatTime(time), time)
        );
    }

}
