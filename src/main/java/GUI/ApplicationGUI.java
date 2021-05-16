package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lightfilesystem.LightFileSystem;

import java.io.IOException;

public class ApplicationGUI extends Application{

    public static LightFileSystem lfs;

    public static void main(String[] args) throws IOException {
        lfs = LightFileSystem.getInstance();
        lfs.init();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainSceene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Light File System - linux filesystem emulator");
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() throws IOException {
        lfs.saveConfigFile();
    }
}
