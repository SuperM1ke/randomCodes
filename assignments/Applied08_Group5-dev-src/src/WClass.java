import java.util.ArrayList;
import java.util.Date;

/**
 * This Class is for Wellness-class.
 *
 * @author Haojun Huang
 * @version 1.0.0
 */
public class WClass {
    private int classID;
    private String className;
    private String description;
    private Instructor instructor;
    private Date datetime;
    private int duration;
    private Studio location;
    private int capacity;
    private int fee;
    private String category;
    private String difficultyLevel;
    private ArrayList<Feedback> listOfFeedback;
    private String type;

    public WClass() {}

    public WClass(int classID, String className, String description, Instructor instructor, Date datetime, int duration, Studio location, int capacity, int fee, String category, String difficultyLevel, ArrayList<Feedback> listOfFeedback, String type) {
        this.classID = classID;
        this.className = className;
        this.description = description;
        this.instructor = instructor;
        this.datetime = datetime;
        this.duration = duration;
        this.location = location;
        this.capacity = capacity;
        this.fee = fee;
        this.category = category;
        this.difficultyLevel = difficultyLevel;
        this.listOfFeedback = listOfFeedback;
        this.type = type;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Studio getLocation() {
        return location;
    }

    public void setLocation(Studio location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public ArrayList<Feedback> getListOfFeedback() {
        return listOfFeedback;
    }

    public void setListOfFeedback(ArrayList<Feedback> listOfFeedback) {
        this.listOfFeedback = listOfFeedback;
    }

    public void addFeedback(Feedback feedback) { listOfFeedback.add(feedback); }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return "Class ID: " + classID + "\n" +
                "Class Name: " + className + "\n" +
                "Description: " + description + "\n" +
                "Instructor: " + instructor.getFullName() + "\n" +
                "Date and Time: " + datetime + "\n" +
                "Duration: " + duration + "min\n" +
                "Location: Studio " + location.getStudioID() + "\n" +
                "Capacity: " + capacity + "\n" +
                "Fee: " + fee + "\n" +
                "Category: " + category + "\n" +
                "Difficulty Level: " + difficultyLevel + "\n" +
                "List of Feedback: " + feedbackToString() + "\n" +
                "Type: " + type;
    }

    private StringBuilder feedbackToString() {
        // todo: whether to show other info of feedback tbd
        StringBuilder feedbacks = new StringBuilder();
        for (Feedback feedback : listOfFeedback) {
            feedbacks.append(feedback.getDescription());
        }
        return feedbacks;
    }
}
