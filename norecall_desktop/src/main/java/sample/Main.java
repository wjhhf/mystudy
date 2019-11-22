package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    public static Stage stage;
    public static MonitorBean monitorBean;
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage=primaryStage;
        monitorBean= new MonitorBean();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/norecall.fxml"));
        stage.setTitle("PCÎ¢ÐÅ·À³·»ØÕÕÆ¬");
        stage.setResizable(false);
        stage.setScene(new Scene(root,260,130));
        stage.show();
        stage.setOnCloseRequest((e)->{System.exit(0);});
    }

    public static void main(String[] args) {
        launch(args);
    }
}
