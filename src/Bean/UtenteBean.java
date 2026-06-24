package Bean;

public class UtenteBean extends UtenteLoggatoBean {
    private int age;
    private String info;
    private String level;

    public UtenteBean(CredenzialiBean credenzialiBean, String Nome, String Cognome, boolean ruolo,int age,String info,String level){
        super(credenzialiBean, Nome, Cognome, ruolo);
        this.age = age;
        this.info = info;
        this.level=level;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age){
        this.age =age;
    }
    public String getInfo(){
        return info;
    }
    public void setInfo(String info){
        this.info=info;
    }
    public String getLevel(){
        return level;
    }
    public void setLevel(String level){
        this.level=level;
    }
}
