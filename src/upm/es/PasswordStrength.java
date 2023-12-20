package upm.es;

public class PasswordStrength {
    /*Really Weak : Only one rule
    Weak : Two of the four rules
    Medium : Three Rules
    Strong : The four rules*/

    String lessThan8CharsRegex = ".{1,7}";
    String hasLettersRegex = ".*[a-zA-Z].*";
    String hasNumbersRegex = ".*\\d.*";
    String hasSpecialCharsRegex = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*";

    public Integer checkPassword(String value) {
        Integer count = 0;

        count += (value.matches(lessThan8CharsRegex) ? 1 : 0);
        count += (value.matches(hasLettersRegex) ? 1 : 0);
        count += (value.matches(hasNumbersRegex) ? 1 : 0);
        count += (value.matches(hasSpecialCharsRegex) ? 1 : 0);


    }
}


