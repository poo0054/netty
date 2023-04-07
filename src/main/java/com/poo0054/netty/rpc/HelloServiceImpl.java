package com.poo0054.netty.rpc;

/**
 * @author zhangzhi
 * @date 2023/4/7
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String msg) {
        return "hi hello" + msg;
    }
}
