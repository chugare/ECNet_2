package cn.edu.nju.ecm_2.views;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        System.out.println(getClass().getResource("/"));
        Parent fxml = FXMLLoader.load(getClass().getResource("main_1.fxml"));
        root.getChildren().add(fxml);
        primaryStage.setTitle("证据链可视化建模工具");
        Scene scene = new Scene(root);
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("mouse position:"+event.getX()+"-"+event.getY());
            }
        });
        System.out.println(fxml.getClass());
        scene.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {

            }
        });
        primaryStage.setScene(scene);

        primaryStage.show();
    }


public static void main(String[] args) {
        launch(args);
        }
        }
