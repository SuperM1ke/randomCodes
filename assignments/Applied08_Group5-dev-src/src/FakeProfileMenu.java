public class FakeProfileMenu extends Menu{

    FakeProfileMenu() {
        super();
        menuItems.add("testing fake profile menu");
        menuItems.add("X. go to member home menu");
        menuItems.add("0. logout");
        id = 999; // todo: temporarily set this page id to 999. should use Constant.VALUE
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

                switch (option.toUpperCase()) {
                    case "X":
                        // todo: test route to member_home_menu
                        id = Constants.MENU_ID_MEMBER_HOME_MENU;
                        validOptionSelected = true;
                        break;
                    default:
                        System.out.println("Error: Unknown option. Please try again.");
                        break;
                }
            }
        }

        return id;
    }
}
