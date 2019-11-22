package com.hhf.study.start;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhf.study.constant.FileConstant;
import com.hhf.study.contorller.ListernerController;
import com.hhf.study.handle.BaseHandler;
import com.hhf.study.handle.DeleteCacheTimer;
import com.hhf.study.handle.impl.BackupFileHandler;
import com.hhf.study.thread.BaseThread;
import com.hhf.study.thread.HandlerThread;
import com.hhf.study.thread.MonitorThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * <p>Description: </p>
 *
 * @author hehuifeng
 * @Date 2019/11/18 14:40
 */
public class NoRecallApplication {

    private static Logger logger = LoggerFactory.getLogger(NoRecallApplication.class);

    public static void main(String[] args) {
        if(args!=null&&args.length>0){
            logger.info("传递参数为:"+args[0]);
        }
        String p="{\"monitorDir\":\"D:\\\\wechat\\\\WeChat Files\",\"wechatId\":\"qq372573336\",\"descDir\":\"D:\\\\convert\"}";
        JSONObject params =JSON.parseObject(p);
        //JSONObject params =JSON.parseObject(args[0]);
        String monitorDir=params.getString("monitorDir");
        String wechatId=params.getString("wechatId");
        String desDir=params.getString("descDir");
        start(monitorDir,wechatId,desDir);


    }
    /*
     * @Author hehuifeng
     * @Description
     * @Date 2019/11/20 13:11
     * @Param baseDir 微信存放文件位置
     * @Param wechatId 微信账号
     * @Param desDir 撤回文件存放地址
     * @return
     **/
    public static void start(String baseDir,String wechatId,String desDir){
        baseDir+=File.separator+wechatId+File.separator+"FileStorage"+File.separator+"Image";
        //初始化常量信息
        new FileConstant(baseDir,desDir);
        //注册执行器，此处将CahcheFileHandler去除，在监控到新增文件时直接拷贝
        BaseHandler[] handlers ={new BackupFileHandler()};
        HandlerThread.getInstance().registerHandler(handlers);
        //注册线程
        BaseThread[] threads = {HandlerThread.getInstance(),MonitorThread.getInstance()};
        //启动
        ListernerController.getInstance().registerThread(threads).start();
        //清除缓存文件定时器
        new DeleteCacheTimer(2*60*1000);

    }

    public static void stopLister(){
        ListernerController.getInstance().stopLister();
    }
    public static void continueLister(){
        ListernerController.getInstance().continueLister();
    }
}
