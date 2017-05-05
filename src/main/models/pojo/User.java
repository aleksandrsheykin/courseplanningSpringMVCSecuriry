package main.models.pojo;

/**
 * Created by admin on 19.04.2017.
 */
public class User {
    private Integer idUser;
    private String firstName;
    private String lastName;
    private String mail;
    private String password;
    private int limit;
    private boolean isAdmin;
    private boolean isBlocked;

    public User(Integer idUser, String firstName, String lastName, String mail, String password, int limit, boolean isAdmin, boolean isBlocked) {
        this.idUser = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
        this.limit = limit;
        this.isAdmin = isAdmin;
        this.isBlocked = isBlocked;
    }

    public boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
