package com.learning.tomato.service.fastdfs;

import com.alibaba.fastjson.JSON;
import com.learning.tomato.dao.ReceiveMessage;

import org.junit.Test;

import java.io.File;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: TODO
 * @date 2019/5/10 18:19
 */
public class FastDFSClientTest {
    private static final String TAG = "FastDFSClientTest";
    @Test
    public void upload() throws Exception {
//        String path="C:\\Users\\14517\\Desktop\\zhu.png";
//        String url=FastDFSClient.upload(new File(path), "png");
//        System.out.println(url);

        String s="{\"imageId\":\"http://129.204.108.13:8888/group1/M00/00/00/rBAAB1ziFYCEMDYxAAAAAEQY8Wo843.jpg\",\"message\":\"傻蛋\",\"name\":\"优秀\",\"time\":\"2019-05-22\",\"usreid\":\"15243643896\"}";
        ReceiveMessage receiveMessage= JSON.parseObject(s,ReceiveMessage.class);
        System.out.println(receiveMessage.toString());
        System.out.println(receiveMessage.getMessage());
    }
}