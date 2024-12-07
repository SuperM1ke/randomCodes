/**
 * This class is for Admin, extends the class User.
 *
 * @author Haojun Huang
 * @version 1.0.0
 */
public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(String email, String password, String firstName, String lastName, String mobileNumber) {
        super(email, password, firstName, lastName, mobileNumber);
    }
}
