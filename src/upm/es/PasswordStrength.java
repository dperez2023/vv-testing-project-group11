package upm.es;

public class PasswordStrength {
    String lessThan8CharsRegex = ".{1,7}";
    String hasLettersRegex = ".*[a-zA-Z].*";
    String hasNumbersRegex = ".*\\d.*";
    String hasSpecialCharsRegex = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*";

    private static PasswordStrength instance;

    private PasswordStrength() { }

    public static synchronized PasswordStrength getInstance() {
        if (instance == null) { instance = new PasswordStrength(); }
        return instance;
    }

    public Integer checkPassword(String value) {
        Integer count = 0;

        if (value != null) {
            count += (value.matches(lessThan8CharsRegex) ? 1 : 0);
            count += (value.matches(hasLettersRegex) ? 1 : 0);
            count += (value.matches(hasSpecialCharsRegex) ? 1 : 0);
        }

        return count;
    }
}


