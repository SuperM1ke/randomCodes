import java.util.ArrayList;
import java.util.Date;

public class Instructor {
    private String email;
    private String firstName;
    private String lastName;
    private Date dob;
    private String gender;
    private ArrayList<String> listOfQualifications;
    private ArrayList<String> listOfSpecialisation;

    public Instructor(){
        email = "";
        firstName = "";
        lastName = "";
        dob = null;
        gender = "None";
        listOfQualifications = new ArrayList<String>();
        listOfSpecialisation = new ArrayList<String>();
    }

    public Instructor(String email, String firstName, String lastName, Date dob) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        gender = "None";
        listOfQualifications = new ArrayList<String>();
        listOfSpecialisation = new ArrayList<String>();
    }

    public Instructor(String email, String firstName, String lastName, Date dob, String gender) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        listOfQualifications = new ArrayList<String>();
        listOfSpecialisation = new ArrayList<String>();
    }

    public Instructor(String email, String firstName, String lastName, Date dob,
                      ArrayList<String> listOfQualifications, ArrayList<String> listOfSpecialisation) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        gender = "None";
        this.listOfQualifications = listOfQualifications;
        this.listOfSpecialisation = listOfSpecialisation;
    }

    public Instructor(String email, String firstName, String lastName, Date dob, String gender,
                      ArrayList<String> listOfQualifications, ArrayList<String> listOfSpecialisation) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.listOfQualifications = listOfQualifications;
        this.listOfSpecialisation = listOfSpecialisation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<String> getListOfQualifications() {
        return listOfQualifications;
    }

    public void setListOfQualifications(ArrayList<String> listOfQualifications) {
        this.listOfQualifications = listOfQualifications;
    }
    public void addQualification(String qualification) {
        listOfQualifications.add(qualification);
    }
    public void removeQualification(String qualification) {
        listOfQualifications.remove(qualification);
    }

    public ArrayList<String> getListOfSpecialisation() {
        return listOfSpecialisation;
    }

    public void setListOfSpecialisation(ArrayList<String> listOfSpecialisation) {
        this.listOfSpecialisation = listOfSpecialisation;
    }

    public void addSpecialisation(String specialisation) {
        listOfSpecialisation.add(specialisation);
        System.out.print(specialisation + " has been successfully added to ");
        System.out.println(firstName + " " + lastName + "'s specialisations");
    }

    public void removeSpecialisation(String specialisation) {
        for (String aSpecialisation : listOfSpecialisation) {
            if (aSpecialisation.equals(specialisation)) {
                listOfSpecialisation.remove(aSpecialisation);
                System.out.print(specialisation + " has been successfully removed from ");
                System.out.println(firstName + " " + lastName + "'s specialisations");
            }
        }
    }
}
