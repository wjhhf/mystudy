package sample;

import com.hhf.study.start.NoRecallApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.Optional;

public class Controller {
    @FXML
    private Button monitorButton;

    private DirectoryChooser directoryChooser;
    private Alert alert ;
    @FXML
    protected void chooseMonitorDir(ActionEvent event) {
            directoryChooser=new DirectoryChooser();
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));   //设置初始路径，默认为当前路径
            directoryChooser.setTitle("选择电脑版微信存放地址");
            File monitorDir=directoryChooser.showDialog(Main.stage);
            if(monitorDir!=null){
                Main.monitorBean.setMonitorDir(monitorDir.getAbsolutePath());
            }

    }

    @FXML
    protected void sendWechatId(ActionEvent event){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("请输入微信账号");

        // 传统的获取输入值的方法
        Optional result = dialog.showAndWait();
        if (result.isPresent()) {
            Main.monitorBean.setWechatId(result.get().toString());
        }
    }
    @FXML
    protected void chooseDescDir(ActionEvent event) {
        directoryChooser=new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));   //设置初始路径，默认为当前路径
        directoryChooser.setTitle("选择备份文件存放地址");
        File monitorDir=directoryChooser.showDialog(Main.stage);
        if(monitorDir!=null){
            Main.monitorBean.setDescDir(monitorDir.getAbsolutePath());
        }

    }
    @FXML
    protected void aboutApp(ActionEvent event){
        createInformationAlert("关于本软件","version:v1.0\r\nauthor:hehuifeng\r\nqq:372573336\r\n");
    }
    @FXML
    protected void course(ActionEvent event){
        createInformationAlert("教程","1、点击步骤，选择步骤1，选择微信文件存放位置(pc版微信点击左下角设置->通用设置->文件管理中的文件地址)。\r\n2、点击步骤，选择步骤2，填写自己的微信账号。\r\n3、点击步骤，选择步骤3，选择撤回文件存放位置。\r\n4、点击开始监控，程序开始监控。\r\n");
    }

    @FXML
    protected void monitor(ActionEvent event){
        if(monitorButton.getText().equals("开始监控")){
            if(startMonitor()){
                createInformationAlert(null,"开始监控!");
                monitorButton.setText("暂停监控");
            }
        }else if(monitorButton.getText().equals("暂停监控")){
            stopMonitor();
            createInformationAlert(null,"暂停监控!");
            monitorButton.setText("继续监控");
        }else if(monitorButton.getText().equals("继续监控")){
            continueMonitor();
            createInformationAlert(null,"继续监控!");
            monitorButton.setText("暂停监控");
        }
    }

    private boolean startMonitor(){
        /*Main.monitorBean.setMonitorDir("D:\\wechat\\WeChat Files");
        Main.monitorBean.setWechatId("qq372573336");
        Main.monitorBean.setDescDir("D:\\convert");*/
        if(Main.monitorBean.getMonitorDir()!=null
                &&Main.monitorBean.getWechatId()!=null
                &&Main.monitorBean.getDescDir()!=null){
            NoRecallApplication.start(Main.monitorBean.getMonitorDir(),Main.monitorBean.getWechatId(),Main.monitorBean.getDescDir());
            return true;
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误提示");
            alert.setHeaderText(null);
            alert.setContentText("请按步骤顺序依次完成!");
            alert.showAndWait();
            return false;
        }
    }

    private void stopMonitor(){
        NoRecallApplication.stopLister();
        createInformationAlert(null,"已停止监控！");
    }
    private void continueMonitor(){
        NoRecallApplication.continueLister();
        createInformationAlert(null,"已继续监控！");
    }

    private Alert createInformationAlert(String title,String content){
        if(alert==null){
            alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
        }
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
        return alert;
    }
}
