package login2_0;

public class Login2_0 {

    private String username;
    private String password;
    private String cellPhone;
    private boolean isLoggedIn = false;

    public Login2_0(String username, String password, String cellPhone) {
        this.username = username;
        this.password = password;
        this.cellPhone = cellPhone;
    }

    // Username check
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    // Password check
    public boolean checkPasswordComplexity() {
        if (password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    // Cell phone check
    public boolean checkCellPhoneNumber() {
        return cellPhone.matches("^\\+27[0-9]{9}$");
    }

    // Register
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        return "User successfully registered.";
    }

    // Login check
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        boolean success = enteredUsername.equals(username) && enteredPassword.equals(password);
        if (success) {
            isLoggedIn = true;
        }
        return success;
    }

    // Login message
    public String returnLoginStatus(boolean status) {
        if (status) {
            return "Welcome user, it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Check if user is logged in
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}