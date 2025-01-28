package org.studenthousingsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentHousingSystem extends Application {

//    public static Connection conn = Database.InitConn();

    public static Student student = new Student("ibrahim" , "ibrahim@gmail.com", "2022", "Alex");
    public static Admin admin;
    public static Gatekeeper gatekeeper;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StudentHousingSystem.class.getResource("SearchForDorm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 450);
        stage.setTitle("Student Housing System");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}