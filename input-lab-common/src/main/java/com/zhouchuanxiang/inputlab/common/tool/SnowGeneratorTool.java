package com.zhouchuanxiang.inputlab.common.tool;


import com.zhouchuanxiang.inputlab.redis.tool.RedisCacheTool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 雪花算法生成全局唯一ID
 * @author hongyang
 * @date 2020/04/26
 */
public class SnowGeneratorTool {

    private static final Logger logger = LoggerFactory.getLogger(SnowGeneratorTool.class);

    /**
     * 时间初始值
     */
    private static final long twepoch = 1288834974657L;

    /**
     * 5位的机器id
     */
    private static final long workerIdBits = 5L;

    /**
     * 5位的机房id
     */
    private static final long datacenterIdBits = 5L;

    /**
     *5bit最多只能有31个数字，机器id最多只能是32以内
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 5bit最多只能有31个数字，机房id最多只能是32以内
     */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /**
     * 每毫秒内产生的id数 2 的 12次方
     */
    private static final long sequenceBits = 12L;
    private static final long workerIdShift = sequenceBits;
    private static final long datacenterIdShift = sequenceBits + workerIdBits;
    private static final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private static final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 机器ID  2进制5位  32位减掉1位 31个
     */
    private static long workerId;

    /**
     * 机房ID 2进制5位  32位减掉1位 31个
     */
    private static long datacenterId;

    /**
     * 代表一毫秒内生成的多个id的最新序号  12位 4096 -1 = 4095 个
     */
    private static long sequence = 0L;
    private static long lastTimestamp = -1L;

    private static final Integer DATA_SIZE = 32;
    private static final String[] RADIX_STR = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v"};
    private static Map<String, Integer> RADIX_MAP = new LinkedHashMap<>();
    public static final  String REIDKS_KEY = "SnowFlakeGenerator_";

    @Autowired
    RedisCacheTool redisCacheTool;


    @Value("${sysType}")
    String sysType;

    static {
        for (int i = 0; i < DATA_SIZE; i++) {
            RADIX_MAP.put(RADIX_STR[i], i);
        }
    }

    public SnowIdDTO calculateDataIdAndWorkId(){
        String key = REIDKS_KEY + sysType;
        long andIncrement = redisCacheTool.increment(key,1);
        //result在0~1023之间
        long result = andIncrement % (DATA_SIZE * DATA_SIZE);
        //将result转化为32进制数，个位为worlId，十位为dataId
        String strResult = Integer.toString(Math.toIntExact(result), DATA_SIZE);
        String strResult2 = StringUtils.leftPad(strResult, 2, "0");
        String substring1 = strResult2.substring(0, 1);
        String substring2 = strResult2.substring(1, 2);
        Integer dataId = RADIX_MAP.get(substring1);
        Integer workId = RADIX_MAP.get(substring2);
        SnowIdDTO snowIdDto = new SnowIdDTO(System.currentTimeMillis(), dataId, workId);
        return snowIdDto;
    }

    private void init() {
        SnowIdDTO snowIdDTO = calculateDataIdAndWorkId();
        logger.info("workerId={} datacenterId={}", snowIdDTO.getWorkerId(), snowIdDTO.getDataCenterId() );
        if (snowIdDTO.getWorkerId() > maxWorkerId || snowIdDTO.getWorkerId() < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }

        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        workerId = snowIdDTO.getWorkerId();
        datacenterId = snowIdDTO.getDataCenterId();
    }


    public static synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    protected static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected static long timeGen() {
        return System.currentTimeMillis();
    }
}



/**
 * 雪花算法机器Id
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
class SnowIdDTO implements Serializable, Comparable<SnowIdDTO> {

    /**
     * 注册时的时间戳
     */
    private Long timestamp;

    /**
     * 数据中心节点  0~31
     */
    private Integer dataCenterId;
    /**
     * 工作节点 0~31
     */
    private Integer workerId;

    @Override
    public int compareTo(SnowIdDTO o) {
        long ex = this.timestamp - o.getTimestamp();
        return ex > 0 ? 1 : -1;
    }
}
