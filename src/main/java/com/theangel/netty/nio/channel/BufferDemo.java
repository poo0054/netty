package com.theangel.netty.nio.channel;

import org.apache.commons.lang3.StringUtils;

import java.nio.IntBuffer;

/**
 * Buffer的使用
 *
 * @author admin
 * Date: 2022/3/16 15:42
 */
public class BufferDemo {
    public static void main(String[] args) {
        //创建一个大小为5的  可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        //向buff中 存放数据
        intBuffer.put(10);
        intBuffer.put(11);
        intBuffer.put(12);
        intBuffer.put(13);
        intBuffer.put(14);
        int capacity = intBuffer.capacity();
        System.out.println("当前容量为:" + capacity);
        //读写切换  读变为取  取变为读
        /**
         * limit = position;   把当前缓存区的最大值 设置成当前位置
         *         position = 0;  当前位置设置成为0
         *         mark = -1;
         *         return this;
         */
        intBuffer.flip();
        //是否存在下一个元素
        while (intBuffer.hasRemaining()) {
            //里面有一个索引进行维护
            System.out.println("取出的数据为：" + intBuffer.get());
        }
        //四个buff很重要的属性
//        private int mark = -1;  标记  一遍不用改动
//        private int position = 0;  当前的位置
//        private int limit;     当前缓存区的终点  最大存放的值
//        private int capacity;  不能改变 开始的时候指定大小


//        hb  真正存放数据的数组

    }
}
