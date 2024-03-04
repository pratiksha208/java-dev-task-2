import java.util.HashMap;
import java.util.Scanner;

public class ATM {
    private HashMap<String, User> users;

    public ATM() {
        users = new HashMap<>();
        // Sample user added for demonstration
        User user = new User("123456", 1234, 1000.0);
        users.put(user.getUserID(), user);
    }

    public boolean authenticateUser(String userId, int pin) {
        return users.containsKey(userId) && users.get(userId).getUserPIN() == pin;
    }

    public void displayMenu() {
        System.out.println("Welcome to the ATM!");
        System.out.println("1. Existing User - Login");
        System.out.println("2. New User - Create Account");
        System.out.println("3. Exit");
    }

    public void checkBalance(String userId) {
        System.out.println("Your balance is: " + users.get(userId).getAccountBalance());
    }

    public void withdrawMoney(String userId, double amount) {
        if (users.get(userId).getAccountBalance() >= amount) {
            users.get(userId).setAccountBalance(users.get(userId).getAccountBalance() - amount);
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void depositMoney(String userId, double amount) {
        users.get(userId).setAccountBalance(users.get(userId).getAccountBalance() + amount);
        System.out.println("Deposit successful.");
    }

    public void createUser(String userId, int pin, double initialBalance) {
        if (users.containsKey(userId)) {
            System.out.println("User already exists.");
        } else {
            User newUser = new User(userId, pin, initialBalance);
            users.put(newUser.getUserID(), newUser);
            System.out.println("User created successfully.");
            displayUserMenu(new Scanner(System.in), userId); // Display user menu after creating the user
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            displayMenu(); // Display the first menu
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter user ID: ");
                    String userId = scanner.nextLine();
                    System.out.print("Enter PIN: ");
                    int pin = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    if (authenticateUser(userId, pin)) {
                        displayUserMenu(scanner, userId);
                    } else {
                        System.out.println("Invalid user ID or PIN.");
                    }
                    break;
                case 2:
                    System.out.print("Enter new user ID: ");
                    String newUserId = scanner.nextLine();
                    System.out.print("Enter new PIN: ");
                    int newPin = scanner.nextInt();
                    System.out.print("Enter initial balance: ");
                    double initialBalance = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    createUser(newUserId, newPin, initialBalance);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close(); // Close scanner before exiting
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public void displayUserMenu(Scanner scanner, String userId) {
        while (true) {
            System.out.println("Welcome, " + userId + "!");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Deposit Money");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    checkBalance(userId);
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    withdrawMoney(userId, withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    depositMoney(userId, depositAmount);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }
}
