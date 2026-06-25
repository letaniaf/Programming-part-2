package login2_0;

public class MessageManager {

    // a. Display sender and recipient of all stored messages
    public static void displayStoredMessages() {
        String[] stored = Message.getStoredMessages();
        int count = Message.getStoredCount();

        System.out.println("\n=== STORED MESSAGES ===");
        if (count == 0) {
            System.out.println("No stored messages found.");
            return;
        }

        for (int i = 0; i < count; i++) {
            System.out.println("Stored Message " + (i + 1) + ": " + stored[i]);
        }
    }

    // b. Display the longest stored message
    public static String displayLongestStoredMessage() {
        String[] stored = Message.getStoredMessages();
        int count = Message.getStoredCount();

        if (count == 0) {
            return "No stored messages found.";
        }

        String longest = stored[0];
        for (int i = 1; i < count; i++) {
            if (stored[i].length() > longest.length()) {
                longest = stored[i];
            }
        }
        return longest;
    }

    // c. Search for message by ID and display recipient and message
    public static String searchByMessageID(String searchID, Message[] allMessages, int messageCount) {
        for (int i = 0; i < messageCount; i++) {
            if (allMessages[i] != null && allMessages[i].getMessageID().equals(searchID)) {
                return "Recipient: " + allMessages[i].getRecipient() + "\n" +
                        "Message: " + allMessages[i].getMessage();
            }
        }
        return "Message ID not found.";
    }

    // d. Search all messages for a particular recipient
    public static String searchByRecipient(String searchRecipient, Message[] allMessages, int messageCount) {
        String result = "";
        boolean found = false;

        for (int i = 0; i < messageCount; i++) {
            if (allMessages[i] != null && allMessages[i].getRecipient().equals(searchRecipient)) {
                result = result + "\"" + allMessages[i].getMessage() + "\" ";
                found = true;
            }
        }

        if (found) {
            return result.trim();
        } else {
            return "No messages found for recipient: " + searchRecipient;
        }
    }

    // e. Delete message using message hash
    public static String deleteMessageByHash(String hashToDelete, Message[] allMessages, int messageCount) {
        for (int i = 0; i < messageCount; i++) {
            if (allMessages[i] != null && allMessages[i].getMessageHash().equals(hashToDelete)) {
                String deletedMessage = allMessages[i].getMessage();
                allMessages[i] = null;
                return "Message: \"" + deletedMessage + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    // f. Display report of all messages
    public static void displayReport(Message[] allMessages, int messageCount) {
        System.out.println("\n=== FULL MESSAGE REPORT ===");
        System.out.println("========================================");

        if (messageCount == 0) {
            System.out.println("No messages to display.");
            return;
        }

        for (int i = 0; i < messageCount; i++) {
            if (allMessages[i] != null) {
                System.out.println("Message " + (i + 1) + ":");
                System.out.println("Message Hash: " + allMessages[i].getMessageHash());
                System.out.println("Recipient: " + allMessages[i].getRecipient());
                System.out.println("Message: " + allMessages[i].getMessage());
                System.out.println("Flag: " + allMessages[i].getFlag());
                System.out.println("----------------------------------------");
            }
        }
    }
}
