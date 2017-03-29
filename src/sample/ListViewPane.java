package sample;

import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
/** A container class that shows a list of strings. */
/**
 * Created by chrillebile on 2017-03-29.
 */
public class ListViewPane extends StackPane {
    private ListView<String> listView;
    private ObservableList<String> obsList;

    public ListViewPane(int width, int height) {
        // Create an observable wrapper for the strings.
        obsList = FXCollections.observableArrayList();
        //obsList.setAll(phoneBook.names());

        // Create a list view to display the strings.
        // The list view is automatically updated when the observable list i updated.
        listView = new ListView<>(obsList);
        listView.setPrefWidth(width);
        listView.setPrefHeight(height);
        getChildren().add(listView);
    }

    public ListViewPane() {
        this(400, 200);
    }

    /**
     * Clears all selections in the list view.
     */
    public void clearSelection() {
        listView.getSelectionModel().clearSelection();
    }

    /**
     * Selects row index in the list view if index in [0, number rows).
     * Otherwise nothing happens.
     * @param index the index of the row to select
     */
    public void select(int index) {
        listView.getSelectionModel().clearSelection();
        if (index > -1 && index < obsList.size()) {
            listView.getSelectionModel().select(index);
        }
    }

    /**
     * Returns the selected row index [0, number rows).
     * @return the selected row index
     */
    public int getSelectedIndex() {
        return listView.getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns the selected String.
     * @return the selected String
     */
    public String getSelectedString() {
        return listView.getSelectionModel().getSelectedItem();
    }

    /**
     * Selects the row containing s. If no row contains s nothing happens.
     * @param s the string to select
     */
    public void select(String s) {
        int index = obsList.indexOf(s);
        select(index);
    }

    /**
     * Fills the rows in the list view with the strings in col.
     * @param col a collection containing strings that will be displayed in the list view
     */
    public void fillList(Collection<String> col) {
        obsList.setAll(col);
    }

    /**
     * Fills the rows in the list view with the strings in s.
     * @param s an array containing strings that will be displayed in the list view
     */
    public void fillList(String[] s) {
        obsList.addAll(s);
    }
}
