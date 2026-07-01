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


    public void setEmail(String Email) {

        this.email = Email;
    }
    public void setPassword(String Password){
        this.password = Password;
    }

    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
}
