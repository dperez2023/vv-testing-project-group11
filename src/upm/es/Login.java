package upm.es;

public class Login {
    private String username;
    private String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Boolean isValid() {
        return username != null && password != null;
    }

    public String getUsername() {
        return username;
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        //TODO: Secured in any way?
        return password;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
