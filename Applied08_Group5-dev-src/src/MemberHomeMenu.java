import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class MemberHomeMenu extends Menu{

    public MemberHomeMenu() {
        super();
        id = Constants.MENU_ID_MEMBER_HOME_MENU;
        pageTitle = "Home Page";
        menuItems.add("1. My Profile");
        menuItems.add("2. Book a class");
        menuItems.add("3. Manage upcoming Classes");
        menuItems.add("4. Previous Classes and feedback");
        menuItems.add("5. Manage Credits");
        menuItems.add("0. Logout");
    }
    public MemberHomeMenu(int id, ArrayList<String> menuItems, String pageTitle, Map<String, Object> data) {
        super(id, menuItems, pageTitle, data);
    }

    public void display() {
        // todo: use passed data from previous menu instead of fetching again.
        FileIO fileIO = new FileIO("./database/member.csv");
        Member member = fileIO.readMember("member@student.monash.edu");
        data.put("member", member);
        displayHeader(member);
        super.display();
    }

    @Override
    public int run() {
        display();
        id = 0;
        boolean validOptionSelected = false;


        while (!validOptionSelected) {
            String option = getUserOption();
            if (isNumeric(option)) {
                int choice = Integer.parseInt(option);
                switch (choice) {
                    case 1:
                        // todo: go to "My Profile" menu
                        //  use 999 (FakeProfileMenu) for only demo
                        id = 999;
                        validOptionSelected = true;
                        break;
                    case 2:
                        id = Constants.MENU_ID_MEMBER_CLASS_LISTING_MENU;
                        validOptionSelected = true;
                        break;
                    case 3:
                        // todo: go to "Manage upcoming classes" menu
                        validOptionSelected = true;
                        break;
                    case 4:
                        // todo: go to "Previous classes and feedback" menu
                        validOptionSelected = true;
                        break;
                    case 5:
                        // todo: go to "Manage credits" menu
                        validOptionSelected = true;
                        break;
                    case 0:
                        // todo: logout behaviour tbd
                        id = -1;
                        validOptionSelected = true;
                        break;
                    default:
                        System.out.println("Error: Unknown option");
                }
            }

        }

        return id;
    }
}
