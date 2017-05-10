package BikeGarage;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Starts the program.
 * @author Ennio Mara
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        DatabaseManager databaseManager = new DatabaseManager();
        CustomerManager customerManager = databaseManager.loadCustomers();
        BikeManager bikeManager = databaseManager.loadBikes();
        Config config = databaseManager.loadConfig();

        AdminManager adminManager = new AdminManager(customerManager, bikeManager, databaseManager);

        HardwareManager hardwareManager = new HardwareManager(customerManager, bikeManager);

        WindowManager windowManager = new WindowManager(adminManager, hardwareManager, config);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
