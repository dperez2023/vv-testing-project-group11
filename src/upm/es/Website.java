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

    public Boolean isValid() {
        return url != null;
    }

    public String getUrl() {
        return url;
    }

    public Boolean passwordExists(Login newLogin) {
        for (Login login : this.logins) {
            if(login.getPassword().contains(newLogin.getPassword())) {
                String message = String.format("Password Exists: Login password %s", newLogin.getPassword());
                System.out.println(message);
                return true;
            }
        }

        String message = String.format("Password doesn't exist: Login password %s hasn't been found.", newLogin.getPassword());
        //System.out.println(message);
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
                .filter(currentLogin -> currentLogin.getUsername().equals(login.getUsername()))
                .findFirst()
                .orElse(null);

        if(exists != null) {
            String message = String.format("Get: Username %s has been found.", login.getUsername());
            //System.out.println(message);
            return exists;
        } else {
            String message = String.format("Get: Username %s haven't been found.", login.getUsername());
            //System.out.println(message);
            return null;
        }
    }

    public void addLogin(Login login) {
        if(getLogin(login) == null) {
            this.logins.add(login);
            String message = String.format("Add: Username %s has been added.", login.getUsername());
            Logger.success(message);
        }
    }

    public Boolean updateLogin(Login login) {
        Login existingLogin = getLogin(login);
        if(existingLogin != null) {
            existingLogin.update(login);
            String message = String.format("Update: Username %s has been updated.", login.getUsername());
            Logger.success(message);
            return false;
        } else {
            String message = String.format("Update: Username %s doesn't exist, can't be updated", login.getUsername());
            Logger.error(message);
            return false;
        }
    }

    public Boolean removeUsername(Login login) {
        if(getLogin(login) != null) {
            this.logins.remove(getLogin(login));
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
        for (Login login : logins) {
            displayUsername(login);
        }
    }

    public void displayUsername(Login login) {
        if(getLogin(login) != null) {
            PasswordStrengthLevel strengthLevel = getPasswordSecurityStrength(login.getPassword());

            String message;
            if(strengthLevel != null) {
                message = String.format("<%s> - login: <%s> pwd: <%s> (%s)", url, login.getUsername(), login.getPassword(), strengthLevel.toString());
                Logger.success(message);
            } else {
                message = String.format("Display: Error displaying login for %s website", url);
                Logger.error(message);
            }
        }
    }
}