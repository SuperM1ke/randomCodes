import java.util.ArrayList;
import java.util.Date;

/**
 * This Class is for the pass class.
 *
 * @author Haojun Huang
 * @version 1.0.0
 */
public class PassClass extends WClass {
    public String memberEmail;
    public AttendanceStatus attendanceStatus;

    public PassClass() {}

    public PassClass(String memberEmail, AttendanceStatus attendanceStatus) {
        this.memberEmail = memberEmail;
        this.attendanceStatus = attendanceStatus;
    }

    public PassClass(int classID, String className, String description, Instructor instructor, Date datetime, int duration, Studio location, int capacity, int fee, String category, String difficultyLevel, ArrayList<Feedback> listOfFeedback, String type, String memberEmail, AttendanceStatus attendanceStatus) {
        super(classID, className, description, instructor, datetime, duration, location, capacity, fee, category, difficultyLevel, listOfFeedback, type);
        this.memberEmail = memberEmail;
        this.attendanceStatus = attendanceStatus;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
}
