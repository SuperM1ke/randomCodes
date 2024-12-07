import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The abstract {@code Menu} class represents a generic menu system
 * with basic functionalities such as displaying menu items, handling user input,
 * and showing headers for the menu pages.
 *
 * This class is meant to be extended by other specific menus in the application.
 * @author Runhui Zhou
 * @version 1.0.0
 */

public abstract class Menu {
    // Add each menu instance to MenuHelper class

    protected int id; // ID for each menu. Add any specific menu id to Constants class and use them
    protected ArrayList<String> menuItems; // Each line of menu
    protected String pageTitle; //Title of this menu
    protected Map<String, Object> data; // Data that can be passed between menus

    /**
     * Abstract method to be implemented by subclasses to run the menu.
     * @return an integer representing the selected menu option or action result
     */
    public abstract int run();

    /**
     * Default constructor for Menu, initializing with default values.
     */
    public Menu() {
        this.id = 0;
        this.menuItems = new ArrayList<>();
        this.pageTitle = "Unknown Menu";
        this.data = new HashMap<>();
    }

    /**
     * Parameterized constructor for Menu.
     * @param id the ID of the menu
     * @param menuItems the list of menu items
     * @param pageTitle the title of the menu page
     * @param data a map of additional data that can be passed between menus, such as a member obj / class obj
     */
    public Menu(int id, ArrayList<String> menuItems, String pageTitle, Map<String, Object> data) {
        this.id = id;
        this.menuItems = menuItems;
        this.pageTitle = pageTitle;
        this.data = data;
    }

    /**
     * Displays the menu items to the console.
     */
    public void display() {
        for (String item : menuItems) {
            System.out.println(item);
        }
    }

    /**
     * Displays the header for the menu, showing the system title, user information, and credits (if applicable).
     * @param username the name of the user
     * @param credits the credits available to the user (if any), or null if not applicable
     */
    private void displayHeader(String username, Double credits) {
        StringBuilder header = new StringBuilder();
        header.append("****************************************************************************************************\n");
        header.append("                               Monash Wellness-Class Booking System\n");
        header.append("****************************************************************************************************\n");
        header.append("User: ").append(username);

        // show credits if not null
        if (credits != null) {
            header.append("\tCredits: ").append(String.format("%.2f", credits));
        }

        header.append("\n****************************************************************************************************\n");
        header.append("                                             ").append(pageTitle).append("\n");
        header.append("****************************************************************************************************\n");

        System.out.println(header);
    }

    /**
     * Displays the header by determining the user's role (Member or Admin) and shows appropriate information.
     * @param user the User object, which could be a Member or Admin
     */
    public void displayHeader(User user) {
        if (user instanceof Member member) {
            // show name and credits if user role is member
            displayHeader(member.getFirstName(), member.getCreditBalance());
        } else if (user instanceof Admin admin) {
            // Only show username if user role is admin
            displayHeader(admin.getFirstName(), null);
        }
    }

    /**
     * Retrieves the data map for the menu.
     * @return a map of data associated with the menu
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Retrieves the ID of the menu.
     * @return the menu ID
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the list of menu items.
     * @return an ArrayList of menu items
     */
    public ArrayList<String> getMenuItems() {
        return menuItems;
    }

    /**
     * Retrieves the title of the menu page.
     * @return the page title
     */
    public String getPageTitle() {
        return pageTitle;
    }

    /**
     * Prompts the user for an integer option and returns the entered option.
     * @return the integer option entered by the user
     */
    protected int getIntOption() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("--- Enter an option: ");
        return scanner.nextInt();
    }

    /**
     * Prompts the user for a string option and returns the trimmed user input.
     * @return the trimmed user input as a string
     */
    protected String getUserOption() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("--- Enter an option: ");
        return scanner.nextLine().trim();  // Read the whole line and trim whitespace
    }

    /**
     * Checks if a given string is numeric (contains only digits).
     * @param str the string to check
     * @return true if the string contains only digits, false otherwise
     */
    protected boolean isNumeric(String str) {
        return str.matches("\\d+");  // Regex to check if the string contains only digits
    }

    /**
     * Prompts the user to press Enter and returns true if the user presses only Enter (no additional input).
     * @return true if the user presses only Enter, otherwise false
     */
    protected boolean promptEnter() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press Enter to continue");
        String input = scanner.nextLine().trim(); // Read user input and trim whitespace

        // Return true if the input is empty (Enter pressed), otherwise false
        return input.isEmpty();
    }

    /**
     * Sets the data map for the menu.
     * @param data the map of data to associate with the menu
     */
    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    /**
     * Sets the ID of the menu.
     * @param id the new ID for the menu
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the list of menu items.
     * @param menuItems the list of menu items to set
     */
    public void setMenuItems(ArrayList<String> menuItems) {
        this.menuItems = menuItems;
    }

    /**
     * Sets the title of the menu page.
     * @param pageTitle the new page title for the menu
     */
    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    /**
     * Returns a string representation of the menu, including its ID and menu items.
     * @return a string representing the menu
     */
    public String toString() {
        return "id: " + id + ", menu items: " + menuItems;
    }
}
