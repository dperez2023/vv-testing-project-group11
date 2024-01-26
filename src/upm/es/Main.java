package upm.es;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

class MakeItSafe {
    private static Account accounts = new Account();
    private static List<Command> commands = new ArrayList<>();

    public static void main(String[] args){
        if (args.length != 1) {
            Logger.error("File path needs to be added as a parameter when executing the program, for example: java -jar MakeItSafe.jar <file_path>");
            System.exit(1);
        } else {
            Logger.success(String.format("Using filepath: '%s'", args[0]));
        }

        commands = CustomFileReader.read(args[0]);
        for (Command command : commands) {
            executeCommand(command);
        }
    }

    private static void executeCommand(Command command) {
        List<String> arguments = command.getArguments();
        String arg1 = arguments.size() >= 1 ? arguments.get(0) : null;
        String arg2 = arguments.size() >= 2 ? arguments.get(1) : null;
        String arg3 = arguments.size() >= 3 ? arguments.get(2) : null;

        if(arguments.size() <= command.getType().getArgumentsSize()) {
            System.out.println("");
            Logger.command(command.getString());
            switch (command.getType()) {
                case help -> help();
                case display -> display(arg1, arg2);
                case add -> add(arg1, arg2, arg3);
                case count -> count(arg1);
                case update -> update(arg1, arg2, arg3);
                case delete -> delete(arg1, arg2);
                default -> {
                }
            }
            Logger.command("---------------------------------------------------");
        } else {
            System.out.println("Command Error: Arguments mismatch (There are more arguments than required by the command:");
            System.out.println(String.format("Command %s Arguments: %s %s %s",command.getType(),arg1,arg2,arg3));
        }
    }

    public static void help() {
        System.out.println("\nInput File:");
        System.out.println("\nFile path needs to be added as a parameter when executing the program, for example:");
        System.out.println("\njava MakeItSafe path/to/your/file.txt");
        displayCommands();
        displayPasswordRules();
    }

    public static void displayCommands() {
        System.out.println("\nCommands:");
        System.out.println("help: Display available commands and password security rules");
        System.out.println("display <Optional WEBSITE> <Optional USERNAME>: Display all USERNAMES");
        System.out.println("add <WEBSITE> <USERNAME> <PASSWORD>: Add USERNAME with PASSWORD to WEBSITE");
        System.out.println("update <WEBSITE> <USERNAME> <PASSWORD>: Update USERNAME with PASSWORD to WEBSITE");
        System.out.println("delete <WEBSITE> <USERNAME>: Delete USERNAME for the given WEBSITE");
    }

    public static void displayPasswordRules() {
        System.out.println("\nPassword Security Rules:");
        System.out.println("Rule 1 : Less than 8 characters");
        System.out.println("Rule 2 : Has letters");
        System.out.println("Rule 3 : Has numbers");
        System.out.println("Rule 4 : Has special characters");
        System.out.println("Password levels: Really Weak (1/4), Weak (2/4), Medium(3/4), Strong (4/4)");
    }

    public static void display(String url, String username) {
        Website newWebsite = new Website(url);
        Login newLogin = new Login(username,null);

        Website foundWebsite = accounts.getWebsite(newWebsite);

        if(!isSafeToContinue(newWebsite, newLogin, true)) {
            Logger.error("DISPLAY: Not safe to continue");
            return;
        }

        if(foundWebsite != null) {
            Login foundLogin = foundWebsite.getLogin(newLogin);
            if(foundLogin == null) {
                //Fallback, display all usernames
                foundWebsite.displayUsernames();
            } else {
                //Specific argument, website and login exist
                foundWebsite.displayUsername(foundLogin);
            }
        } else if(!newWebsite.isValid()) {
            accounts.displayWebsites();
        } else {
            Logger.error("DISPLAY: Website doesn't exist");
        }
    }

    public static void delete(String url, String username) {
        Website newWebsite = new Website(url);
        Login newLogin = new Login(username,null);

        Website foundWebsite = accounts.getWebsite(newWebsite);

        if(!isSafeToContinue(newWebsite, newLogin, true)) {
            Logger.error("DELETE: ERROR");
            return;
        }

        if(foundWebsite == null) {
            accounts.removeWebsites();
        } else {
            Login foundLogin = foundWebsite.getLogin(newLogin);
            if(foundLogin == null) {
                accounts.removeWebsite(newWebsite);
            } else {
                if(foundWebsite.removeUsername(newLogin)) {
                    String message = String.format("Deleted successfully: Username %s has been removed", username);
                    //System.out.println(message);
                } else {
                    String message = String.format("Delete error: Username %s failed to remove", username);
                    System.out.println(message);
                }
            }
        }
    }

    public static void update(String url, String username, String password) {
        Website newWebsite = new Website(url);
        Login newLogin = new Login(username,password);

        Website foundWebsite = accounts.getWebsite(newWebsite);

        if(!isSafeToContinue(newWebsite, null, false)) {
            Logger.error("ADD: ERROR");
            return;
        }

        if (foundWebsite != null) {
            if(foundWebsite.getLogin(newLogin) != null) {
                if(foundWebsite.passwordExists(newLogin)) {
                    String message = String.format("Update error: Username %s can't be updated, password already exists.", newLogin.getUsername());
                    System.out.println(message);
                } else {
                    if(foundWebsite.passwordValidSecurityStrength(newLogin)) {
                        foundWebsite.updateLogin(newLogin);
                    } else {
                        String message = String.format("Update error: Username %s can't be updated, the password is not secure enough.", newLogin.getUsername());
                        System.out.println(message);
                    }
                }
            } else {
                String message = String.format("Update: Username %s doesn't exist in Website %s", newLogin.getUsername(), foundWebsite.getUrl());
                Logger.error(message);
            }
        } else {
            String message = String.format("Update: Website %s doesn't exist", newWebsite.getUrl());
            Logger.error(message);
        }
    }

    public static void add(String url, String username, String password) {
        Website newWebsite = new Website(url);
        Login newLogin = new Login(username,password);

        Website foundWebsite = accounts.getWebsite(newWebsite);

        if(!isSafeToContinue(newWebsite, null, false)) {
            Logger.error("ADD: ERROR");
            return;
        }

        if (foundWebsite == null) {
            accounts.addWebsite(newWebsite);
            Website addedWebsite = accounts.getWebsite(newWebsite);
            if(addedWebsite.passwordValidSecurityStrength(newLogin)) {
                addedWebsite.addLogin(newLogin);
            } else {
                String message = String.format("Adding error: Username %s can't be added, is not secure enough.", newLogin.getUsername());
                System.out.println(message);
            }
        } else {
            if(foundWebsite.getLogin(newLogin) == null) {
                if(foundWebsite.passwordExists(newLogin)) {
                    String message = String.format("Adding error: Username %s can't be added, password already exists.", newLogin.getUsername());
                    System.out.println(message);
                } else {
                    if(foundWebsite.passwordValidSecurityStrength(newLogin)) {
                        foundWebsite.addLogin(newLogin);
                    } else {
                        String message = String.format("Adding error: Username %s can't be added, is not secure enough.", newLogin.getUsername());
                        System.out.println(message);
                    }
                }
            } else {
                String message = String.format("Adding error: Username %s can't be added. Already exist", newLogin.getUsername());
                System.out.println(message);
            }
        }
    }

    public static void count(String url) {
        Website newWebsite = new Website(url);
        Website foundWebsite = accounts.getWebsite(newWebsite);

        if(!isSafeToContinue(newWebsite, null, true)) {
            Logger.error("COUNT: ERROR");
            return;
        }

        if(foundWebsite == null) {
            //No arguments, fallback to counting all websites
            accounts.countWebsites();
        } else {
            //One argument, count specific website
            foundWebsite.countUsernames();
        }
    }

    private static Boolean isSafeToContinue(Website website, Login login, Boolean supportNoArguments) {
        Boolean websiteValid = (website != null && website.isValid());
        Boolean loginValid = (login != null && login.isValid());

        if (supportNoArguments) {
            return !(websiteValid && loginValid);
        } else {
            if (websiteValid) {
                if(website.getUrl().isEmpty() || !isWebsiteValid(website.getUrl())) {
                    String message = "Not safe: WEBSITE doesn't have the right format or is empty";
                    Logger.error(message);
                }
                return !(website.getUrl().isEmpty() && !isWebsiteValid(website.getUrl()));
            }

            if (loginValid) {
                if(login.getUsername().isEmpty() || login.getPassword().isEmpty() || !isEmailValid(login.getUsername())) {
                    String message = "Not safe: LOGIN doesn't have the right format or is empty";
                    Logger.error(message);
                }

                return !(login.getUsername().isEmpty() && login.getPassword().isEmpty() && !isEmailValid(login.getUsername()));
            }
        }

        return false;
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.matches(emailRegex, email);
    }

    public static boolean isWebsiteValid(String website) {
        String websiteRegex = "^(https?|ftp)://(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+(\\S*)?$";
        return Pattern.matches(websiteRegex, website);
    }
}