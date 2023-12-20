package upm.es;

public enum PasswordStrengthLevel {
    REALLY_WEAK(1),
    WEAK(2),
    MEDIUM(3),
    STRONG(4);

    private final int value;

    PasswordStrengthLevel(int value) {
        if (value < 1 || value > 4) {
            throw new IllegalArgumentException("Invalid value for PasswordStrengthLevel");
        }
        this.value = value;
    }

    public static PasswordStrengthLevel fromValue(int value) {
        for (PasswordStrengthLevel level : PasswordStrengthLevel.values()) {
            if (level.value == value) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid value for PasswordStrengthLevel: " + value);
    }

    public String getStringValue() {
        return this.toString();
    }
}
