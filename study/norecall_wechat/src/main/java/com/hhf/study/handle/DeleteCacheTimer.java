package com.hhf.study.handle;

import com.hhf.study.constant.FileConstant;
import com.hhf.study.util.DateUtil;
import com.hhf.study.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * <p>Description: </p>
 *
 * @author hehuifeng
 * @Date 2019/11/19 10:29
 */
public class DeleteCacheTimer {
    private static Logger logger = LoggerFactory.getLogger(DeleteCacheTimer.class);
    public DeleteCacheTimer(long period) {
        deleteCacheFiles(period);
        reflushDir();
    }

    public void deleteCacheFiles(long period) {
        logger.info("清除缓存文件定时器开启，每隔"+period/1000+"秒执行一次");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                List<String> cacheList = new ArrayList<>();
                Long now = new Date().getTime();
                //清空缓存文件夹
                List<File> cacheFiles=FileUtil.getFilesByLastModifiedTime(FileConstant.cacheDir,null,null);
                for(File file:cacheFiles){
                    Long time=file.lastModified();
                    //超过2分钟就清除
                    if(now-time>period){
                        file.delete();
                        logger.info("文件"+file.getName()+"超过两分钟，从缓存文件夹中清除");
                    }
                }
                //清空因为微信新增时先创建后删除后创建生成导致的缓存map
                for (Map.Entry<String,Object[]> entry: FileConstant.deleteMap.entrySet()){
                    Object[] createInfos=entry.getValue();
                    Integer createdNum=(Integer)createInfos[0];//操作次数
                    Long lastModifiedTime=(Long)createInfos[1];//最后修改时间
                    Long beforTwoMins=DateUtil.AddTime(new Date(),6,-2).getTime();
                    //当删除操作两次时，即代表该文件被主动撤回
                    if(createdNum==2||//当删除操作两次时，即代表该文件被主动撤回
                            beforTwoMins-lastModifiedTime>0//该文件最后修改时间已经超过2分钟
                            ){
                        cacheList.add(entry.getKey());
                    }
                }
                if(cacheList.size()>0){
                    for (String key:cacheList){
                        FileConstant.createMap.remove(key);
                        FileConstant.deleteMap.remove(key);
                    }
                    cacheList.clear();

                }
            }
        },period);

    }
    /*
     * @Author hehuifeng
     * @Description 每天0点刷新文件夹
     * @Date 2019/11/20 16:58
     * @Param
     * @return
     **/
    public void reflushDir(){
        Timer timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);//控制小时
        calendar.set(Calendar.MINUTE, 0);//控制分钟
        calendar.set(Calendar.SECOND, 0);//控制秒
        Date time = calendar.getTime();//执行任务时间为00:00:00
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                FileConstant.nowMonth=DateUtil.dateToString(new Date(),DateUtil.YYYY_MM);
                logger.info("每天0点刷新文件夹，当前文件夹:"+FileConstant.nowMonth);
            }
        },time,1000 * 60 * 60 * 24);//延迟1天
    }
}
