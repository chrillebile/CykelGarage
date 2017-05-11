package BikeGarage;

import BikeGarage.controllers.EditBikeController;
import BikeGarage.controllers.EditUserController;
import BikeGarage.controllers.MainController;
import BikeGarage.controllers.SettingsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
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


            MainController controller = loader.getController();
            controller.init(this, adminManager, hardwareManager);

            stage.show();
            if(!Config.SYSTEM_STARTED_BEFORE){
                initSettings(stage);
            }
            stage.setOnCloseRequest(we -> {
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

            SettingsController controller = loader.getController();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage);
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

    public static void progress(Stage stage, Boolean show){
        try {
            if (!show) {
                stage.close();
                return;
            }
            ProgressIndicator progressIndicator = new ProgressIndicator();

            FlowPane root = new FlowPane();
            root.getChildren().addAll(progressIndicator);

            Scene scene = new Scene(root, 75, 75);
            System.out.println("1");
            stage.setTitle("JavaFX ProgressBar & ProgressIndicator (o7planning.org)");
            System.out.println("2");
            stage.setScene(scene);
            System.out.println("3");
            stage.show();
        } catch (Error e){
            e.printStackTrace();
        }
    }
}
