package com.imooc.utils.lambda;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Author xuhaibin
 * @ClassName: LambdaDemo
 * @Description:Lambda表达式练习
 * @Version 1.0.0
 * @Date 2018-08-15 00:34:06
 */
public class LambdaDemo {


    @Test
    public void test1() {
        List<Integer> list = new ArrayList<>();
        Stream<Integer> stream1 = list.stream();


        String[] names = {"chaimm", "peter", "john"};
        Stream<String> stream2 = Arrays.stream(names);

        Stream<String> stream3 = Stream.of("chaimm", "peter", "john");
    }

    /**
     * 过滤
     */
    @Test
    public void test2() {
        // 外部迭代
        List<Integer> list = Lists.newArrayList();
        list.add(1);
        list.add(12);
        list.add(4);
        list.add(2);
        list.add(6);
        list.add(0);
        list.add(2);
        int count = 0;
        for (Integer a : list) {
            if (a > 3) {
                count++;
            }
        }

        System.out.println(count);

        // 内部迭代
        long count1 = list.stream() //进行流操作
                .filter(o -> o > 3) //选出所有大于3的元素
                .count();           //统计数量

        Stream<Integer> stream = list.parallelStream();


        System.out.println(count1);

    }


}
