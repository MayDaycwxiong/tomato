package com.learning.tomato.service.fastdfs;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 图片上传接口
 * @date 2019/5/10 18:10
 */

public class FastDFSClient {
//    private static final String config="file:///android_asset/client.conf";
    private static final String TAG = "FastDFSClient";
    /**
     *
     *
     * @param file
     * @param suffix 图片后缀
     * @return
     */
    public static String upload(File file, String suffix) throws Exception {
        String upload=null;
        FileChannel fileChannel=null;
        FileInputStream fis=null;
        fis=new FileInputStream(file);
        fileChannel=fis.getChannel();
        ByteBuffer byteBuffer=ByteBuffer.allocate((int) fileChannel.size());
        while(fileChannel.read(byteBuffer)>0){}
        byte[] bytes=byteBuffer.array();
        FastDFS fastDFS=FastDFS.getFastDFSInstance();
        upload=fastDFS.upload(bytes,suffix);
        Log.e(TAG,"获得图片上传的路径为："+upload);
        return upload;
    }
}
