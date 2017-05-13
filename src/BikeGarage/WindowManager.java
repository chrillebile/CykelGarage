package BikeGarage;

import BikeGarage.controllers.EditBikeController;
import BikeGarage.controllers.EditUserController;
import BikeGarage.controllers.MainController;
import BikeGarage.controllers.SettingsController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Manages all the windows
 * @author Ennio Mara
 */
public class WindowManager {

    private AdminManager adminManager;
    private HardwareManager hardwareManager;

    public WindowManager(AdminManager adminManager, HardwareManager hardwareManager){
        this.adminManager = adminManager;
        this.hardwareManager = hardwareManager;

        if(Config.SYSTEM_STARTED_BEFORE){
            initMain();
        }
        else{
            // This will start main after settings have been saved
            initSettings(null);
        }
    }

    public void initMain(){
        Stage stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Config.WINDOWFILE_LOCATION + "main.fxml"));
            AnchorPane root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Leave'nLock");
            stage.setResizable(false);

            MainController controller = loader.getController();
            controller.init(this, adminManager, hardwareManager);

            stage.show();
            if(!Config.SYSTEM_STARTED_BEFORE){
                initSettings(stage);
            }
            stage.setOnCloseRequest(close -> {
                adminManager.updateCustomers();
                adminManager.updateBikes();
                adminManager.updateConfig();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initEditUser(Customer customer, MainController mainController){
        Stage stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Config.WINDOWFILE_LOCATION + "editUser.fxml"));
            AnchorPane root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Lägg till/redigera användare");
            stage.setResizable(false);

            EditUserController controller = loader.getController();
            controller.init(this, adminManager, customer, mainController);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initEditBike(Bike bike, Customer customer, MainController mainController){
        Stage stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Config.WINDOWFILE_LOCATION + "editBike.fxml"));
            AnchorPane root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Lägg till/redigera cykel");
            stage.setResizable(false);

            EditBikeController controller = loader.getController();
            controller.init(this, adminManager, bike, customer, mainController);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param parentStage The parent stage. Is set to null if there is no stage available.
     */
    public void initSettings(Stage parentStage){
        Stage stage = new Stage();

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Config.WINDOWFILE_LOCATION + "settings.fxml"));
            AnchorPane root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Inställningar");

            SettingsController controller = loader.getController();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage);
            stage.setResizable(false);
            controller.init(this, adminManager);
            stage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void openPopup(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * For when the program loads the files.
     */
    public static void progress(Stage stage){
        try {
            Label label = new Label("Laddar filer...");
            ProgressBar progressBar = new ProgressBar();
            progressBar.setMinWidth(200);
            progressBar.setMinHeight(25);
            progressBar.setPadding(new Insets(10, 0, 10, 10));

            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setMaxWidth(380);
            textArea.setMaxHeight(200-50-progressBar.getHeight());

            FlowPane root = new FlowPane();
            root.setPadding(new Insets(0, 10, 10, 10));
            root.getChildren().addAll(label, progressBar, textArea);

            Scene scene = new Scene(root, 400, 200);
            stage.setTitle("Startar...");
            stage.setScene(scene);
            stage.toFront();
            stage.setResizable(false);
            stage.show();
            stage.setOnCloseRequest(close -> {
                System.exit(0);
            });
        } catch (Error e){
            e.printStackTrace();
        }
    }
}
