package BikeGarage;

import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/** This class contains methods for showing dialogs. */
/**
 * Created by chrillebile on 2017-03-29.
 */
public class Dialogs {
    /** Shows an information alert.
     * @param title the title of the pop up window
     * @param headerText the string to show in the dialog header area
     * @param infoText the string to show in the dialog content area
     */
    public static void alert(String title, String headerText, String infoText) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(infoText);
        alert.showAndWait();
    }

    /** Shows an confirmation alert with buttons "Yes" and "No".
     * @param title the title of the pop up window
     * @param headerText the string to show in the dialog header area
     * @param question the string to show in the dialog content area
     * @return An Optional that contains the result
     */
    public static boolean confirmDialog(String title, String headerText, String question) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(question);

        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeTwo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            return true;
        } else if (result.get() == buttonTypeTwo) {
            return false;
        } else {
            return false;
        }
    }

    /** Shows an input dialog with one input field.
     * @param title the title of the pop up window
     * @param headerText the string to show in the dialog header area
     * @param label the string to show in the dialog content area before the input field
     * @return An Optional that contains the result
     */
    public static Optional<String> oneInputDialog(String title, String headerText, String label) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.setContentText(label + ": ");
        return dialog.showAndWait();
    }

    /** Shows an input dialog with as many input fields as elements in labels
     * @param title the title of the pop up window
     * @param headerText the string to show in the dialog header area
     * @param labels the strings to show in the dialog content area before the input fields
     * @return An Optional that contains the result
     */
    public static Optional<String[]> twoInputsDialog(String title, String headerText, String[] labels) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);

        GridPane grid = new GridPane();
        Label[] inputLabels = new Label[labels.length];
        TextField[] inputTextFields = new TextField[labels.length];
        for (int i = 0; i < labels.length; i++) {
            inputLabels[i] = new Label(labels[i] + ": ");
            inputTextFields[i] = new TextField();
            grid.add(inputLabels[i], 1, i);
            grid.add(inputTextFields[i], 2, i);
        }

        for (int i = 0; i < labels.length; i++) {

        }

        dialog.getDialogPane().setContent(grid);
        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeCancel, buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, String>() {
            @Override
            public String call(ButtonType b) {
                if (b == buttonTypeOk) {
                    String inputs = "";
                    for (int i = 0; i < labels.length; i++) {
                        if (inputTextFields[i].getText().equals("")) {
                            return null;
                        }
                        inputs = inputs + inputTextFields[i].getText();
                        if (i < labels.length - 1) {
                            inputs = inputs + ":";
                        }
                    }
                    return inputs;
                } else {
                    return null;
                }
            }
        });
        if (labels.length > 0) {
            inputTextFields[0].requestFocus();
        }

        Optional<String> result = dialog.showAndWait();
        String[] input = null;
        if (result.isPresent()) {
            input = result.get().split(":");
        }
        return Optional.ofNullable(input);
    }
}
