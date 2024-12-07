/**
 * This Class is for Studio
 *
 * @author Haojun Huang
 * @version 1.0.0
 */
public class Studio {
    public int studioID;
    public int capacity;

    public Studio() {}

    public Studio(int studioID, int capacity) {
        this.studioID = studioID;
        this.capacity = capacity;
    }

    public int getStudioID() {
        return studioID;
    }

    public void setStudioID(int studioID) {
        this.studioID = studioID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
