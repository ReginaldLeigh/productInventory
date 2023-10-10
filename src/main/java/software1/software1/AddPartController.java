package software1.software1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls actions and behavior of the Add Part user form.
 * Allows user to create new parts and add them to the program's inventory.
 *
 * RUNTIME ERROR - During development, I ran into the issue of accurately validating user inputs for int and double data types.
 * This would often throw a NumberFormatException error.
 * To solve this issue, I created two methods (isInteger and isDouble) which would catch these errors if the values were not valid.
 * I could then continue my validation, and notify the user if any changes were needed prior to saving.
 */
public class AddPartController {
    Stage stage;
    Parent scene;

    @FXML
    private TextField partNameField;

    @FXML
    private TextField partInvField;

    @FXML
    private TextField partPriceField;

    @FXML
    private TextField partMaxField;

    @FXML
    private TextField partMinField;

    @FXML
    private TextField compMachineField;

    @FXML
    private Label compMachineLabel;

    @FXML
    private RadioButton inHouseBtn;

    @FXML
    private RadioButton outsourceBtn;


    /** Moves user to a different page within the application.
     @param event An ActionEvent.
     @param resource The file path for the next FXML resource to be loaded.
     */
    private void switchScene (ActionEvent event, String resource) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(resource));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Adds the "Machine ID" label whenever InHouse toggle is selected. */
    public void onInHouseSelect (ActionEvent event) throws IOException {
        compMachineLabel.setText("Machine ID");
    }

    /** Adds the "Company Name" label whenever Outsource toggle is selected. */
    public void onOutsourcedSelect (ActionEvent event) throws IOException {
        compMachineLabel.setText("Company Name");
    }

    /** Checks user form for any blank values prior to submission.
     Alerts user if any blank inputs are found.
     @return Returns boolean as false if any blank values are found.
     */
    private boolean blankCheck() {
        String fieldName = "";

        if (partNameField.getText() == "") {
            fieldName = "Name";
        } else if (partInvField.getText() == ""){
            fieldName = "Inventory";
        } else if (partPriceField.getText() == ""){
            fieldName = "Price";
        } else if (partMaxField.getText() == ""){
            fieldName = "Max";
        } else if (partMinField.getText() == ""){
            fieldName = "Min";
        } else if (compMachineField.getText() == "") {
            fieldName = compMachineLabel.getText();
        }

        if (fieldName != "") {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid value for the " + fieldName + " field");
            alert.setTitle("Add Parts");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /** Validates user input for correct data types.
     Alerts user if data types are not appropriate.
     @return Returns boolean as false if any data types are not accepted.
     */
    private boolean numCheck() {
        String stock = partInvField.getText();
        String max = partMaxField.getText();
        String min = partMinField.getText();
        String price = partPriceField.getText();
        String machineID = compMachineField.getText();
        String field = "";

        if (!isInteger(stock)) {
            field = "Inventory";
        } else if (!isDouble(price)) {
            field = "Price";
        } else if (!isInteger(max)) {
            field = "Max";
        } else if (!isInteger(min)) {
            field = "Min";
        } else if (!isInteger(machineID) && inHouseBtn.isSelected()) {
            field = "Machine ID";
        }

        if (field != "") {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid value for the " + field + " field");
            alert.setTitle("Add Parts");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Performs check to validate Min value is less than Max, and Inventory is at or between both values.
     * Alerts user if values fall outside of these parameters.
     * @return Returns boolean as false if any errors are found.
     */
    private boolean calcCheck() {
        int stock = Integer.parseInt(partInvField.getText());
        int max = Integer.parseInt(partMaxField.getText());
        int min = Integer.parseInt(partMinField.getText());

        if (min > max) {                                // Check is min is less than max
            Alert alert = new Alert(Alert.AlertType.ERROR, "Minimum inventory should be less than the maximum");
            alert.setTitle("Add Parts");
            alert.showAndWait();
            return false;
        } else if (stock < min || stock > max) {                            // Alerts user if stock is outside of max/min range
            Alert alert = new Alert(Alert.AlertType.ERROR, "Current inventory should be between minimum and maximum values");
            alert.setTitle("Add Parts");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /** Checks if string is an integer.
     @param str String to be checked.
     @return Returns boolean as true if string is an integer.
     */
    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** Checks if string is a double.
     @param str String to be checked.
     @return Returns boolean as true if string is a double.
     */
    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** Accepts user input, and creates a new Part object.
     * This Part object is added to the application's inventory.
     * Redirects user back to the main menu after save.
     @param event An ActionEvent.
     */
    public void onActionSave(ActionEvent event) throws IOException {
        boolean blankCheck = blankCheck();      // Checks for blank values in user form
        boolean calcCheck = false;
        boolean numCheck = false;

        if (blankCheck)                     // Checks data
            numCheck = numCheck();

        if (numCheck) {                     //
            calcCheck = calcCheck();
        }


        if (blankCheck && calcCheck && numCheck) {

            String name = partNameField.getText();
            int stock = Integer.parseInt(partInvField.getText());
            double price = Double.parseDouble(partPriceField.getText());
            int max = Integer.parseInt(partMaxField.getText());
            int min = Integer.parseInt(partMinField.getText());


            if (inHouseBtn.isSelected()) {
                int machineId = Integer.parseInt(compMachineField.getText());
                InHouse part = new InHouse(Inventory.generatePartId(), name, price, stock, min, max);
                part.setMachineId(machineId);
                Inventory.addPart(part);

            } else if (outsourceBtn.isSelected()) {
                String companyName = compMachineField.getText();
                Outsourced part = new Outsourced(Inventory.generatePartId(), name, price, stock, min, max);
                part.setCompanyName(companyName);
                Inventory.addPart(part);
            }

            switchScene(event, "/software1/software1/MainMenu.fxml");
        }
    }

    /** Redirects user back to the main menu of the application.
     @param event An ActionEvent.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        switchScene(event, "/software1/software1/MainMenu.fxml");
    }
}
