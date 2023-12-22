package upm.es;

import java.util.ArrayList;
import java.util.List;

public class Website {
    private String url;
    private List<Login> logins;

    // Constructor
    public Website(String url) {
        this.url = url;
        this.logins = new ArrayList<>();
    }

    public String getUrl() {
        return url;
    }

    public Boolean passwordExists(Login newLogin) {
        for (Login login : logins) {
            if(login.getPassword() == newLogin.getPassword()) {
                String message = String.format("Password Exists: Login password %s has been found.", newLogin.getPassword());
                System.out.println(message);
                return true;
            }
        }

        String message = String.format("Password doesnt exist: Login password %s hasn't been found.", newLogin.getPassword());
        System.out.println(message);
        return false;
    }

    public Boolean passwordValidSecurityStrength(Login newLogin) {
        PasswordStrengthLevel passwordLevel = getPasswordSecurityStrength(newLogin.getPassword());

        if(passwordLevel == PasswordStrengthLevel.REALLY_WEAK) {
            String message = String.format("Password %s doesnt have the minimum strength requirement.", newLogin.getPassword());
            Logger.error(message);
        }

        return passwordLevel != PasswordStrengthLevel.REALLY_WEAK;
    }

    private PasswordStrengthLevel getPasswordSecurityStrength(String password) {
        if (password != null) {
            Integer value = PasswordStrength.getInstance().checkPassword(password);
            PasswordStrengthLevel passwordLevel = PasswordStrengthLevel.fromValue(value);
            return passwordLevel;
        } else {
            String message = String.format("Password error: Password is empty");
            Logger.error(message);
            return null;
        }
    }

    public Login getLogin(Login login) {
        Login exists = this.logins.stream()
                .filter(currentLogin -> currentLogin.getUsername() == login.getUsername())
                .findFirst()
                .orElse(null);

        if(exists != null) {
            String message = String.format("Get: Username %s has been found.", login.getUsername());
            System.out.println(message);
            return login;
        } else {
            String message = String.format("Get: Username %s haven't been found.", login.getUsername());
            System.out.println(message);
            return null;
        }
    }

    public Boolean addLogin(Login login) {
        if(!this.logins.contains(login)) {
            this.logins.add(login);
            String message = String.format("Add: Username %s has been added.", login.getUsername());
            Logger.success(message);
            return true;
        } else {
            String message = String.format("Add: Username %s already exists. Cant be re-added.", login.getUsername());
            Logger.error(message);
            return false;
        }
    }

    public Boolean removeUsername(Login login) {
        if(getLogin(login) != null) {
            this.logins.remove(login);
            String message = String.format("Remove: Username %s has been removed.", login.getUsername());
            Logger.success(message);
            return true;
        } else {
            String message = String.format("Remove: Username %s doesn't exist.", login.getUsername());
            Logger.error(message);
            return false;
        }
    }

    public Integer countUsernames() {
        Integer count = this.logins.size();
        String message = String.format("Count: Website %s have a total of %d usernames", url, count);
        Logger.success(message);
        return count;
    }

    public void displayUsernames() {
        if(this.logins.size() != 0) {
            for (Login login : logins) {
                displayUsername(login);
            }
        } else {
            String message = String.format("Display: Website %s doesn't have usernames to show", url);
            Logger.error(message);
        }
    }

    public void displayUsername(Login login) {
        if(this.logins.size() != 0) {
            if(this.logins.contains(login)) {
                PasswordStrengthLevel strengthLevel = getPasswordSecurityStrength(login.getPassword());

                String message;
                if(strengthLevel != null) {
                    message = String.format("<%s> - login : <%s> pwd : <%s> (%s)", url, login.getUsername(), login.getPassword(), strengthLevel.toString());
                    Logger.success(message);
                } else {
                    message = String.format("Display: Error displaying login for %s website", url);
                    Logger.error(message);
                }
            }
        } else {
            String message = String.format("Display: Website %s doesn't have usernames to show", url);
            Logger.error(message);
        }
    }
}