import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

public class ATMApplication {
    private static final int MAX_ATTEMPTS = 3;
    private static final double WITHDRAWAL_LIMIT = 20000.0;
    private static final double DEPOSIT_LIMIT = 50000.0;

    private static final Map<String, String> userCredentials = new HashMap<>();
    private static double balance = 10000.0; // Default balance

    private static final ArrayList<Transaction> transactionHistory = new ArrayList<>();

    static {
        // Storing sample user credentials
        userCredentials.put("harsha", "6737");
        userCredentials.put("sathvi", "1086");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;

        while (true) {
            if (!loggedIn) {
                loggedIn = authenticateUser(scanner);
            } else {
                displayMenu(scanner, loggedIn);
                loggedIn = false; // Reset loggedIn when returning to the main menu
            }
        }
    }

    private static boolean authenticateUser(Scanner scanner) {
        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            System.out.print("Enter user ID: ");
            String enteredUserId = scanner.nextLine();
            System.out.print("Enter PIN: ");
            String enteredPin = scanner.nextLine();

            if (userCredentials.containsKey(enteredUserId) && userCredentials.get(enteredUserId).equals(enteredPin)) {
                System.out.println("Login successful!");
                return true;
            } else {
                attempts++;
                if (attempts < MAX_ATTEMPTS) {
                    System.out.println("Invalid user ID or PIN. Please try again.");
                } else {
                    System.out.println("Maximum attempts reached. Exiting ATM...");
                    System.exit(0);
                }
            }
        }

        return false;
    }

    private static void displayMenu(Scanner scanner, boolean loggedIn) {
        boolean quit = false;

        while (!quit) {
            System.out.println("\n--ATM Menu:--");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    displayTransactionHistory();
                    break;
                case 2:
                    withdraw(scanner);
                    break;
                case 3:
                    deposit(scanner);
                    break;
                case 4:
                    transfer(scanner);
                    break;
                case 5:
                    quit = true;
                    System.out.println("Thank You! Exiting ATM...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions to display.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction transaction : transactionHistory) {
                System.out.printf("Type: %s, Amount: $%.2f, Timestamp: %d%n",
                        transaction.getTransactionType(),
                        transaction.getAmount(),
                        transaction.getTimestamp());
            }
        }
    }

    private static void withdraw(Scanner scanner) {
        System.out.print("Enter the amount to withdraw: $");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a valid number.");
            return;
        }

        if (amount > WITHDRAWAL_LIMIT) {
            System.out.println("Withdrawal amount exceeds the limit. Transaction rejected.");
        } else if (amount > balance) {
            System.out.println("Insufficient funds. Withdrawal failed.");
        } else {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
            System.out.printf("Withdrawal of $%.2f successful. New balance: $%.2f%n", amount, balance);
        }
    }

    private static void deposit(Scanner scanner) {
        System.out.print("Enter the amount to deposit: $");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a valid number.");
            return;
        }

        if (amount > DEPOSIT_LIMIT) {
            System.out.println("Deposit amount exceeds the limit. Transaction rejected.");
        } else {
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount));
            System.out.printf("Deposit of $%.2f successful. New balance: $%.2f%n", amount, balance);
        }
    }

    private static void transfer(Scanner scanner) {
        System.out.print("Enter the amount to transfer: $");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a valid number.");
            return;
        }

        if (amount > balance) {
            System.out.println("Insufficient funds. Transfer failed.");
        } else {
            balance -= amount;
            transactionHistory.add(new Transaction("Transfer", amount));
            System.out.printf("Transfer of $%.2f successful. New balance: $%.2f%n", amount, balance);
        }
    }

    private static class Transaction {
        private final String transactionType;
        private final double amount;
        private final long timestamp;

        public Transaction(String transactionType, double amount) {
            this.transactionType = transactionType;
            this.amount = amount;
            this.timestamp = System.currentTimeMillis();
        }

        public String getTransactionType() {
            return transactionType;
        }

        public double getAmount() {
            return amount;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}