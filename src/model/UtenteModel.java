package model;

public class UtenteModel extends UtenteLoggatoModel {
    private int age;
    private String info;
    private String level;

    public UtenteModel(CredenzialiModel credenziali, String nome, String cognome, boolean isIstructor, int age, String info, String level) {
        super(credenziali, nome, cognome, isIstructor);
        this.age = age;
        this.info = info;
        this.level = level;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}