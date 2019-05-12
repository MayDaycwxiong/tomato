package com.learning.tomato.until.fastdfs;

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
        String path="C:\\Users\\14517\\Desktop\\zhu.png";
        String url=FastDFSClient.upload(new File(path), "png");
        System.out.println(url);
    }
}