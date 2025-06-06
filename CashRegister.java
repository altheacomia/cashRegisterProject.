import java.util.*;
import java.util.regex.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;

public class CashRegister {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<String> usernames = new ArrayList<>();
    static ArrayList<String> passwords = new ArrayList<>();
    static String loggedInUser = "";

    public static void main(String[] args) {
        System.out.println("Welcome to McDo Menu Register System!");

        boolean authenticated = false;

        while (!authenticated) {
            System.out.println("\n1. Sign Up");
            System.out.println("2. Log In");
            System.out.print("Enter your choice: ");
            int authChoice = 0;
            try {
                authChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            if (authChoice == 1) {
                signUp();
            } else if (authChoice == 2) {
                authenticated = logIn();
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }

        startCashRegister(loggedInUser);
    }

    static void signUp() {
        String username;
        String password;

        while (true) {
            System.out.print("Enter a username (5-15 alphanumeric chars): ");
            username = scanner.nextLine();
            if (!username.matches("^[a-zA-Z0-9]{5,15}$")) {
                System.out.println("Invalid username. Try again.");
                continue;
            }

            System.out.print("Enter a password (8-20 chars, at least 1 uppercase and 1 number): ");
            password = scanner.nextLine();
            if (!password.matches("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,20}$")) {
                System.out.println("Invalid password. Try again.");
                continue;
            }

            usernames.add(username);
            passwords.add(password);
            System.out.println("Signup successful! You may now log in.");
            break;
        }
    }

    static boolean logIn() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        if (!username.matches("^[a-zA-Z0-9]{5,15}$")) {
            System.out.println("Invalid username format. It must be 5-15 alphanumeric characters.");
            return false;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (!password.matches("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,20}$")) {
            System.out.println("Invalid password format. It must be 8-20 characters long, with at least one uppercase letter and one number.");
            return false;
        }

        for (int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i).equals(username) && passwords.get(i).equals(password)) {
                System.out.println("Login successful!");
                loggedInUser = username;
                return true;
            }
        }

        System.out.println("Incorrect username or password. Try again.");
        return false;
    }

    static void startCashRegister(String username) {
        ArrayList<String> menu = new ArrayList<>();
        ArrayList<Double> prices = new ArrayList<>();
        ArrayList<String> cart = new ArrayList<>();
        ArrayList<Double> cartPrices = new ArrayList<>();

        menu.add("Big Mac"); prices.add(199.00);
        menu.add("McChicken"); prices.add(150.00);
        menu.add("Cheeseburger"); prices.add(120.00);
        menu.add("Double Cheeseburger"); prices.add(180.00);
        menu.add("McSpaghetti"); prices.add(90.00);
        menu.add("Chicken McDo w/ Rice"); prices.add(135.00);
        menu.add("6-pc Chicken McNuggets"); prices.add(180.00);
        menu.add("French Fries (Medium)"); prices.add(75.00);
        menu.add("French Fries (Large)"); prices.add(95.00);
        menu.add("Coke (Medium)"); prices.add(50.00);
        menu.add("Coke (Large)"); prices.add(65.00);
        menu.add("Sundae (Chocolate)"); prices.add(45.00);
        menu.add("Apple Pie"); prices.add(39.00);

        boolean running = true;

        while (running) {
            System.out.println("=============================");
            System.out.println("      MCDO MENU REGISTER     ");
            System.out.println("=============================");
            System.out.println("1. Order");
            System.out.println("2. View cart");
            System.out.println("3. Remove product from cart");
            System.out.println("4. Check Out");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    boolean ordering = true;
                    while (ordering) {
                        System.out.println("MENU:");
                        for (int i = 0; i < menu.size(); i++) {
                            System.out.println((i + 1) + ". Item: " + menu.get(i) + ", Price: PHP " + prices.get(i));
                        }
                        System.out.print("Enter item number to order (0 to stop adding items): ");

                        int itemChoice;
                        try {
                            itemChoice = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input.");
                            continue;
                        }

                        if (itemChoice == 0) {
                            ordering = false;
                        } else if (itemChoice >= 1 && itemChoice <= menu.size()) {
                            System.out.print("Enter quantity: ");
                            int quantity;
                            try {
                                quantity = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid quantity.");
                                continue;
                            }
                            if (quantity <= 0) {
                                System.out.println("Quantity must be at least 1.");
                                continue;
                            }

                            String itemName = menu.get(itemChoice - 1);
                            double itemPrice = prices.get(itemChoice - 1);
                            double subtotal = itemPrice * quantity;

                            for (int i = 0; i < quantity; i++) {
                                cart.add(itemName);
                                cartPrices.add(itemPrice);
                            }

                            System.out.printf("%s x%d  PHP %.2f added to cart.\n", itemName, quantity, subtotal);
                        } else {
                            System.out.println("Invalid item number.");
                        }
                    }
                    break;

                case 2:
                    if (cart.isEmpty()) {
                        System.out.println("Your cart is empty.");
                    } else {
                        System.out.println("YOUR CART:");
                        HashMap<String, Integer> itemQty = new HashMap<>();
                        HashMap<String, Double> itemPrice = new HashMap<>();
                        for (int i = 0; i < cart.size(); i++) {
                            itemQty.put(cart.get(i), itemQty.getOrDefault(cart.get(i), 0) + 1);
                            itemPrice.put(cart.get(i), cartPrices.get(i));
                        }
                        double totalCart = 0;
                        for (String item : itemQty.keySet()) {
                            int qty = itemQty.get(item);
                            double price = itemPrice.get(item);
                            double subtotal = price * qty;
                            System.out.println(qty + " x " + item + " = PHP " + subtotal);
                            totalCart += subtotal;
                        }
                        System.out.printf("Total: PHP %.2f\n", totalCart);
                    }
                    break;

                case 3:
                    if (cart.isEmpty()) {
                        System.out.println("Your cart is empty.");
                    } else {
                        System.out.println("YOUR CART:");
                        for (int i = 0; i < cart.size(); i++) {
                            System.out.println((i + 1) + ". Item: " + cart.get(i) + ", Price: PHP " + cartPrices.get(i));
                        }
                        System.out.print("Enter the number of the item to remove: ");
                        int removeChoice;
                        try {
                            removeChoice = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input.");
                            break;
                        }
                        if (removeChoice >= 1 && removeChoice <= cart.size()) {
                            System.out.println(cart.get(removeChoice - 1) + " removed from cart.");
                            cart.remove(removeChoice - 1);
                            cartPrices.remove(removeChoice - 1);
                        } else {
                            System.out.println("Invalid item number.");
                        }
                    }
                    break;

                case 4:
                    if (cart.isEmpty()) {
                        System.out.println("Your cart is empty.");
                    } else {
                        double totalAmount = 0;
                        System.out.println("CHECKOUT:");
                        for (int i = 0; i < cart.size(); i++) {
                            System.out.println((i + 1) + ". Item: " + cart.get(i) + ", Price: PHP " + cartPrices.get(i));
                            totalAmount += cartPrices.get(i);
                        }
                        System.out.printf("Total amount to pay: PHP %.2f\n", totalAmount);
                        System.out.print("Enter payment amount: ");

                        double paymentAmount;
                        try {
                            paymentAmount = Double.parseDouble(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input.");
                            break;
                        }

                        if (paymentAmount >= totalAmount) {
                            System.out.printf("Payment accepted. Change: PHP %.2f\n", paymentAmount - totalAmount);
                            System.out.println("Thank you for choosing and ordering in McDo!");

                            printReceipt(cart, cartPrices, totalAmount, paymentAmount, username);

                            System.out.print("Do you want to perform another transaction? (yes/no): ");
                            String continueChoice = scanner.nextLine();
                            if (continueChoice.equalsIgnoreCase("no")) {
                                running = false;
                            } else {
                                cart.clear();
                                cartPrices.clear();
                            }
                        } else {
                            System.out.println("Insufficient payment.");
                        }
                    }
                    break;

                case 5:
                    System.out.println("Thank you for using McDo Menu Register System!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    static void printReceipt(ArrayList<String> cart, ArrayList<Double> cartPrices, double total, double payment, String cashierUsername) {
        HashMap<String, Integer> itemQuantities = new HashMap<>();
        HashMap<String, Double> itemPrices = new HashMap<>();

        for (int i = 0; i < cart.size(); i++) {
            String item = cart.get(i);
            double price = cartPrices.get(i);
            itemQuantities.put(item, itemQuantities.getOrDefault(item, 0) + 1);
            itemPrices.put(item, price);
        }

        StringBuilder receipt = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = LocalDateTime.now().format(formatter);

        receipt.append("\n============= MCDO OFFICIAL RECEIPT =============\n");
        receipt.append(String.format("Date & Time : %-35s\n", dateTime));
        receipt.append(String.format("Cashier     : %-35s\n", cashierUsername));
        receipt.append("--------------------------------------------------\n");
        receipt.append(String.format("%-25s %-5s %10s\n", "Item", "Qty", "Subtotal"));
        receipt.append("--------------------------------------------------\n");

        for (String item : itemQuantities.keySet()) {
            int qty = itemQuantities.get(item);
            double price = itemPrices.get(item);
            double subtotal = qty * price;
            receipt.append(String.format("%-25s x%-3d   PHP %8.2f\n", item, qty, subtotal));
        }

        receipt.append("--------------------------------------------------\n");
        receipt.append(String.format("%-30s PHP %8.2f\n", "TOTAL:", total));
        receipt.append(String.format("%-30s PHP %8.2f\n", "PAYMENT:", payment));
        receipt.append(String.format("%-30s PHP %8.2f\n", "CHANGE:", payment - total));
        receipt.append("==================================================\n");
        receipt.append("           THANK YOU FOR YOUR ORDER!             \n");
        receipt.append("           VISIT AGAIN AT MCDO! ❤️🍔              \n");

        System.out.println(receipt.toString());

        try (FileWriter fw = new FileWriter("transactions.txt", true)) {
            fw.write(receipt.toString());
        } catch (IOException e) {
            System.out.println("Error saving receipt to file.");
        }
    }
}
