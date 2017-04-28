package BikeGarage;

import BikeGarage.controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowManager {

    private CustomerManager customerManager;
    private BikeManager bikeManager;

    public WindowManager(CustomerManager customerManager, BikeManager bikeManager){
        this.customerManager = customerManager;
        this.bikeManager = bikeManager;
    }

    public void initMain(){
        Stage stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/main.fxml"));
            AnchorPane root = loader.load();

            stage.setScene(new Scene(root));

            MainController controller = loader.getController();
            controller.setWindowManager(this);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initEditUser(){
        Stage stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/editUser.fxml"));
            AnchorPane root = loader.load();

            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initEditBike(){
        Stage stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/editBike.fxml"));
            AnchorPane root = loader.load();

            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
