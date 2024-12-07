import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * This Class is for File IO.
 *
 * @author Haojun Huang
 * @version 1.0.1
 */
public class FileIO {
    private String filename;

    /**
     * Constructor for FileIO.
     */
    public FileIO() {
    }

    /**
     * Constructor for FileIO.
     *
     * @param filename The name of the file.
     */
    public FileIO(String filename) {
        this.filename = filename;
    }

    /**
     * Getter for filename.
     *
     * @return The name of the file.
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Setter for filename.
     *
     * @param filename The name of the file.
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Method to get the name of csv file.
     *
     * @return The name of the csv file as a String.
     */
    public String getCSVName() {
        String filename = this.filename;
        File file = new File(filename);
        return file.getName();
    }

    /**
     * Method to switch to other csv file.
     *
     * @param csv The name of csv file.
     */
    private void switchCSV(String csv) {
        String filename = this.filename;
        File file = new File(filename);
        String parentFile = file.getParent();
        setFilename(parentFile + File.separator + csv);
    }

    /**
     * Method to read the file line by line.
     *
     * @return An ArrayList contains all columns.
     */
    private ArrayList<String[]> readFile() {
        ArrayList<String[]> list = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(this.filename))) {
            String DELIMITER = ",";
            String line;

            // skip header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(DELIMITER);
                list.add(columns);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
        }

        return list;
    }

    /**
     * Method to read csv file header.
     *
     * @return ArrayList contains csv file header.
     */
    private String[] readHeader() {
        String[] columns = new String[]{};

        try (BufferedReader br = Files.newBufferedReader(Paths.get(this.filename))) {
            String DELIMITER = ",";

            // read header
            String line = br.readLine();
            columns = line.split(DELIMITER);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
        }

        return columns;
    }

    /**
     * Method to write data to file.
     *
     * @return The status of the file has been written as a boolean value.
     */
    private boolean writeFile(ArrayList<String[]> columns) {
        boolean isWrote;
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(this.filename))) {
            String DELIMITER = ",";

            for (String[] col : columns) {
                StringBuilder line = new StringBuilder();
                for (int i = 0; i < col.length; i++) {
                    if (i < col.length - 1) {
                        line.append(col[i]).append(DELIMITER);
                    } else {
                        line.append(col[i]);
                    }
                }
                bw.write(line.toString());
                bw.newLine();
            }

            bw.flush();
            isWrote = true;
        } catch (IOException e) {
            System.out.println("An error occurred while writing the file.");
            isWrote = false;
        }
        return isWrote;
    }

    /**
     * Method to convert WClass to Array.
     *
     * @param wClass The object of WClass.
     * @param size   The size of the array.
     * @return A String Array.
     */
    private String[] wClassToArray(WClass wClass, int size) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String[] col = new String[size];
        col[0] = String.valueOf(wClass.getClassID());
        col[1] = wClass.getClassName();
        col[2] = wClass.getDescription();
        col[3] = wClass.getInstructor().getEmail();
        col[4] = formatter.format(wClass.getDatetime());
        col[5] = String.valueOf(wClass.getDuration());
        col[6] = wClass.getType();
        col[7] = String.valueOf(wClass.getFee());
        col[8] = String.valueOf(wClass.getLocation().getStudioID());
        col[9] = String.valueOf(wClass.getCapacity());
        col[10] = wClass.getCategory();
        col[11] = wClass.getDifficultyLevel();
        return col;
    }

    /**
     * Method to convert Booking to Array.
     *
     * @param booking The object of Booking.
     * @param size    The size of the array.
     * @return A String Array.
     */
    private String[] bookingToArray(Booking booking, int size) {
        String[] col = new String[size];
        col[0] = String.valueOf(booking.getBookingID());
        col[1] = String.valueOf(booking.getClassID());
        col[2] = booking.getMemberEmail();
        return col;
    }

    /**
     * Method to read a member by email.
     *
     * @param email The email of the member.
     * @return The object of the member.
     */
    public Member readMember(String email) {
        ArrayList<String[]> list = readFile();

        for (String[] col : list) {
            if (col[0].equals(email)) {
                double creditBalance = 0;
                try {
                    String address = col[7]
                            .substring(1, col[7].length() - 1)
                            .replaceAll("]\\[", ",");
                    creditBalance = Double.parseDouble(col[8]);

                    return new Member(
                            email,
                            col[1],
                            col[2],
                            col[3],
                            col[6],
                            col[4],
                            col[5],
                            address,
                            creditBalance
                    );
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect file data.");
                }
            }
        }
        return null;
    }

    /**
     * Method to read an Admin.
     *
     * @return The object of Admin.
     */
    public Admin readAdmin() {
        ArrayList<String[]> list = readFile();

        for (String[] col : list) {
            return new Admin(
                    col[0],
                    col[1],
                    col[2],
                    col[3],
                    col[4]
            );
        }

        return null;
    }

    /**
     * Method to read a booking.
     *
     * @param bookingID The ID of booking.
     * @return The object of the Member.
     */
    public Booking readBookingByBookingID(int bookingID) {
        ArrayList<String[]> list = readFile();

        for (String[] col : list) {
            int bID = 0, classID = 0;
            try {
                bookingID = Integer.parseInt(col[0]);
                classID = Integer.parseInt(col[1]);

                if (bID == bookingID) {
                    return new Booking(
                            bookingID,
                            classID,
                            col[2]
                    );
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect file data.");
            }
        }
        return null;
    }

    /**
     * Method to read a booking.
     *
     * @param memberEmail The email of the member.
     * @return The object of the Member.
     */
    public Booking readBooking(String memberEmail) {
        ArrayList<String[]> list = readFile();

        for (String[] col : list) {
            int bookingID = 0, classID = 0;
            try {
                bookingID = Integer.parseInt(col[0]);
                classID = Integer.parseInt(col[1]);

                if (col[2].equals(memberEmail)) {
                    return new Booking(
                            bookingID,
                            classID,
                            col[2]
                    );
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect file data.");
            }
        }
        return null;
    }

    /**
     * Method to read All bookings.
     *
     * @return ArrayList contains all booking.
     */
    public ArrayList<Booking> readAllBooking() {
        ArrayList<String[]> list = readFile();
        ArrayList<Booking> bookings = new ArrayList<>();

        for (String[] col : list) {
            int bookingID = 0, classID = 0;
            try {
                bookingID = Integer.parseInt(col[0]);
                classID = Integer.parseInt(col[1]);

                Booking booking = new Booking(
                        bookingID,
                        classID,
                        col[2]
                );
                bookings.add(booking);
            } catch (NumberFormatException e) {
                System.out.println("Incorrect file data.");
            }
        }

        return bookings;
    }

    /**
     * Method to read a class by classID.
     *
     * @param classID The ID of the class.
     * @return The object of WClass.
     */
    public WClass readClass(int classID) {
        ArrayList<String[]> list = readFile();

        for (String[] col : list) {
            int id = 0, studioID = 0, duration = 0, capacity = 0, fee = 0;
            try {
                id = Integer.parseInt(col[0]);
                studioID = Integer.parseInt(col[8]);

                if (id == classID) {
                    // Get instructor
                    String csvFilename = this.getCSVName();
                    this.switchCSV("instructor.csv");
                    Instructor instructor = readInstructor(col[3]);
                    this.switchCSV(csvFilename);

                    Studio studio = readStudio(studioID);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    Date date = Date.from(LocalDate.parse(col[4], formatter)
                            .atStartOfDay(
                                    ZoneId.of("Australia/Melbourne")
                            ).toInstant());
                    duration = Integer.parseInt(col[5]);
                    capacity = Integer.parseInt(col[9]);
                    fee = Integer.parseInt(col[8]);

                    this.switchCSV("feedback.csv");
                    ArrayList<Feedback> feedbacks = readFeedbackByClassID(classID);
                    this.switchCSV(csvFilename);

                    return new WClass(
                            id,
                            col[1],
                            col[2],
                            instructor,
                            date,
                            duration,
                            studio,
                            capacity,
                            fee,
                            col[10],
                            col[11],
                            feedbacks,
                            col[6]
                    );
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                System.out.println("Incorrect file data.");
            }
        }
        return null;
    }

    /**
     * Method to read all classes.
     *
     * @return ArrayList contains all object of WClass.
     */
    public ArrayList<WClass> readAllClass() {
        ArrayList<String[]> list = readFile();
        ArrayList<WClass> classes = new ArrayList<>();

        for (String[] col : list) {
            int classID = 0;

            try {
                classID = Integer.parseInt(col[0]);
                WClass wClass = readClass(classID);
                classes.add(wClass);
            } catch (NumberFormatException e) {
                System.out.println("Incorrect file data.");
            }
        }

        return classes;
    }

    /**
     * Method to read studio information by studioID.
     *
     * @param studioID The ID of the studio.
     * @return The object of studio.
     */
    public Studio readStudio(int studioID) {
        String csvFilename = this.getCSVName();
        this.switchCSV("studio.csv");
        ArrayList<String[]> list = readFile();
        this.switchCSV(csvFilename);

        for (String[] col : list) {
            int id = 0, capacity = 0;
            try {
                id = Integer.parseInt(col[0]);
                capacity = Integer.parseInt(col[1]);

                if (id == studioID) {
                    return new Studio(id, capacity);
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect file data.");
            }
        }
        return null;
    }

    /**
     * Method to read Feedback by classID.
     *
     * @param classID The ID of the class.
     * @return ArrayList contains all object of Feedback of the class.
     */
    public ArrayList<Feedback> readFeedbackByClassID(int classID) {
        ArrayList<String[]> list = readFile();
        ArrayList<Feedback> feedbacks = new ArrayList<>();

        for (String[] col : list) {
            int id = 0, cID = 0, rating = 0;
            try {
                id = Integer.parseInt(col[0]);
                cID = Integer.parseInt(col[1]);
                rating = Integer.parseInt(col[2]);

                if (cID == classID) {
                    Feedback feedback = new Feedback(
                            id,
                            classID,
                            rating,
                            col[3]
                    );
                    feedbacks.add(feedback);
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect file data.");
            }
        }
        return feedbacks;
    }

    /**
     * Method to read Feedback by feedbackID.
     *
     * @param feedbackID The ID of the feedback.
     * @return The object of Feedback.
     */
    public Feedback readFeedback(int feedbackID) {
        ArrayList<String[]> list = readFile();

        for (String[] col : list) {
            int id = 0, classID = 0, rating = 0;
            try {
                id = Integer.parseInt(col[0]);
                classID = Integer.parseInt(col[1]);
                rating = Integer.parseInt(col[2]);

                if (id == feedbackID) {
                    return new Feedback(
                            id,
                            classID,
                            rating,
                            col[3]
                    );
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect file data.");
            }
        }
        return null;
    }

    /**
     * Method to read all Feedback.
     *
     * @return ArrayList contains all the object of Feedback.
     */
    public ArrayList<Feedback> readAllFeedback() {
        ArrayList<String[]> list = readFile();
        ArrayList<Feedback> feedbacks = new ArrayList<>();

        for (String[] col : list) {
            int id = 0, classID = 0, rating;
            try {
                id = Integer.parseInt(col[0]);
                classID = Integer.parseInt(col[1]);
                rating = Integer.parseInt(col[2]);

                Feedback feedback = new Feedback(
                        id,
                        classID,
                        rating,
                        col[3]
                );
                feedbacks.add(feedback);
            } catch (NumberFormatException e) {
                System.out.println("Incorrect file data.");
            }
        }
        return feedbacks;
    }

    /**
     * Method to read the qualification of the instructor.
     *
     * @param instructorEmail The email of the instructor.
     * @return ArrayList contains the qualification as a String.
     */
    public ArrayList<String> readInstructorQualification(String instructorEmail) {
        ArrayList<String[]> qualificationList = readFile();

        ArrayList<String> qList = new ArrayList<>();
        for (String[] qCol : qualificationList) {
            if (qCol[0].equals(instructorEmail)) {
                qList.add(qCol[1]);
            }
        }
        return qList;
    }

    /**
     * Method to read the specialisation of the instructor.
     *
     * @param instructorEmail The email of the instructor.
     * @return ArrayList contains the specialisation as a String.
     */
    public ArrayList<String> readInstructorSpecialisation(String instructorEmail) {
        ArrayList<String[]> specialisationList = readFile();

        ArrayList<String> sList = new ArrayList<>();
        for (String[] sCol : specialisationList) {
            if (sCol[0].equals(instructorEmail)) {
                sList.add(sCol[1]);
            }
        }
        return sList;
    }

    /**
     * Method to read an Instructor.
     *
     * @param instructorEmail The email of the instructor.
     * @return The object of the instructor.
     */
    public Instructor readInstructor(String instructorEmail) {
        ArrayList<String[]> list = readFile();

        for (String[] col : list) {
            if (col[0].equals(instructorEmail)) {
                // Get Instructor Basic Info.
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                Date date = Date.from(LocalDate.parse(col[3], formatter)
                        .atStartOfDay(
                                ZoneId.of("Australia/Melbourne")
                        ).toInstant());
                String gender = col[4].equals("None") ? null : col[4];

                Instructor instructor = new Instructor(
                        col[0],
                        col[1],
                        col[2],
                        date,
                        gender
                );

                String csvFilename = this.getCSVName();

                // Get Qualification
                this.switchCSV("instructor_specialisation.csv");
                ArrayList<String> qList = readInstructorQualification(instructorEmail);
                this.switchCSV(csvFilename);
                // Get Specialisation
                this.switchCSV("instructor_qualification.csv");
                ArrayList<String> sList = readInstructorSpecialisation(instructorEmail);
                this.switchCSV(csvFilename);

                instructor.setListOfQualifications(qList);
                instructor.setListOfSpecialisation(sList);
                return instructor;
            }
        }
        return null;
    }

    /**
     * Method to read all Instructor.
     *
     * @return ArrayList contains all Instructor.
     */
    public ArrayList<Instructor> readAllInstructor() {
        ArrayList<String[]> list = readFile();
        ArrayList<Instructor> instructors = new ArrayList<>();

        for (String[] col : list) {
            Instructor instructor = readInstructor(col[0]);
            if (instructor != null)
                instructors.add(instructor);
        }

        return instructors;
    }

    /**
     * Method to read all pass classes by member email.
     *
     * @return ArrayList contains all pass classes.
     */
    public ArrayList<PassClass> readAllPassClass() {
        ArrayList<String[]> list = readFile();
        ArrayList<PassClass> passClasses = new ArrayList<>();

        for (String[] col : list) {
            int classID = 0;
            try {
                classID = Integer.parseInt(col[1]);
                WClass wClass = readClass(classID);
                AttendanceStatus status = new AttendanceStatus(col[2]);

                PassClass passClass = new PassClass(
                        classID,
                        wClass.getClassName(),
                        wClass.getDescription(),
                        wClass.getInstructor(),
                        wClass.getDatetime(),
                        wClass.getDuration(),
                        wClass.getLocation(),
                        wClass.getCapacity(),
                        wClass.getFee(),
                        wClass.getCategory(),
                        wClass.getDifficultyLevel(),
                        wClass.getListOfFeedback(),
                        wClass.getType(),
                        col[0],
                        status
                );
                passClasses.add(passClass);
            } catch (NumberFormatException e) {
                System.out.println("Incorrect file data.");
            }
        }
        return passClasses;
    }

    /**
     * Method to read all pass classes by member email.
     *
     * @param memberEmail The email of the member
     * @return ArrayList contains all pass classes.
     */
    public ArrayList<PassClass> readAllPassClass(String memberEmail) {
        ArrayList<String[]> list = readFile();
        ArrayList<PassClass> passClasses = new ArrayList<>();

        for (String[] col : list) {
            if (col[0].equals(memberEmail)) {
                int classID = 0;
                try {
                    classID = Integer.parseInt(col[1]);
                    WClass wClass = readClass(classID);
                    AttendanceStatus status = new AttendanceStatus(col[2]);

                    PassClass passClass = new PassClass(
                            classID,
                            wClass.getClassName(),
                            wClass.getDescription(),
                            wClass.getInstructor(),
                            wClass.getDatetime(),
                            wClass.getDuration(),
                            wClass.getLocation(),
                            wClass.getCapacity(),
                            wClass.getFee(),
                            wClass.getCategory(),
                            wClass.getDifficultyLevel(),
                            wClass.getListOfFeedback(),
                            wClass.getType(),
                            memberEmail,
                            status
                    );
                    passClasses.add(passClass);
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect file data.");
                }
            }
        }
        return passClasses;
    }

    /**
     * Method to read CreditHistory.
     *
     * @return ArrayList contains all CreditHistory.
     */
    public ArrayList<CreditHistory> readCreditHistory() {
        ArrayList<String[]> list = readFile();
        ArrayList<CreditHistory> creditHistories = new ArrayList<>();

        for (String[] col : list) {
            int classID = 0;
            try {
                classID = Integer.parseInt(col[1]);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                Date date = Date.from(LocalDate.parse(col[4], formatter)
                        .atStartOfDay(
                                ZoneId.of("Australia/Melbourne")
                        ).toInstant());
                WClass wClass = readClass(classID);
                CreditsUpdateAction action = new CreditsUpdateAction(col[3]);

                CreditHistory creditHistory = new CreditHistory(
                        col[0],
                        date,
                        wClass,
                        action
                );
                creditHistories.add(creditHistory);
            } catch (NumberFormatException e) {
                System.out.println("Incorrect file data.");
            }
        }
        return creditHistories;
    }

    /**
     * Method to read CreditHistory by member email.
     *
     * @param memberEmail The email of the member.
     * @return ArrayList contains all CreditHistory.
     */
    public ArrayList<CreditHistory> readCreditHistory(String memberEmail) {
        ArrayList<String[]> list = readFile();
        ArrayList<CreditHistory> creditHistories = new ArrayList<>();

        for (String[] col : list) {
            if (col[0].equals(memberEmail)) {
                int classID = 0;
                try {
                    classID = Integer.parseInt(col[1]);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    Date date = Date.from(LocalDate.parse(col[4], formatter)
                            .atStartOfDay(
                                    ZoneId.of("Australia/Melbourne")
                            ).toInstant());
                    WClass wClass = readClass(classID);
                    CreditsUpdateAction action = new CreditsUpdateAction(col[3]);

                    CreditHistory creditHistory = new CreditHistory(
                            memberEmail,
                            date,
                            wClass,
                            action
                    );
                    creditHistories.add(creditHistory);
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect file data.");
                }
            }
        }
        return creditHistories;
    }

    /**
     * Method to write booking into cdv file.
     *
     * @param booking The object of booking.
     * @return The status of the file has been written as a boolean value.
     */
    public boolean writeBooking(Booking booking) {
        Booking oldBooking = readBookingByBookingID(booking.getBookingID());

        String[] header = readHeader();
        ArrayList<Booking> bookings = readAllBooking();
        ArrayList<String[]> list = new ArrayList<>();
        if (oldBooking != null) { // if it has old booking, do remove first.
            int index = 0;
            for (Booking el : bookings) {
                if (el.getBookingID() == booking.getBookingID()) {
                    break;
                }
                index++;
            }
            bookings.remove(index);
        }
        bookings.add(booking); // do append

        list.add(header);
        // convert to string
        for (Booking el : bookings) {
            String[] col = bookingToArray(el, header.length);
            list.add(col);
        }

        // write into file
        return writeFile(list);
    }

    /**
     * Method to write class into cdv file.
     *
     * @param wClass The object of WClass.
     * @return The status of the file has been written as a boolean value.
     */
    public boolean writeClass(WClass wClass) {
        WClass oldWClass = readClass(wClass.getClassID());

        String[] header = readHeader();
        ArrayList<WClass> classes = readAllClass();
        ArrayList<String[]> list = new ArrayList<>();

        if (oldWClass != null) { // if it has old class, do remove first.
            int index = 0;
            for (WClass el : classes) {
                if (el.getClassID() == wClass.getClassID()) {
                    break;
                }
                index++;
            }
            classes.remove(index);
        }
        classes.add(wClass);

        list.add(header);
        // convert to string
        for (WClass el : classes) {
            String[] col = wClassToArray(el, header.length);
            list.add(col);
        }

        // write into file
        return writeFile(list);
    }

    /**
     * Method to write instructor qualification.
     *
     * @param instructorEmail The email of instructor.
     * @param qualification The qualification.
     * @return The status of the file has been written as a boolean value.
     */
    public boolean writeInstructorQualification(String instructorEmail, String qualification) {
        ArrayList<String[]> qualificationList = readFile();

        String[] header = readHeader();
        ArrayList<String[]> list = new ArrayList<>();

        boolean isExist = false;
        for (String[] col: qualificationList) {
            if (col[0].equals(instructorEmail) && col[1].equals(qualification)) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            list.add(header);
            list.addAll(qualificationList);
            return writeFile(list);
        }
        return false; // if qualification already exist
    }

    /**
     * Method to write instructor specialisation.
     *
     * @param instructorEmail The email of instructor.
     * @param specialisation The specialisation.
     * @return The status of the file has been written as a boolean value.
     */
    public boolean writeInstructorSpecialisation(String instructorEmail, String specialisation) {
        ArrayList<String[]> specialisationList = readFile();

        String[] header = readHeader();
        ArrayList<String[]> list = new ArrayList<>();

        boolean isExist = false;
        for (String[] col: specialisationList) {
            if (col[0].equals(instructorEmail) && col[1].equals(specialisation)) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            list.add(header);
            list.addAll(specialisationList);
            return writeFile(list);
        }
        return false; // if specialisation already exist
    }

    /**
     * Method to write feedback into cdv file.
     *
     * @param feedback The object of feedback.
     * @return The status of the file has been written as a boolean value.
     */
    public boolean writeFeedback(Feedback feedback) {
        Feedback oldFeedback = readFeedback(feedback.getFeedbackId());

        String[] header = readHeader();
        ArrayList<Feedback> feedbacks = readAllFeedback();
        ArrayList<String[]> list = new ArrayList<>();

        if (oldFeedback != null) { // if it has old feedback, do remove first.
            int index = 0;
            for (Feedback el : feedbacks) {
                if (el.getFeedbackId() == feedback.getFeedbackId()) {
                    break;
                }
                index++;
            }
            feedbacks.remove(index);
        }
        feedbacks.add(feedback); // do append

        list.add(header);
        // convert to string
        for (Feedback el : feedbacks) {
            String[] col = new String[header.length];
            col[0] = String.valueOf(feedback.getFeedbackId());
            col[1] = String.valueOf(feedback.getClassId());
            col[2] = String.valueOf(feedback.getRating());
            col[3] = el.getDescription();
            list.add(col);
        }

        // write into file
        return writeFile(list);
    }

    /**
     * Method to write instructor into cdv file.
     *
     * @param instructor The object of Instructor.
     * @return The status of the file has been written as a boolean value.
     */
    public boolean writeInstructor(Instructor instructor) {
        Instructor oldInstructor = readInstructor(instructor.getEmail());

        String[] header = readHeader();
        ArrayList<Instructor> instructors = readAllInstructor();
        ArrayList<String[]> list = new ArrayList<>();

        if (oldInstructor != null) { // if it has old instructor, do remove first.
            int index = 0;
            for (Instructor el : instructors) {
                if (el.getEmail().equals(instructor.getEmail())) {
                    break;
                }
                index++;
            }
            instructors.remove(index);
        }
        instructors.add(instructor);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        list.add(header);
        // convert to string
        for (Instructor el : instructors) {
            String[] col = new String[header.length];
            col[0] = el.getEmail();
            col[1] = el.getFirstName();
            col[2] = el.getLastName();
            col[3] = formatter.format(el.getDob());
            col[4] = el.getGender();
            list.add(col);
        }

        // write into file
        return writeFile(list);
    }

    /**
     * Method to write pass class into cdv file.
     *
     * @param passClass The object of PassClass
     * @return The status of the file has been written as a boolean value.
     */
    public boolean writePassClass(PassClass passClass) {
        String[] header = readHeader();
        ArrayList<PassClass> passClasses = readAllPassClass();
        ArrayList<String[]> list = new ArrayList<>();

        passClasses.add(passClass);

        list.add(header);
        // convert to string
        for (PassClass el : passClasses) {
            String[] col = new String[header.length];
            col[0] = el.getMemberEmail();
            col[1] = String.valueOf(el.getClassID());
            col[2] = el.getAttendanceStatus().getStatus();
            list.add(col);
        }

        // write into file
        return writeFile(list);
    }

    /**
     * Method to write credit history into cdv file.
     *
     * @param creditHistory The object of CreditHistory.
     * @return The status of the file has been written as a boolean value.
     */
    public boolean writeCreditHistory(CreditHistory creditHistory) {
        String[] header = readHeader();
        ArrayList<CreditHistory> creditHistories = readCreditHistory();
        ArrayList<String[]> list = new ArrayList<>();

        creditHistories.add(creditHistory);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        list.add(header);
        // convert to string
        for (CreditHistory el : creditHistories) {
            String[] col = new String[header.length];
            col[0] = el.getMemberEmail();
            col[1] = formatter.format(el.getDatetime());
            col[2] = String.valueOf(el.getwClass().getClassID());
            col[3] = el.getAction().getAction();
            list.add(col);
        }

        // write into file
        return writeFile(list);
    }

    /**
     * Method to remove a booking.
     *
     * @param booking The object of booking.
     * @return The status of the file has been modified as a boolean value.
     */
    public boolean deleteBooking(Booking booking) {
        Booking oldBooking = readBookingByBookingID(booking.getBookingID());

        String[] header = readHeader();
        ArrayList<Booking> bookings = readAllBooking();
        ArrayList<String[]> list = new ArrayList<>();

        if (oldBooking != null) { // if old booking exist, do remove.
            int index = 0;
            for (Booking el : bookings) {
                if (el.getBookingID() == booking.getBookingID()) {
                    break;
                }
                index++;
            }
            bookings.remove(index);

            list.add(header);
            // convert to string
            for (Booking el : bookings) {
                String[] col = bookingToArray(el, header.length);
                list.add(col);
            }

            // write into file
            return writeFile(list);
        }
        return false; // if old booking does not exist
    }

    /**
     * Method to remove a class.
     *
     * @param wClass The object of the WClass.
     * @return The status of the file has been modified as a boolean value.
     */
    public boolean deleteClas(WClass wClass) {
        WClass oldWClass = readClass(wClass.getClassID());

        String[] header = readHeader();
        ArrayList<WClass> classes = readAllClass();
        ArrayList<String[]> list = new ArrayList<>();

        if (oldWClass != null) { // if old class exist, do remove first.
            int index = 0;
            for (WClass el : classes) {
                if (el.getClassID() == wClass.getClassID()) {
                    break;
                }
                index++;
            }
            classes.remove(index);

            list.add(header);
            // convert to string
            for (WClass el : classes) {
                String[] col = wClassToArray(el, header.length);
                list.add(col);
            }

            // write into file
            return writeFile(list);
        }
        return false; // if old class does not exist
    }

    /**
     * Method to remove an instructor qualification.
     *
     * @param instructorEmail The email of instructor.
     * @param qualification The qualification as a String.
     * @return The status of the file has been modified as a boolean value.
     */
    public boolean deleteInstructorQualification(String instructorEmail, String qualification) {
        ArrayList<String[]> qualificationList = readFile();

        String[] header = readHeader();
        ArrayList<String[]> list = new ArrayList<>();

        int index = 0;
        for (String[] col: qualificationList) {
            if (col[0].equals(instructorEmail) && col[1].equals(qualification)) {
                qualificationList.remove(index);

                list.add(header);
                list.addAll(qualificationList);
                return writeFile(list);
            }
            index++;
        }
        return false; // if qualification does not exist
    }

    /**
     * Method to remove an instructor specialisation.
     *
     * @param instructorEmail The email of instructor.
     * @param specialisation The specialisation as a String.
     * @return The status of the file has been modified as a boolean value.
     */
    public boolean deleteInstructorSpecialisation(String instructorEmail, String specialisation) {
        ArrayList<String[]> specialisationList = readFile();

        String[] header = readHeader();
        ArrayList<String[]> list = new ArrayList<>();

        int index = 0;
        for (String[] col: specialisationList) {
            if (col[0].equals(instructorEmail) && col[1].equals(specialisation)) {
                specialisationList.remove(index);

                list.add(header);
                list.addAll(specialisationList);
                return writeFile(list);
            }
            index++;
        }
        return false; // if specialisation does not exist
    }
}
