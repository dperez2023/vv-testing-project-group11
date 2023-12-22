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

                String command = parts[0].trim();
                String arg1 = (parts.length > 1) ? parts[1].trim() : "";
                String arg2 = (parts.length > 2) ? parts[2].trim() : "";
                String arg3 = (parts.length > 3) ? parts[3].trim() : "";

                Command newCommand = new Command(CommandType.fromString(command), Arrays.asList(arg1,arg2,arg3));
                commandsList.add(newCommand);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commandsList;
    }
}
