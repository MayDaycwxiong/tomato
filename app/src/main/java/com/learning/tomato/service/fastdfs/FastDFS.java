package com.learning.tomato.service.fastdfs;

import android.util.Log;

import com.learning.tomato.until.MyStaticResource;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;

import java.net.InetSocketAddress;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: fastdfs-java客户端
 * @date 2019/5/10 15:18
 */

public class FastDFS {
    private static FastDFS fastDFS=null;
    private static final String TAG = "FastDFS";
    private TrackerClient trackerClient=null;
    private TrackerServer trackerServer=null;
    private StorageServer storageServer=null;
    private StorageClient storageClient=null;

    private FastDFS() {
        try {
            initClientGlobal();
            Log.e(TAG,"初始化配置文件完成");
            trackerClient=new TrackerClient();
            trackerServer=trackerClient.getConnection();
            storageClient=new StorageClient(trackerServer,storageServer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 用配置文件的方式加载出错，这里主动设置ClientGlobal参数
     */
    private void initClientGlobal() {
        //连接超时的时限，单位为毫秒
        ClientGlobal.setG_connect_timeout(MyStaticResource.CONNECTTIMEOUT);
        //网络超时的时限，单位为毫秒
        ClientGlobal.setG_network_timeout(MyStaticResource.NETWORKTIMEOUT);
        ClientGlobal.setG_anti_steal_token(false);
        //字符集
        ClientGlobal.setG_charset(MyStaticResource.FDFSCHARSET);
        ClientGlobal.setG_secret_key(null);
        //HTTP访问服务的端口号
        ClientGlobal.setG_tracker_http_port(MyStaticResource.FDFSTRACKERHTTPPORT);
        //Tracker服务器列表
        InetSocketAddress[] tracker_servers = new InetSocketAddress[1];
        tracker_servers[0] = new InetSocketAddress(MyStaticResource.FDFSTRACKERURL,MyStaticResource.FDFSTRACKERPORT);
        ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));
    }

    public String upload(String filename,String file_ext_name) throws Exception{
        return upload(filename,file_ext_name,null);
    }

    /**
     * 根据文件路径上传
     * @param filename
     * @param file_ext_name
     * @param meta_list
     * @return
     * @throws Exception
     */
    private String upload(String filename, String file_ext_name, NameValuePair[] meta_list)throws Exception {
        String[] url=null;
        url=storageClient.upload_file(filename,file_ext_name,meta_list);
        String path="";
        for(String string:url){
            path+=string+"/";
        }
        return path.substring(0,path.length()-1);
    }

    /**
     * 文件byte数组上传
     * @param fileContent
     * @param extName
     * @param metas
     * @return
     * @throws Exception
     */
    public String upload(byte[] fileContent,String extName,NameValuePair[] metas)throws Exception{
        String[] url=storageClient.upload_appender_file(fileContent,extName,metas);
        String path="";
        for(String string:url){
            path+=string+"/";
        }
        return path.substring(0,path.length()-1);
    }
    public String upload(byte[] fileContent,String extName) throws Exception{
        return upload(fileContent,extName,null);
    }
    /**
     * 获取上传唯一实例
     * @return
     * @throws Exception
     */
    public static FastDFS getFastDFSInstance() throws Exception {
        if(fastDFS==null){
            synchronized (FastDFS.class){
                if(fastDFS==null){
                    fastDFS=new FastDFS();
                    Log.e(TAG,"第一次创建上传对象");
                }
            }
        }
        return fastDFS;
    }
}
