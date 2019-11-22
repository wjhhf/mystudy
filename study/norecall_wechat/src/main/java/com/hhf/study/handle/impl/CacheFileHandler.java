package com.hhf.study.handle.impl;

import com.hhf.study.constant.FileConstant;
import com.hhf.study.handle.BaseHandler;
import com.hhf.study.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * <p>Description: 拷贝文件</p>
 *
 * @author hehuifeng
 * @Date 2019/11/18 15:17
 */
public class CacheFileHandler implements BaseHandler {
    private static Logger logger = LoggerFactory.getLogger(CacheFileHandler.class);

    @Override
    public void handler() {
        //新增文件放到缓存目录
        if(FileConstant.cacheQueryList.size()>0) {
            File src = new File(FileConstant.monitorDir + File.separator +FileConstant.nowMonth + File.separator + FileConstant.cacheQueryList.get(0));
            if (src != null) {
                File desc = new File(FileConstant.cacheDir + File.separator + src.getName());
                try {
                    FileUtil.copyFile(src, desc);
                } catch (Exception e) {
                    logger.error("文件拷贝出错",e);
                }
            } else {
                logger.info("拷贝至缓存文件出错:" + src.getAbsolutePath() + "文件不存在!");
            }
            //拷贝完清除该文件
            FileConstant.cacheQueryList.remove(0);
            logger.info("文件" + src.getName() + "从缓存队列拷贝完成，当前队列数："+ FileConstant.cacheQueryList.size());
        }

    }
}
