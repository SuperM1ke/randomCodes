import java.util.Map;

/**
 * The Application class serves as the entry point for the application.
 * The class simulates a basic menu-driven navigation system where menus are dynamically selected and executed based on the current menu ID.
 *
 * @author Runhui Zhou
 * @version 1.0.0
 */
public class Application {

    public static void main(String[] args) {
        // Initialize the application with the ID of the initial menu
        int menuId = Constants.MENU_ID_INITIAL_MENU;

        do {
            // Create a new instance of MenuHelper to manage menus.
            MenuHelper menuHelper = new MenuHelper();
            Map<String, Object> data = menuHelper.getMenuById(menuId).getData();
            // Iterate over all registered menus to find and execute the one with the current menuId.
            for (Menu menu : menuHelper.getMenus()) {
                if (menu.getId() == menuId) {
                    menu.setData(data);
                    // Execute the current menu and update the menuId with the ID of the next menu to navigate to.
                    menuId = menu.run();
                    data = menu.getData();
                }
            }
        } while (menuId != -1); // Loop until -1 is returned to exit the application.
    }
}
