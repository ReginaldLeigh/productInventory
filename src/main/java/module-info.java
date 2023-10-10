module software1.software1 {
    requires javafx.controls;
    requires javafx.fxml;
    opens software1.software1 to javafx.fxml;
    exports software1.software1;
}