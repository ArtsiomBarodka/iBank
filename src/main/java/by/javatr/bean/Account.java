package by.javatr.ibank.bean;

import by.javatr.ibank.Constants;

/**
 * @author Barodka Artsiom
 * @see https://github.com/ArtsiomBarodka
 */
public class Account extends AbstractBean {
    private static final long serialVersionUID = 7089990360592568147L;

    private String login;
    private int password;
    private String email;
    private Constants.Role role;

    public Account() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Constants.Role getRole() {
        return role;
    }

    public void setRole(Constants.Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" +
                "id=" + getId() +
                "login='" + login + '\'' +
                ", password=" + password +
                ", email='" + email + '\'' +
                ", role=" + role;
    }
}
