package model;

public class IstruttoreModel extends UtenteLoggatoModel {
    private CredenzialiModel credenziali;
    private String certificate;
    private int age;

    public IstruttoreModel() {}

    public IstruttoreModel(CredenzialiModel credenziali, String nome, String cognome, boolean isIstruttore,
                           String certificate,  int age) {
        // Chiamata al costruttore della classe base
        super(credenziali, nome, cognome, isIstruttore);
        this.certificate = certificate;
        this.age = age;
    }

    @Override
    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }
}