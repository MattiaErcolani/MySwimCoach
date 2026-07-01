package model;

public class UtenteLoggatoModel {
    private CredenzialiModel credenziali;
    private String nome;
    private String cognome;
    protected boolean isIstructor;
    private int age;
    private String level;
    private String certificate;

    public UtenteLoggatoModel() {}

    public UtenteLoggatoModel(CredenzialiModel credenziali, String nome, String cognome, boolean isIstructor) {
        this.credenziali = credenziali;
        this.nome = nome;
        this.cognome = cognome;
        this.isIstructor = isIstructor;
    }

    public CredenzialiModel getCredenziali() {
        return credenziali;
    }

    public void setCredenziali(CredenzialiModel credenziali) {
        this.credenziali = credenziali;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public boolean getIsIstructor() {
        return isIstructor;
    }

    public void setIstructor(boolean isIstructor) {
        this.isIstructor = isIstructor;
    }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getCertificate() { return certificate; }
    public void setCertificate(String certificate) { this.certificate = certificate; }
}