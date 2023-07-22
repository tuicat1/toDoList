package com.example.demo4;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.ResourceBundle;

public class ToDoController implements Initializable {

    @FXML
    private TextField todoItem;

    @FXML
    private ListView<String> todoList;

    private ObservableList<String> items;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the todoList with an ObservableList
        items = FXCollections.observableArrayList();
        todoList.setItems(items);
    }

    @FXML
    protected void onAddButtonClick() {
        items.add(todoItem.getText());
        todoItem.clear(); // Clear the input field after adding an item
    }

    @FXML
    protected void onRemoveButtonClick() {
        int indexToRemove = todoList.getSelectionModel().getSelectedIndex();
        if (indexToRemove >= 0) {
            items.remove(indexToRemove);
        }
    }
}