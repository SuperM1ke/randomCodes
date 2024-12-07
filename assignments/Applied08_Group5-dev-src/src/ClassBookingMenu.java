public class ClassBookingMenu extends Menu{
    private MemberClassController controller;
    private Member member;
    private WClass wClass;

    @Override
    public int run() {
        member = (Member)(data.get("member"));
        retrieveWClass();
        displayHeader(member);
        displayClassDetails();


        id = 0; // Initialize default menu ID
        boolean validOptionSelected = false;

        while (!validOptionSelected) {
            display();
            String option = getUserOption();

            if (isNumeric(option)) {
                if (Integer.parseInt(option) == 1) {
                    boolean succeeded = controller.makeBooking(member.getEmail(), wClass.getClassID());
                    if (succeeded) {
                        displayConfirmation();
                        if (promptEnter()) {
                            id = Constants.MENU_ID_MEMBER_CLASS_LISTING_MENU;
                        }
                    }
                    validOptionSelected = succeeded;
                }
            }
            else {
                if (option.toUpperCase().equals("B")) {// Redirect to member home page
                    id = Constants.MENU_ID_MEMBER_CLASS_LISTING_MENU;
                    validOptionSelected = true;
                } else {
                    System.out.println("Error: Unknown option. Please try again.");
                }
            }
        }
        return id;
    }

    ClassBookingMenu() {
        super();
        id = Constants.MENU_ID_MEMBER_CLASS_BOOKING_MENU;
        pageTitle = "Class Detail";
        menuItems.add("1. Make a booking");
        menuItems.add("B. Back");
        controller = new MemberClassController();
        member = new Member();
        wClass = new WClass();
    }

    private void displayConfirmation() {
        setPageTitle("Booking Confirmation");
        retrieveWClass();
        displayHeader(member);
        displayClassDetails();
        System.out.println("!!! Booking Success !!!");
        System.out.println("!!! Details for your booked class is shown above !!!");
    }

    private void displayClassDetails() {
            System.out.println(wClass);
    }

    private void retrieveWClass() {
        if (!data.containsKey("wClass")) {
            System.out.println("Error: wClass data is missing or null.");
            return;
        }

        try {
            int classId = ((WClass) data.get("wClass")).getClassID();
            wClass = controller.getClassById(classId);
        } catch (ClassCastException e) {
            System.out.println("Error: Invalid type for wClass. Expected WClass.");
        }
    }
}
