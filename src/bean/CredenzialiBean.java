package bean;

public class CredenzialiBean {
    //classe che gestisce le credenziali per il login
    private String email;
    private String password;

    public CredenzialiBean(String mail, String password){
        this.email = mail;
        this.password = password;
    }

    public CredenzialiBean(String mail){
        this.email = mail;
        this.password = null;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }
}