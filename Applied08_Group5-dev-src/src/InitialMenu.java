import java.util.ArrayList;
import java.util.Map;

public class InitialMenu extends Menu{

    public InitialMenu() {
        super();
        id = Constants.MENU_ID_INITIAL_MENU;
        pageTitle = "Initial Menu";
        menuItems.add("1. Login");
        menuItems.add("0. Exit");
    }
    public InitialMenu(int id, ArrayList<String> menuItems, String pageTitle, Map<String, Object> data) {
        super(id, menuItems, pageTitle, data);
    }

    public void display() {
        System.out.println("******************************************************************");
        System.out.println("          Wecome to Monash Wellness-Class Booking System          ");
        System.out.println("******************************************************************");
        // call display method from super class Menu to show menuItems
        super.display();
    }

    @Override
    public int run() {
        display();
        id = 0; // Initialize default menu ID
        boolean validOptionSelected = false;

        while (!validOptionSelected) {
            String option = getUserOption();

            if (isNumeric(option)) {
                int choice = Integer.parseInt(option);
                switch (choice) {
                    case 1:
                        // todo: go to the expected menu. Now it directs to "Member Home Menu" for demo
                        id = Constants.MENU_ID_MEMBER_HOME_MENU;
                        validOptionSelected = true;
                        break;
                    case 0:
                        // todo: logout behaviour tbd
                        id = -1;
                        validOptionSelected = true;
                        break;
                    default:
                        System.out.println("Error: Unknown option. Please try again.");
                        break;
                }
            }
            else {
                System.out.println("Error: Unknown option. Please try again.");
            }
        }

        return id;
    }

}
