package com.hhf.study.thread;

import com.hhf.study.handle.BaseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description: 监控类</p>
 *
 * @author hehuifeng
 * @Date 2019/11/18 14:55
 */
public class HandlerThread extends BaseThread {
    private static Logger logger = LoggerFactory.getLogger(HandlerThread.class);
    public static BaseHandler[] baseHandlers;
    private static HandlerThread listerner;
    public static HandlerThread getInstance(){
        if(listerner==null){
            listerner=new HandlerThread();
            listerner.setName("handlerThread");
        }
        return listerner;
    }
    /*
     * @Author hehuifeng
     * @Description 注册handler
     * @Date 2019/11/21 12:48
     * @Param
     * @return
     **/
    public HandlerThread registerHandler(BaseHandler[] baseHandlers){
        this.baseHandlers=baseHandlers;
        return listerner;
    };
    @Override
    public void run() {
        for(BaseHandler baseHandler:baseHandlers){
            logger.info("执行器"+baseHandler+"已注册");
        }
        while(true){
            if(!stop){
                for (BaseHandler handler:baseHandlers){
                    handler.handler();
                }
            }
        }
    }
}
