package sample;

import java.io.Serializable;

/**
 * <p>Description: </p>
 *
 * @author hehuifeng
 * @Date 2019/11/21 15:59
 */
public class MonitorBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String monitorDir;//微信文档存放文件夹

    private String wechatId;//微信号

    private String descDir;//备份文件存放文件夹

    public String getMonitorDir() {
        return monitorDir;
    }

    public void setMonitorDir(String monitorDir) {
        this.monitorDir = monitorDir;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getDescDir() {
        return descDir;
    }

    public void setDescDir(String descDir) {
        this.descDir = descDir;
    }

    @Override
    public String toString() {
        return "{" +
                "monitorDir='" + monitorDir + '\'' +
                ", wechatId='" + wechatId + '\'' +
                ", descDir='" + descDir + '\'' +
                '}';
    }
}
