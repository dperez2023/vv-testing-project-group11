package upm.es;

public class Logger {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String COMMAND = "\u001B[34m";

    public static void error(String message) {
        System.out.println(RED + "[ERROR] " + message + RESET);
    }

    public static void success(String message) {
        System.out.println(GREEN + "[SUCCESS] " + message + RESET);
    }

    public static void command(String message) {
        System.out.println(COMMAND + "[COMMAND] " + message + RESET);
    }
}