package software1.software1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Maintains all parts and products within the application.
 * Responsible for assigning IDs to newly created parts and products.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    private static int partId = 1;
    private static int productId = 1;

    /**
     * Adds a new Part object to the Inventory.
     * @param newPart A Part object.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a new Product object to the Inventory.
     * @param newProduct A Product object.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Retrieves the current amount of individual parts available in the inventory.
     * @return Returns an int.
     */
    public static int getSize() {
        return allParts.size();
    }

    /**
     * Locates a specific Part based on the associated ID.
     * @param partId The ID of a Part.
     * @return A Part object if an ID is found. Returns null otherwise.
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId)
                return part;
        }
        return null;

//        String stringId = Integer.toString(partId);
//        filteredParts.clear();
//
//
//        for(Part part : allParts) {
//            if(Integer.toString(part.getId()).contains(stringId)) {
//                filteredParts.add(part);
//            }
//        }
    }

    /**
     * Locates a specific Product based on the associated ID.
     * @param productId The ID of a Product.
     * @return A Product object if an ID is found. Returns null otherwise.
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId)
                return product;
        }
        return null;
    }

    /**
     * Searches the inventory for Parts based on search criteria.
     * @param partName The string used in the search.
     * @return Returns a filtered list of Parts.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        filteredParts.clear();

        for(Part part : allParts) {
            if(part.getName().toLowerCase().contains(partName.toLowerCase())) {
                filteredParts.add(part);
            }
        }
        return filteredParts;
    }

    /**
     * Searches the inventory for Products based on search criteria.
     * @param productName The string used in the search.
     * @return Returns a filtered list of Products.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        filteredProducts.clear();

        for(Product product : allProducts) {
            if(product.getName().toLowerCase().contains(productName.toLowerCase())) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    /**
     * Updates a Part object with new values.
     * @param index The location of part within the global parts list.
     * @param selectedPart The new Part object used for replacement.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates a Product object with new values.
     * @param index The location of product within the global products list.
     * @param newProduct The new Product object used for replacement.
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Removes a part from the application inventory.
     * @param selectedPart The Part to be removed.
     * @return Returns a boolean as true if a part is found and removed.
     */
    public static boolean deletePart(Part selectedPart) {
        for (Part part : allParts) {
            if (part.getId() == selectedPart.getId()) {
                allParts.remove(part);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a product from the application inventory.
     * @param selectedProduct The Product to be removed.
     * @return Returns a boolean as true if a part is found and removed.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        for (Product product : allProducts) {
            if (product.getId() == selectedProduct.getId()) {
                allProducts.remove(product);
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves all parts currently listed in the inventory.
     * @return A list of Parts.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Retrieves all products currently listed in the inventory.
     * @return A list of Products.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Generates a Part ID based on the current count. IDs are issued in numerical order.
     * @return Returns an int.
     */
    public static int generatePartId() {
        int id = partId++;
        return id;
    }

    /**
     * Generates a Product ID based on the current count. IDs are issues in numberical order.
     * @return Returns an int.
     */
    public static int generateProductId() {
        int id = productId++;
        return id;
    }

    /**
     * Creates Part and Product objects to be used a test data throughout the life of the application.
     */
    public static void setTestData() {

        InHouse part1 = new InHouse(generatePartId(), "Brakes", 15.99, 10, 2, 20);
        Outsourced part2 = new Outsourced(generatePartId(), "Wheels", 49.99, 20, 2, 50);
        InHouse part3 = new InHouse(generatePartId(), "Frame", 99.99, 10, 2, 10);
        Outsourced part4 = new Outsourced(generatePartId(), "Seat", 59.99, 10, 2, 10);
        InHouse part5 = new InHouse(generatePartId(), "Handlebars", 29.99, 10, 2, 40);

        part1.setMachineId(111);
        part2.setCompanyName("Armstrong");
        part3.setMachineId(222);
        part2.setCompanyName("Huffy");
        part5.setMachineId(333);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);

        Product product1 = new Product(generateProductId(), "Street Bicycle", 499.99, 10, 2, 10);
        Product product2 = new Product(generateProductId(), "Tricycle", 399.99, 20, 2, 40);
        Product product3 = new Product(generateProductId(), "Unicycle", 299.99, 10, 2, 10);
        Product product4 = new Product(generateProductId(), "Offroad Bicycle", 599.99, 10, 2, 30);

        product1.addAssociatedPart(part1);
        product2.addAssociatedPart(part2);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);

    }
}
