package com.hhf.study.constant;

import com.hhf.study.util.DateUtil;
import com.hhf.study.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;
import java.util.*;

/**
 * <p>Description: </p>
 *
 * @author hehuifeng
 * @Date 2019/11/18 13:59
 */
public class FileConstant{
    private static Logger logger = LoggerFactory.getLogger(FileConstant.class);
    public static String monitorDir = null;
    public static String cacheDir =null;
    public static String backupDir =null;
    public static volatile String nowMonth=DateUtil.dateToString(new Date(),DateUtil.YYYY_MM);
    public static volatile List<String> backupQueryList = new ArrayList<>(20);//已撤回待copy文件队列
    public static volatile List<String> cacheQueryList = new ArrayList<>(20);//新增文件拷贝至缓存文件夹队列
    private static WatchService watchService;

    public static WatchService getWatchService(){
        if(watchService==null){
            try {
                watchService=FileSystems.getDefault().newWatchService();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return watchService;
    }
    /*
     *  //因为微信image是先生成再删除再生成，所以这里做一些处理
     */
    public static volatile HashMap<String,Object[]> createMap=new HashMap<>();
    public static volatile HashMap<String,Object[]> deleteMap=new HashMap<>();
    public FileConstant(String monitorDir, String descDir){
        try {
            logger.info("初始化文件夹");
            this.cacheDir = descDir+File.separator+"cache";
            this.backupDir = descDir+File.separator+"backup";
            this.monitorDir=monitorDir;
            init();
        } catch (Exception e) {
            logger.error("初始化监控文件夹出错",e);
            System.exit(0);
        }
    }

    public static void init() throws Exception{
        File cacheFiles = new File(cacheDir);
        if(!cacheFiles.exists()){
            cacheFiles.mkdirs();
        }
        logger.info("初始化缓存文件夹成功，路径为:"+cacheDir);
        File backupFiles = new File(backupDir);
        if(!backupFiles.exists()){
            backupFiles.mkdirs();
        }
        logger.info("初始化备份文件夹成功，路径为:"+backupDir);
        //获取最近两分钟的文件
        List<File> files=FileUtil.getFilesByLastModifiedTime(monitorDir+File.separator+nowMonth,5,-2);
        for(File src :files){
            File desc = new File(FileConstant.cacheDir + File.separator + src.getName());
            FileUtil.copyFile(src, desc);
            //将该文件添加进createMap和backupMap中
            Object[] createInfo = {2,src.lastModified()};//已经创建成功
            FileConstant.createMap.put(src.getName(),createInfo);
            Object[] deleteInfo = {1,src.lastModified()};//待撤回
            FileConstant.deleteMap.put(src.getName(),deleteInfo);
        }
        logger.info("初始化文件列表完成，最近2分钟文件共"+ files.size()+"个，已添加至缓存文件夹");

        watchService=FileSystems.getDefault().newWatchService();
    }
}
