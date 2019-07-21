package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();

        Scene scene = new Scene(root,screen.getWidth()/1.35,screen.getHeight()/1.25);
        scene.setFill(Color.TRANSPARENT);

        //primaryStage.setTitle("Hello World");

        primaryStage.setY(screen.getHeight()*0.11);
        primaryStage.setX(screen.getWidth()*0.18);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
