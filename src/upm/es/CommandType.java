package upm.es;

public enum CommandType {
    help,
    display,
    add,
    count,
    update,
    delete,
    unknown;

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
        }

        return -1;
    }

    public static CommandType fromString(String value) {
        switch (value.toLowerCase()) {
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