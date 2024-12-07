import java.util.ArrayList;

/**
 * The MenuHelper class is responsible for managing a collection of Menu objects.
 * It provides functionality to add menus, display menu details, and retrieve the list of menus.
 *
 * @author Runhui Zhou
 * @version 1.0.0
 */
public class MenuHelper {

    private ArrayList<Menu> menus;

    public MenuHelper() {
        menus = new ArrayList<>();
        // Manually add menu objects into the list
        menus.add(new InitialMenu());
        menus.add(new MemberHomeMenu());
        menus.add(new ClassListingMenu());
        menus.add(new ClassBookingMenu());
        // todo: Add other menu instances as needed, manually
        menus.add(new FakeProfileMenu());
    }

    public void display() {
        for (Menu menu : menus) {
            System.out.println("Menu ID: " + menu.getId());
            System.out.println("Menu Title: " + menu.getPageTitle());
        }
    }
    public Menu getMenuById(int id) {
        if (menus != null && !menus.isEmpty()) {
            for (Menu menu : menus) {
                if (menu.getId() == id) {
                    return menu;
                }
            }
        }
        return null;
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<Menu> menus) {
        this.menus = menus;
    }
}
