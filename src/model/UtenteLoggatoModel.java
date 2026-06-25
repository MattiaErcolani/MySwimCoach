package model;

public class UtenteLoggatoModel {
    private CredenzialiModel credenziali;
    private String Nome;
    private String Cognome;
    protected boolean isIstructor;
    private int age;
    private String level;
    private String certificate;

    public UtenteLoggatoModel() {};
    public UtenteLoggatoModel(CredenzialiModel credenziali, String Nome, String Cognome, boolean isIstructor){
        this.credenziali = credenziali;
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.isIstructor = isIstructor;
    }
    public CredenzialiModel getCredenziali(){
        return credenziali;
    }
    public void setCredenziali(CredenzialiModel credenziali) {
        this.credenziali = credenziali;
    }
    public String getNome(){
        return Nome;
    }
    public void setNome(String Nome) {
        this.Nome = Nome;
    }
    public String getCognome(){
        return Cognome;
    }
    public void setCognome(String Cognome) {
        this.Cognome = Cognome;
    }
    public boolean getisIstructor(){
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