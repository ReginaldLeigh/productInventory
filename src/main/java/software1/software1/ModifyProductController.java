package software1.software1;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Allows user to modify values of a previously created Product via form submission.
 */
public class ModifyProductController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField productIDField;
    @FXML
    private TextField productNameField;

    @FXML
    private TextField productInvField;

    @FXML
    private TextField productPriceField;

    @FXML
    private TextField productMaxField;

    @FXML
    private TextField productMinField;
    @FXML
    private TextField partSearchField;
    @FXML
    private TableView<Part> AllPartTableView;
    @FXML
    private TableColumn<Part, Integer> AllPartIDCol;
    @FXML
    private TableColumn<Part, String> AllPartNameCol;
    @FXML
    private TableColumn<Part, Integer> AllPartInvCol;
    @FXML
    private TableColumn<Part, Double> AllPartPriceCol;

    @FXML
    private TableView<Part> AssocPartTableView;

    @FXML
    private TableColumn<Part, Integer> AssocPartIDCol;
    @FXML
    private TableColumn<Part, String> AssocPartNameCol;
    @FXML
    private TableColumn<Part, Integer> AssocPartInvCol;
    @FXML
    private TableColumn<Part, Double> AssocPartPriceCol;

    private static Product modifiedProduct;

    /**
     * Moves user to a different page within the application.
     * @param event an ActionEvent.
     * @param resource file path the next FXML document.
     */
    private void switchScene (ActionEvent event, String resource) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(resource));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Checks if a string is an integer.
     * @param str A string.
     * @return Returns boolean as true if string is an integer
     */
    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if a string can be converted to a double.
     * @param str A string.
     * @return Returns boolean as true if string can be parsed as Double.
     */
    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates user input by checking if no fields have been left blank.
     * If blank fields are found, alerts the user about missing information.
     * @return Returns a boolean as false if blank fields are found.
     */
    private boolean blankCheck() {
        String fieldName = "";

        if (productNameField.getText() == "") {
            fieldName = "Name";
        } else if (productInvField.getText() == ""){
            fieldName = "Inventory";
        } else if (productPriceField.getText() == ""){
            fieldName = "Price";
        } else if (productMaxField.getText() == ""){
            fieldName = "Max";
        } else if (productMinField.getText() == ""){
            fieldName = "Min";
        }

        if (fieldName != "") {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid value for the " + fieldName + " field");
            alert.setTitle("Modify Product");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Validates user input by checking if data can be converted to appropriate data type.
     * Alerts the user if input is not valid.
     * @return Returns a boolean a false if any inputs fail.
     */
    private boolean numCheck() {
        String stock = productInvField.getText();
        String max = productMaxField.getText();
        String min = productMinField.getText();
        String price = productPriceField.getText();
        String field = "";

        if (!isInteger(stock)) {
            field = "Inventory";
        } else if (!isDouble(price)) {
            field = "Price";
        } else if (!isInteger(max)) {
            field = "Max";
        } else if (!isInteger(min)) {
            field = "Min";
        }

        if (field != "") {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid value for the " + field + " field");
            alert.setTitle("Modify Product");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Checks entered Min value is less than the Max value.
     * Then checks if the Inventory value entered is within the Min/Max range.
     * Alerts the user if inputs require new values.
     * @return Returns a boolean as false if any conditions fail.
     */
    private boolean calcCheck() {
        int stock = Integer.parseInt(productInvField.getText());
        int max = Integer.parseInt(productMaxField.getText());
        int min = Integer.parseInt(productMinField.getText());

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

    /**
     * Replaces a product in the inventory with a new product based on its ID.
     * @param id The ID of the current product.
     * @param newProduct The Product object being used as a replacement.
     */
    private void updateProduct(int id, Product newProduct) {
        int index = -1;

        for(Product product : Inventory.getAllProducts()) {
            index++;

            if(product.getId() == id) {
                Inventory.updateProduct(index, newProduct);
            }
        }
    }

    /**
     * Sets the Product object to be modified.
     * @param product A Product object.
     */
    public static void setProduct (Product product) {
        modifiedProduct = product;
    }

    /**
     * Sends user back to the main menu of the application.
     * @param event An ActionEvent.
     */
    @FXML
    void onActionCancel (ActionEvent event) throws IOException {
        switchScene(event, "/software1/software1/MainMenu.fxml");
    }

    /**
     * Validates user input and create new Product object.
     * Adds new Product to Inventory.
     * @param event An ActionEvent.
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

            int id = Integer.parseInt(productIDField.getText());
            String name = productNameField.getText();
            int stock = Integer.parseInt(productInvField.getText());
            double price = Double.parseDouble(productPriceField.getText());
            int max = Integer.parseInt(productMaxField.getText());
            int min = Integer.parseInt(productMinField.getText());

            Product product = new Product(id, name, price, stock, min, max);

            for (Part part : modifiedProduct.getAllAssociatedParts()) {
                product.addAssociatedPart(part);
            }

            updateProduct(id, product);
            switchScene(event, "/software1/software1/MainMenu.fxml");

        }
    }

    /**
     * Adds the selected Part object to a Products 'Associated Parts' list of objects.
     * @param event An ActionEvent.
     */
    @FXML
    void onActionAdd (ActionEvent event) throws IOException {
        Part selectedPart = AllPartTableView.getSelectionModel().getSelectedItem();
        modifiedProduct.addAssociatedPart(selectedPart);

        /*
        boolean matchFound = false;

        for (Part part : modifiedProduct.getAllAssociatedParts()) {
            if (part.getId() == selectedPart.getId()) {
                matchFound = true;
                break;
            }
        }

        if (!matchFound)
            modifiedProduct.addAssociatedPart(selectedPart);
        else
            System.out.println("Part has already been associated with this product");

         */

    }

    /**
     * Creates a filtered list of parts based on search criteria, and displays them in a Table View.
     * Alerts user if criteria does not match any items.
     * @param event An ActionEvent.
     */
    @FXML
    void onActionSearch (ActionEvent event) {
        String searchCriteria = partSearchField.getText();

        ObservableList<Part> matchedParts = Inventory.lookupPart(searchCriteria);

        if (matchedParts.size() == 0) {
            try {
                int partId = Integer.parseInt(searchCriteria);
                Part searchedPart = Inventory.lookupPart(partId);

                if (searchedPart != null)
                    matchedParts.add(searchedPart);
            } catch (NumberFormatException e) {
                // ignore
            }
        }

        setAllPartTableView(matchedParts);

        if (matchedParts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modify Product");
            alert.setHeaderText("Search Results");
            alert.setContentText("Your search returned 0 results");
            alert.showAndWait();
        } else if (matchedParts.size() == 1) {
            AllPartTableView.getSelectionModel().select(0);
        }
    }

    /**
     * Removes a selected part from the list of items associated with a Product.
     * @param event An ActionEvent.
     */
    @FXML
    void onActionRemove (ActionEvent event) throws IOException {
        try {
            Part selectedPart = AssocPartTableView.getSelectionModel().getSelectedItem();
            boolean partFound = false;

            for (Part part : modifiedProduct.getAllAssociatedParts()) {
                if (part.getId() == selectedPart.getId())
                    partFound = true;
            }

            if (partFound) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove this part?");
                alert.setTitle("Associated Parts");
                alert.setHeaderText("Delete");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    modifiedProduct.deleteAssociatedPart(selectedPart);
                    System.out.println("Part has been removed");
                }
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Missing Associated Part");
            alert.setContentText("Please select an associated part to remove");
            alert.showAndWait();
        }
    }

    /**
     * Displays all available parts in the program Inventory in a Table View for selection.
     * @param parts List of parts to be displayed.
     */
    private void setAllPartTableView (ObservableList<Part> parts) {
        AllPartTableView.setItems(parts);
        AllPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AllPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AllPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AllPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Displays all parts currently associated with a Product in a Table View.
     * @param parts List of associated parts.
     */
    private void setAssocPartTableView (ObservableList<Part> parts) {
        AssocPartTableView.setItems(parts);
        AssocPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssocPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Loads the selected Product object and retrieves its current values.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productIDField.setText(String.valueOf(modifiedProduct.getId()));
        productNameField.setText(String.valueOf(modifiedProduct.getName()));
        productInvField.setText(String.valueOf(modifiedProduct.getStock()));
        productPriceField.setText(String.valueOf(modifiedProduct.getPrice()));
        productMinField.setText(String.valueOf(modifiedProduct.getMin()));
        productMaxField.setText(String.valueOf(modifiedProduct.getMax()));

        setAllPartTableView(Inventory.getAllParts());
        setAssocPartTableView(modifiedProduct.getAllAssociatedParts());
    }
}
