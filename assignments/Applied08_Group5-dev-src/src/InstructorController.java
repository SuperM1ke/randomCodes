import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

public class InstructorController {
    private ArrayList<Instructor> instructors;

    public InstructorController() {
        instructors = new ArrayList<Instructor>();
    }

    public InstructorController(ArrayList<Instructor> instructors) {
        this.instructors = instructors;
    }

    // Overloaded method without gender (optional gender)
    public void createNewInstructor(String email, String firstName, String lastName,
                                    Date dob, ArrayList<String> listOfQualifications,
                                    ArrayList<String> listOfSpecialisation) {

        // Check if there is already an instructor with the same email
        for (Instructor instructor : instructors) {
            if (instructor.getEmail().equals(email)) {
                throw new IllegalArgumentException("Instructor with email: " + email + " already exists.");
            }
        }

        // If no existing instructor with the same email, create and add the new instructor
        Instructor newInstructor = new Instructor(email, firstName, lastName, dob,
                listOfQualifications, listOfSpecialisation);
        instructors.add(newInstructor);
        System.out.println("Instructor " + firstName + " " + lastName + " has been successfully added.");
    }

    // Overloaded method with gender (optional gender)
    public void createNewInstructor(String email, String firstName, String lastName,
                                    Date dob, String gender,
                                    ArrayList<String> listOfQualifications,
                                    ArrayList<String> listOfSpecialisation) {

        // Check if there is already an instructor with the same email
        for (Instructor instructor : instructors) {
            if (instructor.getEmail().equals(email)) {
                throw new IllegalArgumentException("Instructor with email: " + email + " already exists.");
            }
        }

        // If no existing instructor with the same email, create and add the new instructor
        Instructor newInstructor = new Instructor(email, firstName, lastName, dob, gender,
                listOfQualifications, listOfSpecialisation);
        instructors.add(newInstructor);
        System.out.println("Instructor " + firstName + " " + lastName + " has been successfully added.");
    }

    public void deleteInstructor(String email) {
        boolean instructorEmailFound = false;
        for (Instructor instructor : instructors) {
            if (instructor.getEmail().equals(email)) {
                instructors.remove(instructor);
                System.out.print("Instructor " + instructor.getFullName());
                System.out.println(" has been successfully deleted.");
                instructorEmailFound = true;
                break;  // Exit the loop after finding and deleting the instructor
            }
        }

        if (!instructorEmailFound) {
            throw new NoSuchElementException("No instructor found with email: " + email);
        }
    }

    public Instructor getSpecificInstructor(String email) {
        for (Instructor instructor : instructors) {
            if (instructor.getEmail().equals(email)) {
                return instructor;
            }
        }

        // Throw exception if no instructor is found
        throw new NoSuchElementException("No instructor found with email: " + email);
    }

}
