package bean;

public class IstruttoreBean extends UtenteLoggatoBean {
    // Campi privati come richiesto
    private String certificate;
    private int age;
    private String note;

    public IstruttoreBean(CredenzialiBean credenzialiBean, String nome, String cognome, boolean ruolo,
                          String certificate, int age, String note) {

        // Chiamata al costruttore della superclasse (Utenteloggatobean)
        super(credenzialiBean, nome, cognome, ruolo);
        this.certificate = certificate;
        this.age = age;
        this.note = note;
    }

    // --- GETTER e SETTER ---

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}