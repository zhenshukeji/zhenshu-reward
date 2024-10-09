package com.zhenshu.reward.common.library.cache;

import cn.hutool.core.util.RandomUtil;
import com.zhenshu.reward.common.constant.Constants;
import com.zhenshu.reward.common.library.core.redis.RedisUtils;
import com.zhenshu.reward.common.constant.domain.PageEntity;
import com.zhenshu.reward.common.utils.json.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author jing
 * @version 1.0
 * @desc 缓存以及业务类
 * @date 2020/6/11 0011 13:50
 * <p>
 * 方法不传递time、time传递Null: 永不过期
 * time大于0: 设置指定的过期时间
 */
@Component
public class CacheManages {
    @Resource
    protected RedisUtils cache;
    @Resource(name = "stringRedisTemplate")
    protected StringRedisTemplate redisTemplate;

    @Resource
    protected RedissonClient redissonClient;

    /**
     * 获取一天的秒数，加上一小时的随机时间，避免缓存雪崩
     *
     * @return 缓存过期时间, 单位秒
     */
    public long getDayTime() {
        return Constants.DAY_TIME_SECOND + RandomUtil.randomInt(0, Constants.ONE_HOUR);
    }

    /**
     * 获取一小时的秒数，加上5分钟的随机时间，避免缓存雪崩
     *
     * @return 缓存过期时间, 单位秒
     */
    public long getHourTime() {
        return Constants.ONE_HOUR + RandomUtil.randomInt(0, Constants.FIVE_MINUTES);
    }

    /**
     * 获取Hash类型分页key
     *
     * @param pageEntity 分页入参
     * @return 分页key
     */
    public String getPageHashKey(PageEntity pageEntity) {
        return String.format("%d-%d", pageEntity.getPageNum(), pageEntity.getPageSize());
    }

    /**
     * 保存HashKey, 同时设置过期时间, 默认单位秒;
     *
     * @param cacheKey 缓存Key
     * @param hashKey  hashKey
     * @param data     数据
     * @param time     过期时间
     */
    protected List<Object> hSet(String cacheKey, String hashKey, String data, Long time) {
        return this.hSet(cacheKey, hashKey, data, time, TimeUnit.SECONDS);
    }

    /**
     * 保存HashKey, 同时设置过期时间; 支持毫秒和秒
     *
     * @param cacheKey 缓存Key
     * @param hashKey  hashKey
     * @param data     数据
     * @param time     过期时间
     * @param timeUnit 时间单位
     * @return 结果
     */
    protected List<Object> hSet(String cacheKey, String hashKey, String data, Long time, TimeUnit timeUnit) {
        return this.pipeline(connection -> {
            // 设置缓存
            connection.hSet(cacheKey, hashKey, data);
            // 设置过期时间
            if (Objects.isNull(time)) {
                return;
            }
            if (timeUnit == TimeUnit.SECONDS) {
                connection.expire(cacheKey, time);
            } else if (timeUnit == TimeUnit.MILLISECONDS) {
                connection.pExpire(cacheKey, time);
            }
        });
    }

    /**
     * 缓存获取数据
     *
     * @param cacheKey 缓存key
     * @param supplier 获取数据的函数式接口
     * @param tClass   转换的class对象
     * @param time     过期时间
     * @param <T>      泛型
     * @return 结果
     */
    protected <T> T get(String cacheKey, Supplier<T> supplier, Class<T> tClass, Long time) {
        String data = cache.get(cacheKey);
        if (StringUtils.isEmpty(data)) {
            if (Objects.nonNull(supplier)) {
                return this.set(cacheKey, supplier, time);
            } else {
                return null;
            }
        }
        return JsonUtil.toObject(data, tClass);
    }

    /**
     * 缓存获取数据
     *
     * @param cacheKey 缓存key
     * @param tClass   转换的class对象
     * @param <T>      泛型
     * @return 结果
     */
    public  <T> T get(String cacheKey, Class<T> tClass) {
        return this.get(cacheKey, null, tClass, null);
    }

    /**
     * 设置数据到redis中, 当过期时间为null或小于0时, 将设置永不过期的缓存
     * 过期时间为null: 一天的过期时间
     * 过期时间等于小于0: 永不过期
     * 过期时间大于0: 设置指定的过期时间
     *
     * @param cacheKey 缓存key
     * @param t        数据
     * @param time     过期时间
     * @param <T>      泛型
     * @return 缓存对象
     */
    public <T> T set(String cacheKey, T t, Long time) {
        if (time == null) {
            cache.set(cacheKey, JsonUtil.toJson(t));
        } else {
            cache.set(cacheKey, JsonUtil.toJson(t), time);
        }
        return t;
    }

    protected <T> T set(String cacheKey, Supplier<T> supplier, Long time) {
        return this.set(cacheKey, supplier.get(), time);
    }

    /**
     * 缓存获取数据, 永不过期
     *
     * @param cacheKey 缓存key
     * @param supplier 获取数据的函数式接口
     * @param tClass   转换的class对象
     * @param <T>      泛型
     * @return 结果
     */
    protected <T> T get(String cacheKey, Supplier<T> supplier, Class<T> tClass) {
        return this.get(cacheKey, supplier, tClass, null);
    }

    /**
     * 缓存获取数据
     *
     * @param cacheKey 缓存key
     * @param supplier 获取数据的函数式接口
     * @param tClass   转换的class对象
     * @param time     缓存的过期时间
     * @param <T>      泛型
     * @return 结果
     */
    protected <T> List<T> getList(String cacheKey, Supplier<List<T>> supplier, Class<T> tClass, Long time) {
        String data = cache.get(cacheKey);
        if (StringUtils.isEmpty(data)) {
            return this.set(cacheKey, supplier, time);
        }
        return JsonUtil.toArray(data, tClass);
    }

    /**
     * 缓存获取数据, 时间一天
     *
     * @param cacheKey 缓存key
     * @param supplier 获取数据的函数式接口
     * @param tClass   转换的class对象
     * @param <T>      泛型
     * @return 结果
     */
    protected <T> List<T> getList(String cacheKey, Supplier<List<T>> supplier, Class<T> tClass) {
        return this.getList(cacheKey, supplier, tClass, null);
    }

    /**
     * 缓存获取数据, 永不过期
     *
     * @param cacheKey 缓存key
     * @param supplier 获取数据的函数式接口
     * @param tClass   转换的class对象
     * @param <T>      泛型
     * @return 结果
     */
    protected <T> List<T> page(String cacheKey, PageEntity pageEntity, Supplier<List<T>> supplier, Class<T> tClass) {
        return this.page(cacheKey, pageEntity, supplier, tClass, null);
    }

    /**
     * 缓存获取数据
     *
     * @param cacheKey 缓存key
     * @param supplier 获取数据的函数式接口
     * @param tClass   转换的class对象
     * @param time     缓存的过期时间
     * @param <T>      泛型
     * @return 结果
     */
    protected <T> List<T> page(String cacheKey, String hashKey, Supplier<List<T>> supplier, Class<T> tClass, Long time) {
        String data = (String) cache.hGet(cacheKey, hashKey);
        if (StringUtils.isEmpty(data)) {
            List<T> list = supplier.get();
            this.hSet(cacheKey, hashKey, JsonUtil.toJson(list), time);
            return list;
        }
        return JsonUtil.toArray(data, tClass);
    }

    /**
     * 缓存获取数据
     *
     * @param cacheKey 缓存key
     * @param supplier 获取数据的函数式接口
     * @param tClass   转换的class对象
     * @param time     缓存的过期时间
     * @param <T>      泛型
     * @return 结果
     */
    protected <T> List<T> page(String cacheKey, PageEntity pageEntity, Supplier<List<T>> supplier, Class<T> tClass, Long time) {
        return this.page(cacheKey, this.getPageHashKey(pageEntity), supplier, tClass, time);
    }

    /**
     * 管道操作
     *
     * @param consumer redis操作
     * @return 结果
     */
    public List<Object> pipeline(Consumer<StringRedisConnection> consumer) {
        return redisTemplate.executePipelined((RedisCallback<?>) redisConnection -> {
            StringRedisConnection connection = (StringRedisConnection) redisConnection;
            connection.openPipeline();
            consumer.accept(connection);
            return null;
        });
    }


    /**
     * 获取redisson锁
     *
     * @param lockKey 锁Key
     * @return 结果
     */
    protected RLock getRLock(String lockKey) {
        return redissonClient.getLock(lockKey);
    }
}

