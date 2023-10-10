package software1.software1;


/** Subclass of the Part class.
 * Allows the manufacturing company's name to be associated with a Part that has been outsourced.
 */
public class Outsourced extends Part {
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }

    /**
     * Retrieves the name of the manufacturing company.
     * @return Returns a string.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the manufacturing company for an outsourced part.
     * @param companyName A string.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
