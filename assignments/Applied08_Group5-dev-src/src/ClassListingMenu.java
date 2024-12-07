import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class ClassListingMenu extends Menu{
    private MemberClassController controller;

    @Override
    public int run() {
        display();
        id = 0; // Initialize default menu ID
        boolean validOptionSelected = false;

        while (!validOptionSelected) {
            String option = getUserOption();

            if (isNumeric(option)) {
                int choice = Integer.parseInt(option);

                if (choice > 0 && choice <= controller.getWClasses().size()) {
                    return displayClassDetail(choice);
                }
                else {
                    {
                        if (choice == 0) {
                            id = -1;
                            validOptionSelected = true;
                        } else {
                            System.out.println("Error: Unknown option. Please try again.");
                        }
                    }
                }
            }
            else {
                if (option.toUpperCase().equals("B")) {// Redirect to member home page
                    id = Constants.MENU_ID_MEMBER_HOME_MENU;
                    validOptionSelected = true;
                } else {
                    System.out.println("Error: Unknown option. Please try again.");
                }
            }
        }
        return id;
    }

    public ClassListingMenu() {
        super();
        id = Constants.MENU_ID_MEMBER_CLASS_LISTING_MENU;
        pageTitle = "Available Classes";
        // todo: menu items tbd
        menuItems.add("B. Back to Home Page");
        menuItems.add("0. logout");
        controller = new MemberClassController();
    }

    public ClassListingMenu(int id, ArrayList<String> menuItems, String pageTitle, MemberClassController controller, Map<String, Object> data) {
        super(id, menuItems, pageTitle, data);
        this.controller = controller;
    }

    public void display() {
        // todo: use passed data from previous menu instead of fetching again.
        FileIO fileIO = new FileIO("./database/member.csv");
        Member member = fileIO.readMember("member@student.monash.edu");
        data.put("member", member);
        displayHeader(member);
        displayClassListing();
        super.display();
    }

    private void displayClassListing() {
        try {
            ArrayList<WClass> classes = controller.retrieveClasses(); // 假设这个方法可能抛出异常

            if (classes.isEmpty()) {
                System.out.println("No classes available.");
            } else {
                for (int i = 0; i < classes.size(); i++) {
                    System.out.println((i + 1) + ". " + classes.get(i).getClassName());
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to retrieve classes: " + e.getMessage());
        }
    }


    private int displayClassDetail(int classId) {
        for (WClass wClass : controller.getWClasses()) {
            if (wClass.getClassID() == classId) {
                data.put("wClass", wClass);
                System.out.println(data.size());
                return Constants.MENU_ID_MEMBER_CLASS_BOOKING_MENU;
            }
        }
        return -1;
    }
}
