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