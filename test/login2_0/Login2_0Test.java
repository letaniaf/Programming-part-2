package login2_0;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class Login2_0Test {

    private Login2_0 validUser;
    private Login2_0 invalidUser;
    private Message testMessage1;
    private Message testMessage2;
    private Message testMessage3;
    private Message testMessage4;
    private Message testMessage5;

    @Before
    public void setUp() {
        // Part 1 test data
        validUser = new Login2_0("kyl_1", "Pass@1234", "+27718693002");
        invalidUser = new Login2_0("kyle", "pass", "08575975889");

        // Part 2 test data
        testMessage1 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 1);
        testMessage2 = new Message("08575975889", "Hi Keegan, did you receive the payment?", 2);

        // Part 3 test data
        testMessage3 = new Message("+27834557896", "Did you get the cake?", 3);
        testMessage4 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 4);
        testMessage5 = new Message("+27834484567", "Yohoooo, I am at your gate.", 5);

        Message.resetTotalMessages();
        Message.resetArrays();
    }

    // ===== PART 1 TESTS =====
    @Test
    public void testCheckUserName() {
        assertTrue(validUser.checkUserName());
        assertFalse(invalidUser.checkUserName());
    }

    @Test
    public void testCheckPasswordComplexity() {
        assertTrue(validUser.checkPasswordComplexity());
        assertFalse(invalidUser.checkPasswordComplexity());
    }

    @Test
    public void testCheckCellPhoneNumber() {
        assertTrue(validUser.checkCellPhoneNumber());
        assertFalse(invalidUser.checkCellPhoneNumber());
    }

    @Test
    public void testRegisterUser() {
        assertEquals("User successfully registered.", validUser.registerUser());
        assertNotEquals("User successfully registered.", invalidUser.registerUser());
    }

    @Test
    public void testLoginUser() {
        assertTrue(validUser.loginUser("kyl_1", "Pass@1234"));
        assertFalse(validUser.loginUser("wrong", "wrong"));
    }

    @Test
    public void testReturnLoginStatus() {
        assertEquals("Welcome user, it is great to see you again.",
                validUser.returnLoginStatus(true));
        assertEquals("Username or password incorrect, please try again.",
                validUser.returnLoginStatus(false));
    }

    // ===== PART 2 TESTS =====
    @Test
    public void testMessageLengthSuccess() {
        assertEquals("Message ready to send.", testMessage1.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        String longMessage = "";
        for (int i = 0; i < 260; i++) {
            longMessage = longMessage + "A";
        }
        Message msg = new Message("+27718693002", longMessage, 1);
        String result = msg.checkMessageLength();
        assertTrue(result.contains("exceeds 250 characters by 10"));
    }

    @Test
    public void testRecipientNumberSuccess() {
        assertEquals("Cell phone number successfully captured.", testMessage1.checkRecipientCell());
    }

    @Test
    public void testRecipientNumberFailure() {
        Message msg = new Message("08575975889", "Test", 1);
        assertTrue(msg.checkRecipientCell().contains("incorrectly formatted"));
    }

    @Test
    public void testMessageHashCorrect() {
        assertNotNull(testMessage1.getMessageHash());
        assertTrue(testMessage1.getMessageHash().contains(":"));
        assertEquals(testMessage1.getMessageHash(), testMessage1.getMessageHash().toUpperCase());
    }

    @Test
    public void testMessageIDGenerated() {
        assertNotNull(testMessage1.getMessageID());
        assertEquals(10, testMessage1.getMessageID().length());
        assertTrue(testMessage1.checkMessageID());
    }

    @Test
    public void testReturnTotalMessages() {
        assertEquals(0, Message.returnTotalMessages());
        int total = Message.returnTotalMessages();
        assertTrue(total >= 0);
    }

    @Test
    public void testMessageHashLoop() {
        String[] testMessages = {
            "Hi Mike, can you join us for dinner tonight?",
            "Hi Keegan, did you receive the payment?",
            "Hello World",
            "This is a test message"
        };

        for (int i = 0; i < testMessages.length; i++) {
            Message msg = new Message("+27718693002", testMessages[i], i + 1);
            String hash = msg.getMessageHash();
            assertNotNull(hash);
            assertTrue(hash.length() > 0);
            assertTrue(hash.contains(":"));
        }
    }

    // ===== PART 3 TESTS =====
    @Test
    public void testSentMessagesArrayPopulated() {
        Message.resetArrays();
        Message.addMessageToArrays("+27834557896", "Did you get the cake?", "SENT");
        Message.addMessageToArrays("0838884567", "It is dinner time!", "SENT");

        String[] sentMessages = Message.getSentMessages();
        int sentCount = Message.getSentCount();

        boolean foundCake = false;
        boolean foundDinner = false;

        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].contains("Did you get the cake?")) {
                foundCake = true;
            }
            if (sentMessages[i].contains("It is dinner time!")) {
                foundDinner = true;
            }
        }

        assertTrue("Message 'Did you get the cake?' should be in sent messages", foundCake);
        assertTrue("Message 'It is dinner time!' should be in sent messages", foundDinner);

        assertEquals("\"Did you get the cake?\", \"It is dinner time!\"",
                "\"" + sentMessages[0] + "\", \"" + sentMessages[1] + "\"");
    }

    @Test
    public void testDisplayLongestMessage() {
        Message.resetArrays();
        Message.addMessageToArrays("+27834557896", "Did you get the cake?", "SENT");
        Message.addMessageToArrays("+27838884567", "Where are you? You are late! I have asked you to be on time.", "STORED");
        Message.addMessageToArrays("+27834484567", "Yohoooo, I am at your gate.", "DISREGARD");
        Message.addMessageToArrays("0838884567", "It is dinner time!", "SENT");

        String longest = MessageManager.displayLongestStoredMessage();
        String expected = "Where are you? You are late! I have asked you to be on time.";
        assertEquals("Longest message should be: " + expected, expected, longest);
    }

    @Test
    public void testSearchByMessageID() {
        Message[] allMessages = {testMessage1, testMessage2, testMessage3, testMessage4, testMessage5};
        String result = MessageManager.searchByMessageID(testMessage4.getMessageID(), allMessages, 5);
        assertTrue(result.contains("Where are you?"));
    }

    @Test
    public void testSearchByRecipient() {
        Message msg1 = new Message("+27838884567", "Where are you? You are late!", 1);
        Message msg2 = new Message("+27838884567", "Ok, I am leaving without you", 2);
        Message[] allMessages = {msg1, msg2};
        String result = MessageManager.searchByRecipient("+27838884567", allMessages, 2);
        assertTrue(result.contains("Where are you?"));
        assertTrue(result.contains("Ok, I am leaving without you"));
    }

    @Test
    public void testDeleteMessageByHash() {
        Message testMsg = new Message("+27834557896", "Delete this message", 1);
        Message[] allMessages = {testMsg};
        String hashToDelete = testMsg.getMessageHash();
        String result = MessageManager.deleteMessageByHash(hashToDelete, allMessages, 1);
        assertTrue(result.contains("successfully deleted"));
        assertTrue(result.contains("Delete this message"));
    }

    @Test
    public void testStoredMessagesArrayPopulated() {
        Message.resetArrays();
        Message.addMessageToArrays("+27838884567", "Where are you? You are late!", "STORED");
        Message.addMessageToArrays("+27838884567", "Ok, I am leaving without you", "STORED");

        String[] storedMessages = Message.getStoredMessages();
        int storedCount = Message.getStoredCount();

        boolean foundLate = false;
        boolean foundLeaving = false;

        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i].contains("Where are you?")) {
                foundLate = true;
            }
            if (storedMessages[i].contains("Ok, I am leaving without you")) {
                foundLeaving = true;
            }
        }

        assertTrue("Stored message about being late should exist", foundLate);
        assertTrue("Stored message about leaving should exist", foundLeaving);
    }

    @Test
    public void testDisplayReport() {
        Message[] allMessages = {testMessage1, testMessage2, testMessage3, testMessage4, testMessage5};
        // Just test that it runs without error
        MessageManager.displayReport(allMessages, 5);
        assertTrue(true);
    }
}
/*
I also recieved help from my tutors ofentse and mpho
*/
/*
    Code Attribution:
    *Source: Oracle Java Documentation | Stack Overflow
    *Prompt Used: How do I check if a string contains a character and limit its length in Java?
    *Date Accessed: 14 April 2026
    *Description: Used String methods such as contains() and length() to validate username format.
    *Modification: Combined two conditions (underscore check + length restriction) into one boolean return statement.
    *URL: https://docs.oracle.com/javase/8/docs/api/java/lang/String.html
    *Author: Calvin Ern
*/
/*
    Code Attribution:
    *Source: Oracle Java Documentation | GeeksforGeeks
    *Prompt Used: How do I check for uppercase letters, numbers, and special characters in a Java string?
    *Date Accessed: 14 April 2026
    *Description: Used Character.isUpperCase, Character.isDigit, and looping through string characters.
    *Modification: Simplified logic into three boolean flags for readability in a beginner-level program.
    *URL: https://docs.oracle.com/javase/8/docs/api/java/lang/Character.html
    *Author: Calvin Ern
*/
/*
    Code Attribution:
    *Source: Oracle Java Regex Documentation | Stack Overflow
    *Prompt Used: How do I validate a South African phone number using regex in Java?
    *Date Accessed: 14 April 2026
    *Description: Used regular expression ^\\+27[0-9]{9}$ to validate international SA numbers.
    *Modification: Adjusted regex to match assignment requirement (international code + fixed length format).
    *URL: https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
    *Author: Calvin Ern
*/