import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MemberClassController {
    private ArrayList<WClass> wClasses;
    private FileIO classFileIO;
    private FileIO memberFileIO;
    private FileIO bookingFileIO;

    public MemberClassController() {
        try {
            this.classFileIO = new FileIO("./database/class_list.csv");
            this.memberFileIO = new FileIO("./database/member.csv");
            this.bookingFileIO = new FileIO("./database/booking.csv");
            wClasses = retrieveClasses();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            wClasses = new ArrayList<WClass>();
        }
    }

    public MemberClassController(ArrayList<WClass> classList) {
        try {
            this.classFileIO = new FileIO("./database/class_list.csv");
            this.memberFileIO = new FileIO("./database/member.csv");
            this.bookingFileIO = new FileIO("./database/booking.csv");
            wClasses = classList;
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            wClasses = new ArrayList<WClass>();
        }
    }

    public ArrayList<WClass> retrieveClasses() {
        ArrayList<WClass> wClasses = new ArrayList<WClass>();
        try {
            wClasses = classFileIO.readAllClass();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
        return wClasses;
    }

    public WClass getClassById(int classId) {
        WClass wClass = null;
        try {
            wClass = classFileIO.readClass(classId);
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
        return wClass;
    }

    public ArrayList<WClass> getWClasses() {
        return wClasses;
    }

    public boolean makeBooking(String memberEmail, int classId) {
        // Step 1: Check if the member has enough balance for the class
        WClass wClass = getClassById(classId);
        if (wClass == null) {
            System.out.println("Class not found.");
            return false;
        }

        if (!checkBalance(memberEmail, wClass.getFee())) {
            System.out.println("Insufficient credits to book this class.");
            return false;
        }

        // Step 2: Check if the class has enough capacity
        if (!checkCapacity(classId)) {
            System.out.println("Class is full.");
            return false;
        }

        // Step 3: Check if the booking is being made within the allowed time window
        if (!checkTimeWindow(classId)) {
            System.out.println("Booking time window has closed (less than 2 hours before class start).");
            return false;
        }

        // Step 4: Check if there is a scheduling conflict with any of the member's existing bookings
        if (checkClashingClass(memberEmail, classId)) {
            System.out.println("!!! This class conflicts with another booking !!!");
            return false;
        }

        // Step 5: Deduct credits from member account
        if (!deductCredits(memberEmail, wClass.getFee())) {
            System.out.println("!!! Failed to deduct credits !!!");
            return false;
        }

        // Step 6: Reduce class capacity by 1
        wClass.setCapacity(wClass.getCapacity() - 1);

        // Step 7: Create a new booking and save it
        Booking newBooking = new Booking(generateNewBookingID(), classId, memberEmail);
        ArrayList<Booking> bookings = retrieveBookings(); // Get existing bookings

        try {
            // Save updated booking list
            if (bookingFileIO.writeBooking(newBooking)) {
                bookings.add(newBooking); // Add the new booking
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error saving the booking: " + e.getMessage());
            return false;
        }
    }

    private int generateNewBookingID() {
        ArrayList<Booking> bookings = retrieveBookings();
        int maxBookingID = 0;
        for (Booking booking : bookings) {
            if (booking.getBookingID() > maxBookingID) {
                maxBookingID = booking.getBookingID();
            }
        }
        return maxBookingID + 1;
    }

    private boolean checkBalance(String userEmail, int requiredCredits) {
        try {
            // read member info
            Member member = memberFileIO.readMember(userEmail);

            // return false if user not found
            if (member == null) {
                System.out.println("User not found.");
                return false;
            }

            // check if credits are sufficient
            return member.getCreditBalance() >= requiredCredits;

        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            return false;
        }
    }

    private boolean checkTimeWindow(int classId) {
        Date currentTime = new Date();
        WClass wClass = getClassById(classId);
        if (wClass == null) {
            System.out.println("Class not found.");
            return false;
        }
        Date classTime = wClass.getDatetime();
        long timeDifferenceInMillis = classTime.getTime() - currentTime.getTime();
        return timeDifferenceInMillis >= 7200000;
    }

    private boolean checkClashingClass(String memberEmail, int classId) {
        // get new class info
        WClass newClass = getClassById(classId);
        if (newClass == null) {
            System.out.println("Class not found.");
            return false;
        }

        // get new class start and end time
        Date newClassStartTime = newClass.getDatetime();
        Date newClassEndTime = calculateClassEndTime(newClass);  // calculate class end time

        // check whether new class conflicts with existing classes
        ArrayList<Booking> bookings = bookingFileIO.readAllBooking();
        for (Booking booking : bookings) {
            // only check bookings from the current member
            if (booking.getMemberEmail().equals(memberEmail)) {
                WClass bookedClass = getClassById(booking.getClassID());
                // if bookedClass does not exist, skip checking
                if (bookedClass == null) continue;


                // get the start and end time of the booked class
                Date bookedClassStartTime = bookedClass.getDatetime();
                Date bookedClassEndTime = calculateClassEndTime(bookedClass);  // calculate end time of the booked class

                // check time clash
                if (newClassStartTime.before(bookedClassEndTime) && newClassEndTime.after(bookedClassStartTime)) {
                    System.out.println("!!! Booking conflict detected !!!");
                    System.out.println("!!! Class ID: " + bookedClass.getClassID() + " !!!");
                    System.out.println("Class Name: " + bookedClass.getClassName() + " !!!");
                    return true;
                }
            }
        }
        return false;
    }

    private ArrayList<Booking> retrieveBookings() {
        ArrayList<Booking> bookings = new ArrayList<Booking>();
        try {
            bookings = bookingFileIO.readAllBooking();
        } catch (Exception e) {
            System.out.println("Error retrieving bookings: " + e.getMessage());
        }
        return bookings;
    }

    private Date calculateClassEndTime(WClass wClass) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(wClass.getDatetime());  // set class begin time
        int durationInMinutes = (int) (wClass.getDuration() * 60);  // convert duration from hours to minutes
        calendar.add(Calendar.MINUTE, durationInMinutes);  // add duration to begin time
        return calendar.getTime();  // return class end time
    }

    private boolean deductCredits(String memberEmail, double credits) {
        Member member = memberFileIO.readMember(memberEmail);
//        return member.removeCredits(credits);
        // todo: Wait creditController implementation
        return true;
    }

    private boolean checkCapacity(int classId) {
        return classFileIO.readClass(classId).getCapacity() > 0;
    }
}
