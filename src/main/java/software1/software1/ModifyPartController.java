package software1.software1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Allows user to modify values of a previously created Part via form submission.
 */
public class ModifyPartController implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    private TextField partIDField;
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

    private static Part modifiedPart;

    /**
     * Replaces a part in the inventory with a new part based on its ID.
     * @param id The ID of the current part.
     * @param newPart The Part object being used as a replacement.
     */
    private void update(int id, Part newPart) {
        int index = -1;

        for(Part part : Inventory.getAllParts()) {
            index++;

            if(part.getId() == id) {
                Inventory.updatePart(index, newPart);
            }
        }
    }

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

    /** Redirects user back to the main menu of the application.
     @param event An ActionEvent.
     */
    @FXML
    void onActionCancel (ActionEvent event) throws IOException {
        switchScene(event, "/software1/software1/MainMenu.fxml");
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

        if (min > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Minimum inventory should be less than the maximum");
            alert.setTitle("Add Parts");
            alert.showAndWait();
            return false;
        } else if (stock < min || stock > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Current inventory should be between minimum and maximum values");
            alert.setTitle("Add Parts");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /** Accepts user input, and creates a new Part object.
     * This Part object used to replace the previous Part within the application's inventory.
     * Redirects user back to the main menu after save.
     @param event An ActionEvent.
     */
    @FXML
    void onActionSave (ActionEvent event) throws IOException {
        boolean blankCheck = blankCheck();
        boolean calcCheck = false;
        boolean numCheck = false;

        if (blankCheck)
            numCheck = numCheck();

        if (numCheck) {
            calcCheck = calcCheck();
        }


        if (blankCheck && calcCheck && numCheck) {

            int id = Integer.parseInt(partIDField.getText());
            String name = partNameField.getText();
            int stock = Integer.parseInt(partInvField.getText());
            double price = Double.parseDouble(partPriceField.getText());
            int max = Integer.parseInt(partMaxField.getText());
            int min = Integer.parseInt(partMinField.getText());

            if (inHouseBtn.isSelected()) {
                int machineId = Integer.parseInt(compMachineField.getText());
                InHouse part = new InHouse(id, name, price, stock, min, max);
                part.setMachineId(machineId);
                update(id, part);
            } else if (outsourceBtn.isSelected()) {
                String companyName = compMachineField.getText();
                Outsourced part = new Outsourced(id, name, price, stock, min, max);
                part.setCompanyName(companyName);
                update(id, part);
            }

            switchScene(event, "/software1/software1/MainMenu.fxml");
        }
    }

    /**
     * Sets the Part object to be modified.
     * @param part A Part object.
     */
    @FXML
    public static void setPart (Part part) {
        modifiedPart = part;
    }

    /** Adds the "Machine ID" label whenever InHouse toggle is selected. */
    public void onInHouseSelect (ActionEvent event) throws IOException {
        compMachineLabel.setText("Machine ID");
    }

    /** Adds the "Company Name" label whenever Outsource toggle is selected. */
    public void onOutsourcedSelect (ActionEvent event) throws IOException {
        compMachineLabel.setText("Company Name");
    }

    /**
     * Loads the selected Part object and retrieves its current values.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIDField.setText(String.valueOf(modifiedPart.getId()));
        partNameField.setText(String.valueOf(modifiedPart.getName()));
        partInvField.setText(String.valueOf(modifiedPart.getStock()));
        partPriceField.setText(String.valueOf(modifiedPart.getPrice()));
        partMinField.setText(String.valueOf(modifiedPart.getMin()));
        partMaxField.setText(String.valueOf(modifiedPart.getMax()));

        if(modifiedPart instanceof InHouse) {
            inHouseBtn.setSelected(true);
            compMachineLabel.setText("Machine Id");
            compMachineField.setText(String.valueOf(((InHouse) modifiedPart).getMachineId()));
        } else if (modifiedPart instanceof Outsourced) {
            outsourceBtn.setSelected(true);
            compMachineLabel.setText("Company Name");
            compMachineField.setText(((Outsourced) modifiedPart).getCompanyName());
        }
    }
}
