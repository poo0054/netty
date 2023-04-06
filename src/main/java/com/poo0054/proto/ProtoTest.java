package com.poo0054.proto;

import com.poo0054.netty.codec.CodecPo;
import org.junit.Test;

/**
 * @author zhangzhi
 * @date 2023/4/6
 */
public class ProtoTest {
    public static void main(String[] args) {
        CodecPo.Study build = CodecPo.Study.newBuilder().setId(1).addEmail("123").setType(CodecPo.Study.Type.StudyType).build();
        System.out.println(build);
    }

    @Test
    public void test() {
        CodecPo.Study build = CodecPo.Study.newBuilder().setId(1).addEmail("123").setType(CodecPo.Study.Type.WorkerType).build();
        System.out.println(build);
    }
}
