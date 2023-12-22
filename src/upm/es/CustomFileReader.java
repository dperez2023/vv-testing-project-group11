package upm.es;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CustomFileReader {
    static List<Command> read(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String command = parts[0].trim();

                String arg1 = (parts.length > 1) ? parts[1].trim() : "";
                String arg2 = (parts.length > 2) ? parts[2].trim() : "";
                String arg3 = (parts.length > 3) ? parts[3].trim() : "";

                System.out.println("Command: " + command);
                System.out.println("Argument 1: " + arg1);
                System.out.println("Argument 2: " + arg2);
                System.out.println("Argument 3: " + arg3);

                return Collections.emptyList();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
