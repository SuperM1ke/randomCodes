/**
 * This Class is for unify the attendance status.
 *
 * @author Haojun Huang
 * @version 1.0.0
 */
public class AttendanceStatus {
    public String status;

    public AttendanceStatus() {}

    public AttendanceStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String markAttended() {
        this.status = "Attended";
        return this.getStatus();
    }

    public String markCancelledByMember() {
        this.status = "Cancelled by the member";
        return this.getStatus();
    }

    public String markCancelledByAdmin() {
        this.status = "Cancelled by the admin";
        return this.getStatus();
    }
}
