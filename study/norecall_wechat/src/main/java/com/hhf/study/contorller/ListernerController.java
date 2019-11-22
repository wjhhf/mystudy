package com.hhf.study.contorller;

import com.hhf.study.thread.BaseThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description: </p>
 *
 * @author hehuifeng
 * @Date 2019/11/21 17:34
 */
public class ListernerController {
    private static Logger logger = LoggerFactory.getLogger(ListernerController.class);
    private static ListernerController controller;
    public static ListernerController getInstance(){
        if(controller==null){
            controller=new ListernerController();
        }
        return controller;
    }

    private BaseThread[] threads;
    /*
     * @Author hehuifeng
     * @Description 注册thread
     * @Date 2019/11/21 12:48
     * @Param
     * @return
     **/
    public ListernerController registerThread(BaseThread[] threads){
        this.threads=threads;
        return controller;
    };

    public void start(){
        for(BaseThread t:threads){
            logger.info("线程"+t.getName()+"已注册");
        }
        for (BaseThread t:threads){
            t.start();
        }
    }

    public void stopLister(){
        BaseThread.stop=true;
        logger.info("暂停监控，当前状态"+!BaseThread.stop);
    }
    public void continueLister(){
        BaseThread.stop=false;
        logger.info("继续监控，当前状态"+!BaseThread.stop);
    }
}
