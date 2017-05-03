package BikeGarage;

import BikeGarage.controllers.EditBikeController;
import BikeGarage.controllers.EditUserController;
import BikeGarage.controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

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

    public void initEditUser(Customer customer){
        Stage stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/editUser.fxml"));
            AnchorPane root = loader.load();

            stage.setScene(new Scene(root));

            EditUserController controller = loader.getController();
            controller.init(this, adminManager, customer);


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
