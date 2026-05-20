package Login2_0;

import java.util.Random;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;

    private static int totalMessages = 0;

    // Constructor
    public Message(int messageNumber, String recipient, String message) {

        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;

        generateMessageID();
    }

    Message(String recipient, String messageText, int messageCount) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Generate random 10-digit ID
    private void generateMessageID() {

        Random random = new Random();

        long number = 1000000000L + 
                (long)(random.nextDouble() * 9000000000L);

        messageID = String.valueOf(number);
    }

    // Check Message ID
    public boolean checkMessageID() {

        return messageID.length() <= 10;
    }

    // Check recipient cell
    public String checkRecipientCell() {

        if (recipient.matches("^\\+27[0-9]{9}$")) {

            return "Cell phone number successfully captured.";

        } else {

            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Check message length
    public String checkMessageLength() {

        if (message.length() <= 250) {

            return "Message ready to send.";

        } else {

            int excess = message.length() - 250;

            return "Message exceeds 250 characters by " 
                    + excess + ", please reduce the size.";
        }
    }

    // Create message hash
    public String createMessageHash() {

        String[] words = message.split(" ");

        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        return messageID.substring(0, 2)
                + ":"
                + messageNumber
                + ":"
                + firstWord.toUpperCase()
                + lastWord.toUpperCase();
    }

    // Sent message option
    public String sentMessage(int option) {

        switch (option) {

            case 1:
                totalMessages++;
                return "Message successfully sent.";

            case 2:
                return "Press 0 to delete the message.";

            case 3:
                return "Message successfully stored.";

            default:
                return "Invalid option.";
        }
    }

    // Print message details
    public String printMessages() {

        return "Message ID: " + messageID
                + "\nMessage Hash: " + createMessageHash()
                + "\nRecipient: " + recipient
                + "\nMessage: " + message;
    }

    // Return total messages
    public static int returnTotalMessages() {

        return totalMessages;
    }

    // Getters
    public String getMessageID() {
        return messageID;
    }

    String getMessageHash() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    String sendMessage() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
