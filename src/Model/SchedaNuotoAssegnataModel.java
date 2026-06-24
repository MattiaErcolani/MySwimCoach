package Model;

public class SchedaNuotoAssegnataModel {
    private String idScheda;
    private String emailUser;
    private int distanzaTotale;
    private int durata;

    public SchedaNuotoAssegnataModel(String idScheda, String emailUser, int distanzaTotale, int durata) {
        this.idScheda = idScheda;
        this.emailUser = emailUser;
        this.distanzaTotale = distanzaTotale;
        this.durata = durata;
    }

    public String getIdScheda() { return idScheda; }
    public void setIdScheda(String idScheda) { this.idScheda = idScheda; }

    public String getEmailUser() { return emailUser; }
    public void setEmailUser(String emailUser) { this.emailUser = emailUser; }

    public int getDistanzaTotale() { return distanzaTotale; }
    public void setDistanzaTotale(int distanzaTotale) { this.distanzaTotale = distanzaTotale; }

    public int getDurata() { return durata; }
    public void setDurata(int durata) { this.durata = durata; }
}
