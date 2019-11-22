package com.hhf.study.handle.impl;

import com.hhf.study.constant.FileConstant;
import com.hhf.study.handle.BaseHandler;
import com.hhf.study.util.WeChatImgRevert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * <p>Description: </p>
 *
 * @author hehuifeng
 * @Date 2019/11/21 14:04
 */
public class BackupFileHandler implements BaseHandler {
    private static Logger logger = LoggerFactory.getLogger(BackupFileHandler.class);

    @Override
    public void handler() {
        //撤回文件就解析dat文件并放到backup目录
        if(FileConstant.backupQueryList.size()>0){
            File src = new File(FileConstant.cacheDir+File.separator+FileConstant.backupQueryList.get(0));
            WeChatImgRevert.convertSingleFile(src, FileConstant.backupDir+File.separator+FileConstant.nowMonth);
            //将该文件从map中去除
            FileConstant.backupQueryList.remove(0);
            logger.info("解析撤回文件"+src.getName()+"完成"+",当前队列数："+ FileConstant.backupQueryList.size());
        }
    }
}
