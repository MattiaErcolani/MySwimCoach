package bean;

public class UtenteLoggatoBean {
    private CredenzialiBean credenzialiBean;
    private String nome;
    private String cognome;
    protected boolean isIstructor;
    private int age;
    private String level;
    private String certificate;

    public UtenteLoggatoBean(CredenzialiBean credenzialiBean, String nome, String cognome, boolean ruolo) {
        this.credenzialiBean = credenzialiBean;
        this.nome = nome;
        this.cognome = cognome;
        this.isIstructor = ruolo;
    }

    public UtenteLoggatoBean(CredenzialiBean credenzialiBean, String nome, String cognome) {
        this.credenzialiBean = credenzialiBean;
        this.nome = nome;
        this.cognome = cognome;
    }

    public CredenzialiBean getCredenziali() { return credenzialiBean; }
    public void setCredenziali(CredenzialiBean credenzialiBean) { this.credenzialiBean = credenzialiBean; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public void setRuolo(boolean role) { this.isIstructor = role; }
    public boolean getRuolo() { return this.isIstructor; }
    public boolean isIstructor() { return this.isIstructor; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public String getCertificate() { return certificate; }
    public void setCertificate(String certificate) { this.certificate = certificate; }
}