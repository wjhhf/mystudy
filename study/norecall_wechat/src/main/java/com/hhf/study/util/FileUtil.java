package com.hhf.study.util;

import com.hhf.study.constant.FileConstant;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: </p>
 *
 * @author hehuifeng
 * @Date 2019/11/18 14:01
 */
public class FileUtil {
    /*
     * @Author hehuifeng
     * @Description 根据最后修改时间获取文件,type或addnos为空，默认取所有的
     * @Date 2019/11/18 14:03
     * @Param monitorDir 文件夹
     * @Param type 类型 0Y,1M,2W,3D,4H,5M,6S
     * @Param seconds 时间
     * @return
     **/
    public static  List<File> getFilesByLastModifiedTime(String baseDir,Integer type,Integer addnos){
        List<File> fileList = new ArrayList<>();
        Date now = new Date();
        Date lastDate=null;
        if(type!=null&&addnos!=null){
            lastDate = DateUtil.AddTime(now,type,addnos);
        }
        File[] files = new File(baseDir).listFiles();
        if(lastDate!=null){
            for(File file1 : files){
                if(file1.isFile()&&getFileCreateTime(file1)>lastDate.getTime()){
                    fileList.add(file1);
                }
            }
        }else{
            for(File file1 : files){
                if(file1.isFile()){
                    fileList.add(file1);
                }
            }
        }
        return fileList;
    }
    public static Long getFileCreateTime(File file){
        if(file==null){
            return null;
        }
        try {
            Path path= Paths.get(file.getAbsolutePath());
            BasicFileAttributeView basicview= Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS );
            BasicFileAttributes attr = basicview.readAttributes();
            return attr.creationTime().toMillis();
        } catch (Exception e) {
            e.printStackTrace();
            return file.lastModified();
        }
    }

    public static boolean copyFile(File src,File des) throws Exception{
        if (!src.exists()) {
            return false;
        }
        if (!des.exists()) {
            des.createNewFile();
        }
        RandomAccessFile srcFile = new RandomAccessFile(src,"rw");
        RandomAccessFile desFile = new RandomAccessFile(des,"rw");
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        FileLock lockInput=null;
        FileLock lockOutput=null;
        try {
            inputChannel = srcFile.getChannel();
            outputChannel = desFile.getChannel();
            while (null == lockInput) {
                try {
                    lockInput = inputChannel.lock();
                } catch (Exception e) {
                    System.out.println("Write Process : get inputLock failed");
                }
            }
            while (null == outputChannel) {
                try {
                    lockOutput = outputChannel.lock();
                } catch (Exception e) {
                    System.out.println("Write Process : get outputLock failed");
                }
            }
            if(lockInput!=null&&lockOutput!=null){
                outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
                lockInput.release();
                lockOutput.release();
            }
        }finally {
            if(inputChannel!=null){
                inputChannel.close();
            }
            if(outputChannel!=null){
                outputChannel.close();
            }
        }
        return true;
    }

    public static boolean copyFileByStream(FileInputStream src,FileOutputStream des){
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = src.getChannel();
            outputChannel = des.getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                inputChannel.close();
                outputChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
