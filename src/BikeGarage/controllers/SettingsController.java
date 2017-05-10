package BikeGarage.controllers;

import BikeGarage.AdminManager;
import BikeGarage.Config;
import BikeGarage.WindowManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Ennio Mara
 */
public class SettingsController {
    private WindowManager windowManager;
    AdminManager adminManager;

    @FXML
    private TextField tbxMaxParkingSpots;

    /**
     * Used to initialize this controller. Here we set all global variables this program uses. Then the components get initialized.
     * @param windowManager The instance of WindowManager the system uses.
     * @param adminManager The istance of AdminManager.
     */
    public void init(WindowManager windowManager, AdminManager adminManager){
        this.windowManager = windowManager;
        this.adminManager = adminManager;

        initComponents();
    }

    /**
     * A custom method to fill all components with information.
     */
    private void initComponents(){
        tbxMaxParkingSpots.setText(Config.MAX_PARKING_SPOTS + "");
    }

    @FXML
    private void handleSaveButton(){
        int maxParkingSpots;
        try{
            maxParkingSpots = Integer.parseInt(tbxMaxParkingSpots.getText());
            Config.setMaxParkingSports(maxParkingSpots);


            // If the setting is now changed then start main application
            if(!Config.SYSTEM_STARTED_BEFORE){
                Config.setSystemStartedBefore(true);
                windowManager.initMain();
            }

            adminManager.updateConfig();

            // Close this window since we are done
            closeStage();
        }
        catch(NumberFormatException e){
            windowManager.openPopup(e.getMessage());
            return;
        }


    }

    /**
     * Used when closing this window.
     */
    private void closeStage(){
        Stage stage = (Stage) tbxMaxParkingSpots.getScene().getWindow();
        stage.close();
    }

}
