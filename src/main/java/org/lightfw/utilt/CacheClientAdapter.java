package org.lightfw.utilt;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis 的简单测试工具类缺省适配器。
 */
public class CacheClientAdapter implements CacheClient {

    private static final RuntimeException UNIMPLEMENTED_EX = new RuntimeException("unimplemented operation");

    @Override
    public String get(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public String set(String key, String value) {
        throw UNIMPLEMENTED_EX;
    }

    public Boolean exists(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long del(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public String set(String key, String value, String nxxx, String expx, long time) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public String echo(String string) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long move(String key, int dbIndex) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long bitcount(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long bitcount(String key, long start, long end) {
        throw UNIMPLEMENTED_EX;
    }
    @Override
    public Long pfadd(String key, String... elements) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public long pfcount(String key) {
        throw UNIMPLEMENTED_EX;
    }


    @Override
    public Long persist(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public String type(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long expire(String key, int seconds) {
        //throw UNIMPLEMENTED_EX;
        return 0L;
    }

    @Override
    public Long pexpire(String key, long milliseconds) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long expireAt(String key, long unixTime) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long pexpireAt(String key, long millisecondsTimestamp) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long ttl(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Boolean setbit(String key, long offset, boolean value) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Boolean setbit(String key, long offset, String value) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Boolean getbit(String key, long offset) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long setrange(String key, long offset, String value) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public String getrange(String key, long startOffset, long endOffset) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public String getSet(String key, String value) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long setnx(String key, String value) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public String setex(String key, int seconds, String value) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long decrBy(String key, long integer) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long decr(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long incrBy(String key, long integer) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Double incrByFloat(String key, double value) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long incr(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long append(String key, String value) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public String substr(String key, int start, int end) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long hset(String key, String field, String value) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public String hget(String key, String field) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long hsetnx(String key, String field, String value) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public String hmset(String key, Map<String, String> hash) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long hincrBy(String key, String field, long value) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Boolean hexists(String key, String field) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long hdel(String key, String... field) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long hlen(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Set<String> hkeys(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public List<String> hvals(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long rpush(String key, String... string) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long lpush(String key, String... string) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long llen(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public String ltrim(String key, long start, long end) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public String lindex(String key, long index) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public String lset(String key, long index, String value) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long lrem(String key, long count, String value) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public String lpop(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public String rpop(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long sadd(String key, String... member) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Set<String> smembers(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long srem(String key, String... member) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public String spop(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Set<String> spop(String key, long count) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long scard(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Boolean sismember(String key, String member) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public String srandmember(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public List<String> srandmember(String key, int count) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long strlen(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long zadd(String key, double score, String member) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long zrem(String key, String... member) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Double zincrby(String key, double score, String member) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long zrank(String key, String member) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long zrevrank(String key, String member) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Set<String> zrevrange(String key, long start, long end) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long zcard(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Double zscore(String key, String member) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public List<String> sort(String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long zcount(String key, double min, double max) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long zcount(String key, String min, String max) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, String max, String min) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long zremrangeByRank(String key, long start, long end) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long zremrangeByScore(String key, double start, double end) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long zremrangeByScore(String key, String start, String end) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long zlexcount(String key, String min, String max) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long zremrangeByLex(String key, String min, String max) {
        throw UNIMPLEMENTED_EX;
    }

      @Override
    public Long lpushx(String key, String... string) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public Long rpushx(String key, String... string) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public List<String> blpop(String arg) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public List<String> blpop(int timeout, String key) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public List<String> brpop(String arg) {
        throw UNIMPLEMENTED_EX;
    }

    @Override
    public List<String> brpop(int timeout, String key) {
        throw UNIMPLEMENTED_EX;
    }
}