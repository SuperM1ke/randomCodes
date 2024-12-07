/**
 * This class represents a Booking for a class made by a member.
 * It contains details such as the booking ID, class ID, and member's email.
 *
 * <p>This class is a key component of the booking system, allowing the
 * management of class reservations and checking for conflicts or availability.</p>
 *
 * @author Runhui Zhou
 * @version 1.0.0
 */
public class Booking {
    private int bookingID;
    private int classID;
    private String memberEmail;

    // Constructor
    public Booking(int bookingID, int classID, String memberEmail) {
        this.bookingID = bookingID;
        this.classID = classID;
        this.memberEmail = memberEmail;
    }

    // Getter for bookingID
    public int getBookingID() {
        return bookingID;
    }

    // Setter for bookingID
    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    // Getter for classID
    public int getClassID() {
        return classID;
    }

    // Setter for classID
    public void setClassID(int classID) {
        this.classID = classID;
    }

    // Getter for memberEmail
    public String getMemberEmail() {
        return memberEmail;
    }

    // Setter for memberEmail
    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    // toString method for debugging or easy printing
    public String toString() {
        return "Booking{" +
                "bookingID=" + bookingID +
                ", classID=" + classID +
                ", memberEmail='" + memberEmail + '\'' +
                '}';
    }
}
