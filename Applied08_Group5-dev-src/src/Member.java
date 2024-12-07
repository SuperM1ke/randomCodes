/**
 * This class is for Member, extends the class User.
 *
 * @author Haojun Huang
 * @version 1.0.0
 */
public class Member extends User {
    public String dob;
    public String gender;
    public String address;
    public double creditBalance;

    public Member() {
        super();
    }

    public Member(String email, String password, String firstName, String lastName, String mobileNumber, String dob, String gender, String address, double creditBalance) {
        super(email, password, firstName, lastName, mobileNumber);
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.creditBalance = creditBalance;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(double creditBalance) {
        this.creditBalance = creditBalance;
    }
}
