package login2_0;

import java.util.Scanner;

public class QuickChatApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Message[] allMessages = new Message[100];
        int messageCount = 0;

        // ===== PART 1: REGISTRATION =====
        System.out.println("=== REGISTRATION ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter cell phone (+27...): ");
        String cell = scanner.nextLine();

        Login2_0 user = new Login2_0(username, password, cell);
        System.out.println(user.registerUser());

        // ===== PART 1: LOGIN =====
        System.out.println("\n=== LOGIN ===");
        System.out.print("Enter username: ");
        String loginUser = scanner.nextLine();
        System.out.print("Enter password: ");
        String loginPass = scanner.nextLine();

        boolean loginStatus = user.loginUser(loginUser, loginPass);
        System.out.println(user.returnLoginStatus(loginStatus));

        // ===== PARTS 2 & 3: MESSAGING SYSTEM =====
        if (user.isLoggedIn()) {
            System.out.println("\nWelcome to QuickChat.");

            System.out.print("How many messages do you wish to enter? ");
            int maxMessages = scanner.nextInt();
            scanner.nextLine();

            int messageCount2 = 0;
            boolean quit = false;

            while (!quit && messageCount2 < maxMessages) {
                System.out.println("\n=== MAIN MENU ===");
                System.out.println("Option 1) Send Messages");
                System.out.println("Option 2) Show recently sent messages - Coming Soon");
                System.out.println("Option 3) Quit");
                System.out.println("Option 4) Stored Messages"); // PART 3
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    // ===== PART 2: Send Message =====
                    System.out.println("\n--- Message " + (messageCount2 + 1) + " ---");

                    System.out.print("Enter recipient cell number: ");
                    String recipient = scanner.nextLine();

                    System.out.print("Enter your message: ");
                    String messageText = scanner.nextLine();

                    Message newMessage = new Message(recipient, messageText, messageCount2 + 1);

                    // Validate recipient
                    String recipientCheck = newMessage.checkRecipientCell();
                    System.out.println(recipientCheck);

                    if (!recipientCheck.equals("Cell phone number successfully captured.")) {
                        System.out.println("Please try again with correct format.");
                        continue;
                    }

                    // Validate message length
                    String lengthCheck = newMessage.checkMessageLength();
                    System.out.println(lengthCheck);

                    if (lengthCheck.contains("exceeds")) {
                        System.out.println("Please try again with shorter message.");
                        continue;
                    }

                    // Display message details
                    System.out.println("\n=== MESSAGE DETAILS ===");
                    System.out.println("Message ID: " + newMessage.getMessageID());
                    System.out.println("Message Hash: " + newMessage.getMessageHash());
                    System.out.println("Recipient: " + recipient);
                    System.out.println("Message: " + messageText);

                    // Choose what to do with message
                    String result = newMessage.sendMessage();
                    System.out.println(result);

                    if (result.equals("Message successfully sent.") ||
                            result.equals("Message successfully stored.") ||
                            result.equals("Press 0 to delete the message.")) {
                        allMessages[messageCount] = newMessage;
                        messageCount++;
                        messageCount2++;
                    }

                } else if (choice == 2) {
                    System.out.println("Coming Soon.");

                } else if (choice == 3) {
                    quit = true;
                    System.out.println("Quitting application...");

                } else if (choice == 4) {
                    // ===== PART 3: Stored Messages Menu =====
                    storedMessagesMenu(scanner, allMessages, messageCount);

                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            }

            // Display summary
            System.out.println("\n=== SUMMARY ===");
            System.out.println("Total number of messages sent: " + Message.returnTotalMessages());

        } else {
            System.out.println("You must be logged in to send messages.");
        }

        scanner.close();
    }

    // ===== PART 3: Stored Messages Menu =====
    public static void storedMessagesMenu(Scanner scanner, Message[] allMessages, int messageCount) {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== STORED MESSAGES MENU ===");
            System.out.println("a. Display sender and recipient of all stored messages");
            System.out.println("b. Display the longest stored message");
            System.out.println("c. Search for a message ID");
            System.out.println("d. Search for all messages for a particular recipient");
            System.out.println("e. Delete a message using message hash");
            System.out.println("f. Display full report of all stored messages");
            System.out.println("g. Back to Main Menu");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "a":
                    MessageManager.displayStoredMessages();
                    break;

                case "b":
                    String longest = MessageManager.displayLongestStoredMessage();
                    System.out.println("Longest stored message: \"" + longest + "\"");
                    break;

                case "c":
                    System.out.print("Enter Message ID to search: ");
                    String searchID = scanner.nextLine();
                    String result = MessageManager.searchByMessageID(searchID, allMessages, messageCount);
                    System.out.println(result);
                    break;

                case "d":
                    System.out.print("Enter recipient cell number: ");
                    String recipient = scanner.nextLine();
                    String searchResult = MessageManager.searchByRecipient(recipient, allMessages, messageCount);
                    System.out.println(searchResult);
                    break;

                case "e":
                    System.out.print("Enter Message Hash to delete: ");
                    String hash = scanner.nextLine();
                    String deleteResult = MessageManager.deleteMessageByHash(hash, allMessages, messageCount);
                    System.out.println(deleteResult);
                    break;

                case "f":
                    MessageManager.displayReport(allMessages, messageCount);
                    break;

                case "g":
                    back = true;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}