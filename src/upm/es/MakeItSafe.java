package upm.es;

import java.util.*;

public class MakeItSafe {
    private Map<String, Map<String, String>> accounts; // Map<Website, Map<Login, Password>>

    public MakeItSafe() {
        accounts = new HashMap<>();
    }

    public void help() {
        System.out.println("Available commands:");
        System.out.println("help - Display available commands and password security rules");
        System.out.println("display <Optional ARGUMENT1> <Optional ARGUMENT2> - Display accounts");
        System.out.println("add <ARGUMENT1> <ARGUMENT2> <ARGUMENT3> - Add an account");
        System.out.println("delete <Optional ARGUMENT1> <Optional ARGUMENT2> - Delete accounts");
        System.out.println("update <ARGUMENT1> <ARGUMENT2> <ARGUMENT3> - Update an account's password");
        System.out.println("count <Optional ARGUMENT1> - Count accounts");
        System.out.println("exit - Exit the program");
        System.out.println("\nPassword Security Rules:");
        System.out.println("Rule 1 : Less than 8 characters");
        System.out.println("Rule 2 : Has letters");
        System.out.println("Rule 3 : Has numbers");
        System.out.println("Rule 4 : Has special characters");
        System.out.println("Password levels: Really Weak, Weak, Medium, Strong");
    }

    public void display(String website, String login) {
        if (website == null && login == null) {
            for (String web : accounts.keySet()) {
                System.out.println(web);
            }
        } else if (website != null && login == null) {
            if (accounts.containsKey(website)) {
                Map<String, String> websiteAccounts = accounts.get(website);
                for (String log : websiteAccounts.keySet()) {
                    System.out.println("<" + website + "> - login: " + log + " pwd: " + websiteAccounts.get(log));
                }
            } else {
                System.out.println("Error: No accounts found for this website.");
            }
        } else if (website != null && login != null) {
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

    public void add(String website, String login, String password) {
        if (password.length() < 8 || !password.matches(".*[a-zA-Z].*") ||
                !password.matches(".*\\d.*") || !password.matches(".*[^a-zA-Z0-9].*")) {
            System.out.println("Error: Password does not meet security requirements.");
            return;
        }

        if (!accounts.containsKey(website)) {
            accounts.put(website, new HashMap<>());
        }

        Map<String, String> websiteAccounts = accounts.get(website);
        if (websiteAccounts.containsKey(login)) {
            System.out.println("Error: An account with this login already exists for the website.");
        } else {
            websiteAccounts.put(login, password);
            System.out.println("Account added successfully.");
            checkPasswordStrength(password);
        }
    }

    public void delete(String website, String login) {
        if (website == null && login == null) {
            accounts.clear();
            System.out.println("All accounts deleted.");
        } else if (website != null && login == null) {
            if (accounts.containsKey(website)) {
                accounts.remove(website);
                System.out.println("Accounts for website '" + website + "' deleted.");
            } else {
                System.out.println("Error: No accounts found for this website.");
            }
        } else if (website != null && login != null) {
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

    public void update(String website, String login, String newPassword) {
        if (newPassword.length() < 8 || !newPassword.matches(".*[a-zA-Z].*") ||
                !newPassword.matches(".*\\d.*") || !newPassword.matches(".*[^a-zA-Z0-9].*")) {
            System.out.println("Error: New password does not meet security requirements.");
            return;
        }

        if (accounts.containsKey(website)) {
            Map<String, String> websiteAccounts = accounts.get(website);
            if (websiteAccounts.containsKey(login)) {
                String oldPassword = websiteAccounts.get(login);
                if (!oldPassword.equals(newPassword)) {
                    websiteAccounts.put(login, newPassword);
                    System.out.println("Password updated successfully.");
                    checkPasswordStrength(newPassword);
                } else {
                    System.out.println("Error: New password matches the old password.");
                }
            } else {
                System.out.println("Error: No account found for this login in the specified website.");
            }
        } else {
            System.out.println("Error: No accounts found for this website.");
        }
    }

    public void count(String website) {
        if (website == null) {
            int totalAccounts = 0;
            for (Map<String, String> websiteAccounts : accounts.values()) {
                totalAccounts += websiteAccounts.size();
            }
            System.out.println("Total accounts: " + totalAccounts);
        } else {
            if (accounts.containsKey(website)) {
                int websiteAccountsCount = accounts.get(website).size();
                System.out.println("Accounts for website '" + website + "': " + websiteAccountsCount);
            } else {
                System.out.println("Error: No accounts found for this website.");
            }
        }
    }

    private void checkPasswordStrength(String password) {
        int rulesMet = 0;
        if (password.length() >= 8) rulesMet++;
        if (password.matches(".*[a-zA-Z].*")) rulesMet++;
        if (password.matches(".*\\d.*")) rulesMet++;
        if (password.matches(".*[^a-zA-Z0-9].*")) rulesMet++;

        String strength;
        switch (rulesMet) {
            case 1:
                strength = "Really Weak";
                break;
            case 2:
                strength = "Weak";
                break;
            case 3:
                strength = "Medium";
                break;
            case 4:
                strength = "Strong";
                break;
            default:
                strength = "Unknown";
        }
        System.out.println("Password strength: " + strength);
    }

    public static void main(String[] args) {
        MakeItSafe safeSystem = new MakeItSafe();
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Welcome to Make It Safe!");
        
        do {
            System.out.println("\nEnter a command (Type 'help' for available commands):");
            command = scanner.nextLine();

            String[] parts = command.split(" ");
            String action = parts[0].toLowerCase();

            switch (action) {
                case "help":
                    safeSystem.help();
                    break;
                case "display":
                    if (parts.length == 1) {
                        safeSystem.display(null, null);
                    } else if (parts.length == 2) {
                        safeSystem.display(parts[1], null);
                    } else if (parts.length == 3) {
                        safeSystem.display(parts[1], parts[2]);
                    } else {
                        System.out.println("Invalid arguments for display command.");
                    }
                    break;
                case "add":
                    if (parts.length == 4) {
                        safeSystem.add(parts[1], parts[2], parts[3]);
                    } else {
                        System.out.println("Invalid arguments for add command.");
                    }
                    break;
                case "delete":
                    if (parts.length == 1) {
                        safeSystem.delete(null, null);
                    } else if (parts.length == 2) {
                        safeSystem.delete(parts[1], null);
                    } else if (parts.length == 3) {
                        safeSystem.delete(parts[1], parts[2]);
                    } else {
                        System.out.println("Invalid arguments for delete command.");
                    }
                    break;
                case "update":
                    if (parts.length == 4) {
                        safeSystem.update(parts[1], parts[2], parts[3]);
                    } else {
                        System.out.println("Invalid arguments for update command.");
                    }
                    break;
                case "count":
                    if (parts.length == 1) {
                        safeSystem.count(null);
                    } else if (parts.length == 2) {
                        safeSystem.count(parts[1]);
                    } else {
                        System.out.println("Invalid arguments for count command.");
                    }
                    break;
                case "exit":
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid command. Type 'help' for available commands.");
                    break;
            }
        } while (!command.equalsIgnoreCase("exit"));

        scanner.close();
    }
    
}
