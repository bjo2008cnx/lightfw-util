package org.lightfw.utilx.structure;

import org.lightfw.util.lang.StringUtil;
import org.lightfw.utilx.bigdata.SimpleBloomFilter;

public class SimpleBloomFilterTest {
    public static void main(String[] args) {
        String value = "";

        // 定义一个filter，定义的时候会调用构造函数，即初始化七个hash函数对象所需要的信息。
        SimpleBloomFilter filter = new SimpleBloomFilter();
        // 判断是否包含在里面。因为没有调用add方法，所以肯定是返回false

        int count = 1000000;  //测试数据100万元小于默认值33554432,碰撞的概率很小，经测试，大于在36/33554432,约百万分之一

        String prefix = "asdfadsOLdfd#RELsdfdlkkl9uouLLKJljdsfoiueajfoadsjioiuoijoafjdsoji*****lasidfjajdsfoadsfff";
        String postfix = "@186.comasdf3asdfadssspu4pua;osjfa;oeup9ssss33qe3joajfaaqapaodfia7887(*)(*podfuwr4623462";
        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            value = prefix + "186" + StringUtil.leftPad(i, 8, '0') + postfix;
            filter.add(value);
        }

        System.out.println(System.currentTimeMillis() - start);

        long afterAdd = System.currentTimeMillis();
        System.out.println("==========================================================");

        System.out.println(filter.contains("35q45w4"));//应返回false
        System.out.println(filter.contains("35q45wsdffd4"));//应返回false
        System.out.println(filter.contains("18600000359" + postfix));  //应返回false
        System.out.println(filter.contains(prefix + "18600000359" + postfix));   //应返回true
        System.out.println(filter.contains(prefix + "18600888888" + postfix));   //应返回true

        System.out.println(System.currentTimeMillis() - afterAdd);

        //测试误差概率
        int k = 0;
        for (int i = 0; i < 33554432; i++) {
            String tmp = String.valueOf(i); //不应存在的串
            boolean exists = filter.contains(tmp);
            if (exists) {
                k++;
            }
        }
        System.out.println("误差概率：" + k + "/33554432");

        for (int i = 0; i < 33554432; i++) {
            String tmp = String.valueOf(i * 10); //不应存在的串
            boolean exists = filter.contains(tmp);
            if (exists) {
                k++;
            }
        }
        System.out.println("误差概率：" + k + "/33554432");

    }
}