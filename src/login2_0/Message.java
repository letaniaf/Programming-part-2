package login2_0;

import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Message {

    // Variables
    private String messageID;
    private int numMessagesSent;
    private String recipient;
    private String message;
    private String messageHash;
    private String flag; // "SENT", "STORED", or "DISREGARDED"
    private static int totalMessagesSent = 0;
    private static final String FILE_NAME = "messages.txt";

    // Arrays for Part 3
    private static String[] sentMessages = new String[100];
    private static String[] disregardedMessages = new String[100];
    private static String[] storedMessages = new String[100];
    private static String[] messageHashes = new String[100];
    private static String[] messageIDs = new String[100];
    private static int sentCount = 0;
    private static int disregardedCount = 0;
    private static int storedCount = 0;
    private static int hashCount = 0;
    private static int idCount = 0;

    // Constructor
    public Message(String recipient, String message, int numMessagesSent) {
        this.messageID = generateMessageID();
        this.numMessagesSent = numMessagesSent;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
        this.flag = "";

        // Add to arrays
        addToMessageIDs(messageID);
        addToMessageHashes(messageHash);
    }

    // Generate random 10-digit message ID
    private String generateMessageID() {
        Random rand = new Random();
        String id = "";
        for (int i = 0; i < 10; i++) {
            id = id + rand.nextInt(10);
        }
        return id;
    }

    // Check message ID (not more than 10 chars)
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // Check recipient cell number
    public String checkRecipientCell() {
        if (recipient.length() <= 10 && recipient.startsWith("+27")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Create message hash
    public String createMessageHash() {
        String[] words = message.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        String idPrefix = messageID.substring(0, 2);
        String hash = idPrefix + ":" + numMessagesSent + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    // Check message length (not more than 250 chars)
    public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = message.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    // Send message with user choice
    public String sendMessage() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");
        System.out.print("Enter your choice (1-3): ");

        int choice = scanner.nextInt();

        if (choice == 1) {
            totalMessagesSent = totalMessagesSent + 1;
            flag = "SENT";
            addToSentMessages(message);
            storeMessageInFile("SENT");
            return "Message successfully sent.";
        } else if (choice == 2) {
            flag = "DISREGARDED";
            addToDisregardedMessages(message);
            storeMessageInFile("DISREGARDED");
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            flag = "STORED";
            addToStoredMessages(message);
            storeMessageInFile("STORED");
            return "Message successfully stored.";
        } else {
            return "Invalid option selected.";
        }
    }

    // Store message in text file
    private void storeMessageInFile(String status) {
        try {
            FileWriter writer = new FileWriter(FILE_NAME, true);

            writer.write("========================================\n");
            writer.write("MESSAGE DETAILS\n");
            writer.write("========================================\n");
            writer.write("Message ID: " + messageID + "\n");
            writer.write("Message Number: " + numMessagesSent + "\n");
            writer.write("Recipient: " + recipient + "\n");
            writer.write("Message: " + message + "\n");
            writer.write("Message Hash: " + messageHash + "\n");
            writer.write("Status: " + status + "\n");
            writer.write("========================================\n\n");

            writer.flush();
            writer.close();

        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    // Print all message details
    public String printMessages() {
        return "Message ID: " + messageID + "\n" +
                "Message Hash: " + messageHash + "\n" +
                "Recipient: " + recipient + "\n" +
                "Message: " + message + "\n" +
                "Flag: " + flag + "\n";
    }

    // ===== ARRAY METHODS (PART 3) =====
    private void addToSentMessages(String msg) {
        if (sentCount < sentMessages.length) {
            sentMessages[sentCount] = msg;
            sentCount++;
        }
    }

    private void addToDisregardedMessages(String msg) {
        if (disregardedCount < disregardedMessages.length) {
            disregardedMessages[disregardedCount] = msg;
            disregardedCount++;
        }
    }

    private void addToStoredMessages(String msg) {
        if (storedCount < storedMessages.length) {
            storedMessages[storedCount] = msg;
            storedCount++;
        }
    }

    private void addToMessageHashes(String hash) {
        if (hashCount < messageHashes.length) {
            messageHashes[hashCount] = hash;
            hashCount++;
        }
    }

    private void addToMessageIDs(String id) {
        if (idCount < messageIDs.length) {
            messageIDs[idCount] = id;
            idCount++;
        }
    }

    // Getters for arrays
    public static String[] getSentMessages() {
        return sentMessages;
    }

    public static String[] getDisregardedMessages() {
        return disregardedMessages;
    }

    public static String[] getStoredMessages() {
        return storedMessages;
    }

    public static String[] getMessageHashes() {
        return messageHashes;
    }

    public static String[] getMessageIDs() {
        return messageIDs;
    }

    public static int getSentCount() {
        return sentCount;
    }

    public static int getStoredCount() {
        return storedCount;
    }

    public static int getDisregardedCount() {
        return disregardedCount;
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    public static void resetTotalMessages() {
        totalMessagesSent = 0;
    }

    public static void resetArrays() {
        sentMessages = new String[100];
        disregardedMessages = new String[100];
        storedMessages = new String[100];
        messageHashes = new String[100];
        messageIDs = new String[100];
        sentCount = 0;
        disregardedCount = 0;
        storedCount = 0;
        hashCount = 0;
        idCount = 0;
    }

    public static void addMessageToArrays(String recipient, String msg, String flag) {
        Message temp = new Message(recipient, msg, sentCount + 1);
        temp.flag = flag;

        if (flag.equalsIgnoreCase("SENT")) {
            if (sentCount < sentMessages.length) {
                sentMessages[sentCount] = msg;
                sentCount++;
            }
        } else if (flag.equalsIgnoreCase("STORED")) {
            if (storedCount < storedMessages.length) {
                storedMessages[storedCount] = msg;
                storedCount++;
            }
        } else if (flag.equalsIgnoreCase("DISREGARD")) {
            if (disregardedCount < disregardedMessages.length) {
                disregardedMessages[disregardedCount] = msg;
                disregardedCount++;
            }
        }
    }

    // Getters
    public String getMessageID() {
        return messageID;
    }

    public int getNumMessagesSent() {
        return numMessagesSent;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getFlag() {
        return flag;
    }
}