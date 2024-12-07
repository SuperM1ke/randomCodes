public class CreditsUpdateAction {
    public String action;

    public CreditsUpdateAction() {}

    public CreditsUpdateAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String markBooked() {
        this.action = "Booked";
        return this.getAction();
    }

    public String markCancelledByMember() {
        this.action = "Cancelled by member";
        return this.getAction();
    }

    public String markCancelledByAdmin() {
        this.action = "Cancelled by admin";
        return this.getAction();
    }

    public String markModifiedByAdmin() {
        this.action = "Modified by admin";
        return this.getAction();
    }
}
