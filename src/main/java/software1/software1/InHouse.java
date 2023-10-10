package software1.software1;

/** Subclass of the Part class.
 * Allows a machine ID to be associated with a Part object that has been produced internally.
 */
public class InHouse extends Part {
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }

    /** Sets the Machine ID of an InHouse object. */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /** Retrieves the machine ID of an InHouse object.
     * @return Returns an int.
     */
    public int getMachineId() {
        return machineId;
    }
}
