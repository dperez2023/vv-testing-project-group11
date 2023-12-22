package upm.es;

import java.util.List;

public class Command {
    private final CommandType type;
    private final List<String> arguments;

    public Command(CommandType type, List<String> arguments) {
        this.type = type;
        this.arguments = arguments;
    }

    public CommandType getType() {
        return type;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public String getString() {
        return String.format("%s %s",type.toString(), arguments.toString());
    }
}