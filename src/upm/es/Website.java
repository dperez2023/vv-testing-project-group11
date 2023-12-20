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
                String message = String.format("Password Exists: Login's password '%s' has been found.", newLogin.getPassword());
                System.out.println(message);
                return true; //At first match, break the for loop
            }
        }

        String message = String.format("Password doesn't exist: Login's password '%s' hasn't been found.", newLogin.getPassword());
        System.out.println(message);
        return false;
    }

    public Boolean passwordValidSecurityStrength(Login newLogin) {
        Integer value = PasswordStrength.getInstance().checkPassword(newLogin.getPassword());

        PasswordStrengthLevel passwordLevel = PasswordStrengthLevel.fromValue(value);

        if(passwordLevel == PasswordStrengthLevel.REALLY_WEAK) {
            String message = String.format("Password '%s' doesn't have the minimum strength requirement.", newLogin.getPassword());
            System.out.println(message);
        }

        return passwordLevel != PasswordStrengthLevel.REALLY_WEAK;
    }

    public Login getLogin(Login login) {
        if(this.logins.contains(login)) {
            String message = String.format("Get: Username '%s' has been found.", login.getUsername());
            System.out.println(message);
            return login;
        } else {
            String message = String.format("Get: Username '%s' haven't been found.", login.getUsername());
            System.out.println(message);
            return null;
        }
    }

    public Boolean addLogin(Login login) {
        if(!this.logins.contains(login)) {
            this.logins.add(login);
            String message = String.format("Add: Username '%s' has been added.", login.getUsername());
            System.out.println(message);
            return true;
        } else {
            String message = String.format("Add: Username '%s' exists. Can't be added.", login.getUsername());
            System.out.println(message);
            return false;
        }
    }

    public Boolean removeLogin(Login login) {
        if(getLogin(login) != null) {
            this.logins.remove(login);
            String message = String.format("Remove: Username '%s' has been removed.", login.getUsername());
            System.out.println(message);
            return true;
        } else {
            String message = String.format("Remove: Username '%s' doesn't exist.", login.getUsername());
            System.out.println(message);
            return false;
        }
    }

    public Integer countUsernames() {
        Integer count = this.logins.size();
        String message = String.format("Count: Website '%s' have a total of %d usernames", url, count);
        System.out.println(message);
        return count;
    }
}