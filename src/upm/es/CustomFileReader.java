package upm.es;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomFileReader {
    static List<Command> read(String filePath) {
        List<Command> commandsList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                List<String> rawArguments = new ArrayList<>();
                List<String> arguments = new ArrayList<>();

                String command = parts[0].trim();
                rawArguments.add((parts.length > 1) ? parts[1].trim() : "");
                rawArguments.add((parts.length > 2) ? parts[2].trim() : "");
                rawArguments.add((parts.length > 3) ? parts[3].trim() : "");

                for (String argument : rawArguments) {
                    if(!argument.isEmpty()) {
                        arguments.add(argument);
                    }
                }

                Command newCommand = new Command(CommandType.fromString(command), arguments);

                if(parts.length <= 3) {
                    commandsList.add(newCommand);
                } else {
                    System.out.println("Command Error: Arguments mismatch (There are more arguments than supported by the program");
                    for(int i = 0; i < parts.length; i++) {
                        if(i > 3) {
                            System.out.println(String.format("Argument: %s",parts[i]));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commandsList;
    }
}
