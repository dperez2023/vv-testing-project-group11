package upm.es;

public enum CommandType {
    help("help"),
    display("display"),
    add("add"),
    count("count"),
    update("update"),
    delete("delete"),
    unknown("unknown");

    private String type;

    CommandType(String type) {
        this.type = type;
    }

    public Integer getArgumentsSize() {
        switch (this) {
            case help -> {
                return 0;
            }
            case display, delete -> {
                return 2;
            }
            case add, update -> {
                return 3;
            }
            case count -> {
                return 1;
            }
            default -> {
                return -1;
            }
        }
    }

    public static CommandType fromString(String value) {
        switch (value) {
            case "help":
                return help;
            case "display":
                return display;
            case "add":
                return add;
            case "count":
                return count;
            case "update":
                return update;
            case "delete":
                return delete;
            default:
                System.out.println("Unknown command type: " + value);
                return unknown;
        }
    }
}