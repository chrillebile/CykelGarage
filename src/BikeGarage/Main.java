package BikeGarage;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        DatabaseManager databaseManager = new DatabaseManager();
        CustomerManager customerManager = databaseManager.loadCustomers();
        BikeManager bikeManager = databaseManager.loadBikes();
        AdminManager adminManager = new AdminManager(customerManager, bikeManager, databaseManager);


        WindowManager windowManager = new WindowManager(adminManager);
        windowManager.initMain();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
