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
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));   //���ó�ʼ·����Ĭ��Ϊ��ǰ·��
            directoryChooser.setTitle("ѡ����԰�΢�Ŵ�ŵ�ַ");
            File monitorDir=directoryChooser.showDialog(Main.stage);
            if(monitorDir!=null){
                Main.monitorBean.setMonitorDir(monitorDir.getAbsolutePath());
            }

    }

    @FXML
    protected void sendWechatId(ActionEvent event){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("������΢���˺�");

        // ��ͳ�Ļ�ȡ����ֵ�ķ���
        Optional result = dialog.showAndWait();
        if (result.isPresent()) {
            Main.monitorBean.setWechatId(result.get().toString());
        }
    }
    @FXML
    protected void chooseDescDir(ActionEvent event) {
        directoryChooser=new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));   //���ó�ʼ·����Ĭ��Ϊ��ǰ·��
        directoryChooser.setTitle("ѡ�񱸷��ļ���ŵ�ַ");
        File monitorDir=directoryChooser.showDialog(Main.stage);
        if(monitorDir!=null){
            Main.monitorBean.setDescDir(monitorDir.getAbsolutePath());
        }

    }
    @FXML
    protected void aboutApp(ActionEvent event){
        createInformationAlert("���ڱ����","version:v1.0\r\nauthor:hehuifeng\r\nqq:372573336\r\n");
    }
    @FXML
    protected void course(ActionEvent event){
        createInformationAlert("�̳�","1��������裬ѡ����1��ѡ��΢���ļ����λ��(pc��΢�ŵ�����½�����->ͨ������->�ļ������е��ļ���ַ)��\r\n2��������裬ѡ����2����д�Լ���΢���˺š�\r\n3��������裬ѡ����3��ѡ�񳷻��ļ����λ�á�\r\n4�������ʼ��أ�����ʼ��ء�\r\n");
    }

    @FXML
    protected void monitor(ActionEvent event){
        if(monitorButton.getText().equals("��ʼ���")){
            if(startMonitor()){
                createInformationAlert(null,"��ʼ���!");
                monitorButton.setText("��ͣ���");
            }
        }else if(monitorButton.getText().equals("��ͣ���")){
            stopMonitor();
            createInformationAlert(null,"��ͣ���!");
            monitorButton.setText("�������");
        }else if(monitorButton.getText().equals("�������")){
            continueMonitor();
            createInformationAlert(null,"�������!");
            monitorButton.setText("��ͣ���");
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
            alert.setTitle("������ʾ");
            alert.setHeaderText(null);
            alert.setContentText("�밴����˳���������!");
            alert.showAndWait();
            return false;
        }
    }

    private void stopMonitor(){
        NoRecallApplication.stopLister();
        createInformationAlert(null,"��ֹͣ��أ�");
    }
    private void continueMonitor(){
        NoRecallApplication.continueLister();
        createInformationAlert(null,"�Ѽ�����أ�");
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
