package upm.es;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
            switch (command.getType()) {
                case help:
                    help();
                    break;
                case display:
                    display(arg1,arg2);
                case add:
                    add(arg1,arg2,arg3);
                case count:
                    count(arg1);
                case update:
                    update(arg1,arg2,arg3);
                case delete:
                    delete(arg1,arg2);
                default:
                    //Silent break, unknown commands are simply ignored
                    break;
            }
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
        System.out.println("------- DISPLAY -------");
        //TODO: Simplify. Display and count have the same logic
        Website newWebsite = new Website(url);
        Login newLogin = new Login(username,null);

        Website foundWebsite = accounts.getWebsite(newWebsite);

        if(!isSafeToContinue(newWebsite, newLogin)) {
            Logger.error("DISPLAY: ERROR");
            return;
        }

        if(foundWebsite == null) {
            //No arguments, fallback to displaying all websites
            accounts.displayWebsites();
        } else {
            Login foundLogin = foundWebsite.getLogin(newLogin);
            if(foundLogin == null) {
                //Fallback, display all usernames
                foundWebsite.displayUsernames();
            } else {
                //Specific argument, website and login exist
                foundWebsite.displayUsername(foundLogin);
            }
        }
    }

    public static void delete(String url, String username) {
        System.out.println("------- DELETE -------");
        Website newWebsite = new Website(url);
        Login newLogin = new Login(username,null);

        Website foundWebsite = accounts.getWebsite(newWebsite);

        if(!isSafeToContinue(newWebsite, newLogin)) {
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
                    System.out.println(message);
                } else {
                    String message = String.format("Delete error: Username %s failed to remove", username);
                    System.out.println(message);
                }
            }
        }
    }

    public static void update(String url, String username, String password) {
        System.out.println("------- UPDATE -------");
        //TODO: Pending
    }

    public static void add(String url, String username, String password) {
        System.out.println("------- ADD -------");
        Website newWebsite = new Website(url);
        Login newLogin = new Login(username,password);

        Website foundWebsite = accounts.getWebsite(newWebsite);

        if(!isSafeToContinue(newWebsite, null)) {
            Logger.error("ADD: ERROR");
            return;
        }

        if (foundWebsite == null) {
            //If the website is not found
            accounts.addWebsite(newWebsite); //Adds website because it doesn't exist

            accounts.getWebsite(newWebsite) //Adds login to the recently added website
                    .addLogin(newLogin);
        } else {
            //Website is found
            if(foundWebsite.getLogin(newLogin) == null) {
                //If the login doesn't exist, check if the password exists within the list of logins of that website
                if(foundWebsite.passwordExists(newLogin)) {
                    //Show message that the password exists and can't be added
                    String message = String.format("Adding error: Username %s can't be added.", newLogin.getUsername());
                    System.out.println(message);
                } else {
                    //The password doesn't exist, we can continue checking if it can be added
                    if(foundWebsite.passwordValidSecurityStrength(newLogin)) {
                        //If the password matches security requirements, add the login
                        if(foundWebsite.addLogin(newLogin)) {
                            //If adding didn't fail, show success message
                            String message = String.format("Adding successfully: Username %s has been added.", newLogin.getUsername());
                            System.out.println(message);
                        } else {
                            //If adding did fail, show error message
                            String message = String.format("Adding error: Username %s can't be added.", newLogin.getUsername());
                            System.out.println(message);
                        }
                    } else {
                        //If the password strength validation failed, show error
                        String message = String.format("Adding error: Username %s can't be added.", newLogin.getUsername());
                        System.out.println(message);
                    }
                }
            } else {
                //If the login already exists, we can't add it again, show error
                String message = String.format("Adding error: Username %s can't be added.", newLogin.getUsername());
                System.out.println(message);
            }
        }
    }

    public static void count(String url) {
        System.out.println("------- COUNT -------");
        Website newWebsite = new Website(url);
        Website foundWebsite = accounts.getWebsite(newWebsite);

        if(!isSafeToContinue(newWebsite, null)) {
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

    private static Boolean isSafeToContinue(Website website, Login login) {
        if (website != null && website.getUrl() != null) {
            if(website.getUrl().isEmpty()) {
                String message = String.format("Not safe: WEBSITE doesn't have the right format or is empty");
                Logger.error(message);
            }
            return !website.getUrl().isEmpty();
        }

        if (login != null && login.getUsername() != null && login.getPassword() != null) {
            if(login.getUsername().isEmpty() || login.getPassword().isEmpty()) {
                String message = String.format("Not safe: LOGIN doesn't have the right format or is empty");
                Logger.error(message);
            }

            return !(login.getUsername().isEmpty() && login.getPassword().isEmpty());
        }

        return true;
    }
}