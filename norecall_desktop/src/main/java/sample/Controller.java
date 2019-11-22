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
    protected void about(ActionEvent event){
        createInformationAlert("关于本软件","version:v1.0\r\nauthor:hehuifeng\r\nqq:372573336");
    }

    @FXML
    protected void monitor(ActionEvent event){
        if(monitorButton.getText().equals("开始监控")){
            monitorButton.setText("暂停监控");
            startMonitor();
        }else if(monitorButton.getText().equals("暂停监控")){
            monitorButton.setText("继续监控");
            stopMonitor();
        }else if(monitorButton.getText().equals("继续监控")){
            monitorButton.setText("暂停监控");
            continueMonitor();
        }
    }

    private void startMonitor(){
        Main.monitorBean.setMonitorDir("D:\\wechat\\WeChat Files");
        Main.monitorBean.setWechatId("qq372573336");
        Main.monitorBean.setDescDir("D:\\convert");
        if(Main.monitorBean.getMonitorDir()!=null
                &&Main.monitorBean.getWechatId()!=null
                &&Main.monitorBean.getDescDir()!=null){
            NoRecallApplication.start(Main.monitorBean.getMonitorDir(),Main.monitorBean.getWechatId(),Main.monitorBean.getDescDir());
            createInformationAlert(null,"已开始监控！");
        }else{
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("错误提示");
            alert.setContentText("请按步骤顺序依次完成!");
            alert.showAndWait();
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
            alert.setWidth(30);
            alert.setHeight(10);
        }
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
        return alert;
    }
}
