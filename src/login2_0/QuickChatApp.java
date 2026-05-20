package Login2_0;

import java.util.ArrayList;
import java.util.Scanner;

public class QuickChatApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Message> messages = new ArrayList<>();

        // ---------------- REGISTER ----------------
        System.out.println("=== REGISTRATION ===");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter cell phone (+27...): ");
        String cell = scanner.nextLine();

        Login2_0 user = new Login2_0(username, password, cell);

        System.out.println(user.registerUser());

        // ---------------- LOGIN ----------------
        System.out.println("\n=== LOGIN ===");

        System.out.print("Enter username: ");
        String loginUser = scanner.nextLine();

        System.out.print("Enter password: ");
        String loginPass = scanner.nextLine();

        boolean loginStatus = user.loginUser(loginUser, loginPass);

        System.out.println(user.returnLoginStatus(loginStatus));

        // ---------------- MESSAGING SYSTEM ----------------
        if (loginStatus) {

            System.out.println("\nWelcome to QuickChat.");

            // Ask number of messages
            System.out.print("How many messages do you wish to enter? ");
            int numMessagesToSend = scanner.nextInt();
            scanner.nextLine();

            boolean running = true;
            int messageCount = 1;

            while (running && messageCount <= numMessagesToSend) {

                System.out.println("\n=== MAIN MENU ===");
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");

                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {

                    case 1:

                        // ---------------- CREATE MESSAGE ----------------
                        System.out.println("\n--- Send Message "
                                + messageCount + " ---");

                        System.out.print("Enter recipient cell number: ");
                        String recipient = scanner.nextLine();

                        System.out.print("Enter your message: ");
                        String messageText = scanner.nextLine();

                        // Correct constructor order
                        Message newMessage =
                                new Message(messageCount,
                                        recipient,
                                        messageText);

                        // ---------------- VALIDATE RECIPIENT ----------------
                        String recipientCheck =
                                newMessage.checkRecipientCell();

                        System.out.println(recipientCheck);

                        if (!recipientCheck.equals(
                                "Cell phone number successfully captured.")) {

                            System.out.println(
                                    "Please restart message with correct format.");

                            continue;
                        }

                        // ---------------- VALIDATE MESSAGE LENGTH ----------------
                        String lengthCheck =
                                newMessage.checkMessageLength();

                        System.out.println(lengthCheck);

                        if (!lengthCheck.equals(
                                "Message ready to send.")) {

                            System.out.println(
                                    "Please restart message with correct length.");

                            continue;
                        }

                        // ---------------- DISPLAY MESSAGE DETAILS ----------------
                        System.out.println("\n=== MESSAGE DETAILS ===");

                        System.out.println("Message ID: "
                                + newMessage.getMessageID());

                        System.out.println("Message Hash: "
                                + newMessage.createMessageHash());

                        System.out.println("Recipient: "
                                + recipient);

                        System.out.println("Message: "
                                + messageText);

                        // ---------------- SEND OPTIONS ----------------
                        System.out.println("\n1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message");

                        System.out.print("Choose option: ");

                        int option = scanner.nextInt();
                        scanner.nextLine();

                        String result =
                                newMessage.sentMessage(option);

                        System.out.println(result);

                        // ---------------- STORE/SEND ----------------
                        if (result.equals("Message successfully sent.")
                                || result.equals("Message successfully stored.")) {

                            messages.add(newMessage);

                            System.out.println("\nFULL MESSAGE DETAILS");
                            System.out.println(
                                    newMessage.printMessages());

                            messageCount++;

                        } else if (result.equals(
                                "Press 0 to delete the message.")) {

                            System.out.println("Message disregarded.");
                        }

                        break;

                    case 2:

                        System.out.println("Coming Soon.");
                        break;

                    case 3:

                        running = false;

                        System.out.println("Quitting application...");
                        break;

                    default:

                        System.out.println(
                                "Invalid option. Please try again.");
                }
            }

            // ---------------- SUMMARY ----------------
            System.out.println("\n=== SUMMARY ===");

            System.out.println("Total number of messages sent/stored: "
                    + Message.returnTotalMessages());

            // ---------------- DISPLAY ALL MESSAGES ----------------
            if (!messages.isEmpty()) {

                System.out.println("\n=== ALL MESSAGES ===");

                for (int i = 0; i < messages.size(); i++) {

                    System.out.println("\nMessage " + (i + 1));

                    System.out.println(
                            messages.get(i).printMessages());
                }
            }

        } else {

            System.out.println(
                    "You must be logged in to send messages.");
        }

        scanner.close();
    }
}
