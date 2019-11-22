package com.hhf.study.thread;

import com.hhf.study.constant.FileConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.util.Date;

/**
 * <p>Description: 监控文件夹线程，此线程单独进行</p>
 *
 * @author hehuifeng
 * @Date 2019/11/21 13:02
 */
public class MonitorThread extends BaseThread {
    private static Logger logger = LoggerFactory.getLogger(MonitorThread.class);

    private static MonitorThread listerner;

    public static MonitorThread getInstance(){
        if(listerner==null){
            listerner = new MonitorThread();
            listerner.setName("monitorThread");
        }
        return listerner;
    }

    private WatchKey key;
    private MonitorThread() {
        String monitor = FileConstant.monitorDir+File.separator+ FileConstant.nowMonth;
        try {
            Paths.get(monitor).register(FileConstant.getWatchService(),
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);
        } catch (IOException e) {
            logger.error("",e);
        }
        logger.info("开始监控文件夹"+monitor);
    }

    @Override
    public void run() {
        try {
           while (true) {
               if(!stop){
                   key = FileConstant.getWatchService().take();//没有文件增加或删除时，阻塞在这里
                   for (WatchEvent<?> event : key.pollEvents()) {
                       Long modifiedTime = new Date().getTime();
                       //新增
                       if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                           //微信是先生成后删除再生成，所以只在第二次生成的时候进行操作
                           Object[] nums = FileConstant.createMap.get(event.context().toString());
                           Integer createNum = nums == null ? 1 : (Integer) nums[0] + 1;
                           Object[] news = {createNum, modifiedTime};
                           FileConstant.createMap.put(event.context().toString(), news);
                           if (createNum == 2) {
                               FileConstant.cacheQueryList.add(event.context().toString());
                               logger.info("新增文件" + event.context() + "添加至缓存队列,当前队列数：" + FileConstant.cacheQueryList.size());

                           }
                       }
                       //撤回
                       else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                           //微信是先生成后删除再生成，所以只在主动撤回的时候进行操作
                           Object[] nums = FileConstant.deleteMap.get(event.context().toString());
                           Integer deleteNum = nums == null ? 1 : (Integer) nums[0] + 1;
                           Object[] news = {deleteNum,modifiedTime};
                           FileConstant.deleteMap.put(event.context().toString(), news);
                           if (deleteNum == 2) {
                               FileConstant.backupQueryList.add(event.context().toString());
                               logger.info("撤回文件" + event.context() + "添加至解析队列，当前队列数：" + FileConstant.backupQueryList.size());
                           }
                       }
                   }
                   if(!key.reset()){
                       break;
                   }
               }
            }
        }catch (Exception e) {
            logger.error("监控文件夹出错:",e);
        }
    }
}
