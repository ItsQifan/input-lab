package com.zhouchuanxiang.inputlab.redis.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 * zhouchuanxiang
 * @date 2025-12-11
 */
@Component
public class RedisCacheTool {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // ============================= 字符串操作 =============================

    /**
     * 字符串操作 - 设置值
     */
    public void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 字符串操作 - 设置值并设置过期时间
     */
    public void setString(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 字符串操作 - 设置值（如果不存在）
     */
    public Boolean setStringIfAbsent(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 字符串操作 - 设置值并设置过期时间（如果不存在）
     */
    public Boolean setStringIfAbsent(String key, String value, long timeout, TimeUnit unit) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
    }

    /**
     * 字符串操作 - 获取值
     */
    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 字符串操作 - 获取并删除
     */
    public String getAndDelete(String key) {
        return (String) redisTemplate.opsForValue().getAndDelete(key);
    }

    /**
     * 字符串操作 - 获取并设置新值
     */
    public String getAndSet(String key, String value) {
        return (String) redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 字符串操作 - 自增
     */
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 字符串操作 - 自减
     */
    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }


    /**
     * 字符串操作 - 获取长度
     */
    public Long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    // ============================= Hash 操作 【相当于码值，有两层】 =============================

    /**
     * Hash 操作 - 设置字段值
     */
    public void setHash(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * Hash 操作 - 批量设置字段值
     */
    public void setHashAll(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * Hash 操作 - 获取字段值
     */
    public Object getHash(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * Hash 操作 - 获取多个字段值
     */
    public List<Object> getHashMulti(String key, Collection<String> fields) {
        return redisTemplate.opsForHash().multiGet(key, Collections.singleton(fields));
    }

    /**
     * Hash 操作 - 获取所有字段值
     */
    public Map<Object, Object> getHashAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * Hash 操作 - 删除字段
     */
    public Long deleteHash(String key, Object... fields) {
        return redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * Hash 操作 - 判断字段是否存在
     */
    public Boolean hasHashKey(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * Hash 操作 - 获取所有字段
     */
    public Set<Object> hashKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * Hash 操作 - 获取所有值
     */
    public List<Object> hashValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }


    /**
     * Hash 操作 - 获取字段数量
     */
    public Long hashSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    // ============================= List 操作 =============================

    /**
     * List 操作 - 添加元素到列表头部
     */
    public void pushList(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * List 操作 - 批量添加元素到列表头部
     */
    public void pushListAll(String key, Object... values) {
        redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * List 操作 - 添加元素到列表尾部
     */
    public void pushListRight(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * List 操作 - 批量添加元素到列表尾部
     */
    public void pushListRightAll(String key, Object... values) {
        redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * List 操作 - 获取列表中的所有元素
     */
    public List<Object> getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * List 操作 - 获取指定范围的元素
     */
    public List<Object> getListRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * List 操作 - 弹出列表头部元素
     */
    public Object popListLeft(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * List 操作 - 弹出列表尾部元素
     */
    public Object popListRight(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * List 操作 - 获取列表长度
     */
    public Long listSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * List 操作 - 根据索引获取元素
     */
    public Object listGetByIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * List 操作 - 根据索引设置元素
     */
    public void listSetByIndex(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * List 操作 - 删除指定值的元素
     * @param count > 0: 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT
     *              < 0: 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值
     *              = 0: 移除表中所有与 VALUE 相等的值
     */
    public Long listRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * List 操作 - 修剪列表，只保留指定范围内的元素
     */
    public void listTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    // ============================= Set 操作 =============================

    /**
     * Set 操作 - 添加元素到集合
     */
    public void addSet(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     * Set 操作 - 移除集合中的元素
     */
    public Long removeSet(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * Set 操作 - 获取集合中的所有元素
     */
    public Set<Object> getSetAll(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * Set 操作 - 判断元素是否在集合中
     */
    public Boolean isSetMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * Set 操作 - 获取集合大小
     */
    public Long setSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * Set 操作 - 随机获取集合中的元素
     */
    public Object setRandomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * Set 操作 - 随机获取集合中的多个元素
     */
    public List<Object> setRandomMembers(String key, long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * Set 操作 - 随机弹出集合中的元素
     */
    public Object setPop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * Set 操作 - 获取多个集合的并集
     */
    public Set<Object> setUnion(String key1, String key2) {
        return redisTemplate.opsForSet().union(key1, key2);
    }

    /**
     * Set 操作 - 获取多个集合的并集并存储到新集合
     */
    public Long setUnionAndStore(String key1, String key2, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key1, key2, destKey);
    }

    /**
     * Set 操作 - 获取多个集合的交集
     */
    public Set<Object> setIntersect(String key1, String key2) {
        return redisTemplate.opsForSet().intersect(key1, key2);
    }

    /**
     * Set 操作 - 获取多个集合的差集
     */
    public Set<Object> setDifference(String key1, String key2) {
        return redisTemplate.opsForSet().difference(key1, key2);
    }

    // ============================= ZSet 操作 =============================

    /**
     * ZSet 操作 - 添加元素到有序集合
     */
    public Boolean addZSet(String key, Object value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * ZSet 操作 - 批量添加元素到有序集合
     */
    public Long addZSetAll(String key, Set<ZSetOperations.TypedTuple<Object>> tuples) {
        return redisTemplate.opsForZSet().add(key, tuples);
    }

    /**
     * ZSet 操作 - 获取有序集合大小
     */
    public Long zSetSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * ZSet 操作 - 获取分数范围内的元素数量
     */
    public Long zSetCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * ZSet 操作 - 增加元素的分数
     */
    public Double zSetIncrementScore(String key, Object value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * ZSet 操作 - 获取元素的排名（从小到大）
     */
    public Long zSetRank(String key, Object value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * ZSet 操作 - 获取元素的排名（从大到小）
     */
    public Long zSetReverseRank(String key, Object value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * ZSet 操作 - 获取元素的分数
     */
    public Double zSetScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * ZSet 操作 - 获取指定排名范围的元素（从小到大）
     */
    public Set<Object> zSetRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * ZSet 操作 - 获取指定分数范围的元素（从小到大）
     */
    public Set<Object> zSetRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * ZSet 操作 - 获取指定排名范围的元素（从大到小）
     */
    public Set<Object> zSetReverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * ZSet 操作 - 获取指定排名范围的元素（带分数）
     */
    public Set<ZSetOperations.TypedTuple<Object>> zSetRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * ZSet 操作 - 移除元素
     */
    public Long zSetRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * ZSet 操作 - 移除排名范围内的元素
     */
    public Long zSetRemoveRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * ZSet 操作 - 移除分数范围内的元素
     */
    public Long zSetRemoveRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    // ============================= 通用操作 =============================

    /**
     * - 设置对象（自动序列化）
     */
    public void setObject(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 高级操作 - 获取对象（自动反序列化）
     */
    public <T> T getObject(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null && clazz.isInstance(value)) {
            return clazz.cast(value);
        }
        return null;
    }


    /**
     * 通用操作 - 设置过期时间
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 通用操作 - 设置过期时间（指定日期）
     */
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 通用操作 - 获取过期时间
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 通用操作 - 获取过期时间（指定时间单位）
     */
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 通用操作 - 删除键
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 通用操作 - 批量删除键
     */
    public Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 通用操作 - 判断键是否存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 通用操作 - 查找匹配的键
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 通用操作 - 重命名键
     */
    public void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 通用操作 - 如果新键不存在则重命名
     */
    public Boolean renameIfAbsent(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }


    /**
     * 通用操作 - 移出键的过期时间，使键永不过期
     */
    public Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 通用操作 - 原子操作 - 获取分布式锁
     */
    public Boolean lock(String key, String value, long timeout, TimeUnit unit) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
    }

    /**
     * 通用操作 - 原子操作 - 释放分布式锁
     */
    public void unlock(String key, String value) {
        String currentValue = (String) redisTemplate.opsForValue().get(key);
        if (value.equals(currentValue)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 发布订阅 - 发布消息
     */
    public void publish(String channel, Object message) {
        redisTemplate.convertAndSend(channel, message);
    }



    // ============================= 高级操作 =============================

    /**
     * 高级操作 - 扫描匹配的键（避免使用 keys 命令）
     */
    public Set<String> scan(String pattern, long count) {
        Set<String> keys = new HashSet<>();
        Cursor<byte[]> cursor = redisTemplate.getConnectionFactory()
                .getConnection()
                .scan(ScanOptions.scanOptions()
                        .match(pattern)
                        .count(count)
                        .build());

        while (cursor.hasNext()) {
            keys.add(new String(cursor.next()));
        }
        cursor.close();
        return keys;
    }




}