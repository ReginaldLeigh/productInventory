package software1.software1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import software1.software1.Part;

/**
 * Class used to create Product objects throughout the application.
 * Product ID are auto-assigned with the Inventory class.
 * A list of parts can be associated with each instance of a Product.
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product (int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param part The Part to be associated with the product.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Removes a Part from the Product's list of associated parts.
     * @param selectedAssociatedPart An item from the associated part list.
     * @return Returns a boolean as true if a part was found and removed.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        for (Part part : associatedParts) {
            if (part.getId() == selectedAssociatedPart.getId())
                associatedParts.remove(part);
                return true;
        }
        return false;
    }

    /**
     * @return Returns a current list of parts associated with a product.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
