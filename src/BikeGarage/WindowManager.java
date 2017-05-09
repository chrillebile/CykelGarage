package BikeGarage;

import BikeGarage.controllers.EditBikeController;
import BikeGarage.controllers.EditUserController;
import BikeGarage.controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Manages all the windows
 * @author Ennio Mara
 */
public class WindowManager {

    private AdminManager adminManager;

    public WindowManager(AdminManager adminManager){
        this.adminManager = adminManager;
    }

    public void initMain(){
        Stage stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/main.fxml"));
            AnchorPane root = loader.load();

            stage.setScene(new Scene(root));


            MainController controller = loader.getController();
            controller.init(this, adminManager);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initEditUser(Customer customer, MainController mainController){
        Stage stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/editUser.fxml"));
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
            loader.setLocation(getClass().getResource("views/editBike.fxml"));
            AnchorPane root = loader.load();

            stage.setScene(new Scene(root));

            EditBikeController controller = loader.getController();
            controller.init(this, adminManager, bike, customer, mainController);

            stage.show();
        } catch (IOException e) {
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

}
