import java.util.ArrayList;

public class MemberProfile {
    private FileIO fileIO;
    
    public MemberProfile() {
        fileIO = new FileIO();
    }

    /**
     * Method to cancel a member's booked class by member email.
     *
     * @param memberEmail The email of the member.
     * @return True if the booking was successfully cancelled, false otherwise.
     */
    public boolean cancelBooking(String memberEmail) {
        ArrayList<Booking> bookings = fileIO.readAllBooking();

        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            if (booking.getMemberEmail().equals(memberEmail)) {
                bookings.remove(i);
                return fileIO.writeBooking(booking);
            }
        }
        return false;
    }

    /**
     * Method to get all past classes of a member.
     *
     * @param memberEmail The email of the member.
     * @return List of past classes.
     */
    public ArrayList<WClass> browsePastClasses(String memberEmail) {
        ArrayList<PassClass> passClasses = fileIO.readAllPassClass(memberEmail);
        ArrayList<WClass> pastClasses = new ArrayList<>();

        for (PassClass passClass : passClasses) {
            if ("Attended".equalsIgnoreCase(passClass.getAttendanceStatus().getStatus())) {
                pastClasses.add(passClass);
            }
        }
        return pastClasses;
    }
    

    /**
     * Method to get all upcoming booked classes of a member.
     *
     * @param memberEmail The email of the member.
     * @return List of upcoming booked classes.
     */
    public ArrayList<WClass> browseUpcomingClasses(String memberEmail) {
        ArrayList<Booking> bookings = fileIO.readAllBooking();
        ArrayList<WClass> upcomingClasses = new ArrayList<>();

        for (Booking booking : bookings) {
            if (booking.getMemberEmail().equals(memberEmail)) 
            {
                WClass upcomingClass = fileIO.readClass(booking.getClassID());
                if (upcomingClass != null) {
                    upcomingClasses.add(upcomingClass);
                }
            }
        }
        return upcomingClasses;
    }
}

