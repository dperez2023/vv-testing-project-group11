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
                return true; //At first match, break the for loop
            }
        }

        String message = String.format("Password doesnt exist: Login password %s hasnt been found.", newLogin.getPassword());
        System.out.println(message);
        return false;
    }

    public Boolean passwordValidSecurityStrength(Login newLogin) {
        PasswordStrengthLevel passwordLevel = getPasswordSecurityStrength(newLogin.getPassword());

        if(passwordLevel == PasswordStrengthLevel.REALLY_WEAK) {
            String message = String.format("Password %s doesnt have the minimum strength requirement.", newLogin.getPassword());
            System.out.println(message);
        }

        return passwordLevel != PasswordStrengthLevel.REALLY_WEAK;
    }

    private PasswordStrengthLevel getPasswordSecurityStrength(String password) {
        Integer value = PasswordStrength.getInstance().checkPassword(password);
        PasswordStrengthLevel passwordLevel = PasswordStrengthLevel.fromValue(value);
        return passwordLevel;
    }

    public Login getLogin(Login login) {
        if(this.logins.contains(login)) {
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
            System.out.println(message);
            return true;
        } else {
            String message = String.format("Add: Username %s exists. Cant be added.", login.getUsername());
            System.out.println(message);
            return false;
        }
    }

    public Boolean removeUsername(Login login) {
        if(getLogin(login) != null) {
            this.logins.remove(login);
            String message = String.format("Remove: Username %s has been removed.", login.getUsername());
            System.out.println(message);
            return true;
        } else {
            String message = String.format("Remove: Username %s doesn't exist.", login.getUsername());
            System.out.println(message);
            return false;
        }
    }

    public Integer countUsernames() {
        Integer count = this.logins.size();
        String message = String.format("Count: Website %s have a total of %d usernames", url, count);
        System.out.println(message);
        return count;
    }

    public void displayUsernames() {
        if(this.logins.size() != 0) {
            for (Login login : logins) {
                displayUsername(login);
            }
        } else {
            String message = String.format("Display: Website %s doesn't have usernames to show", url);
            System.out.println(message);
        }
    }

    public void displayUsername(Login login) {
        if(this.logins.size() != 0) {
            if(this.logins.contains(login)) {
                String passwordSecurityStrength = getPasswordSecurityStrength(login.getPassword()).getStringValue();
                String message = String.format("<%s> - login : <%s> pwd : <%s> (%s)", url, login.getUsername(), login.getPassword(), passwordSecurityStrength);
                System.out.println(message);
            }
        } else {
            String message = String.format("Display: Website %s doesn't have usernames to show", url);
            System.out.println(message);
        }
    }
}