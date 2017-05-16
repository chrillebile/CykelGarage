package BikeGarage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Starts the program.
 * @author Ennio Mara
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.getIcons().add(new Image("icon_256x256.png"));
        WindowManager.progress(primaryStage);
        ((TextArea) primaryStage.getScene().getRoot().getChildrenUnmodifiable().get(2)).appendText("Startar...\n");

        new Thread(){
            @Override
            public void run() {
                DatabaseManager databaseManager = new DatabaseManager();
                ((TextArea) primaryStage.getScene().getRoot().getChildrenUnmodifiable().get(2)).appendText("Läser in... '" + Config.FILENAME_CONFIG + "'\n");
                databaseManager.loadConfig();
                ((TextArea) primaryStage.getScene().getRoot().getChildrenUnmodifiable().get(2)).appendText("Klar med inläsning av: '" + Config.FILENAME_CONFIG + "'\nLäser in... '" + Config.FILENAME_CUSTOMER + "'\n");
                CustomerManager customerManager = databaseManager.loadCustomers();
                ((TextArea) primaryStage.getScene().getRoot().getChildrenUnmodifiable().get(2)).appendText("Klar med inläsning av: '" + Config.FILENAME_CUSTOMER + "'\nLäser in... '" + Config.FILENAME_BIKE + "'\n");
                BikeManager bikeManager = databaseManager.loadBikes();
                ((TextArea) primaryStage.getScene().getRoot().getChildrenUnmodifiable().get(2)).appendText("Klar med inläsning av: '" + Config.FILENAME_BIKE + "'\n");
                AdminManager adminManager = new AdminManager(customerManager, bikeManager, databaseManager);

                Platform.runLater(() -> {
                    ((TextArea) primaryStage.getScene().getRoot().getChildrenUnmodifiable().get(2)).appendText("Startar Leave'nLock...");
                    HardwareManager hardwareManager = new HardwareManager(adminManager);
                    new WindowManager(adminManager, hardwareManager);
                    ((TextArea) primaryStage.getScene().getRoot().getChildrenUnmodifiable().get(2)).appendText("Klar");
                    primaryStage.close();
                });
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
