package org.lightfw.util.ext.algorithm;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.lightfw.util.common.collection.ListUtil;
import org.lightfw.utilx.bigdata.ConsistentHash;

import java.util.List;

/**
 * HashTest
 *
 * @author Wangxm
 * @date 2016/5/28
 */
public class ConsistentHashTest {
    public static void main(String[] args) {
        main3(args);
    }

    public static void main2(String[] args) {
        List<String> al = ListUtil.newArrayList("redis1", "redis2", "redis3", "redis4", "redis5");

        String[] userIds = {"-84942321036308", "-76029520310209", "-68343931116147", "-54921760962352", "3763465345", "324532", "33545243"};
        HashFunction hf = Hashing.md5();

        ConsistentHash<String> consistentHash = new ConsistentHash<String>(hf, 100, al);
        for (String userId : userIds) {
            System.out.println(consistentHash.get(userId));
        }
    }

    public static void main3(String[] args) {
        //模拟redis5 down 掉
        List<String> al = ListUtil.newArrayList("redis1", "redis2", "redis3", "redis4");

        String[] userIds = {"-84942321036308", "-76029520310209", "-68343931116147", "-54921760962352", "3763465345", "324532", "33545243"};
        HashFunction hf = Hashing.md5();

        ConsistentHash<String> consistentHash = new ConsistentHash<String>(hf, 100, al);
        for (String userId : userIds) {
            System.out.println(consistentHash.get(userId));
        }
    }

    public static void main4(String[] args) {
        //模拟redis5 down 掉
        List<String> al = ListUtil.newArrayList("redis1", "redis2", "redis3", "redis4");

        String[] userIds = {"-84942321036308", "-76029520310209", "-68343931116147", "-54921760962352", "3763465345", "324532", "33545243"};
        HashFunction hf = Hashing.md5();

        ConsistentHash<String> consistentHash = new ConsistentHash<String>(hf, 100, al);
        for (String userId : userIds) {
            System.out.println(consistentHash.get(userId));
        }

//    var servers = ['a', 'b', 'c'],
//    key = 'foo',
//    vnodes = [0, 1, 2, 0, 1, 2]; // each server is assigned several vnodes
//
//    server = servers[vnodes[hash('foo') % vnodes.length]];
    }


}
