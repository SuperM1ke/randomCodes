import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;

public class WClassController {
    private ArrayList<WClass> classes;

    public void assignInstructorToClass(int classId, Instructor instructor) {
        boolean classIdFound = false;
        for (WClass wClass : classes) {
            if (wClass.getClassID() == (classId)) {
                if (verifyInstructorEligibility(wClass, instructor)) {
                    if (verifyInstructorAvailability(wClass, instructor)) {
                        wClass.setInstructor(instructor);
                        System.out.print("Instructor " + instructor.getFullName());
                        System.out.println(" has been successfully assigned to class.");
                    }
                    else {
                        System.out.print("Instructor " + instructor.getFullName());
                        System.out.println(" is occupied at the class time.");
                    }
                }
                else {
                    System.out.print("Instructor " + instructor.getFullName() + " is not ");
                    System.out.println("qualified for teaching the class.");
                }
                classIdFound = true;
                break;  // Exit the loop after finding and assigning the class
            }
        }

        if (!classIdFound) {
            throw new NoSuchElementException("No class found with id: " + classId);
        }
    }

    public void assignStudioToClass(int classId, Studio studio) {
        boolean classIdFound = false;
        for (WClass wClass : classes) {
            if (wClass.getClassID() == (classId)) {
                if (verifyStudioAvailability(wClass, studio)) {
                    if (verifyStudioCapacity(wClass, studio)) {
                        wClass.setLocation(studio);
                        System.out.print(wClass.getClassName() + " has been successfully ");
                        System.out.println("assigned to studio " + studio.getStudioID());
                    }
                    else {
                        System.out.print("Studio " + studio.getStudioID());
                        System.out.println(" is occupied at the class time.");
                    }
                }
                else {
                    System.out.print("Class capacity overceeded the studio ");
                    System.out.println(studio.getStudioID() + "'s capacity.");
                }
                classIdFound = true;
                break;  // Exit the loop after finding and assigning the class
            }
        }

        if (!classIdFound) {
            throw new NoSuchElementException("No class found with id: " + classId);
        }
    }

    public void changeClassInstructor(int classId, Instructor instructor) {
        boolean classIdFound = false;
        for (WClass wClass : classes) {
            if (wClass.getClassID() == (classId)) {
                if (verifyInstructorEligibility(wClass, instructor)) {
                    if (verifyInstructorAvailability(wClass, instructor)) {
                        wClass.setInstructor(instructor);
                        System.out.print("Instructor " + instructor.getFullName());
                        System.out.println(" has been successfully assigned to class.");
                    }
                    else {
                        System.out.print("Instructor " + instructor.getFullName());
                        System.out.println(" is occupied at the class time.");
                    }
                }
                else {
                    System.out.print("Instructor " + instructor.getFullName() + " is not ");
                    System.out.println("qualified for teaching the class.");
                }
                classIdFound = true;
                break;  // Exit the loop after finding and assigning the class
            }
        }

        if (!classIdFound) {
            throw new NoSuchElementException("No class found with id: " + classId);
        }
    }

    public void changeClassLocation(int classId, Studio studio) {
        boolean classIdFound = false;
        for (WClass wClass : classes) {
            if (wClass.getClassID() == (classId)) {
                if (verifyStudioAvailability(wClass, studio)) {
                    if (verifyStudioCapacity(wClass, studio)) {
                        wClass.setLocation(studio);
                        System.out.print(wClass.getClassName() + " has been successfully ");
                        System.out.println("assigned to studio " + studio.getStudioID());
                    }
                    else {
                        System.out.print("Studio " + studio.getStudioID());
                        System.out.println(" is occupied at the class time.");
                    }
                }
                else {
                    System.out.print("Class capacity overceeded the studio ");
                    System.out.println(studio.getStudioID() + "'s capacity.");
                }
                classIdFound = true;
                break;  // Exit the loop after finding and assigning the class
            }
        }

        if (!classIdFound) {
            throw new NoSuchElementException("No class found with id: " + classId);
        }
    }

    public void createNewClass(int classID, String className, String description, String date, Date DateTime,
                        int duration, int capacity, int fee, String category, String difficultyLevel) {
    }

    public void cancelClass(int classID) {
        boolean classIdFound = false;
        for (WClass wClass : classes) {
            if (wClass.getClassID() == (classID)) {
                if (verifyClassTimeBeforeCancel(wClass)) {
                    classes.remove(wClass);
                }
                else {
                    System.out.println("The class is already ended and cannot be cancelled.");
                }
                classIdFound = true;
                break;  // Exit the loop after finding and assigning the class
            }
        }

        if (!classIdFound) {
            throw new NoSuchElementException("No class found with id: " + classID);
        }
    }

    public WClass getSpecificClass(int classID) {
        for (WClass wClass : classes) {
            if (wClass.getClassID() == (classID)) {
                return wClass;
            }
        }

        throw new NoSuchElementException("No class found with id: " + classID);
    }

    public boolean verifyClassTimeBeforeCancel(WClass wclass) {
        // Get the current time
        Date currentTime = new Date();

        // Calculate the end time of the class
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(wclass.getDatetime());  // Set the start time (datetime)
        calendar.add(Calendar.MINUTE, wclass.getDuration());  // Add the duration

        Date classEndTime = calendar.getTime();

        // Compare current time with the class end time
        return classEndTime.before(currentTime);
    }

    public boolean verifyInstructorEligibility(WClass wClass, Instructor instructor){

    }

    public boolean verifyInstructorAvailability(WClass wClass, Instructor instructor){
        //verify instructor’s availability at the class’s scheduled time
    }

    public boolean verifyStudioAvailability(WClass wClass, Studio studio){
        //verify studio’s availability at the class’s scheduled time
    }

    public boolean verifyStudioCapacity(WClass wClass, Studio studio){
        if (wClass.getCapacity() < studio.getCapacity()){
            return true;
        }
        else {
            return false;
        }
    }

}
