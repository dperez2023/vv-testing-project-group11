package upm.es;

public enum CommandType {
    help,
    display,
    add,
    count,
    update,
    delete;

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
}