package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(e -> Platform.exit());

        Button btn2 = new Button();
        btn2.setText("Lägg till customer");
        // See lambda expression
        btn2.setOnAction(e -> /**Todo: Lägg till en användare*/);



        HBox hbox = new HBox();
        BorderPane root = new BorderPane();
        hbox.getChildren().add(btn);
        hbox.getChildren().add(btn2);
        root.setBottom(hbox);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
