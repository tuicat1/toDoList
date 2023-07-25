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

        // Allow editing of items in the ListView
        todoList.setEditable(true);
        todoList.setCellFactory(listView -> new EditableListCell());
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

    // Custom ListCell for editing the items
    private class EditableListCell extends javafx.scene.control.ListCell<String> {
        private TextField textField;

        public EditableListCell() {
            // Create a text field to handle edits
            textField = new TextField();
            textField.setOnAction(e -> commitEdit(textField.getText()));
            textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (!isNowFocused) {
                    commitEdit(textField.getText());
                }
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item);
                setGraphic(null);
            }
        }

        @Override
        public void startEdit() {
            super.startEdit();
            String text = getItem();
            if (text != null) {
                textField.setText(text);
            }
            setGraphic(textField);
            setText(null);
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem());
            setGraphic(null);
        }
    }
}
