package software1.software1;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controls user functions for the Main Menu of the application.
 * Allows users to add, modify, and delete Parts and Products.
 */
public class MainMenuController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    @FXML
    private TextField searchPartField;
    @FXML
    private TextField searchProductField;
    @FXML
    private Button searchPartBtn;
    @FXML
    private Button searchProductBtn;

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

    /**
     * Directs the user to the Add Part user form.
     * @param event An ActionEvent.
     */
    @FXML
    void onActionAddPart (ActionEvent event) throws IOException {
        switchScene(event, "/software1/software1/AddPart.fxml");
    }

    /**
     * Allows the user to modify a selected part.
     * Alerts the user if a part has not been selected first.
     * @param event An ActionEvent.
     */
    @FXML
    void onActionModifyPart (ActionEvent event) throws IOException {
        try {
            Part part = partTableView.getSelectionModel().getSelectedItem();
            ModifyPartController.setPart(part);
            switchScene(event, "/software1/software1/ModifyPart.fxml");
        } catch (LoadException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Missing Part");
            alert.setContentText("Please select a part to modify");
            alert.showAndWait();
        }
    }

    /**
     * Removes a part from the application inventory.
     * Allows the user to confirm prior to deletion.
     * Notifies the user if a part has not been selected.
     * @param event An ActionEvent.
     */
    @FXML
    void onActionDeletePart (ActionEvent event) {
        Part part = partTableView.getSelectionModel().getSelectedItem();

        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a part to delete");
            alert.setTitle("Parts");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this part?");
            alert.setTitle("Parts");
            alert.setHeaderText("Delete");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(part);
            }
        }
    }

    /**
     * Locates a part within the application inventory using search criteria.
     * Alerts the user if no results were found.
     * @param event An ActionEvent.
     */
    @FXML
    void onPartSearch (ActionEvent event) {
        String searchCriteria = searchPartField.getText();

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

        setPartTableView(matchedParts);

        if (matchedParts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Parts");
            alert.setHeaderText("Search Results");
            alert.setContentText("Your search returned 0 results");
            alert.showAndWait();
        } else if (matchedParts.size() == 1) {
            partTableView.getSelectionModel().select(0);
        }
    }

    /**
     * Locates a product within the application inventory using search criteria.
     * Alerts the user if no results were found.
     * @param event An ActionEvent.
     */
    @FXML
    void onProductSearch (ActionEvent event) {
        String searchCriteria = searchProductField.getText();

        ObservableList<Product> matchedProducts = Inventory.lookupProduct(searchCriteria);

        if (matchedProducts.size() == 0) {
            try {
                int productId = Integer.parseInt(searchCriteria);
                Product searchedProduct = Inventory.lookupProduct(productId);

                if (searchedProduct != null)
                    matchedProducts.add(searchedProduct);
            } catch (NumberFormatException e) {
                // ignore
            }
        }

        setProductTableView(matchedProducts);

        if (matchedProducts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Products");
            alert.setHeaderText("Search Results");
            alert.setContentText("Your search returned 0 results");
            alert.showAndWait();
        } else if (matchedProducts.size() == 1) {
            productTableView.getSelectionModel().select(0);
        }
    }

    /**
     * Directs the user to the Add Product form.
     * @param event An ActionEvent.
     */
    @FXML
    void onActionAddProduct (ActionEvent event) throws IOException {
        switchScene(event, "/software1/software1/AddProduct.fxml");
    }

    /**
     * Directs user to the Modify Product form.
     * Allows the user to modify values for a specific product.
     * Alerts user if no product was previously selected.
     * @param event An ActionEvent.
     */
    @FXML
    void onActionModifyProduct (ActionEvent event) throws IOException {
        try {
            Product product = productTableView.getSelectionModel().getSelectedItem();
            ModifyProductController.setProduct(product);
            switchScene(event, "/software1/software1/ModifyProduct.fxml");
        } catch (LoadException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Missing Product");
            alert.setContentText("Please select a product to modify");
            alert.showAndWait();
        }
    }

    /**
     * Allows the user to remove a product from the application inventory.
     * NOTE: Products may not be removed if they have any parts associated with them.
     * Alerts user if a product has not be selected, or if there are parts still associated with a product.
     * @param event An ActionEvent.
     */
    @FXML
    void onActionDeleteProduct (ActionEvent event) {
        Product product = productTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Products");

        if (product == null) {
            alert.setContentText("Please select a product to delete");
            alert.showAndWait();
        } else {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Do you want to delete this product?");
            alert.setHeaderText("Delete");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (product.getAllAssociatedParts().size() == 0) {
                    Inventory.deleteProduct(product);
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setHeaderText("Error");
                    alert.setContentText("Associated parts must be removed before deleting a product");
                    alert.showAndWait();
                }
            }
        }
    }

    /**
     * Exits the application.
     * @param event An ActionEvent.
     */
    @FXML
    void onActionExit (ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
        alert.setHeaderText("Exit Program");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**
     * Displays a list of parts available with the application inventory.
     * @param parts The list of parts to be displayed in the TableView.
     */
    @FXML
    private void setPartTableView(ObservableList<Part> parts) {
        partTableView.setItems(parts);
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Display a list of a products available within the application inventory.
     * @param products The list of products to be displayed in the TableView.
     */
    @FXML
    private void setProductTableView(ObservableList<Product> products) {
        productTableView.setItems(products);
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Used to set the Parts and Product TableViews prior to displaying the page.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPartTableView(Inventory.getAllParts());
        setProductTableView(Inventory.getAllProducts());
    }
}
