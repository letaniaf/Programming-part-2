package login2_0;

public class Login2_0Test {

    private String username;
    private String password;
    private String cellPhone;

    public Login2_0Test(String username, String password, String cellPhone) {
        this.username = username;
        this.password = password;
        this.cellPhone = cellPhone;
    }

    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity() {
        if (password.length() < 8) return false;

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasCapital = true;
            else if (Character.isDigit(c)) hasNumber = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    public boolean checkCellPhoneNumber() {
        return cellPhone.matches("^\\+27[0-9]{9}$");
    }

    public String registerUser() {
        if (!checkUserName())
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";

        if (!checkPasswordComplexity())
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";

        if (!checkCellPhoneNumber())
            return "Cell phone number incorrectly formatted or does not contain international code.";

        return "User successfully registered.";
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return enteredUsername.equals(username) && enteredPassword.equals(password);
    }

    public String returnLoginStatus(boolean status) {
        return status
                ? "Welcome user, it is great to see you again."
                : "Username or password incorrect, please try again.";
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