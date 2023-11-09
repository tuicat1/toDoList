module com.example.markdowns {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.markdowns to javafx.fxml;
    exports com.example.markdowns;
}