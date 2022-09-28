package io.renren.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

        @Autowired
        private StringRedisTemplate redisTemplate;

        /**
         * 写入缓存
         *
         * @param key
         * @param value
         * @return
         */
        public boolean set(final String key, String value) {
            boolean result = false;
            try {
                ValueOperations<String, String> operations = redisTemplate.opsForValue();
                byte[] bytes = key.getBytes("ISO8859-1");
                byte[] bytes2 = value.getBytes("ISO8859-1");
                String str = new String(bytes, "UTF-8");
                String str2 = new String(bytes2, "UTF-8");
                operations.set(str, str2);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
            return result;
        }

        /**
         * 写入缓存设置时效时间
         *
         * @param key
         * @param value
         * @return
         */
        public boolean setExp(final String key, String value, Long expireTime) {
            boolean result = false;
            try {
                ValueOperations<String, String> operations = redisTemplate.opsForValue();
                operations.set(key, value);
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
            return result;
        }

        /**
         * 批量删除对应的value
         *
         * @param keys
         */
        public void removes(final String... keys) {
            for (String key : keys) {
                remove(key);
            }
        }

        /**
         * 批量删除key
         *
         * @param pattern
         */
        public void removes(final String pattern) {
            try {
                Set<String> keys = redisTemplate.keys(pattern);
                if (keys.size() > 0) {
                    redisTemplate.delete(keys);
                }
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }
        /**
         * 删除对应的value
         *
         * @param key
         */
        public void remove(final String key) {
            try {
                if (exists(key)) {
                    redisTemplate.delete(key);
                }
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }
        /**
         * 判断缓存中是否有对应的key
         *
         * @param key
         * @return
         */
        public boolean exists(final String key) {
            try {
                return redisTemplate.hasKey(key);
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }
        /**
         * 读取缓存
         *
         * @param key
         * @return
         */
        public String get(final String key) {
            try {
                ValueOperations<String, String> operations = redisTemplate.opsForValue();
                String result = operations.get(key);
                return result;
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }
        /**
         * 哈希 添加
         *
         * @param key
         * @param field
         * @param value
         */
        public void hmSet(String key, String field, String value) {
            try {
                HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
                hash.put(key, field, value);
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }

        /**
         *
         * @param key
         * @param field
         * @param value
         * @param expireTime
         */
        public void hmSet(String key, String field, String value, Long expireTime) {
            try {
                HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
                hash.put(key, field, value);
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }

        /**
         * 哈希获取数据
         *
         * @param key
         * @param hashKey
         * @return
         */
        public String hmGet(String key, String hashKey) {
            try {
                HashOperations<String, String, String> hash = redisTemplate.opsForHash();
                return hash.get(key, hashKey);
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }


        public Map<String, String> hmGetAll(String key) {
            try {
                HashOperations<String, String, String> hash = redisTemplate.opsForHash();
                return hash.entries(key);
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }

        public void hmDel(String key, String field) {
            try {
                HashOperations<String, String, String> hash = redisTemplate.opsForHash();
                hash.delete(key, field);
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }

        public void hmdels(String key, List<Long> fields) {
            try {
                HashOperations<String, String, String> hash = redisTemplate.opsForHash();
                if (CollectionUtils.isEmpty(fields)) {
                    return;
                }
                for (Long field : fields) {
                    hash.delete(key, field.toString());
                }
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }

        /**
         * 列表添加
         *
         * @param key
         * @param value
         */
        public void lSet(String key, String value) {
            try {
                ListOperations<String, String> list = redisTemplate.opsForList();
                list.rightPush(key, value);
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }


        public boolean lSets(String key, String... value) {
            try {
                ListOperations<String, String> list = redisTemplate.opsForList();

                list.rightPushAll(key, value);
                return true;
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }

        /**
         * 列表获取
         *
         * @param key
         * @param startIndex
         * @param endIndex
         * @return
         */
        public List<String> lGet(String key, long startIndex, long endIndex) {
            try {
                ListOperations<String, String> list = redisTemplate.opsForList();
                return list.range(key, startIndex, endIndex);
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }

        public Long lGetSize(String key) {
            try {
                ListOperations<String, String> list = redisTemplate.opsForList();
                return list.size(key);
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }


        public boolean ldel(String key, long var2, Object var4) {
            try {
                ListOperations<String, String> list = redisTemplate.opsForList();
                return list.remove(key,var2,var4) >0;
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }


        /**
         * 集合添加
         *
         * @param key
         * @param value
         */
        public void sSet(String key, String value) {
            try {
                SetOperations<String, String> set = redisTemplate.opsForSet();
                set.add(key, value);
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }


        public void sSet(String key, String value,Long expireTime) {
            try {
                SetOperations<String, String> set = redisTemplate.opsForSet();
                set.add(key, value);
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);

            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }

        /**
         * 集合获取
         *
         * @param key
         * @return
         */
        public Set<String> sGet(String key) {
            try {
                SetOperations<String, String> set = redisTemplate.opsForSet();
                return set.members(key);
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }

        /**
         * 有序集合添加
         *
         * @param key
         * @param value
         * @param scoure
         */
        public void zsset(String key, String value, double scoure) {
            try {
                ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
                zset.add(key, value, scoure);
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }

        /**
         * 有序集合获取
         *
         * @param key
         * @param scoure
         * @param scoure1
         * @return
         */
        public Set<String> zsget(String key, double scoure, double scoure1) {
            try {
                ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
                return zset.rangeByScore(key, scoure, scoure1);
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }

        /**
         * 获取所有set
         * @param key
         * @return
         */
        public Set<String> zsetGetAll(String key) {
            try {
                ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
                return zset.range(key, 0, -1);
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }
        /**
         * 获取范围的元素来自start于end从下令从低分到高分排序集。
         * @param key
         * @param start
         * @param end
         * @return
         */
        public Set<String> rangeByScore(String key, long start, long end) {
            try {
                ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
                return zset.rangeByScore(key, start, end);
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }

        public Double getScore(String key, String member) {
            try {
                ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
                return zset.score(key,member);
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
        }


        /**
         * 删除zset元素
         * @param key
         * @param val
         * @return
         */
        public boolean zdel(String key, String... val) {
            try {
                ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
                zset.remove(key,val);
                return zset.remove(key,val) > 0;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
            }
            return false;
        }


        public int removeListValue(String key, List<String> values) {
            int result = 0;
            if (values != null && values.size() > 0) {
                for (String value : values) {
                    if (ldel(key, Long.valueOf(1),value)) {
                        result++;
                    }
                }
            }
            return result;
        }
}
