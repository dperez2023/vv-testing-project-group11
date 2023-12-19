package upm.es;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class MakeItSafe {

    public static void main(String[] args){
        System.out.println("Hello, World!");
        help();
        //TODO: We need to map commands from the file to each of the functions
        add("www.google.com","username","password");
        display("www.google.com", "username");
        delete("www.google.com", "username");
        display("www.google.com", "username");
    }

    private static Map<String, Map<String, String>> accounts = Collections.singletonMap("", Collections.singletonMap("",""));

    public static void help() {
        System.out.println("Available commands:");
        System.out.println("help - Display available commands and password security rules");
        System.out.println("display <Optional ARGUMENT1> <Optional ARGUMENT2> - Display accounts");
        // Add explanations for other commands...

        System.out.println("\nPassword Security Rules:");
        System.out.println("Rule 1 : Less than 8 characters");
        System.out.println("Rule 2 : Has letters");
        System.out.println("Rule 3 : Has numbers");
        System.out.println("Rule 4 : Has special characters");
        System.out.println("Password levels: Really Weak, Weak, Medium, Strong");
    }

    public static void display(String website, String login) {
        if (website == null && login == null) {
            // Display all websites
            for (String web : accounts.keySet()) {
                System.out.println(web);
            }
        } else if (website != null && login == null) {
            // Display logins and passwords for a specific website
            if (accounts.containsKey(website)) {
                Map<String, String> websiteAccounts = accounts.get(website);
                for (String log : websiteAccounts.keySet()) {
                    System.out.println("<" + website + "> - login: " + log + " pwd: " + websiteAccounts.get(log));
                }
            } else {
                System.out.println("Error: No accounts found for this website.");
            }
        } else if (website != null && login != null) {
            // Display a specific login's password for a website
            if (accounts.containsKey(website)) {
                Map<String, String> websiteAccounts = accounts.get(website);
                if (websiteAccounts.containsKey(login)) {
                    System.out.println("<" + website + "> - login: " + login + " pwd: " + websiteAccounts.get(login));
                } else {
                    System.out.println("Error: No account found for this login in the specified website.");
                }
            } else {
                System.out.println("Error: No accounts found for this website.");
            }
        }
    }

    public static void add(String website, String login, String password) {
        //TODO: If website doesn't exists, add it to the map
        //TODO: If website exists, check if the login exists and if not, check if the password exists within the logins of that website, if it doesn't add
        if (accounts != null) {
            Map<String, String> loginMap = Collections.singletonMap(login, password);
            accounts = Collections.singletonMap(website, loginMap);//.put(website, loginMap);
        }
    }
    
    public static void delete(String website, String login) {
        if (website == null && login == null) {
            // Delete all accounts
            accounts.clear();
            System.out.println("All accounts deleted.");
        } else if (website != null && login == null) {
            // Delete accounts for a specific website
            if (accounts.containsKey(website)) {
                accounts.remove(website);
                System.out.println("Accounts for website '" + website + "' deleted.");
            } else {
                System.out.println("Error: No accounts found for this website.");
            }
        } else if (website != null && login != null) {
            // Delete a specific login's account for a website
            if (accounts.containsKey(website)) {
                Map<String, String> websiteAccounts = accounts.get(website);
                if (websiteAccounts.containsKey(login)) {
                    websiteAccounts.remove(login);
                    System.out.println("Account for login '" + login + "' deleted from website '" + website + "'.");
                } else {
                    System.out.println("Error: No account found for this login in the specified website.");
                }
            } else {
                System.out.println("Error: No accounts found for this website.");
            }
        }
    }

    public static void update(String website, String login, String newPassword) {
        // Update password for an existing account
        // Implement the update functionality here
    }

    public static void count(String website) {
        // Count total accounts or accounts for a specific website
        // Implement the count functionality here
    }
}